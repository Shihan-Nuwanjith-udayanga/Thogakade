package com.seekerscloud.pos.controller;

import com.seekerscloud.pos.db.Database;
import com.seekerscloud.pos.model.Customer;
import com.seekerscloud.pos.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PlaceOrderFormController {
    public AnchorPane placeOrderFormContext;
    public TextField txtOrderId;
    public TextField txtDate;
    public ComboBox<String> cmbCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public ComboBox<String> cmbItemCode;
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
        setDateAndOrderId();
        loadAllCustomerIds();
        loadAllItemIds();
    }

    private void loadAllItemIds() {
        for(Item i:Database.itemTable){
            cmbItemCode.getItems().add(i.getCode());
        }
    }

    private void loadAllCustomerIds() {
        for(Customer c:Database.customerTable){
            cmbCustomerId.getItems().add(c.getId());
        }
    }

    private void setDateAndOrderId() {
        /* Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String d =df.format(date);
        txtDate.setText(d);*/
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)placeOrderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/DashboardForm.fxml")))));
    }

    public void addToCartOnAction(ActionEvent actionEvent) {

    }

    public void placeOrderOnAction(ActionEvent actionEvent) {

    }
}
