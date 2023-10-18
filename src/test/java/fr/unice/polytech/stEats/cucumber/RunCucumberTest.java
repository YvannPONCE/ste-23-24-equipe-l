package fr.unice.polytech.stEats.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:src/test/Ressources/features/AddMenuToCart.feature"},
        glue = {"fr.unice.polytech.stEats.cucumber"})
public class RunCucumberTest {}

