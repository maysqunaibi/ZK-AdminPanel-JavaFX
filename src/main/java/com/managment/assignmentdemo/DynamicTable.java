package com.managment.assignmentdemo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DynamicTable<T> {
    private TableView<T> tableview;
    private ObservableList<T> data;


    public TableView<T> getTableview() {
        return tableview;
    }

    public void setTableview(TableView<T> tableview) {
        this.tableview = tableview;
    }

    public ObservableList<T> getData() {
        return data;
    }

    public void setData(ObservableList<T> data) {
        this.data = data;
    }




    public void buildData(String tableName){
        Connection c ;

        try{
            c = DBConnect.connect();
            //SQL FOR SELECTING ALL THE ELEMENTS IN THE TABLE PASSED
            String SQL = "SELECT * from " + tableName;
            ResultSet rs = c.createStatement().executeQuery(SQL);
            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            System.out.println("num of colm: "+rs.getMetaData().getColumnCount());
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                this.tableview.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
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
                this.data.add((T) row);
            }

            //FINALLY, ADDED TO TableView
            this.tableview.setItems(this.data);
            this.tableview.setTableMenuButtonVisible(true);
            c.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    /********************************
     * GET THE FOREIGN KEY OPTIONS YOU WANT FROM ANY TABLE *
     ********************************/
    public ArrayList<String> getForeignKey(String foreignKey, String reference) throws SQLException {
        Connection c = DBConnect.connect();
        ArrayList<String> result = new ArrayList<>();
        String SELECT_QUERY = "select " + foreignKey + " from "+ reference;
        System.out.println(SELECT_QUERY);
        PreparedStatement stmt=c.prepareStatement(SELECT_QUERY);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            result.add(rs.getString(1));
        }
        return  result;
    }
    /********************************
     * RELOAD DATA AFTER ADDING NEW TUPLE TO THE TABLE*
     ********************************/
    public void refreshTable(String tableName){
        this.data.clear();
        this.tableview.getColumns().clear();
       this.buildData(tableName);
    }

}
