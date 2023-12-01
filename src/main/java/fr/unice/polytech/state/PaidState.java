package fr.unice.polytech.state;


public class PaidState extends IOrderState {

    public PaidState(){
        status =status.PAID;
    }

    @Override
    public void next(OrderState orderState) {
        orderState.setState(new ProcessingState());


        orderState.setStatus(orderState.getState().getStatus());

    }



}
