package fr.unice.polytech.DeliveryManager;

import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.GroupOrder;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.UserManager;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryManager implements  DeliveryManagerCampusManager, DeliveryManagerConnectedUser{


    private final UserManager usermanager;
    OrderManager orderManager;
    private Map<String, Boolean> deliveryMenAvailability;
    private Map<String, GroupOrder> deliveryMenOrders;
    private List<String> deliveryLocations;

    public DeliveryManager(OrderManager orderManager, UserManager userManager) {
        this.orderManager = orderManager;
        this.deliveryMenAvailability = new HashMap<>();
        this.usermanager=userManager;
        this.deliveryMenOrders = new HashMap<>();
        deliveryLocations = new ArrayList<>();
    }

    @Override
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

    public void addOrder(GroupOrder groupOrder){
        getDeliveryMen();
        Map.Entry<String, Boolean> availibleDeliveryMan = deliveryMenAvailability.entrySet().stream()
                .filter(Map.Entry::getValue)
                .findFirst().orElse(null);

        if(availibleDeliveryMan != null){
            deliveryMenAvailability.put(availibleDeliveryMan.getKey(), false);
            deliveryMenOrders.put(availibleDeliveryMan.getKey(), groupOrder);
        }

    }

    @Override
    public List<String> getLocations() {
        return deliveryLocations;
    }

    public void validateOrder(String deliveryman, UUID orderID){
        GroupOrder groupOrder = deliveryMenOrders.get(deliveryman);
        groupOrder.validateOrderReceipt();
        if (groupOrder.getOrderState().getStatus() == Status.CLOSED){
            deliveryMenOrders.replace(deliveryman, null);
            deliveryMenAvailability.replace(deliveryman, true);
        }
    }
    public void validateOrder(UUID orderID){
        String deliveryManEmail = deliveryMenOrders.entrySet().stream()
                .filter(entry -> entry.getValue().getUuid().equals(orderID))
                .findFirst().orElse(null).getKey();
        validateOrder(deliveryManEmail, orderID);
    }

    private GroupOrder getGroupOrder(UUID orderID){
        return deliveryMenOrders.values().stream()
                .filter(groupOrder -> groupOrder.getUuid().equals(orderID))
                .findFirst().orElse(null);
    }

    private void getDeliveryMen(){
        List<String> deliveryMenIDs = usermanager.getDeliveryMenID();
        for(String deliveryManID : deliveryMenIDs){
            if(deliveryMenAvailability.get(deliveryManID) == null){
                deliveryMenAvailability.put(deliveryManID, true);
                deliveryMenOrders.put(deliveryManID, null);
            }
        }
    }
}
