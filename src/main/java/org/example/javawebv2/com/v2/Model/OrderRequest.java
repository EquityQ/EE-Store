package org.example.javawebv2.com.v2.Model;

   import java.util.List;

   public class OrderRequest {
       private List<CartItem> items;

       // Getter and Setter
       public List<CartItem> getItems() {
           return items;
       }

       public void setItems(List<CartItem> items) {
           this.items = items;
       }
   }
