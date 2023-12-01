package fr.unice.polytech.DeliveryManager;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;

import java.util.*;

public class DeliveryManager implements  DeliveryManagerCampusManager, DeliveryManagerConnectedUser{


    private final UserManager usermanager;
    OrderManager orderManager;
    private Map<String, Boolean> deliveryMenAvailability;
    private Map<String, UUID> deliveryMenOrders;
    private List<String> deliveryLocations;

    public DeliveryManager(OrderManager orderManager, UserManager userManager) {
        this.orderManager = orderManager;
        this.deliveryMenAvailability = new HashMap<>();
        this.usermanager=userManager;
        this.deliveryMenOrders = new HashMap<>();
        deliveryLocations = new ArrayList<>();
    }
    public Map<String, Boolean> getDeliveryMenAvailability() {
        return deliveryMenAvailability;
    }

    @Override
    public void addDeliveryLocation(String deliveryLocation) {
        deliveryLocations.add(deliveryLocation);
    }

    @Override
    public void deleteDeliveryLocation(String deliveryLocation) {
        for (String deliveryLocation2 : deliveryLocations){
            if(deliveryLocation2.equals(deliveryLocation)){
                deliveryLocations.remove(deliveryLocation2);
            }
        }
    }

    public Map<String, UUID> getDeliveryMenOrders() {
        return deliveryMenOrders;
    }

    public User addOrder(UUID orderID){
        for (Map.Entry<String, Boolean> entry : deliveryMenAvailability.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();
            if(value){
                deliveryMenOrders.put(key,orderID);
                deliveryMenAvailability.replace(key,false);

                return usermanager.getUser(key);
            }
        }
        return null;
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

    public void addDeliveryman(String deliverManMail,String deliveryManPassword) {
        deliveryMenAvailability.put(deliverManMail, true);
        User user = new User(deliverManMail,deliveryManPassword, Role.DELIVER_MAN);
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

    @Override
    public List<String> getLocations() {
        return deliveryLocations;
    }
}
