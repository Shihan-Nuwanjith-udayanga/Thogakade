package com.seekerscloud.pos.db;

import com.seekerscloud.pos.model.CustomerDTO;
import com.seekerscloud.pos.model.ItemDTO;
import com.seekerscloud.pos.model.OrderDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","Shihan@1998");
    }

    public static DBConnection getDatabase() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? new DBConnection():dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }

    public static ArrayList<CustomerDTO> customerTable = new ArrayList<>();
    public static ArrayList<ItemDTO> itemTable = new ArrayList<>();
    public static ArrayList<OrderDTO> orderTable = new ArrayList<>();

    static {
        customerTable.add(new CustomerDTO("C001","Shihan","Galle",85000));
        customerTable.add(new CustomerDTO("C002","Kamal","Matara",45000));
        customerTable.add(new CustomerDTO("C003","Amal","Colombo",25000));
        customerTable.add(new CustomerDTO("C004","Nimal","Kaluthara",30000));
        customerTable.add(new CustomerDTO("C005","Sunil","Galle",45000));

        itemTable.add(new ItemDTO("I-001","Description 1",25,20));
        itemTable.add(new ItemDTO("I-002","Description 2",35,50));
        itemTable.add(new ItemDTO("I-003","Description 3",45,200));
        itemTable.add(new ItemDTO("I-004","Description 4",20,70));
        itemTable.add(new ItemDTO("I-005","Description 5",65,80));
    }
}
