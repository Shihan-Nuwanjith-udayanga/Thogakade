package com.seekerscloud.pos.dao;

import com.seekerscloud.pos.db.DBConnection;
import com.seekerscloud.pos.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl {
    public void getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomer = new ArrayList();

        Connection connection = DBConnection.getDatabase().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer WHERE name LIKE ? || address LIKE ?");
        while (rst.next()){

        }
    }
}
