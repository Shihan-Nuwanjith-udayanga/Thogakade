package com.seekerscloud.pos.controller;

import com.seekerscloud.pos.model.ItemDetails;
import com.seekerscloud.pos.model.Order;
import com.seekerscloud.pos.view.tm.OrderTM;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class OrderDetailsFormController {
    public AnchorPane orderDetailContext;
    public TableView<OrderTM> tblOrders;
    public TableColumn<OrderTM, String> colId;
    public TableColumn<OrderTM, String> colName;
    public TableColumn<OrderTM, Date> colDate;
    public TableColumn<OrderTM, Double> colTotal;
    public TableColumn<OrderTM, Button> colOption;

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)orderDetailContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/DashboardForm.fxml")))));
    }
}
