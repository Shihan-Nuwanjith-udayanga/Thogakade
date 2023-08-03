package com.seekerscloud.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.seekerscloud.pos.db.Database;
import com.seekerscloud.pos.model.Item;
import com.seekerscloud.pos.view.tm.ItemTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane itemFormContext;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQTYOnHand;
    public TextField txtSearch;
    public JFXButton btnSaveItem;
    public TableView<ItemTM> tblItem;
    public TableColumn<Item, String> colCode;
    public TableColumn<Item, String> colDescription;
    public TableColumn<Item, String> colUnitPrice;
    public TableColumn<Item, String> colQTYOnHand;
    public TableColumn<Item, Button> colOption;

    private String searchText = "";

    public void initialize() {
        searchItems(searchText);

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("uniPrice"));
        colQTYOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setData(newValue);
            }
        });

        txtSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            searchText = newValue;
            searchItems(searchText);
        }));
    }

    private void setData(ItemTM tm) {
        txtCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUniPrice()));
        txtQTYOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        btnSaveItem.setText("Update Customer");
    }

    private void searchItems(String text) {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
        for (Item i : Database.itemTable
        ) {
            if (i.getCode().contains(text) || i.getDescription().contains(text)) {
                Button btn = new Button("Delete");
                ItemTM tm = new ItemTM(i.getCode(), i.getDescription(), i.getUniPrice(), i.getQtyOnHand(), btn);
                tmList.add(tm);

                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure whether do you want to delete this Item?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        boolean isDeleted = Database.itemTable.remove(i);
                        if (isDeleted) {
                            searchItems(searchText);
                            new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Item Not Deleted!").show();
                        }
                    }
                });
            }
        }
        tblItem.setItems(tmList);
    }


    public void saveItemOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtCode.getText(), txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQTYOnHand.getText()));

        if (btnSaveItem.getText().equalsIgnoreCase("Save Item")) {
            boolean isSaved = Database.itemTable.add(item);
            if (isSaved) {
                searchItems(searchText);
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } else {
            for (int i = 0; i < Database.itemTable.size(); i++) {
                if (txtCode.getText().equalsIgnoreCase(Database.itemTable.get(i).getCode())) {
                    Database.itemTable.get(i).setDescription(txtDescription.getText());
                    Database.itemTable.get(i).setUniPrice(Double.parseDouble(txtUnitPrice.getText()));
                    Database.itemTable.get(i).setQtyOnHand(Integer.parseInt(txtQTYOnHand.getText()));
                    searchItems(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
                    clearFields();
                }
            }
        }
    }

    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) itemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/" + ui + ".fxml")))));
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnSaveItem.setText("Save Item");
    }

    private void clearFields() {
        txtCode.clear();
        txtDescription.clear();
        txtQTYOnHand.clear();
        txtUnitPrice.clear();
    }
}
