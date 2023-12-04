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
    String restaurant_name;
    Date creation_time;
    List<Menu> menus;
    private OrderState orderState;
    private double totalPrice;

    public  Order(String restaurant_name, List<Menu> menus){
        this.restaurant_name = restaurant_name;
        this.menus = menus;
        this.totalPrice=0.0;
        this.orderState=new OrderState();
        this.creation_time=new Date();
    }

    public  Order(String restaurant_name){
        this.restaurant_name = restaurant_name;
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
        System.out.println(" Restaurant Name: " + restaurant_name);
        for (Menu menu : menus) {
            System.out.println(" Menu Name: " +menu.getItemName());
        }
    }
    public double calculateTotalPrice() {
        double sum=this.totalPrice;

        for (Menu menu : menus) {
            sum =sum+ menu.getPrice();
        }
        this.setTotalPrice(sum);
        return sum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", menus=" + menus +
                ", orderState=" + orderState +
                '}';
    }
}
