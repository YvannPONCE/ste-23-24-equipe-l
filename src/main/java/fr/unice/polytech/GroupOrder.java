package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.state.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
@Getter
@Setter
public class GroupOrder {
    private OrderState orderState;
    UUID uuid;
    Locations delivery_location;
    public HashMap<String, List<Order>> global_orders;

    public GroupOrder(UUID uuid, Locations delivery_location) {
        this.uuid = uuid;
        this.delivery_location = delivery_location;
        this.global_orders = new HashMap<>();
        this.orderState = new OrderState();

    }


    public HashMap<String, List<Order>> getGlobal_orders() {
        return global_orders;
    }

    public Locations getDeliveryLocation() {
        return delivery_location;
    }

    public boolean add_order(String user_email, Order order) {
        if (order.getMenus().size() < 1) {
            return false;
        }
        List<Order> user_orders = global_orders.get(user_email);
        if (user_orders != null) {
            List<Order> orders = user_orders.stream().filter(filtered_order -> filtered_order.getRestaurant_name().equals(order.getRestaurant_name())).collect(Collectors.toList());
            if (orders.size() > 0) {
                Order current_order = orders.get(0);
                current_order.add_menu(order.getMenus().get(0));
                return true;
            } else {
                user_orders.add(order);
                return true;
            }
        } else {
            global_orders.put(user_email, new ArrayList<>(Arrays.asList(order)));
            return true;
        }

    }

    public List<Order> get_orders(String user_email) {
        return this.global_orders.get(user_email);
    }

    public void setPaid(String userEmail) {
        List<Order> orders = get_orders(userEmail);
        for (Order order : orders) {
            order.getOrderState().next();
        }

        for (List<Order> orders2 : this.global_orders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PAID) return;
            }
        }
        orderState.next();

    }

    public HashMap<String, List<Menu>> getMenusByRestaurants() {
        HashMap<String, List<Menu>> menusByRestaurant = new HashMap<>();
        for (List<Order> orders : global_orders.values()) {
            for (Order order : orders) {
                Map<String, List<Menu>> orderByRestaurant = menusByRestaurant.entrySet().stream()
                        .filter(entry -> entry.getKey().equals(order.getRestaurant_name()))
                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
                if (orderByRestaurant.isEmpty()) {
                    menusByRestaurant.put(order.getRestaurant_name(), order.getMenus());
                } else {
                    menusByRestaurant.get(order.getRestaurant_name()).addAll(order.getMenus());
                }
            }
        }
        return menusByRestaurant;
    }


    public boolean isPaid() {
        return orderState.getStatus() == Status.PAID;
    }

    public HashMap<String, List<Order>> getOrdersByRestaurants() {
        HashMap<String, List<Order>> restaurant_orders = new HashMap<>();
        for (List<Order> orders : this.global_orders.values()) {
            for (Order order : orders) {
                if (restaurant_orders.containsKey(order.getRestaurant_name())) {
                    List<Order> orders_2 = restaurant_orders.get(order.getRestaurant_name());
                    orders_2.add(order);
                } else {
                    // Use new ArrayList<>(Arrays.asList(order)) to create a mutable list
                    restaurant_orders.put(order.getRestaurant_name(), new ArrayList<>(Arrays.asList(order)));
                }
            }
        }
        return restaurant_orders;
    }


    public boolean qualifiesForMenuDiscount(int itemCountThreshold) {
        int totalItemCount = global_orders.values()
                .stream()
                .flatMap(List::stream)
                .mapToInt(Order::getItemCount)
                .sum();

        return totalItemCount >= itemCountThreshold;
    }

    public void setOrderProcessing(String restaurantName) {

        for (List<Order> orders : global_orders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());
            for (Order order : matchingOrders) {
                order.getOrderState().next();

            }
        }

        for (List<Order> orders2 : this.global_orders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PROCESSING) return;
            }
        }
        orderState.next();
    }

    public void resetOrderProcessing(String restaurantName) {

        for (List<Order> orders : global_orders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());
            for (Order order : matchingOrders) {

                order.getOrderState().setStatus(Status.PROCESSING);
                // order.getOrderState().next();

            }
        }

        for (List<Order> orders2 : this.global_orders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PROCESSING) return;
            }
        }
        orderState.next();
    }

    public void validate_order(String restaurantName) {

        for (List<Order> orders : global_orders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());

            for (Order order : matchingOrders) {
                order.getOrderState().next();
            }

        }


        for (List<Order> orders2 : this.global_orders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.READY) return;
            }


        }
        orderState.next();

    }

        public boolean isReady () {
            return orderState.getStatus() == Status.READY;
        }

    public void validateOrderReceipt() {
        System.out.println("Next");
        orderState.next();

    }
}


