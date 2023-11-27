package fr.unice.polytech;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.OrderManager.OrderManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeliveryManager {


    private final UserManager usermanager;
    OrderManager orderManager;



    User user;
    private Map<String, Boolean> deliveryMenAvailability;
    private Map<String, UUID> deliveryMenOrders;

    public DeliveryManager(OrderManager orderManager, UserManager userManager) {
        this.orderManager = orderManager;
        this.deliveryMenAvailability = new HashMap<>();
        this.usermanager=userManager;
        this.deliveryMenOrders = new HashMap<>();
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, Boolean> getDeliveryMenAvailability() {
        return deliveryMenAvailability;
    }

    public Map<String, UUID> getDeliveryMenOrders() {
        return deliveryMenOrders;
    }

    public boolean addOrder(UUID orderID){
        for (Map.Entry<String, Boolean> entry : deliveryMenAvailability.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();
            if(value){
                deliveryMenOrders.put(key,orderID);
                deliveryMenAvailability.replace(key,false);
                return true;
            }
        }
        return false;
    }

    public void validateOrder(String deliveryman, UUID order_id){
        orderManager.setOrderAsClosed(order_id);
        deliveryMenOrders.remove(deliveryman);
        deliveryMenAvailability.replace(deliveryman, true);
    }

    public UUID getOrderToDelivery(String deliveryman, UUID orderID){
        for (Map.Entry<String, UUID> entry : deliveryMenOrders.entrySet()) {
            String key = entry.getKey();
            UUID value = entry.getValue();
            if(key.equals(deliveryman)){
                return value;
            }
        }
        return null;
    }

    public void addDeliveryman(String delivermanmail,String deliveryman) {
        deliveryMenAvailability.put(delivermanmail, true);
        setUser(new User(delivermanmail,deliveryman,"ggg", Role.DELIVER_MAN));
        usermanager.add_user(user);
    }

    public void deleteDeliveryman(String deliveryman){
        if(!deliveryMenOrders.containsKey(deliveryman)){
            deliveryMenAvailability.remove(deliveryman);
        }
    }

    public boolean isAvailable(String deliveryman){
        return deliveryMenAvailability.get(deliveryman);
    }
}
