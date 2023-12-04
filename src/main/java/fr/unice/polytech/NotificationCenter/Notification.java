package fr.unice.polytech.NotificationCenter;

public class Notification {


    String Message;

    public Notification(String message){
        this.Message=message;

    }
    public String getMessage() {
        return Message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "Message='" + Message + '\'' +
                '}';
    }
}
