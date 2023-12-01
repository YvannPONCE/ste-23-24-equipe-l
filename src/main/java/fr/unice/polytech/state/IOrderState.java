package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;
import lombok.Getter;

@Getter
public abstract class IOrderState {

        protected Status status ;
        public abstract void next(OrderState orderState);

}

