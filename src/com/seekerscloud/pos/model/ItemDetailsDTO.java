package com.seekerscloud.pos.model;

public class ItemDetailsDTO {
    private String code;
    private double unitPrice;
    private int qty;

    public ItemDetailsDTO() {
    }

    public ItemDetailsDTO(String code, double unitPrice, int qty) {
        this.code = code;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
