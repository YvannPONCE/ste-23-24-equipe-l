package fr.unice.polytech;

import fr.unice.polytech.Enum.Status;

import javax.xml.stream.Location;
import java.util.Date;
import java.util.UUID;

public class Order {
    UUID id;



    Status status;
    String Restaurant_name;
    Date Creation_time;
    String username;
    float price;
    Location delivery_location;
    Date delivery_time;

    public  Order(){

    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
