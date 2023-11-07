package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

import java.util.*;
import java.util.stream.Collectors;

public class UserManager {

    List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public List<Order> get_order_history(String mail) {
        User user = get_user(mail);
        return user.getOrderHistory();
    }

    public User get_user(String email) {
        List<User> users = this.users.stream()
                .filter(user -> user.get_email().equals(email))
                .collect(Collectors.toList());
        if(users.size()>0)return users.get(0);
        return null;
    }

    public void displayOrderHistory(String mail) {
        List<Order> orderHistory = get_order_history(mail);

        // Tri de la liste des commandes par date chronologique (du plus récent au plus ancien)
        Collections.sort(orderHistory, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order2.getCreationTime().compareTo(order1.getCreationTime()); // Inversion de l'ordre
            }
        });
        for(Order order:orderHistory){
        order.displayOrderSummary();
        }


    }
   public Order  find_selectedOrder(UUID orderId,String mail){
        List<Order> orderHistory=get_order_history(mail);
        Order selectedOrder = null;
        for(Order order:orderHistory){
            if(order.id.equals(orderId)){
                selectedOrder=order;
            }
        }
        return selectedOrder;
   }


    public void signIn(String userEmail, String userPassword) {
        User user = new User(userEmail, userPassword);
        users.add(user);
    }
}


