package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.util.*;
import java.util.stream.Collectors;

public class GroupOrder {
   UUID uuid;
   Locations delivery_location;
   HashMap<String, List<Order>> global_orders;

   public GroupOrder(UUID uuid, Locations delivery_location)
   {
       this.uuid = uuid;
       this.delivery_location = delivery_location;
       this.global_orders = new HashMap<>();
   }

    public UUID get_uuid() {
        return uuid;
    }

    public HashMap<String, List<Order>> getGlobal_orders() {
        return global_orders;
    }

    public Locations get_delivery_location() {
        return delivery_location;
    }

    public boolean add_order(String user_email, Order order)
   {
       if(order.get_menus().size() != 1)
       {
           return false;
       }
       List<Order> user_orders = global_orders.get(user_email);
       if(user_orders!=null)
       {
           List<Order> orders = user_orders.stream().filter(filtered_order -> filtered_order.get_restaurant_name().equals(order.get_restaurant_name())).collect(Collectors.toList());
           if(orders.size()>0)
           {
               Order current_order = orders.get(0);
               current_order.add_menu(order.get_menus().get(0));
               return true;
           }
           else
           {
               user_orders.add(order);
               return true;
           }
       }
       else{
           global_orders.put(user_email, new ArrayList<>(Arrays.asList(order)));
           return true;
       }

   }

   public List<Order> get_orders(String user_email)
   {
       System.out.println(global_orders);
       return this.global_orders.get(user_email);
   }

    public boolean isPaid() {
       for (List<Order> orders : this.global_orders.values())
       {
            for (Order order : orders)
            {
                if(order.status != Status.PAID)return false;
            }
       }
       return true;
    }

    public HashMap<String, List<Order>> getOrdersByRestaurants() {
       HashMap<String, List<Order>> restaurant_orders = new HashMap<>();
        for (List<Order> orders : this.global_orders.values())
        {
            for (Order order: orders)
            {
                if(restaurant_orders.containsKey(order.get_restaurant_name()))
                {
                    List<Order> orders_2 = restaurant_orders.get(order.get_restaurant_name());
                    orders_2.add(order);
                }
                else
                {
                    restaurant_orders.put(order.get_restaurant_name(), Arrays.asList(order));
                }
            }
        }
        return restaurant_orders;
    }
}
