package fr.unice.polytech.DeliveryManager;

import java.util.Map;

public interface DeliveryManagerCampusManager extends DeliveryManagerConnectedUser {
    public void addDeliveryman(String delivermanmail,String deliveryman);
    public void deleteDeliveryman(String deliveryman);
    public Map<String, Boolean> getDeliveryMenAvailability();

    void addDeliveryLocation(String deliveryLocation);

    void deleteDeliveryLocation(String deliveryLocation);
}
