package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.OrderManager.OrderManagerConnectedUser;
import lombok.Getter;
import lombok.Setter;
import org.mockito.internal.matchers.Or;

import java.util.*;
import java.util.stream.Collectors;
@Getter
@Setter

public class UserManager {

    private OrderManagerConnectedUser orderManager;
    List<User> userList;


    public UserManager() {
        userList = new ArrayList<>();
    }
    public void addOrderManager(OrderManagerConnectedUser orderManager){
        this.orderManager = orderManager;
    }

    /**
     * Display the order history of a user
     * @param mail the e-mail address of the user
     */
    public HashMap<String,List<Order>> get_order_history(String mail) {
        return get_user(mail).getOrderHistory();
    }
    public void add_user(User user)
    {
        if(user !=null)
        {
            userList.add(user);
        }
    }
    public User get_user(String email) {

        List<User> users = this.userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .collect(Collectors.toList());
        if(users.size()>0)return users.get(0);
        return null;
    }

    public void displayOrderHistory(String mail) {
        HashMap<String,List<Order>> orderHistory = get_order_history(mail);
        List<Order> orderHistoryList = new ArrayList<>();
        for(String restaurant_name: orderHistory.keySet()) {
            for (Order order : orderHistory.get(restaurant_name)) {
                orderHistoryList.add(order);
            }
        }

        // Tri de la liste des commandes par date chronologique (du plus récent au plus ancien)
        Collections.sort(orderHistoryList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order2.getCreation_time().compareTo(order1.getCreation_time()); // Inversion de l'ordre
            }
        });
        for(Order order: orderHistoryList) {
            order.displayOrderSummary();
        }
    }

   public Order  find_selectedOrder(UUID orderId,String mail){
        HashMap<String, List<Order>> orderHistory=get_order_history(mail);

        for(String restaurant_name: orderHistory.keySet()){
            for(Order order: orderHistory.get(restaurant_name)) {
                if (order.id.equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
   }
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }



    public void signIn(String userEmail, String userPassword,Role role) {
        User user = new User(userEmail, userPassword,role);
        userList.add(user);
    }
    public void addOrdersToHistory(String email, List<Order> orders) {
       User user = get_user(email);
       user.addOrderToHistory(orders);
    }
    public void displaySelectedOrderDetails(UUID orderId, String mail) {
        Order selectedOrder = find_selectedOrder(orderId, mail);

        if (selectedOrder != null) {
            System.out.println("Selected Order Details:");
            List<Menu> orderItems = selectedOrder.getMenus();
            System.out.println("Order Items:");
            for (Menu orderItem : orderItems) {
                System.out.println("  - " + orderItem.getItemName() +
                        ", Price: " + orderItem.getPrice());
            }
        } else {
            System.out.println("Selected Order not found.");
        }
    }

    public void addUser(User user)
    {
        if(user !=null)
        {
            userList.add(user);
        }
    }

    public OrderManagerConnectedUser logIn(String userEmail, String userPassword) {
        User user = getUser(userEmail);
        if(user != null && user.getPassword() == userPassword)return orderManager;
        return null;
    }

    public User getUser(String email) {
        List<User> users = this.userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .collect(Collectors.toList());
        if(users.size()>0)return users.get(0);
        return null;
    }

    public List<String> getDeliveryMenID() {
         return userList.stream()
                .filter(user -> user.getRole().equals(Role.DELIVER_MAN))
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    /*
    public void setOrderDelivered(String usersID, UUID orderID) {
        User user = userList.stream()
                .filter(user1 -> user1.getEmail().equals(usersID))
                .findFirst().orElse(null);
        if(user == null)return;
        Order order = user.getOrderHistory().stream()
                .filter(order2 -> order2.getId().equals(orderID))
                .findFirst().orElse(null);
        if(order == null)return;
        order.getOrderState().closed();
    }
     */

    public void deleteUser(String userEmail) {
        List<User> users = userList.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .collect(Collectors.toList());
        userList.removeAll(users);
    }
}


