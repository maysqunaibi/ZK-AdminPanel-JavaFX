package com.managment.assignmentdemo;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Payment extends DynamicTable{
    private int customerNumber;
    private String checkNumber;
    private Date paymentDate;
    private Double amount;

    public Payment() {
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void insertPayment() throws SQLException {
        String INSERT_QUERY = "insert into payments values(?,?,?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setInt(1, this.getCustomerNumber());
            stmt.setString(2, this.getCheckNumber());
            stmt.setDate(3, this.getPaymentDate());
            stmt.setDouble(4, this.getAmount());
            // Step 3: Execute the query or update query
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New Payment has been added");
            alert.setHeaderText(null);
            alert.showAndWait();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            // print SQL exception information
            DBConnect.printSQLException(e);
        }
    }
}
