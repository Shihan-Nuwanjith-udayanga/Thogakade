package com.seekerscloud.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.seekerscloud.pos.db.Database;
import com.seekerscloud.pos.model.Customer;
import com.seekerscloud.pos.view.tm.CustomerTM;
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
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerFormContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn<Customer, String> colId;
    public TableColumn<Customer, String> colName;
    public TableColumn<Customer, String> colAddress;
    public TableColumn<Customer, String> colSalary;
    public TableColumn<Customer, Button> colOption;
    public JFXButton btnSaveCustomer;
    public TextField txtSearch;
    private String searchText = "";

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        searchCustomers(searchText);
        clearFields();

        //lister
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) { // newValue!=null
                setData(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            searchCustomers(searchText);
        });
    }

    private void setData(CustomerTM tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtSalary.setText(String.valueOf(tm.getSalary()));
        btnSaveCustomer.setText("Update Customer");

    }

    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/" + ui + ".fxml")))));
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(txtId.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")) {

            //==================Database===================
            try {
                //1 step [driver load ram]
                Class.forName("com.mysql.cj.jdbc.Driver");
                //2 step [create connection]
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "Shihan@1998");
                String sql = "INSERT INTO Customer VALUES (?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, c1.getId());
                statement.setString(2, c1.getName());
                statement.setString(3, c1.getAddress());
                statement.setDouble(4, c1.getSalary());
                //3 step [create statement]
//                Statement statement = connection.createStatement();
                //4 step [create query]
//                String sql = "INSERT INTO Customer VALUES('"+c1.getId()+"','"+c1.getName()+"','"+c1.getAddress()+"','"+c1.getSalary()+"')";
                //5 step [statement execute]
//                int isSaved = statement.executeUpdate(sql);

                if (statement.executeUpdate() > 0) {
                    searchCustomers(searchText);
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            // =================================================

//            boolean isSaved = Database.customerTable.add(c1);
           /* if (isSaved) {
                searchCustomers(searchText);
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }*/
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "Shihan@1998");
                String sql = "UPDATE Customer SET name=?,address=?,salary=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, c1.getName());
                statement.setString(2, c1.getAddress());
                statement.setDouble(3, c1.getSalary());
                statement.setString(4, c1.getId());
                if (statement.executeUpdate() > 0) {
                    searchCustomers(searchText);
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated !").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }catch (ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
            /*for (int i = 0; i < Database.customerTable.size(); i++) {
                if (txtId.getText().equalsIgnoreCase(Database.customerTable.get(i).getId())) {
                    Database.customerTable.get(i).setName(txtName.getText());
                    Database.customerTable.get(i).setAddress(txtAddress.getText());
                    Database.customerTable.get(i).setSalary(Double.parseDouble(txtSalary.getText()));
                    searchCustomers(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                    clearFields();
                }
            }*/
        }
    }

    private void searchCustomers(String text) {
        String searchText = "%"+text+"%";
        try {
            ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "Shihan@1998");
//            String sql = "SELECT * FROM Customer";
            String sql = "SELECT * FROM Customer WHERE name LIKE ? || address LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,searchText);
            statement.setString(2,searchText);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(
                        set.getString(1),
                        set.getString(2),
                        set.getString(3),
                        set.getDouble(4),
                        btn);
                tmList.add(tm);

                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure whether do you want to delete this customer?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "Shihan@1998");
                            String sql1 = "DELETE FROM Customer WHERE id=?";
                            PreparedStatement statement1 = connection1.prepareStatement(sql1);
                            statement1.setString(1,tm.getId());
                            if (statement1.executeUpdate()>0) {
                                searchCustomers(searchText);
                                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                       /* boolean isDeleted = Database.customerTable.remove(c);
                        if (isDeleted) {
                            searchCustomers(searchText);
                            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }*/
                    }
                });
            }
            tblCustomer.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        /*ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        for (Customer c : Database.customerTable
        ) {
            if (c.getName().contains(text) || c.getAddress().contains(text)) {
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(c.getId(), c.getName(), c.getAddress(), c.getSalary(), btn);
                tmList.add(tm);

                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure whether do you want to delete this customer?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        boolean isDeleted = Database.customerTable.remove(c);
                        if (isDeleted) {
                            searchCustomers(searchText);
                            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    }
                });
            }
        }
        tblCustomer.setItems(tmList);*/
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
    }
}
