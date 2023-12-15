package fr.unice.polytech.RatingManager;

import java.util.*;

public class RatingManager {

    Map<String, List<Double>> usersRate;

    public RatingManager(){
        usersRate = new HashMap<>();
    }

    public void rateUser(String email, Double rate) {
        List<Double> rates = usersRate.get(email);
        if(rates == null)
        {
            rates = new ArrayList<>();
            rates.add(rate);
            usersRate.put(email, rates);
        }
        else {
            rates.add(rate.doubleValue());
        }
    }

    public Double getRate(String user) {
        return usersRate.get(user).stream().mapToDouble(Double::doubleValue).average().orElse(-1);
    }
}
