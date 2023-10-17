package fr.unice.polytech;

public class User {
    String email;
    String username;
    public User(String email,String username){
        this.email=email;
        this.username=username;
    }

    public String get_email()
    {
        return this.email;
    }


}
