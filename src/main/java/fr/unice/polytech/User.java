package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

import java.util.ArrayList;
import java.util.List;

public class User {
    String email;
    String username;



    Double Credit;
    List<Order> OrderHistory;
    Role role;



    public User(String email, String username, Role role){
        this.email=email;
        this.username=username;
        this.OrderHistory =new ArrayList<>();
        this.role=role;
        this.Credit=0.00;
    }
    public List<Order> getOrderHistory() {
        return OrderHistory;
    }
    public void addOrderToHistory(List<Order> orders)
    {
        this.OrderHistory.addAll(orders);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String get_email()
    {
        return this.email;
    }

    public Double getCredit() {
        return this.Credit;
    }
    public void setCredit(Double credit) {
        this.Credit = credit;
    }
    public double addCredit(Double sum){
        return this.Credit=this.Credit+sum;
    }
}
