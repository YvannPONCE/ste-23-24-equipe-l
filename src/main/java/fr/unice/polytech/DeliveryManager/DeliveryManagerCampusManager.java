package fr.unice.polytech.DeliveryManager;

import java.util.Map;

public interface DeliveryManagerCampusManager extends DeliveryManagerConnectedUser {
    public Map<String, Boolean> getDeliveryMenAvailability();

    void addDeliveryLocation(String deliveryLocation);

    void deleteDeliveryLocation(String deliveryLocation);
}
