package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.util.*;
import java.util.stream.Collectors;

public class GroupOrder {
   UUID uuid;
   Locations delivery_location;
   Status orderStatus;
   public  HashMap<String, List<Order>> global_orders;

   public GroupOrder(UUID uuid, Locations delivery_location)
   {
       this.uuid = uuid;
       this.delivery_location = delivery_location;
       this.global_orders = new HashMap<>();
       this.orderStatus = Status.CREATED;
       setOrdersStatus(Status.CREATED);
   }

    public Status getOrderStatus() {
        return orderStatus;
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
       if(order.get_menus().size() < 1)
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
       return this.global_orders.get(user_email);
   }

   public void setPaid(String userEmail)
   {
       List<Order> orders = get_orders(userEmail);
       for (Order order : orders) {
           order.setStatus(Status.PAID);
       }

       for (List<Order> orders2 : this.global_orders.values())
       {
           for (Order order : orders2)
           {
               if(order.status != Status.PAID) return;
           }
       }
       this.orderStatus = Status.PAID;
       setOrdersStatus(Status.PAID);
   }
   public HashMap<String, List<Menu>> getMenusByRestaurants()
   {
       HashMap<String, List<Menu>> menusByRestaurant = new HashMap<>();
       for(List<Order> orders : global_orders.values())
       {
           for(Order order : orders)
           {
               Map<String , List<Menu>> orderByRestaurant = menusByRestaurant.entrySet().stream()
                       .filter(entry -> entry.getKey().equals(order.get_restaurant_name()))
                       .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
               if(orderByRestaurant.isEmpty())
               {
                   menusByRestaurant.put(order.get_restaurant_name(), order.get_menus());
               } else {
                   menusByRestaurant.get(order.get_restaurant_name()).addAll(order.get_menus());
               }
           }
       }
       return menusByRestaurant;
   }


    public boolean isPaid() {
       return orderStatus == Status.PAID;
    }
    public HashMap<String, List<Order>> getOrdersByRestaurants() {
        HashMap<String, List<Order>> restaurant_orders = new HashMap<>();
        for (List<Order> orders : this.global_orders.values()) {
            for (Order order : orders) {
                if (restaurant_orders.containsKey(order.get_restaurant_name())) {
                    List<Order> orders_2 = restaurant_orders.get(order.get_restaurant_name());
                    orders_2.add(order);
                } else {
                    // Use new ArrayList<>(Arrays.asList(order)) to create a mutable list
                    restaurant_orders.put(order.get_restaurant_name(), new ArrayList<>(Arrays.asList(order)));
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

    public void validate_order(String restaurantName) {
       for(List<Order> orders : global_orders.values())
       {
           List<Order> matchingOrders = orders.stream()
                   .filter(order -> order.get_restaurant_name().equals(restaurantName))
                   .collect(Collectors.toList());
           for (Order order : matchingOrders) order.setStatus(Status.READY);
       }

        for (List<Order> orders2 : this.global_orders.values())
        {
            for (Order order : orders2)
            {
                if(order.status != Status.READY) return;
            }
        }
        this.orderStatus = Status.READY;
        setOrdersStatus(Status.READY);


    }

    public void validate_order_receipt() {
        orderStatus = Status.DELIVERED;
        setOrdersStatus(Status.DELIVERED);
    }

    public void setClose() {
       this.orderStatus = Status.CLOSED;
        setOrdersStatus(Status.CLOSED);
    }
    private void setOrdersStatus(Status status)
    {
        for(List<Order> orders : global_orders.values())
        {
            for (Order order : orders)
            {
                order.setStatus(status);
            }
        }
    }

    public boolean isReady() {
       return orderStatus == Status.READY;
    }
}

