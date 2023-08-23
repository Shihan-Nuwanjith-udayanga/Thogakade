package com.seekerscloud.pos.controller;

import com.seekerscloud.pos.db.Database;
import com.seekerscloud.pos.model.CustomerDTO;
import com.seekerscloud.pos.model.ItemDTO;
import com.seekerscloud.pos.model.ItemDetailsDTO;
import com.seekerscloud.pos.model.OrderDTO;
import com.seekerscloud.pos.view.tm.CartTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

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
    public TableView<CartTM> tblItemList;
    public TableColumn<CartTM, String> colItemCode;
    public TableColumn<CartTM, String> colDescription;
    public TableColumn<CartTM, Double> colUnitPrice;
    public TableColumn<CartTM, Integer> colQty;
    public TableColumn<CartTM, Double> colTotal;
    public TableColumn<CartTM, Button> colOption;
    public Label lblTotal;

    public void initialize(){
        setDateAndOrderId();
        loadAllCustomerIds();
        loadAllItemIds();
        setOrderId();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                setCustomerDetails();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           if (newValue != null){
               setItemDetails();
           }
        });

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setOrderId() {
        if (Database.orderTable.isEmpty()){
            txtOrderId.setText("D-1");
            return;
        }
        String tempOrderId = Database.orderTable.get(Database.orderTable.size()-1).getOrderId(); // D-3
        String[] array = tempOrderId.split("-");//[D-3
        int tempNumber = Integer.parseInt(array[1]);
        int finalizeOrderId = tempNumber+1;
        txtOrderId.setText("D-"+finalizeOrderId);
    }

    private void setItemDetails() {
        for (ItemDTO i: Database.itemTable
             ) {
            if (i.getCode().equals(cmbItemCode.getValue())){
                txtDescription.setText(i.getDescription());
                txtUnitPrice.setText(String.valueOf(i.getUniPrice()));
                txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
            }
        }
    }

    private void setCustomerDetails() {
        for (CustomerDTO c: Database.customerTable
             ) {
            if (c.getId().equals(cmbCustomerId.getValue())){
                txtName.setText(c.getName());
                txtAddress.setText(c.getAddress());
                txtSalary.setText(String.valueOf(c.getSalary()));
            }
        }
    }

    private void loadAllItemIds() {
        for(ItemDTO i:Database.itemTable){
            cmbItemCode.getItems().add(i.getCode());
        }
    }

    private void loadAllCustomerIds() {
        for(CustomerDTO c:Database.customerTable){
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

    private boolean checkQty(String code, int qty){
        for (ItemDTO i: Database.itemTable
             ) {
            if (code.equals(i.getCode())){
                if (i.getQtyOnHand()>= qty){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {
        if (!checkQty(cmbItemCode.getValue(),Integer.parseInt(txtQty.getText()))){
            new Alert(Alert.AlertType.WARNING,"Invalid Qty").show();
            return;
        }

        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = unitPrice*qty;
        Button btn = new Button("Delete");

        int row = isAlreadyExists(cmbItemCode.getValue());
        if (row == -1){
            CartTM tm = new CartTM(cmbItemCode.getValue(),txtDescription.getText(),unitPrice,qty,total,btn);
            obList.add(tm);
            tblItemList.setItems(obList);
        }else{
            int tempQty = obList.get(row).getQty()+qty;
            double tempTotal = unitPrice*tempQty;

            if (!checkQty(cmbItemCode.getValue(),tempQty)){
                new Alert(Alert.AlertType.WARNING,"Invalid Qty").show();
                return;
            }

            obList.get(row).setQty(tempQty);
            obList.get(row).setTotal(tempTotal);
            tblItemList.refresh();
        }
        calculateTotal();
        clearFields();
        cmbItemCode.requestFocus();

        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get()==ButtonType.YES){
                for (CartTM tm: obList
                     ) {
                    if (tm.getCode().equals(tm.getCode())){
                        obList.remove(tm);
                        calculateTotal();
                        tblItemList.refresh();
                        return;
                    }
                }
            }
        });
    }

    private int isAlreadyExists(String code){
        for (int i = 0; i < obList.size(); i++) {
            if (obList.get(i).getCode().equals(code)){
                return i;
            }
        }
        return -1;
    }

    private void calculateTotal(){
        double total = 0.00;
        for (CartTM tm:obList
             ) {
            total += tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void clearFields() {
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtQty.clear();
    }


    public void placeOrderOnAction(ActionEvent actionEvent) {
        if (obList.isEmpty()) return;
        ArrayList<ItemDetailsDTO> details = new ArrayList<>();
        for (CartTM tm: obList
             ) {
            details.add(new ItemDetailsDTO(tm.getCode(),tm.getUnitPrice(), tm.getQty()));
        }
        OrderDTO order = new OrderDTO(
                txtOrderId.getText(), new Date(),
                Double.parseDouble(lblTotal.getText()),
                cmbCustomerId.getValue(),details
        );
        Database.orderTable.add(order);
        manageQty();
        clearAll();
    }

    private void manageQty() {
        for (CartTM tm:obList
             ) {
            for (ItemDTO i: Database.itemTable
                 ) {
                if (i.getCode().equals(tm.getCode())){
                    i.setQtyOnHand(i.getQtyOnHand()-tm.getQty());
                    break;
                }
            }
        }
    }

    private void clearAll(){
        obList.clear();
        calculateTotal();

        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();

        //=================
        cmbCustomerId.setValue(null);
        cmbItemCode.setValue(null);
        //=================

        clearFields();
        cmbCustomerId.requestFocus();
        setOrderId();
    }
}
