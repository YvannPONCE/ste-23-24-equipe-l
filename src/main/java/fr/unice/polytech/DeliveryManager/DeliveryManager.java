package fr.unice.polytech.DeliveryManager;

import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.GroupOrder;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.UserManager;

import java.time.LocalDateTime;
import java.util.*;

public class DeliveryManager implements  DeliveryManagerCampusManager, DeliveryManagerConnectedUser{


    private final UserManager userManager;
    private Map<String, Boolean> deliveryMenAvailability;
    private Map<String, GroupOrder> deliveryMenOrders;
    private List<String> deliveryLocations;
    private NotificationCenter notificationCenter;

    public DeliveryManager(UserManager userManager, NotificationCenter notificationCenter) {
        this.deliveryMenAvailability = new HashMap<>();
        this.userManager =userManager;
        this.deliveryMenOrders = new HashMap<>();
        this.notificationCenter = notificationCenter;
        deliveryLocations = new ArrayList<>();
    }

    @Override
    public Map<String, Boolean> getDeliveryMenAvailability() {
        getDeliveryMen();
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

    public void addOrder(GroupOrder groupOrder){
        getDeliveryMen();
        Map.Entry<String, Boolean> availibleDeliveryMan = deliveryMenAvailability.entrySet().stream()
                .filter(Map.Entry::getValue)
                .findFirst().orElse(null);
        if(availibleDeliveryMan != null) {
            deliveryMenAvailability.put(availibleDeliveryMan.getKey(), false);
            deliveryMenOrders.put(availibleDeliveryMan.getKey(), groupOrder);

            for (String userEmail : groupOrder.getGlobalOrders().keySet()) {
                notificationCenter.orderReady(groupOrder.getUuid(), availibleDeliveryMan.getKey(), groupOrder.getDeliveryLocation(), userEmail);
            }
        }
    }

    @Override
    public List<String> getLocations() {
        return deliveryLocations;
    }

    public void validateOrder(String deliveryman, Boolean closeOrder){
        GroupOrder groupOrder = deliveryMenOrders.get(deliveryman);
        if(groupOrder == null){
            System.out.println("The command link to "+ deliveryman +" has not been found");
            return;
        }
        groupOrder.validateOrderReceipt();
        if (closeOrder || groupOrder.getOrderState().getStatus() == Status.CLOSED){
            groupOrder.closeOrder();
            addOrdersToHistory(groupOrder);
            deliveryMenOrders.replace(deliveryman, null);
            deliveryMenAvailability.replace(deliveryman, true);
        }
    }
    public void validateOrder(String deliveryman){validateOrder(deliveryman, false);}

    public void validateOrder(UUID orderID){
        try {
            String deliveryManEmail = deliveryMenOrders.entrySet().stream()
                    .filter(entry -> entry.getValue().getUuid().equals(orderID))
                    .findFirst().orElse(null).getKey();
            validateOrder(deliveryManEmail, true);
        }
        catch (NullPointerException e)
        {
            System.out.println("Order "+ orderID +" does not exist");
        }
    }

    public Status getOrderStatus(UUID orderID){
        return deliveryMenOrders.values().stream()
                .filter(groupOrder -> groupOrder.getUuid().equals(orderID))
                .findFirst().orElse(null).getOrderState().getStatus();
    }

    private void getDeliveryMen(){
        List<String> deliveryMenIDs = userManager.getDeliveryMenID();
        for(String deliveryManID : deliveryMenIDs){
            if(deliveryMenAvailability.get(deliveryManID) == null){
                deliveryMenAvailability.put(deliveryManID, true);
                deliveryMenOrders.put(deliveryManID, null);
            }
        }
    }

    private void addOrdersToHistory(GroupOrder groupOrder){
        for(String email : groupOrder.getGlobalOrders().keySet())
        {
            userManager.addOrdersToHistory(email, groupOrder.getOrders(email));
            notificationCenter.order_delivered(groupOrder.getUuid(), groupOrder.getDeliveryLocation(), LocalDateTime.now(), email);
        }
    }
}
