package fr.unice.polytech.RatingManager;

import java.util.*;

public class RatingManager {

    Map<String, List<Double>> Rating;

    public RatingManager(){
        Rating = new HashMap<>();
    }

    public void rate(String email, Double rate) {
        List<Double> rates = Rating.get(email);
        if(rates == null)
        {
            rates = new ArrayList<>();
            rates.add(rate);
            Rating.put(email, rates);
        }
        else {
            rates.add(rate);
        }
    }

    public Double getRate(String user) {
        return Rating.get(user).stream().mapToDouble(Double::doubleValue).average().orElse(-1);
    }
}
