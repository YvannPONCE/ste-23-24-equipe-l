package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

import java.util.ArrayList;
import java.util.List;

public class User {
    String email;
    String username;



    List<Order> OrderHistory;

    Role role;
    public User(String email,String username,Role role){
        this.email=email;
        this.username=username;
        this.OrderHistory =new ArrayList<>();
        this.role=role;
    }
    public List<Order> getOrderHistory() {
        return OrderHistory;
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


}
