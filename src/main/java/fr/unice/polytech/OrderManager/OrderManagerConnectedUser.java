package fr.unice.polytech.OrderManager;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.GroupOrder;
import fr.unice.polytech.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderManagerConnectedUser {

    public UUID placeOrder(String email, Order order, Locations delivery_location, LocalDateTime chosenSlot);
    public UUID placeOrder(String email, Order order, Locations delivery_location);
    public List<Order> getCurrentOrders(UUID order_id, String user_email);
    public List<Order> getCurrentUserOrders(String user_email);
    public GroupOrder getCurrentOrders(UUID order_id);
    public void payOrders(String email, String card_number);
    public void validateOrderReceipt(UUID order_id);
    public boolean cancelOrder(UUID orderId, String email);
}
