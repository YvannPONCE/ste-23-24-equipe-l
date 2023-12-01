package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;


public class CanceledState extends IOrderState {

    public CanceledState(){
        status = Status.CANCELED;
    }

    public void next(OrderState orderState) {
        orderState.setState(new CanceledState());
    }



}
