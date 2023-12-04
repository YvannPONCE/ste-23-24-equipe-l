package fr.unice.polytech;

import fr.unice.polytech.state.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class Order {
    UUID id;
    String restaurantName;
    Date creation_time;
    List<Menu> menus;
    private OrderState orderState;
    private double totalPrice;

    public  Order(String restaurantName, List<Menu> menus){
        this.restaurantName = restaurantName;
        this.menus = menus;
        this.totalPrice=0.0;
        this.orderState=new OrderState();
        this.creation_time=new Date();
    }

    public  Order(String restaurantName){
        this.restaurantName = restaurantName;
        this.menus = new ArrayList<>();
        this.totalPrice=0.0;
        this.orderState=new OrderState();
        this.creation_time=new Date();
    }

    public int getItemCount() {
        return menus.size();
    }

    public void add_menu(Menu menu)
    {
        this.menus.add(menu);
        this.totalPrice += menu.getPrice();
    }

    public void displayOrderSummary() {
        System.out.println(" Restaurant Name: " + restaurantName);
        for (Menu menu : menus) {
            System.out.println(" Menu Name: " +menu.getItemName());
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", restaurant_name='" + restaurantName + '\'' +
                ", menus=" + menus +
                ", orderState=" + orderState +
                '}';
    }
}
