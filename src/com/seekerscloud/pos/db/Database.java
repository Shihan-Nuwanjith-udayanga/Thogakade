package com.seekerscloud.pos.db;

import com.seekerscloud.pos.model.Customer;
import com.seekerscloud.pos.model.Item;
import com.seekerscloud.pos.model.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    private static Database database;
    private final Connection connection;

    private Database() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","Shihan@1998");
    }

    public static Database getDatabase() throws SQLException, ClassNotFoundException {
        return database == null ? new Database():database;
    }

    public Connection getConnection(){
        return connection;
    }

    public static ArrayList<Customer> customerTable = new ArrayList<>();
    public static ArrayList<Item> itemTable = new ArrayList<>();
    public static ArrayList<Order> orderTable = new ArrayList<>();

    static {
        customerTable.add(new Customer("C001","Shihan","Galle",85000));
        customerTable.add(new Customer("C002","Kamal","Matara",45000));
        customerTable.add(new Customer("C003","Amal","Colombo",25000));
        customerTable.add(new Customer("C004","Nimal","Kaluthara",30000));
        customerTable.add(new Customer("C005","Sunil","Galle",45000));

        itemTable.add(new Item("I-001","Description 1",25,20));
        itemTable.add(new Item("I-002","Description 2",35,50));
        itemTable.add(new Item("I-003","Description 3",45,200));
        itemTable.add(new Item("I-004","Description 4",20,70));
        itemTable.add(new Item("I-005","Description 5",65,80));
    }
}
