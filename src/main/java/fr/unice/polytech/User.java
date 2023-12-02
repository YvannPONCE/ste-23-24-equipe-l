package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Getter
@Setter
public class User {

    List<Notification> Notifications;
    String email;
    String username;
    Double Credit;
    //List<Order> OrderHistory;
    HashMap<String, List<Order>> OrderHistory;
    Role role;
    private String password;

    public User(String email, String username, String password, Role role){
        this.email=email;
        this.username=username;
        //this.OrderHistory =new ArrayList<>();
        this.OrderHistory =new HashMap<>();
        this.role=role;
        this.Credit=0.00;
        this.Notifications =new ArrayList<>();
    }

    public User(String email, String password, Role role){
        this(email, email, password, role);
    }

    public User(String email, String password){
        this(email, email, password, Role.CUSTOMER_STUDENT);
    }

    /*
    public void addOrderToHistory(List<Order> orders)
    {
        this.OrderHistory.addAll(orders);
    }
     */

    public double addCredit(Double sum){
        return this.Credit=this.Credit+sum;
    }

    /**
     * Add a list of orders to the order history of the user
     * @param orders the list of orders to add
     */
    public void addOrderToHistory(List<Order> orders)
    {
        for(Order o:orders)
        {
            if(this.OrderHistory.containsKey(o.getRestaurant_name()))
            {
                this.OrderHistory.get(o.getRestaurant_name()).add(o);
            }
            else
            {
                List<Order> newOrders=new ArrayList<>();
                newOrders.add(o);
                this.OrderHistory.put(o.getRestaurant_name(),newOrders);
            }
        }
    }

    /**
     * Get the number of orders from a restaurant
     * @param restaurantName the restaurant name to check
     * @return the number of orders from the restaurant by the user
     */
    public Integer getNumberOfOrdersFromRestaurant(String restaurantName)
    {
        if(this.OrderHistory.containsKey(restaurantName))
        {
            return this.OrderHistory.get(restaurantName).size();
        }
        else
        {
            return 0;
        }
    }
}
