package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

public class User {
    String email;
    String username;



    Role role;
    public User(String email,String username,Role role){
        this.email=email;
        this.username=username;
        this.role=role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}