package fr.unice.polytech;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAmountCalculator {
    GroupOrder order ;
    UserManager userManager;



    int itemCountThreshold=10;
    public OrderAmountCalculator(GroupOrder order,UserManager userManager) {
        this.userManager=userManager;
        this.order = order;

    }




    public void applyMenuDiscount( double discountPercentage) {

        if(this.order.qualifiesForMenuDiscount(itemCountThreshold)){

        for (String email : this.order.globalOrders.keySet()) {
          for(Order order:  this.order.get_orders(email))  {

              double discountAmount = (discountPercentage / 100) * order.calculateTotalPrice();

              userManager.get_user(email).addCredit(discountAmount);

            }

        }
    }
        else{
            for (String email : this.order.globalOrders.keySet()) {
                for(Order order:  this.order.get_orders(email))  {
                    order.calculateTotalPrice();

                }

            }

        }



        }

    }

