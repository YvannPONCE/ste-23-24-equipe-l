package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.state.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Getter
@Setter
public class GroupOrder {
    private OrderState orderState;
    UUID uuid;
    Locations delivery_location;
    LocalDateTime deliveryTime;
    public HashMap<String, List<Order>> globalOrders;
    private int numberOfParticipants = 0; //acts as a counter for the number of participants in the group order or afterWork

    public GroupOrder(UUID uuid, Locations delivery_location, LocalDateTime deliveryTime) {
        this.uuid = uuid;
        this.delivery_location = delivery_location;
        this.globalOrders = new HashMap<>();
        this.orderState = new OrderState();
        this.deliveryTime = deliveryTime;

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

    public boolean addOrder(String user_email, Order order) {
        if (order.getMenus().size() < 1) {
            return false;
        }

        if(!globalOrders.containsKey(user_email)){
            this.numberOfParticipants++;
        }

        if(order.getMenus().get(0).getMenuType() == MenuType.AFTERWORK_MENU){
            order.getOrderState().setStatus(Status.PROCESSING);
            this.numberOfParticipants += order.getMenus().get(0).getNumberOfParticipants();
        }

        List<Order> user_orders = globalOrders.get(user_email);
        if (user_orders != null) {
            List<Order> orders = user_orders.stream().filter(filtered_order -> filtered_order.getRestaurantName().equals(order.getRestaurantName())).collect(Collectors.toList());
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

    public List<Order> getOrders(String user_email) {
        List<Order> orders = this.globalOrders.get(user_email);
        if (orders == null) return new ArrayList<>();
        return orders;
    }

    public void setPaid(String userEmail) {
        List<Order> orders = getOrders(userEmail);
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
                        .filter(entry -> entry.getKey().equals(order.getRestaurantName()))
                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
                if (orderByRestaurant.isEmpty()) {
                    menusByRestaurant.put(order.getRestaurantName(), order.getMenus());
                } else {
                    menusByRestaurant.get(order.getRestaurantName()).addAll(order.getMenus());
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
                if (restaurant_orders.containsKey(order.getRestaurantName())) {
                    List<Order> orders_2 = restaurant_orders.get(order.getRestaurantName());
                    orders_2.add(order);
                } else {
                    // Use new ArrayList<>(Arrays.asList(order)) to create a mutable list
                    restaurant_orders.put(order.getRestaurantName(), new ArrayList<>(Arrays.asList(order)));
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
                    .filter(order -> order.getRestaurantName().equals(restaurantName))
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
                    .filter(order -> order.getRestaurantName().equals(restaurantName))
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
                    .filter(order -> order.getRestaurantName().equals(restaurantName))
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


