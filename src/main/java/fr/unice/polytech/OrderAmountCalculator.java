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




    public void applyMenuDiscount(double discountPercentage, String email) {

        if(this.order.qualifiesForMenuDiscount(itemCountThreshold)){

          for(Order order:  this.order.getOrders(email))  {

//              double discountAmount = (discountPercentage / 100) * order.calculateTotalPrice();
              double discountAmount = (discountPercentage / 100) * order.getTotalPrice();
              //System.out.println("discountAmount "+discountAmount);

              userManager.get_user(email).addCredit(discountAmount);

            }

    }
        else{
                for(Order order:  this.order.getOrders(email))  {
//                    order.calculateTotalPrice();


            }

        }



        }

    }

