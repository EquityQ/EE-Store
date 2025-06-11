package org.example.javawebv2.com.v2.Model;

public class MyShopElement {
    public String name;
    public double price;
    public int value;
    public String description;
    public String image;
    public String oldName;

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getOldName() {
        return oldName;
    }

    // Setter methods
    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
