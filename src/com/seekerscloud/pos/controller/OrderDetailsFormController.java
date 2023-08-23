package com.seekerscloud.pos.controller;

import com.seekerscloud.pos.db.Database;
import com.seekerscloud.pos.model.OrderDTO;
import com.seekerscloud.pos.view.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void initialize(){
        loadOrders();

        colId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadOrders() {
        ObservableList<OrderTM> tmList = FXCollections.observableArrayList();
        for (OrderDTO o: Database.orderTable
             ) {
            Button btn = new Button("View More");
            OrderTM tm = new OrderTM(o.getOrderId(),o.getCustomer(),o.getDate(),o.getTotalCost(),btn);
            tmList.add(tm);

            btn.setOnAction(e ->{
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ItemDetailsForm.fxml"));
                    Parent parent = loader.load();
                    ItemDetailsFormController controller = loader.getController();
                    controller.loadOrderDetails(tm.getOrderId());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

        }
        tblOrders.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)orderDetailContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/DashboardForm.fxml")))));
    }
}
