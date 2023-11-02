package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;

import java.util.ArrayList;
import java.util.List;

public class UserManager {



    public UserManager() {
    }

public List<Order> get_order_history(String mail){
        User user=get_user(mail);
        return user.getOrderHistory();


        }
        public User get_user(String email){
            return new User("hsjhxj","jdjd", Role.CUSTOMER_STUDENT);
        }
    }

