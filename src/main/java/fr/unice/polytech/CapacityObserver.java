package fr.unice.polytech;

import java.util.Observer;

public interface CapacityObserver extends Observer {
    void updateCapacity(int newCapacity);
}
