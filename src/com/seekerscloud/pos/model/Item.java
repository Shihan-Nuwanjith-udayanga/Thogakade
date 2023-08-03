package com.seekerscloud.pos.model;

public class Item {
    private String code;
    private String description;
    private double uniPrice;
    private int qtyOnHand;

    public Item() {
    }

    public Item(String code, String description, double uniPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.uniPrice = uniPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUniPrice() {
        return uniPrice;
    }

    public void setUniPrice(double uniPrice) {
        this.uniPrice = uniPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}
