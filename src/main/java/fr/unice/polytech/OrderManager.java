package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class OrderManager {

    List<GroupOrder> group_orders;

    public OrderManager(){
        this.group_orders = new ArrayList<>();
    }
    public boolean place_order(String email, Order order, Locations delivery_location, UUID order_id)
    {
        List<GroupOrder> filtered_group_orders = group_orders.stream().filter(current_group_order -> current_group_order.get_uuid().equals(order_id))
            .collect(Collectors.toList());
        if(filtered_group_orders.size()>0)
        {
            GroupOrder group_order = filtered_group_orders.get(0);
            if(group_order.get_delivery_location() != delivery_location)return false;
            group_order.add_order(email, order);
            return true;
        }
        else {
            GroupOrder group_order = new GroupOrder(order_id, delivery_location);
            group_order.add_order(email, order);
            this.group_orders.add(group_order);
            return true;
        }
    }
    public UUID place_order(String email, Order order, Locations delivery_location)
    {
        UUID uuid = UUID.randomUUID();
        place_order(email, order, delivery_location, uuid);
        return uuid;
    }

    public List<Order> get_current_orders(UUID order_id, String user_email){
        List<GroupOrder> group_orders = this.group_orders.stream()
                .filter(group_order -> group_order.get_uuid().equals(order_id))
                .collect(Collectors.toList());
        if(group_orders.size()>0)
        {
            GroupOrder group_order = group_orders.get(0);
            return group_order.get_orders(user_email);
        }
        return new ArrayList<>();
    }
    public void pay_order(String email){

    }

    public void validate_order(UUID order_id){

    }
}
