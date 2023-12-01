package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;

public class ClosedState extends IOrderState {
    public ClosedState(){
        status = Status.CLOSED;
    }

    @Override
    public void next(OrderState orderState) {
        orderState.setState(new ClosedState());

    }


}


