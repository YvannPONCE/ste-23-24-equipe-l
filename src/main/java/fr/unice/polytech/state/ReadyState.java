package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;


public class ReadyState extends IOrderState {

    public ReadyState(){
        status = Status.READY;
    }
    @Override
    public void next(OrderState orderState) {

        orderState.setState(new DeliveredState());
        orderState.setStatus(orderState.getState().getStatus());

    }


}
