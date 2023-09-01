package com.seekerscloud.pos.controller;

import com.seekerscloud.pos.db.DBConnection;
import com.seekerscloud.pos.model.ItemDetailsDTO;
import com.seekerscloud.pos.model.OrderDTO;
import com.seekerscloud.pos.view.tm.ItemDetailsTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ItemDetailsFormController {
    public AnchorPane orderItemDetailsContext;
    public TableView<ItemDetailsTM> tblOrderItemDetails;
    public TableColumn<ItemDetailsTM,String> colItemCode;
    public TableColumn<ItemDetailsTM, Double> colUnitPrice;
    public TableColumn<ItemDetailsTM, Integer> colQty;
    public TableColumn<ItemDetailsTM, Double> colTotal;

    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void loadOrderDetails(String id){
        for (OrderDTO o: DBConnection.orderTable
             ) {
            if (o.getOrderId().equals(id)){
                ObservableList<ItemDetailsTM> tmList = FXCollections.observableArrayList();
                for (ItemDetailsDTO d: o.getItemDetails()
                ){
                    double tempUnitPrice = d.getUnitPrice();
                    int tempQtyOnHand = d.getQty();
                    double tempTotal = tempQtyOnHand*tempUnitPrice;
                    tmList.add(new ItemDetailsTM(d.getCode(),d.getUnitPrice(),d.getQty(),tempTotal));
                }
                tblOrderItemDetails.setItems(tmList);
                return;
            }
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) orderItemDetailsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/DashboardForm.fxml")))));
    }
}
