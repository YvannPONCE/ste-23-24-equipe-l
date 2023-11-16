package fr.unice.polytech;

public abstract class BaseDecorator  {
    private NotificationCenter wrappee;

    public BaseDecorator(NotificationCenter wrappee) {
        this.wrappee = wrappee;
    }

}
