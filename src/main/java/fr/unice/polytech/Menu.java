package fr.unice.polytech;

import fr.unice.polytech.Enum.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
public class Menu {
    private String itemName;
    private double price;
    private MenuType menuType;
    private int numberOfParticipants = 0 ; //in case of afterwork

    public Menu(String itemName, double price, MenuType menuType) {
        this.itemName = itemName;
        this.menuType = menuType;
        if(this.menuType == MenuType.AFTERWORK_MENU) {
            this.price = 0;
        } else {
            this.price = price;
        }
    }

    public Menu(String itemName, double price, MenuType menuType, int numberOfParticipants) {
        this.itemName = itemName;
        this.menuType = menuType;
        if(this.menuType == MenuType.AFTERWORK_MENU) {
            this.price = 0;
            this.numberOfParticipants = numberOfParticipants;
        } else {
            this.price = price;
        }
    }

    public Menu(Menu menu) {
        itemName = menu.getItemName();
        price = menu.getPrice();
    }


    public void displayItem() {
        System.out.println(itemName + " - " + price + " €");
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

    @Override
    public String toString() {
        return "Menu{" +
                "itemName='" + itemName + '\'' +
                ", price=" + price +
                '}';
    }
}
