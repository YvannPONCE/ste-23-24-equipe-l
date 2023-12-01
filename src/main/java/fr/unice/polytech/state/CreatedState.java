package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;


public class CreatedState extends IOrderState {

    public CreatedState(){
        status = Status.CREATED;
    }

    @Override public void next(OrderState orderState) {
        orderState.setState(new PaidState());
        orderState.setStatus(orderState.getState().getStatus());
    }


}
