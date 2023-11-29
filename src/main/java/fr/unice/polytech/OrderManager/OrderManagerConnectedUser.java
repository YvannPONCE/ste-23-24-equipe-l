package fr.unice.polytech.OrderManager;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.GroupOrder;
import fr.unice.polytech.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderManagerConnectedUser {

    public UUID place_order_slot(String email, Order order, Locations delivery_location, LocalDateTime chosenSlot);
    public UUID place_order(String email, Order order, Locations delivery_location);
    public List<Order> getCurrentOrders(UUID order_id, String user_email);
    public List<Order> get_current_user_orders(String user_email);
    public GroupOrder getCurrentOrders(UUID order_id);
    public void pay_order(UUID orderId, String email, String card_number);
    public void pay_user_orders(String email, String card_number);
    public void validate_order_receipt(UUID order_id);
    public boolean cancelOrder(UUID orderId, String email);
}
