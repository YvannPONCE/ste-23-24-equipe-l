package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.rmi.server.UID;
import java.util.*;
import java.util.stream.Collectors;


public class OrderManager {

    PaymentSystem paymentSystem;
    RestaurantManager restaurantManager;
    List<GroupOrder> group_orders;



     public UserManager userManager;

    public OrderManager(RestaurantManager restaurantManager) {
        this.group_orders = new ArrayList<>();
        this.restaurantManager = restaurantManager;
        this.userManager=new UserManager();
    }

    public boolean place_order(String email, Order order, Locations delivery_location, UUID order_id) {
        order.setId(UUID.randomUUID());
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
        if (group_orders.size() > 0) {
            GroupOrder group_order = group_orders.get(0);
            return group_order.get_orders(user_email);
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


    public void pay_order(UUID orderId, String email, String card_number){
        GroupOrder groupOrder = get_current_orders(orderId);
        List<Order> orders= groupOrder.get_orders(email);

        for(Order order : orders)
        {
            if (paymentSystem.pay(card_number)) {
                order.setStatus(Status.PAID);
            }
        }
        if(groupOrder.isPaid())sendOrders(groupOrder);
    }

    private void sendOrders(GroupOrder groupOrder){
        HashMap<String, List<Order>> restaurantOrders = groupOrder.getOrdersByRestaurants();
        for(Map.Entry<String, List<Order>> entry: restaurantOrders.entrySet())
        {
            Restaurant restaurant = this.restaurantManager.get_restaurant(entry.getKey());
            restaurant.placeOrder(entry.getValue());
        }
    }

    public void validate_order(UUID order_id, String restaurant_name){
        for(GroupOrder groupOrder : this.group_orders)
        {
            for (List<Order> orders : groupOrder.global_orders.values())
            {
                List<Order> matchingOrders = orders.stream()
                        .filter(order -> order.getId().equals(order_id))
                        .collect(Collectors.toList());
                for (Order order : matchingOrders)order.setStatus(Status.READY);
            }
        }
    }
    public void validate_order_receipt(UUID order_id, String usermail) {
        for (GroupOrder groupOrder : this.group_orders) {
            for (List<Order> orders : groupOrder.global_orders.values()) {
                for (Order order : orders) {
                    if (order.getId().equals(order_id)) {
                        if (order.getStatus() == Status.DELIVERED) {

                            userManager.get_order_history(usermail).add(order);
                        }
                    }
                }
            }
        }
    }


}
