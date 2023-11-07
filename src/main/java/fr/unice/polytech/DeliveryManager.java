package fr.unice.polytech;
import fr.unice.polytech.Enum.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryManager {


    OrderManager orderManager;
    private Map<String, Boolean> deliveryMenAvailability;
    private Map<String, UUID> deliveryMenOrders;

    public DeliveryManager(OrderManager orderManager) {
        this.orderManager = orderManager;
        this.deliveryMenAvailability = new HashMap<>();
        this.deliveryMenOrders = new HashMap<>();
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

    public void addDeliveryman(String deliveryman){
        deliveryMenAvailability.put(deliveryman,true);
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
