package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserManager {


    public UserManager() {
    }

    public List<Order> get_order_history(String mail) {
        User user = get_user(mail);
        return user.getOrderHistory();


    }

    public User get_user(String email) {
        return new User("hsjhxj", "jdjd", Role.CUSTOMER_STUDENT);
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

}


