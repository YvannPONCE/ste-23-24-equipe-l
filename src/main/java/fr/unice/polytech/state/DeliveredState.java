package fr.unice.polytech.state;


import fr.unice.polytech.Enum.Status;

public class DeliveredState extends IOrderState {

    public DeliveredState(){
        status = Status.DELIVERED.DELIVERED;
    }

    @Override
    public void next(OrderState orderState) {

        orderState.setState(new ClosedState());
        orderState.setStatus(orderState.getState().getStatus());
    }



}
