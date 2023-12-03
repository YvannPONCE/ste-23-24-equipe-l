package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.state.OrderState;
import lombok.Getter;
import lombok.Setter;
import org.mockito.internal.matchers.Or;

import java.util.*;
import java.util.stream.Collectors;
@Getter
@Setter
public class GroupOrder {
    private OrderState orderState;
    UUID uuid;
    Locations delivery_location;
    public HashMap<String, List<Order>> globalOrders;

    public GroupOrder(UUID uuid, Locations delivery_location) {
        this.uuid = uuid;
        this.delivery_location = delivery_location;
        this.globalOrders = new HashMap<>();
        this.orderState = new OrderState();

    }

    public GroupOrder(GroupOrder groupOrder) {
        this.uuid = groupOrder.getUuid();
        this.delivery_location = groupOrder.getDeliveryLocation();
        this.globalOrders = new HashMap<>(groupOrder.globalOrders);
        this.orderState = groupOrder.getOrderState();
    }


    public Map<String, List<Order>> getGlobalOrders() {
        Map<String, List<Order>> orders = new HashMap<>();
        for(Map.Entry<String, List<Order>> entry : globalOrders.entrySet()){
            orders.put(entry.getKey(), Collections.unmodifiableList(globalOrders.get(entry.getKey())));
        }
        return  Collections.unmodifiableMap(orders);
    }

    public Locations getDeliveryLocation() {
        return delivery_location;
    }

    public boolean add_order(String user_email, Order order) {
        if (order.getMenus().size() < 1) {
            return false;
        }
        List<Order> user_orders = globalOrders.get(user_email);
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
            globalOrders.put(user_email, new ArrayList<>(Arrays.asList(order)));
            return true;
        }

    }

    public List<Order> get_orders(String user_email) {
        return this.globalOrders.get(user_email);
    }

    public void setPaid(String userEmail) {
        List<Order> orders = get_orders(userEmail);
        for (Order order : orders) {
            order.getOrderState().next();
        }

        for (List<Order> orders2 : this.globalOrders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PAID) return;
            }
        }
        orderState.next();

    }

    public HashMap<String, List<Menu>> getMenusByRestaurants() {
        HashMap<String, List<Menu>> menusByRestaurant = new HashMap<>();
        for (List<Order> orders : globalOrders.values()) {
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
        for (List<Order> orders : this.globalOrders.values()) {
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
        int totalItemCount = globalOrders.values()
                .stream()
                .flatMap(List::stream)
                .mapToInt(Order::getItemCount)
                .sum();

        return totalItemCount >= itemCountThreshold;
    }

    public void setOrderProcessing(String restaurantName) {

        for (List<Order> orders : globalOrders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());
            for (Order order : matchingOrders) {
                order.getOrderState().next();

            }
        }

        for (List<Order> orders2 : this.globalOrders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PROCESSING) return;
            }
        }
        orderState.next();
    }

    public void resetOrderProcessing(String restaurantName) {

        for (List<Order> orders : globalOrders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());
            for (Order order : matchingOrders) {

                order.getOrderState().setStatus(Status.PROCESSING);
                // order.getOrderState().next();

            }
        }

        for (List<Order> orders2 : this.globalOrders.values()) {
            for (Order order : orders2) {
                if (order.getOrderState().getStatus() != Status.PROCESSING) return;
            }
        }
        orderState.next();
    }

    public void setOrderReady(String restaurantName) {
        for (List<Order> orders : globalOrders.values()) {
            List<Order> matchingOrders = orders.stream()
                    .filter(order -> order.getRestaurant_name().equals(restaurantName))
                    .collect(Collectors.toList());

            for (Order order : matchingOrders) {
                order.getOrderState().next();
            }

        }
        for (List<Order> orders2 : this.globalOrders.values()) {
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
        orderState.next();
        List<Order> concatenatedOrders = globalOrders.values().stream()
                .flatMap(List::stream) // Stream each list and concatenate
                .collect(Collectors.toList());
        for (Order order : concatenatedOrders)order.getOrderState().next();
    }

    public void closeOrder() {
        orderState.setStatus(Status.CLOSED);
        List<Order> concatenatedOrders = globalOrders.values().stream()
                .flatMap(List::stream) // Stream each list and concatenate
                .collect(Collectors.toList());
        for (Order order : concatenatedOrders)order.getOrderState().setStatus(Status.CLOSED);
    }

    @Override
    public String toString() {
        return "GroupOrder{" +
                "globalOrders=" + globalOrders +
                '}';
    }
}


