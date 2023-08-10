package com.seekerscloud.pos.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class PlaceOrderFormController {
    public AnchorPane placeOrderFormContext;
    public TextField txtOrderId;
    public TextField txtDate;
    public ComboBox cmbCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public ComboBox cmbItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TextField txtQtyOnHand;
    public TableView tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public Label lblTotal;

    public void initialize(){

    }

    public void backToHomeOnAction(ActionEvent actionEvent) {

    }

    public void addToCartOnAction(ActionEvent actionEvent) {

    }

    public void placeOrderOnAction(ActionEvent actionEvent) {

    }
}
