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

public class Employee<T> extends DynamicTable{
    private int employeeNumber = 3000;
    private String lastName;
    private String  firstName;
    private String officeCode;
    private int reportsTo;
    private String jobTitle;

    public Employee(){
        this.employeeNumber = this.employeeNumber + 1;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public int getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(int reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public void insertEmployee() throws SQLException {
        String INSERT_QUERY = "insert into employees values(?,?,?,?,?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setInt(1, this.getEmployeeNumber());//1 specifies the first parameter in the query
            stmt.setString(2, this.getLastName());
            stmt.setString(3, this.getFirstName());
            stmt.setString(4, this.getOfficeCode());
            stmt.setInt(5, this.getReportsTo());
            stmt.setString(6, this.getJobTitle());

            System.out.println(stmt);
            // Step 3: Execute the query or update query
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New employee has been added");
            alert.setHeaderText(null);
            alert.showAndWait();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            // print SQL exception information
            DBConnect.printSQLException(e);
        }
    }


    public TableView<T> getEmployeeOrder(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL = "SELECT employees.lastName, employees.firstName, orders.orderNumber, orders.orderDate FROM `employees`, `customers`,`orders` WHERE employees.employeeNumber = customers.salesRepEmployeeNumber AND customers.customerNumber = orders.customerNumber";

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
    public TableView<T> getTotalEmployeePrice(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL = "select employees.firstName, SUM(products.buyPrice) FROM employees,customers,orders,orderdetails,products WHERE employees.employeeNumber= customers.salesRepEmployeeNumber AND customers.customerNumber=orders.customerNumber AND orders.orderNumber =orderdetails.orderNumber AND orderdetails.productCode=products.productCode Group BY(employees.employeeNumber)";

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
    public TableView<T> getuselessEmployees(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL ="SELECT employees.firstName FROM employees LEFT JOIN customers ON customers.salesRepEmployeeNumber = employees.employeeNumber WHERE customers.salesRepEmployeeNumber IS NULL";
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
    public TableView<T> getEmployeeswithProducts(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL ="SELECT employees.firstName FROM employees LEFT JOIN customers ON customers.salesRepEmployeeNumber = employees.employeeNumber WHERE customers.salesRepEmployeeNumber IS NULL";
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
