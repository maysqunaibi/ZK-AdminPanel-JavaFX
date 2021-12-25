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

public class ProductLine extends DynamicTable{
    private String productLine;
    private String textDescription;


    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }
    public void insertProductLine() throws SQLException {
        String INSERT_QUERY = "insert into productLine values(?,?)";
        Connection c ;
        try{
            c = DBConnect.connect();
            // Step 2:Create a statement using connection object
            PreparedStatement stmt = c.prepareStatement(INSERT_QUERY);
            stmt.setString(1, this.getProductLine());
            stmt.setString(2, this.getTextDescription());

            // Step 3: Execute the query or update query
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("New Product Line has been added");
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
