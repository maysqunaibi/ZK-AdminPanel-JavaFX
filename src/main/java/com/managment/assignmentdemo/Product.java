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

public class Product<T> extends DynamicTable{
    private String productCode;
    private String productName;
    private String productLine;
    private String productDescription;
    private int quantityInStock;
    private Double buyPrice;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void insertProduct() throws SQLException {
        String INSERT_QUERY = "insert into products values(?,?,?,?,?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setString(1, this.getProductCode());//1 specifies the first parameter in the query
            stmt.setString(2, this.getProductName());
            stmt.setString(3, this.getProductLine());
            stmt.setString(4, this.getProductDescription());
            stmt.setInt(5, this.getQuantityInStock());
            stmt.setDouble(6, this.getBuyPrice());
            // Step 3: Execute the query or update query
            stmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New product has been added");
            alert.setHeaderText(null);
            alert.showAndWait();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            // print SQL exception information
            DBConnect.printSQLException(e);
        }
    }
    public TableView<T> getProductsByCustomersCountry(String cityName, String countryName){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            if(cityName.equals(null)){
                 SQL = "SELECT productName,quantityInStock ,buyPrice, city, country FROM products, orders,orderdetails,customers WHERE customers.customerNumber = orders.customerNumber AND orders.orderNumber = orderdetails.orderNumber AND orderdetails.productCode = products.productCode AND customers.city = \'"+cityName+"\'";
            }
            else if(countryName.equals(null)){
                 SQL = "SELECT productName,quantityInStock ,buyPrice, city, country FROM products, orders,orderdetails,customers WHERE customers.customerNumber = orders.customerNumber AND orders.orderNumber = orderdetails.orderNumber AND orderdetails.productCode = products.productCode AND customers.country = \'"+countryName+"\'";
            }else{
                SQL = "SELECT productName,quantityInStock ,buyPrice, city,country FROM products, orders,orderdetails,customers WHERE customers.customerNumber = orders.customerNumber AND orders.orderNumber = orderdetails.orderNumber AND orderdetails.productCode = products.productCode AND (customers.country = \'"+countryName+"\' OR customers.city = \'"+cityName+"\' )";
            }
            //SQL FOR SELECTING ALL THE ELEMENTS IN THE TABLE PASSED
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            System.out.println("num of colm: " + rs.getMetaData().getColumnCount());
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

            //FINALLY, ADDED TO TableView
            tableviewnew.setItems(data);
            tableviewnew.setTableMenuButtonVisible(true);
            c.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return tableviewnew;
    }
    public TableView<T> getProductsByPrice(String price){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();

                int priceNum = Integer.parseInt(price);
                SQL = "SELECT * FROM products WHERE buyPrice > "+ priceNum;

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
    public TableView<T> getProductsByDescription(String desc){
        Connection c ;
        TableView<T> tableviewnew = new TableView<T>();
        ObservableList<T> data = FXCollections.observableArrayList();
        String SQL;
        try{
            c = DBConnect.connect();
            SQL = "SELECT * FROM `products` WHERE `productDescription` LIKE '%"+desc+"%'";

            //SQL FOR SELECTING ALL THE ELEMENTS IN THE TABLE PASSED
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableviewnew.getColumns().addAll(col);
            }

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
