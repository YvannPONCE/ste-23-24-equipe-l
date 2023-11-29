package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    List<Notification> notifications;
    String email;
    String username;

    Integer NumOrdersTilDiscount;

    Date LastAppliedDiscount;

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
        this.NumOrdersTilDiscount=10;
        this.notifications=new ArrayList<>();
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
    public Date getLastAppliedDiscount() {
        return this.LastAppliedDiscount;
    }
    public Integer getNumOrdersTilDiscount() {
        return this.NumOrdersTilDiscount;
    }

    public void setLastAppliedDiscount(Date lastAppliedDiscount) {
        this.LastAppliedDiscount = lastAppliedDiscount;
    }

    public void decrementNumOrdersTilDiscount() {
        this.NumOrdersTilDiscount--;
    }

    public void resetNumOrdersTilDiscount() {
        this.NumOrdersTilDiscount=10;
    }

    public Integer getDaysBetweenNowAndLastDiscount() {
        Date now = new Date();
        long diff = now.getTime() - this.LastAppliedDiscount.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getUsername() {
        return username;
    }

}
