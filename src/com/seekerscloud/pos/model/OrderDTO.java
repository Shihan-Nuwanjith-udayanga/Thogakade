package com.seekerscloud.pos.model;

import java.util.ArrayList;
import java.util.Date;

public class OrderDTO {
    private String orderId; //D-1
    private Date date;
    private double totalCost;
    private String customer;
    private ArrayList<ItemDetailsDTO> itemDetails;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, Date date, double totalCost, String customer, ArrayList<ItemDetailsDTO> itemDetails) {
        this.orderId = orderId;
        this.date = date;
        this.totalCost = totalCost;
        this.customer = customer;
        this.itemDetails = itemDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<ItemDetailsDTO> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ArrayList<ItemDetailsDTO> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
