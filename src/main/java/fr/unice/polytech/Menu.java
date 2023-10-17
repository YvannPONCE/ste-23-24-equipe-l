package fr.unice.polytech;

public class Menu {
    String name;
    double price;
    public Menu(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    public String get_name() {
        return name;
    }

    public double get_price() {
        return price;
    }
}
