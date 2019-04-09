package ahmedg2797.inventory.utils;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private int quantity;
    private int price;
    private String email;
    private String image;

    public Product() {
    }

    public Product(String name, int quantity, int price, String email, String image) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.email = email;
        this.image = image;
    }

    public Product(int id, String name, int quantity, int price, String email, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
