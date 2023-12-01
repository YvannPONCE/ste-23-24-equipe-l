package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class User {

    List<Notification> Notifications;
    String email;
    String username;



    Double Credit;
    List<Order> OrderHistory;
    Role role;
    private String password;


    public User(String email, String username, String password, Role role){
        this.email=email;
        this.username=username;
        this.OrderHistory =new ArrayList<>();
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
    public List<Order> getOrderHistory() {
        return OrderHistory;
    }
    public void addOrderToHistory(List<Order> orders)
    {
        this.OrderHistory.addAll(orders);
    }



    public double addCredit(Double sum){
        return this.Credit=this.Credit+sum;
    }






}
