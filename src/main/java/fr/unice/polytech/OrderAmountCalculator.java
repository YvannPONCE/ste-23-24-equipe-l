package fr.unice.polytech;

import fr.unice.polytech.Enum.Status;
import org.mockito.internal.matchers.Or;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderAmountCalculator {
    GroupOrder order ;
    UserManager userManager;
    int itemCountThreshold=10;
    public OrderAmountCalculator(GroupOrder order) {
        this.order = order;

    }

    public void applyMenuDiscount( double discountPercentage) {
        if(this.order.qualifiesForMenuDiscount(itemCountThreshold)){
        for (String email : this.order.global_orders.keySet()) {
          for(Order order:  this.order.get_orders(email))  {
              double discountAmount = (discountPercentage / 100) * order.calculateTotalPrice();
              userManager.get_user(email).addCredit(discountAmount);

            }

        }
    }

        }

    }

