package com.managment.assignmentdemo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Customers<T> extends DynamicTable{
    private int customerNumber = 400;
    private String customerName;
    private String city;
    private String country;
    private int salesRepEmployeeNumber;
    private Double creditLimit;

    public Customers() {
    }

    public Customers(String customerName, String city, String country, int salesRepEmployeeNumber, Double creditLimit) {
        this.customerNumber = this.customerNumber + 1;
        this.customerName = customerName;
        this.city = city;
        this.country = country;
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        this.creditLimit = creditLimit;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSalesRepEmployeeNumber() {
        return salesRepEmployeeNumber;
    }

    public void setSalesRepEmployeeNumber(int salesRepEmployeeNumber) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
    public void insertCustomer() throws SQLException {
        String INSERT_QUERY = "insert into customers values(?,?,?,?,?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setInt(1, this.getCustomerNumber());
            stmt.setString(2, this.getCustomerName());
            stmt.setString(3, this.getCity());
            stmt.setString(4, this.getCountry());
            stmt.setInt(5, this.getSalesRepEmployeeNumber());
            stmt.setDouble(6, this.getCreditLimit());

            System.out.println(stmt);
            // Step 3: Execute the query or update query
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New customer has been added");
            alert.setHeaderText(null);
            alert.showAndWait();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            // print SQL exception information
            DBConnect.printSQLException(e);
        }
    }
    public Integer getCustomersByCredit(String from, String to){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        String SQL;
        Integer result = 0;
        try{
            c = DBConnect.connect();
            SQL = "SELECT COUNT(customers.customerNumber) FROM customers WHERE creditLimit BETWEEN "+Integer.parseInt(from)+ " AND "+ Integer.parseInt(to);
            //SQL FOR SELECTING ALL THE ELEMENTS IN THE TABLE PASSED
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                result = Integer.parseInt(rs.getString(1));
                System.out.println("Test value: "+rs.getString(1));
            }
            c.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return result;
    }

    public TableView<T> getCustomersProducts(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL = "SELECT `customerName` ,`productName` FROM `products`, `customers`, `orders`,`orderdetails` WHERE customers.customerNumber = orders.customerNumber AND orders.orderNumber = orderdetails.orderNumber AND orderdetails.productCode = products.productCode;";

            //SQL FOR SELECTING ALL THE ELEMENTS IN THE TABLE PASSED
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableviewnew.getColumns().addAll(col);
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i)!= null? rs.getString(i) :" null " );
                }
                data.add((T) row);
            }
            tableviewnew.setItems(data);
            tableviewnew.setTableMenuButtonVisible(true);
            c.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return tableviewnew;
    }

}
