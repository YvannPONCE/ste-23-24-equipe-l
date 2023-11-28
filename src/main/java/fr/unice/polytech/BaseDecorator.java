package fr.unice.polytech;

import fr.unice.polytech.NotificationCenter.NotificationCenter;

public abstract class BaseDecorator  {
    private NotificationCenter wrappee;

    public BaseDecorator(NotificationCenter wrappee) {
        this.wrappee = wrappee;
    }

}
