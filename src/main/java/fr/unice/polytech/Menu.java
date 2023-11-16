package fr.unice.polytech;

import java.util.Objects;

public class Menu {
    private String itemName;
    private double price;

    public Menu(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public void displayItem() {
        System.out.println(itemName + " - " + price + " €");
    }

    public String get_name() {
        return itemName;
    }

    public double get_price() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Double.compare(price, menu.price) == 0 && Objects.equals(itemName, menu.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, price);
    }
}
