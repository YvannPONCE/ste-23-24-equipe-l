package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class OrderManager  implements CapacityObserver{

    PaymentSystem paymentSystem = new PaymentSystem();
    RestaurantManager restaurantManager;
    DeliveryManager deliveryManager;
//    List<Restaurant> restaurantList;
    List<GroupOrder> group_orders;
    private RestaurantCapacityCalculator capacityCalculator;


    public UserManager userManager;
OrderAmountCalculator orderAmountCalculator;



    private LocalDateTime nextSlot;

    public OrderManager(RestaurantManager restaurantManager, UserManager userManager) {
        this.group_orders = new ArrayList<>();
        this.restaurantManager = restaurantManager;
//        this.restaurantList = restaurantManager.get_restaurants();
        this.userManager = userManager;

    }
    public boolean place_order(String email, Order order, Locations delivery_location, UUID order_id) {
        order.setId(order_id);
        order.setStatus(Status.CREATED);
        List<GroupOrder> filtered_group_orders = group_orders.stream().filter(current_group_order -> current_group_order.get_uuid().equals(order_id))
                .collect(Collectors.toList());
        if (filtered_group_orders.size() > 0) {
            GroupOrder group_order = filtered_group_orders.get(0);
            if (group_order.get_delivery_location() != delivery_location) return false;
            group_order.add_order(email, order);
            return true;
        } else {
            GroupOrder group_order = new GroupOrder(order_id, delivery_location);
            group_order.add_order(email, order);
            this.group_orders.add(group_order);
            return true;
        }
    }

    public UUID place_order(String email, Order order, Locations delivery_location) {
        UUID uuid = UUID.randomUUID();
        Restaurant restaurant=restaurantManager.get_restaurant(order.restaurant_name);
        capacityCalculator=new RestaurantCapacityCalculator(restaurant);
        OrderObserver orderObserver = new OrderObserver(capacityCalculator);

        if (capacityCalculator.canPlaceOrder(order.get_menus().size())) {
            System.out.println("1010");
            capacityCalculator.placeOrder(order.get_menus().size());
            place_order(email, order, delivery_location, uuid);
            this.capacityCalculator.addObserver(this);


          return uuid;
        } else {
            System.out.println("1110");
            nextSlot=capacityCalculator.getNextSlot();
            return null;
        }
    }
    public LocalDateTime getNextSlot() {
        return nextSlot;
    }

    public List<Order> get_current_orders(UUID order_id, String user_email) {
        List<GroupOrder> group_orders = this.group_orders.stream()
                .filter(group_order -> group_order.get_uuid().equals(order_id))
                .collect(Collectors.toList());
        if (group_orders.size() > 0) {
            GroupOrder group_order = group_orders.get(0);
            return group_order.get_orders(user_email);
        }
        return new ArrayList<>();
    }

    public List<Order> get_current_user_orders(String user_email){
        List<GroupOrder> group_orders = new ArrayList<>(this.group_orders);
        if (!group_orders.isEmpty()) {
            List<Order> response = new ArrayList<>();
            for (GroupOrder groupOrder : group_orders) {
                response.addAll(groupOrder.get_orders(user_email));
            }
            return response;
        }
        return new ArrayList<>();
    }

    public GroupOrder get_current_orders(UUID order_id) {
        List<GroupOrder> group_orders = this.group_orders.stream()
                .filter(group_order -> group_order.get_uuid().equals(order_id))
                .collect(Collectors.toList());
        if (group_orders.size() > 0) {
            return group_orders.get(0);
        }
        return null;
    }


    public void pay_order(UUID orderId, String email, String card_number) {
        GroupOrder groupOrder = get_current_orders(orderId);
        this.orderAmountCalculator= new OrderAmountCalculator(groupOrder,this.userManager);
        orderAmountCalculator.applyMenuDiscount(15);
        if(paymentSystem.pay(card_number))
        {
            groupOrder.setPaid(email);
        }
        if (groupOrder.isPaid()) sendOrders(groupOrder);
    }

    public void pay_user_orders(String email, String card_number){
        List<Order> orders = get_current_user_orders(email);
        for (Order order : orders) {
                this.pay_order(order.getId(), email, card_number);
        }
    }

    private void sendOrders(GroupOrder groupOrder) {
        HashMap<String, List<Order>> restaurantOrders = groupOrder.getOrdersByRestaurants();
        for (Map.Entry<String, List<Order>> entry : restaurantOrders.entrySet()) {
            Restaurant restaurant = this.restaurantManager.get_restaurant(entry.getKey());
            restaurant.placeOrder(entry.getValue());
        }
    }

    public void validate_order(UUID order_id, String restaurant_name) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.group_orders.stream()
                .filter(groupOrder1 -> groupOrder1.get_uuid() == order_id)
                .collect(Collectors.toList());
        if(groupOrders.size()>0) {
            groupOrder = groupOrders.get(0);
            groupOrder.validate_order(restaurant_name);

            if (groupOrder.isReady()) {
                deliveryManager.addOrder(order_id);
            }
        }
    }

    public void validate_order_receipt(UUID order_id) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.group_orders.stream()
                .filter(groupOrder1 -> groupOrder1.get_uuid() == order_id)
                .collect(Collectors.toList());

        OrderObserver orderObserver = new OrderObserver(capacityCalculator);
        if(groupOrders.size()>0)
        {

            groupOrder = groupOrders.get(0);
            groupOrder.validate_order_receipt();
            for(String email : groupOrder.getGlobal_orders().keySet())
            {
                userManager.addOrdersToHistory(email, groupOrder.get_orders(email));
                List<Order> orders=get_current_user_orders(email);
                for(Order order1:orders){
                    Restaurant restaurant=restaurantManager.get_restaurant(order1.restaurant_name);
                    capacityCalculator=new RestaurantCapacityCalculator(restaurant);
                    capacityCalculator.resetCapacityafterDelivery(order1.get_menus().size());



                }


            }

        }
    }

    public void setOrderAsClosed(UUID order_id) {
        GroupOrder groupOrder;
        List<GroupOrder> groupOrders = this.group_orders.stream()
                .filter(groupOrder1 -> groupOrder1.get_uuid() == order_id)
                .collect(Collectors.toList());
        if(groupOrders.size()>0)
        {
            groupOrder = groupOrders.get(0);
            groupOrder.setClose();
        }
    }

    public Order reorderFromHistory(UUID selectedOrderId, String userMail, Locations deliveryLocation) {
        Order selectedOrder = userManager.find_selectedOrder(selectedOrderId, userMail);

        if (selectedOrder != null) {
            UUID newOrderId = UUID.randomUUID();
            Order newOrder = new Order(selectedOrder.restaurant_name);
            for(Menu menu:selectedOrder.menus){
                newOrder.add_menu(menu);
            }
            place_order(userMail, newOrder, deliveryLocation, newOrderId);

            return newOrder;
        } else {
            System.out.println("Commande sélectionnée introuvable dans l'historique de l'utilisateur.");
            return null;
        }


    }

    public void addDeliveryManager(DeliveryManager deliveryManager) {
        this.deliveryManager = deliveryManager;
    }




    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RestaurantCapacityCalculator) {
            RestaurantCapacityCalculator restaurantCapacityCalculator = (RestaurantCapacityCalculator) o;
            int newCapacity = restaurantCapacityCalculator.getCapacity();
            System.out.println("Capacity changed. New capacity: " + newCapacity);
        }
    }

    @Override
    public void updateCapacity(int newCapacity) {


        System.out.println("Capacity changed. New capacity: " + newCapacity);
        // Ajoutez ici la logique spécifique que vous souhaitez exécuter en réponse au changement de capacité
    }
}
