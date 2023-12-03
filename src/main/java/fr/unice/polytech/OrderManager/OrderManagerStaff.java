package fr.unice.polytech.OrderManager;

import fr.unice.polytech.Order;

import java.util.List;

public interface OrderManagerStaff {
    public List<Order> getCurrentOrders(String restaurantName);
}
