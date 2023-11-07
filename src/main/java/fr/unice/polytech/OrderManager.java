package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.rmi.server.UID;
import java.util.*;
import java.util.stream.Collectors;


public class OrderManager {

    PaymentSystem paymentSystem = new PaymentSystem();
    RestaurantManager restaurantManager;
//    List<Restaurant> restaurantList;
    List<GroupOrder> group_orders;


    public UserManager userManager;

    public OrderManager(RestaurantManager restaurantManager) {
        this.group_orders = new ArrayList<>();
        this.restaurantManager = restaurantManager;
//        this.restaurantList = restaurantManager.get_restaurants();
        this.userManager = new UserManager();
    }

    public boolean place_order(String email, Order order, Locations delivery_location, UUID order_id) {
        order.setId(order_id);
        System.out.println(email);
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
        place_order(email, order, delivery_location, uuid);
        return uuid;
    }

    public List<Order> get_current_orders(UUID order_id, String user_email) {
        List<GroupOrder> group_orders = this.group_orders.stream()
                .filter(group_order -> group_order.get_uuid().equals(order_id))
                .collect(Collectors.toList());
        System.out.println(group_orders);
        if (group_orders.size() > 0) {
            GroupOrder group_order = group_orders.get(0);
            System.out.println(group_order.getGlobal_orders());
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
        List<Order> orders = groupOrder.get_orders(email);

        for (Order order : orders) {
            if (paymentSystem.pay(card_number)) {
                order.setStatus(Status.PAID);
            }
        }
        if (groupOrder.isPaid()) sendOrders(groupOrder);
    }

    public void pay_user_orders(String email, String card_number){
        List<Order> orders = get_current_user_orders(email);
        System.out.println(orders);
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
        for (GroupOrder groupOrder : this.group_orders) {
            for (List<Order> orders : groupOrder.global_orders.values()) {
                List<Order> matchingOrders = orders.stream()
                        .filter(order -> order.getId().equals(order_id))
                        .collect(Collectors.toList());
                for (Order order : matchingOrders) order.setStatus(Status.READY);
            }
        }
    }

    public void validate_order_receipt(String email, UUID order_id) {
        for (GroupOrder groupOrder : this.group_orders) {
            for (List<Order> orders : groupOrder.global_orders.values()) {
                for (Order order : orders) {
                    if (order.getId().equals(order_id)) {
                        order.setStatus(Status.DELIVERED);
                        userManager.get_order_history(email).add(order);
                    }
                }
            }
        }
    }

    public void setOrderAsClosed(UUID order_id) {
        for (GroupOrder groupOrder : this.group_orders) {
            for (List<Order> orders : groupOrder.global_orders.values()) {
                for (Order order : orders) {
                    if (order.getId().equals(order_id)) {
                        order.setStatus(Status.CLOSED);
                    }
                }
            }
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
}
