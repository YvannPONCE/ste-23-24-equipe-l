package fr.unice.polytech;
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

}
