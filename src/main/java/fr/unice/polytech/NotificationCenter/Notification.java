package fr.unice.polytech.NotificationCenter;

public class Notification {


    String Message;

    public Notification(String message){
        this.Message=message;

    }
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
