package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;
public class ProcessingState extends IOrderState {

    public ProcessingState(){
        status = Status.PROCESSING;
    }

    public void next(OrderState orderState) {
        orderState.setState(new ReadyState());
        orderState.setStatus(orderState.getState().getStatus());
    }

}
