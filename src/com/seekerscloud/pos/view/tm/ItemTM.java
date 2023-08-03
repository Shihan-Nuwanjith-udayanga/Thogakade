package com.seekerscloud.pos.view.tm;

import javafx.scene.control.Button;

public class ItemTM {
    private String code;
    private String description;
    private double uniPrice;
    private int qtyOnHand;
    private Button btn;

    public ItemTM() {

    }

    public ItemTM(String code, String description, double uniPrice, int qtyOnHand, Button btn) {
        this.code = code;
        this.description = description;
        this.uniPrice = uniPrice;
        this.qtyOnHand = qtyOnHand;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
