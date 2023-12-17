package fr.unice.polytech.state;

import fr.unice.polytech.Enum.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderState  {

    private Status status ;
    private IOrderState state ;
    public OrderState(){
        this.state=new CreatedState();
        this.status = this.state.getStatus();
    }

    public void SetProcessingForAfterWork(){
        this.status = Status.PROCESSING;
    }

    public void next() {
        state.next(this);
        status = state.getStatus();
    }

    public void cancel(){
        state = new CanceledState();
        status = state.getStatus();
    }
    public void closed(){
        state = new ClosedState();
        status = state.getStatus();
    }


}
