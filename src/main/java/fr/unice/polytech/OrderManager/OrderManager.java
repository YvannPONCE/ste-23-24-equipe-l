package fr.unice.polytech.OrderManager;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Restaurant.Restaurant;

import fr.unice.polytech.RestaurantManager.CapacityObserver;
import fr.unice.polytech.RestaurantManager.RestaurantCapacityCalculator;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.statisticsManager.StatisticManagerOrderManager;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class OrderManager  implements CapacityObserver, OrderManagerConnectedUser, OrderManagerDeliveryManager, OrderManagerStaff {

    private final StatisticManagerOrderManager statisticsManager;
    PaymentSystem paymentSystem = new PaymentSystem();
    RestaurantManager restaurantManager;
    DeliveryManager deliveryManager;
    NotificationCenter notificationCenter;
    List<GroupOrder> groupOrders;
    public UserManager userManager;
    OrderAmountCalculator orderAmountCalculator;
    private RestaurantCapacityCalculator capacityCalculator;
    @Getter
    private LocalDateTime nextSlot;



    String email;

    public OrderManager(RestaurantManager restaurantManager, UserManager userManager, StatisticManagerOrderManager statisticsManager, DeliveryManager deliveryManager, NotificationCenter notificationCenter) {
        this.groupOrders = new ArrayList<>();
        this.statisticsManager = statisticsManager;
        this.restaurantManager = restaurantManager;
        this.userManager = userManager;
        this.deliveryManager = deliveryManager;
        this.notificationCenter = notificationCenter;
    }
    public OrderManager(RestaurantManager restaurantManager, UserManager userManager, StatisticManagerOrderManager statisticsManager, NotificationCenter notificationCenter){
        this(restaurantManager, userManager, statisticsManager, null, notificationCenter);
    }

    public boolean placeOrder(String email, Order order, UUID orderID) {
        GroupOrder groupOrder = groupOrders.stream()
                .filter(groupOrder1 -> groupOrder1.getUuid().equals(orderID))
                .findFirst()
                .orElse(null);
        if (groupOrder == null)return false;
        placeOrder(email, order, groupOrder.getDeliveryLocation(),groupOrder.getDeliveryTime(), orderID);
        return true;
    }
    public UUID placeOrder(String email, Order order, Locations deliveryLocation) {
        LocalDateTime deliveryTime = LocalDateTime.now();
        return placeOrder(email, order, deliveryLocation, deliveryTime);
    }
    public UUID placeOrder(String email, Order order, Locations deliveryLocation, LocalDateTime chosenSlot) {
        UUID uuid = UUID.randomUUID();

        Restaurant restaurant = restaurantManager.getRestaurant(order.getRestaurant_name());
        capacityCalculator = new RestaurantCapacityCalculator(restaurant);
        if (capacityCalculator.canPlaceOrder(order.getMenus().size(), chosenSlot)) {
            capacityCalculator.placeOrderSlot(order.getMenus().size(), chosenSlot);
            placeOrder(email, order, deliveryLocation, chosenSlot, uuid);
            this.capacityCalculator.addObserver(this);
            return uuid;
        } else {
            nextSlot = capacityCalculator.getNextSlotChosen(chosenSlot);
            return null;
        }
    }
    private void placeOrder(String email, Order order, Locations deliveryLocation, LocalDateTime deliveryTime, UUID orderID)
    {
        order.setId(orderID);
        order.getOrderState().setStatus(Status.CREATED);
        GroupOrder groupOrder = groupOrders.stream()
                .filter(current_group_order -> current_group_order.getUuid().equals(orderID))
                .findFirst()
                .orElse(null);

        if (groupOrder != null) {
            groupOrder.addOrder(email,order);
        } else {
            groupOrder = new GroupOrder(orderID, deliveryLocation, deliveryTime);
            groupOrder.addOrder(email, order);
            this.groupOrders.add(groupOrder);
        }
        notificationCenter.order_confirmed(orderID, deliveryLocation, order.getCreation_time(), email);
    }

    public List<Order> getCurrentOrders(UUID order_id, String user_email) {
        List<GroupOrder> group_orders = this.groupOrders.stream()
                .filter(group_order -> group_order.getUuid().equals(order_id))
                .collect(Collectors.toList());
        if (!group_orders.isEmpty()) {
            GroupOrder group_order = group_orders.get(0);
            return Collections.unmodifiableList(group_order.get_orders(user_email));
        }
        return new ArrayList<>();
    }

    public List<Order> getCurrentUserOrders(String user_email){
         return groupOrders.stream()
                 .map(groupOrder -> groupOrder.globalOrders)
                 .filter(stringListHashMap -> stringListHashMap.get(user_email)!=null)
                 .flatMap(map -> map.values().stream())
                 .flatMap(List::stream)
                 .collect(Collectors.toList());
    }

    public List<Order> getCurrentOrders(String restaurantName) {
        List<Order> orders = new ArrayList<>();
        for(GroupOrder groupOrder : groupOrders){
            List<Order> orderList = groupOrder.getOrdersByRestaurants().getOrDefault(restaurantName, new ArrayList<>()).stream()
                    .filter(order -> order.getOrderState().getStatus().equals(Status.PAID) || order.getOrderState().getStatus().equals(Status.PROCESSING))
                    .collect(Collectors.toList());
            orders.addAll(orderList);

        }
        return Collections.unmodifiableList(orders);
    }

    public GroupOrder getCurrentOrders(UUID order_id) {
        List<GroupOrder> group_orders = this.groupOrders.stream()
                .filter(group_order -> group_order.getUuid().equals(order_id))
                .collect(Collectors.toList());
        if (!group_orders.isEmpty()) {
            return group_orders.get(0);
        }
        return null;
    }


    public void payOrder(UUID orderId, String email, String card_number) {
        GroupOrder groupOrder = getCurrentOrders(orderId);
        this.orderAmountCalculator= new OrderAmountCalculator(groupOrder,this.userManager);
        orderAmountCalculator.applyMenuDiscount(15);

        if(paymentSystem.pay(card_number))
        {
            groupOrder.setPaid(email);
        }
        if (groupOrder.isPaid())
        {
           statisticsManager.addOrder(groupOrder);
        }
    }

    public void payOrders(String email, String card_number){
        List<Order> orders = getCurrentUserOrders(email);
        for (Order order : orders) {
            if(order.getOrderState().getStatus()==Status.CREATED)
            {
                this.payOrder(order.getId(), email, card_number);
            }
        }
    }

    private void sendOrders(GroupOrder groupOrder) {
        HashMap<String, List<Order>> restaurantOrders = groupOrder.getOrdersByRestaurants();
        for (Map.Entry<String, List<Order>> entry : restaurantOrders.entrySet()) {
            Restaurant restaurant = this.restaurantManager.getRestaurant(entry.getKey());
            restaurant.placeOrder(entry.getValue());
        }
    }

    public void setOrderReady(UUID orderID, String restaurant_name) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.groupOrders.stream()
                .filter(groupOrder1 -> groupOrder1.getUuid() == orderID)
                .collect(Collectors.toList());
        if(!groupOrders.isEmpty()) {
            groupOrder = groupOrders.get(0);
            groupOrder.setOrderReady(restaurant_name);

            if (groupOrder.isReady()) {
                deliveryManager.addOrder(groupOrder);
            }
        }
    }

    public void validateOrderReceipt(UUID order_id) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.groupOrders.stream()
                .filter(groupOrder1 -> groupOrder1.getUuid() == order_id)
                .collect(Collectors.toList());

        OrderObserver orderObserver = new OrderObserver(capacityCalculator);
        if(!groupOrders.isEmpty())
        {

            groupOrder = groupOrders.get(0);

            groupOrder.validateOrderReceipt();
        }
    }

    public void setOrderAsClosed(UUID order_id) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.groupOrders.stream()
                .filter(groupOrder1 -> groupOrder1.getUuid() == order_id)
                .collect(Collectors.toList());
        if(!groupOrders.isEmpty())
        {
            groupOrder = groupOrders.get(0);
            groupOrder.getOrderState().next();
        }
    }

    public UUID reorderFromHistory(UUID selectedOrderId, String userMail, Locations deliveryLocation) {
        Order selectedOrder = userManager.find_selectedOrder(selectedOrderId, userMail);
        return placeOrder(userMail, selectedOrder, deliveryLocation);
    }

    public void addDeliveryManager(DeliveryManager newDeliveryManager) {
        this.deliveryManager = newDeliveryManager;
    }




    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RestaurantCapacityCalculator) {
            RestaurantCapacityCalculator restaurantCapacityCalculator = (RestaurantCapacityCalculator) o;
            int newCapacity = restaurantCapacityCalculator.getCapacity();
            //System.out.println("Capacity changed. New capacity: " + newCapacity);
        }
    }

    @Override
    public void updateCapacity(int newCapacity) {


        //System.out.println("Capacity changed. New capacity: " + newCapacity);
        // Ajoutez ici la logique spécifique que vous souhaitez exécuter en réponse au changement de capacité
    }

    public boolean cancelOrder(UUID orderId, String email) {
        List<Order> orders = getCurrentOrders(orderId, email);
        User user = userManager.get_user(email);
        for(Order order : orders){
            if(order.getOrderState().getStatus().ordinal()>Status.PROCESSING.ordinal()){
                return false;
            }
        }
        for(Order order : orders){
            List<Menu> menus = order.getMenus();
            for(Menu menu : menus){
                user.addCredit(menu.getPrice());
            }
            order.getOrderState().cancel();
        }
        return true;
    }

    public void reprocessingOrder(UUID orderId, String restaurantName) {
        GroupOrder groupOrder = getCurrentOrders(orderId);
        groupOrder.resetOrderProcessing(restaurantName);
    }
    public void processingOrder(UUID orderId, String restaurantName) {
        GroupOrder groupOrder = getCurrentOrders(orderId);
        groupOrder.setOrderProcessing(restaurantName);
    }
}
