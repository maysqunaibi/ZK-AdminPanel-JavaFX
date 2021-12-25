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

public class Office<T> extends DynamicTable{
    private String officeCode = "7";
    private String city;
    private String state;
    private String country;

    public Office() {
        this.officeCode =Integer.toString(Integer.parseInt(this.officeCode) + 1);
    }

    public Office(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        if(1 <= Integer.parseInt(officeCode) && Integer.parseInt(officeCode)  <= 7 ){
            this.officeCode = officeCode;
        }
        else{
            this.officeCode = "1";
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public void insertOffice() throws SQLException {
        String INSERT_QUERY = "insert into offices values(?,?,?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setString(1, this.getOfficeCode());
            stmt.setString(2, this.getCity());
            stmt.setString(3, this.getState());
            stmt.setString(4, this.getCountry());

            // Step 3: Execute the query or update query
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New offices has been added");
            alert.setHeaderText(null);
            alert.showAndWait();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            // print SQL exception information
            DBConnect.printSQLException(e);
        }
    }

    public TableView<T> getTotalOfficesSalary(){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL = "SELECT offices.officeCode, SUM(payments.amount) FROM offices, employees, customers, payments WHERE offices.officeCode = employees.officeCode AND employees.employeeNumber = customers.salesRepEmployeeNumber AND customers.customerNumber = payments.customerNumber GROUP BY(offices.officeCode)";
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
