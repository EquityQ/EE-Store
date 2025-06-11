package org.example.javawebv2.com.v2.Model;


public class Orders {

  private long id;
  private String username;
  private String orderId;
  private double value;
  private java.sql.Timestamp time;
  private String other;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }


  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }


  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

}
