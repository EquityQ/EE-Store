package org.example.javawebv2.com.v2.Model;

   public class CartItem {
       private String name;
       private double price;
       private int quantity;
       private double localtotal;
       private int maxValue;

       // Getters and Setters
       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public double getPrice() {
           return price;
       }

       public void setPrice(double price) {
           this.price = price;
       }

       public int getQuantity() {
           return quantity;
       }

       public void setQuantity(int quantity) {
           this.quantity = quantity;
       }

       public double getLocaltotal() {
           return localtotal;
       }

       public void setLocaltotal(double localtotal) {
           this.localtotal = localtotal;
       }

       public int getMaxValue() {
           return maxValue;
       }

       public void setMaxValue(int maxValue) {
           this.maxValue = maxValue;
       }
   }
