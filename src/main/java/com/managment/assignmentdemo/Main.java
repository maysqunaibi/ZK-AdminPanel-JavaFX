package com.managment.assignmentdemo;
import com.dlsc.formsfx.model.structure.Section;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


    public class Main extends Application {

        //TABLE VIEW AND DATA
        private ObservableList<ObservableList> data = FXCollections.observableArrayList();
        private TableView tableview;
        private Employee em;
        private Customers customer;
        private Office office;
        private Orders order;
        private Scene dashboardScene;
        private OrderDetails orderDetails;
        private Payment payment;
        private Product product;
        private ProductLine productLine;
        private BarChart chart;

        //MAIN EXECUTOR
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            tableview = new TableView();
            //Main Dashboard Scene
            VBox sideBar = new VBox();
            BorderPane dashboardLayout = new BorderPane();
            dashboardScene = new Scene(dashboardLayout, 870, 600);
            dashboardLayout.setRight(sideBar);
            BorderPane.setMargin(sideBar, new Insets(30, 30, 0, 10));


            MenuBar menuBar = new MenuBar();
            Menu aboutMenu = new Menu("About");
            ToggleGroup tgroup = new ToggleGroup();
            RadioMenuItem dbDesc = new RadioMenuItem("DataBase Description");
            RadioMenuItem devDesc = new RadioMenuItem("Developer Description");
            RadioMenuItem dashItem = new RadioMenuItem("Dashboard");
            dbDesc.setToggleGroup(tgroup);
            devDesc.setToggleGroup(tgroup);
            dashItem.setToggleGroup(tgroup);
            MenuItem exit = new MenuItem("Exit");
            exit.setOnAction(e -> Platform.exit());
            aboutMenu.getItems().addAll(dbDesc,devDesc,dashItem,new SeparatorMenuItem(), exit);

            Menu reports = new Menu("Reports");
            ToggleGroup tgroup1 = new ToggleGroup();
            RadioMenuItem products = new RadioMenuItem("Products Reports");
            RadioMenuItem customers = new RadioMenuItem("Customers Reports");
            RadioMenuItem employees = new RadioMenuItem("Employees Reports");
            RadioMenuItem offices = new RadioMenuItem("Offices Reports");
            products.setToggleGroup(tgroup1);
            customers.setToggleGroup(tgroup1);
            employees.setToggleGroup(tgroup1);
            offices.setToggleGroup(tgroup1);
            reports.getItems().addAll(products,customers,employees,offices);

//            Menu dashMenu = new Menu("Dashboard");
//            MenuItem dashItem = new MenuItem("Home");
//            dashMenu.getItems().add(dashItem);

            menuBar.getMenus().addAll(reports,aboutMenu);
            menuBar.setPadding(new Insets(15, 5,5,20));

            HBox footerBar = new HBox();
            BorderPane.setMargin(footerBar, new Insets(15,5,5,10));
            ImageView titleLogo = new ImageView(new Image("file:logo.png"));
            footerBar.setSpacing(10);
            titleLogo.setFitWidth(20);
            titleLogo.setPreserveRatio(true);
            titleLogo.setSmooth(true);
            titleLogo.setCache(true);
            Label footer = new Label("Wassilli | © 2021 ALL RIGHTS RESERVED ");
            footer.setFont(new Font("Sanserif", 10));
            footerBar.getChildren().addAll(titleLogo,footer);


            dashboardLayout.setTop(menuBar);
            dashboardLayout.setBottom(footerBar);

            //Sidebar buttons
            Button officesBtn = new Button("Offices");
            officesBtn.setFont(Font.font("SanSerif", 15));
            officesBtn.setMaxWidth(120);

            Button employeeBtn = new Button("Employees");
            employeeBtn.setFont(Font.font("SanSerif", 15));
            employeeBtn.setMaxWidth(120);

            Button customersBtn = new Button("Customers");
            customersBtn.setFont(Font.font("SanSerif", 15));
            customersBtn.setMaxWidth(120);

            Button ordersBtn = new Button("Orders");
            ordersBtn.setFont(Font.font("SanSerif", 15));
            ordersBtn.setMaxWidth(120);

            Button ordersDetailsBtn = new Button("Order Details");
            ordersDetailsBtn.setFont(Font.font("SanSerif", 15));
            ordersDetailsBtn.setMaxWidth(120);

            Button paymentsBtn = new Button("Payments");
            paymentsBtn.setFont(Font.font("SanSerif", 15));
            paymentsBtn.setMaxWidth(120);

            Button productsBtn = new Button("Products");
            productsBtn.setFont(Font.font("SanSerif", 15));
            productsBtn.setMaxWidth(120);

            Button productLinesBtn = new Button("Product Lines");
            productLinesBtn.setFont(Font.font("SanSerif", 15));
            productLinesBtn.setMaxWidth(120);


            // Employees Scene
            BorderPane employeePage = new BorderPane();
            Scene employeesScene = new Scene(employeePage, 870, 600);

            // Customers Scene
            BorderPane customerPage = new BorderPane();
            Scene customerScene = new Scene(customerPage, 870, 600);

            // Offices Scene
            BorderPane officePage = new BorderPane();
            Scene officeScene = new Scene(officePage, 870, 600);

            // Orders Scene
            BorderPane orderPage = new BorderPane();
            Scene orderScene = new Scene(orderPage, 870, 600);

            // Order Details Scene
            BorderPane orderDetailsPage = new BorderPane();
            Scene orderDetailsScene = new Scene(orderDetailsPage, 870, 600);

            // Payments Scene
            BorderPane paymentPage = new BorderPane();
            Scene paymentScene = new Scene(paymentPage, 870, 600);

            // Product Scene
            BorderPane productPage = new BorderPane();
            Scene productScene = new Scene(productPage, 870, 600);

            // ProductLines Scene
            BorderPane productLinesPage = new BorderPane();
            Scene productLinesScene = new Scene(productLinesPage, 870, 600);

/*******************************************************************************************************************************************/
            // Products Query Scene
            BorderPane productsQueryPage = new BorderPane();
            Scene productsQueryScene = new Scene(productsQueryPage, 870, 600);

            // Customers Query Scene
            BorderPane customersQueryPage = new BorderPane();
            Scene customersQueryScene = new Scene(customersQueryPage, 870, 600);

            // Employees Query Scene
            BorderPane employeesQueryPage = new BorderPane();
            Scene employeesQueryScene = new Scene(employeesQueryPage, 870, 600);

            // Offices Query Scene
            BorderPane officesQueryPage = new BorderPane();
            Scene officesQueryScene = new Scene(officesQueryPage, 870, 600);

/*******************************************************************************************************************************************/
            //TableView
            em = new Employee();
            em.setData( FXCollections.observableArrayList());
            em.setTableview(new TableView());
            em.buildData("employees");

            customer = new Customers();
            customer.setData( FXCollections.observableArrayList());
            customer.setTableview(new TableView());
            customer.buildData("customers");

            office = new Office();
            office.setData( FXCollections.observableArrayList());
            office.setTableview(new TableView());
            office.buildData("offices");

            order = new Orders();
            order.setData( FXCollections.observableArrayList());
            order.setTableview(new TableView());
            order.buildData("orders");

            orderDetails = new OrderDetails();
            orderDetails.setData( FXCollections.observableArrayList());
            orderDetails.setTableview(new TableView());
            orderDetails.buildData("orderdetails");

            payment = new Payment();
            payment.setData( FXCollections.observableArrayList());
            payment.setTableview(new TableView());
            payment.buildData("payments");

            product = new Product();
            product.setData( FXCollections.observableArrayList());
            product.setTableview(new TableView());
            product.buildData("products");

            productLine = new ProductLine();
            productLine.setData( FXCollections.observableArrayList());
            productLine.setTableview(new TableView());
            productLine.buildData("productlines");


            officesBtn.setOnAction(e -> {
                try {
                    stage.setScene(officeScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            employeeBtn.setOnAction(e -> {
                try {
                    stage.setScene(employeesScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            customersBtn.setOnAction(e -> {
                try {
                    stage.setScene(customerScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            ordersBtn.setOnAction(e -> {
                try {
                    stage.setScene(orderScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            ordersDetailsBtn.setOnAction(e -> {
                try {
                    stage.setScene(orderDetailsScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            paymentsBtn.setOnAction(e -> {
                try {
                    stage.setScene(paymentScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            productsBtn.setOnAction(e -> {
                try {
                    stage.setScene(productScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            productLinesBtn.setOnAction(e -> {
                try {
                    stage.setScene(productLinesScene);
                    stage.show();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            sideBar.getChildren().addAll(customersBtn, employeeBtn, officesBtn, ordersBtn, ordersDetailsBtn, paymentsBtn, productsBtn, productLinesBtn);
            sideBar.setSpacing(18);


/**********************************************************************************************************************************************/


            String devstr = "\n Bethlehem University" +
                    "\n Student ID: 201802559\n\n" +
                    "\n Mais Qunaibi built and developed " +
                    "\n this Admin Panel in the process of completing" +
                    "\n the Database Requirements Course.\n"+
                    "\n Mais is a passionate, self-directed learner" +
                    "\n  and Full Stack Software Engineer who enjoys " +
                    "\n creating visually appealing pages and Android apps ";

            String dbstr = "\n Database for the Wassilli System" +
                    "\n This Admin Panel was created to allow system " +
                    "\n administrators access the database and generate reports.\n" +
                    "\n The Wassilli Database architecture would provide" +
                    "\n a more efficient data system for reporting and retrieving"+
                    "\n data derived from Wassilli system contributors." +
                    "\n The system's database design is made up" +
                    "\n of definitions for database objects generated from"+
                    "\n mapping entities to tables (8 tables), attributes to columns," +
                    "\n unique identifiers to unique keys,"+
                    "\n and relationships to foreign keys.\n" +
                    "\n Tables in the database: (Employees, customers, Offices," +
                    "\n Products, ProductLines, Orders, OrderLines, Payments).";


            Pane dbgroup = new Pane();
            Text dbText = new Text(dbstr);
            dbText.setTranslateY(70);
            dbText.setTranslateX(10);
            FlowPane header = new FlowPane();
            header.setPrefHeight(55);
            header.setPadding(new Insets(10,0,5,10));
            String background = "-fx-background-color: lightblue; -fx-end-margin: 10;";
            header.setStyle(background);
            ImageView headerLogo = new ImageView(new Image("file:logo.png"));
            headerLogo.setFitWidth(40);
            headerLogo.setPreserveRatio(true);

            header.getChildren().addAll(headerLogo,getTitle("Wassilli Database"));
            dbgroup.getChildren().addAll(header,dbText);
            dbgroup.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-effect: dropshadow(gaussian, black, " + 10 + ", 0, 0, 0);"
            );
            dbText.setFont(Font.font("Sanserif", 15));
            dbgroup.setVisible(false);


            Pane devgroup = new Pane();
            Text devText = new Text(devstr);
            devText.setTranslateY(70);
            devText.setTranslateX(10);
            devText.setFont(Font.font("Sanserif", 15));

            FlowPane header2 = new FlowPane();
            header2.setPrefHeight(55);
            header2.setPadding(new Insets(10,0,5,10));
            header2.setStyle(background);

            ImageView headerAvatar = new ImageView(new Image("file:avatar.png"));
            headerAvatar.setFitWidth(40);
            headerAvatar.setPreserveRatio(true);

            header2.getChildren().addAll(headerAvatar,getTitle("Mais Qunebi"));
            devgroup.getChildren().addAll(header2,devText);
            devgroup.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-effect: dropshadow(gaussian, black, " + 10 + ", 0, 0, 0);"
            );
            devgroup.setVisible(false);

            dbDesc.setOnAction(e->{
                if(dbDesc.isSelected() && !devDesc.isSelected()){
                    dashboardLayout.setLeft(dbgroup);
                    BorderPane.setMargin(dbgroup, new Insets(30, 0, 0, 45));
                    dbgroup.setVisible(true);
                    devgroup.setVisible(false);
                    chart.setVisible(false);
                }else{
                    dbgroup.setVisible(false);
                }

            });
            devDesc.setOnAction(e->{
                if(!dbDesc.isSelected() && devDesc.isSelected()) {
                    dashboardLayout.setLeft(devgroup);
                    BorderPane.setMargin(devgroup, new Insets(30, 0, 0, 45));
                    devgroup.setVisible(true);
                    dbgroup.setVisible(false);
                    chart.setVisible(false);
                }else{
                    devgroup.setVisible(false);
                }
            });
//            dashItem.setOnAction(e->{
//                if(!dbDesc.isSelected() && !devDesc.isSelected()) {
//                    dashboardLayout.setLeft(chart);
//                    BorderPane.setMargin(chart, new Insets(30, 0, 0, 45));
//                    devgroup.setVisible(false);
//                    dbgroup.setVisible(false);
//                    chart.setVisible(true);
//                }else{
//                    chart.setVisible(false);
//                }
//            });

            products.setOnAction(e ->{
                if(products.isSelected()){
                    try {
                        stage.setScene(productsQueryScene);
                        stage.show();
                    } catch (Exception el) {
                        System.out.println(el);
                    }
                }
            });
            customers.setOnAction(e->{
                if(customers.isSelected()){
                    try {
                        stage.setScene(customersQueryScene);
                        stage.show();
                    } catch (Exception el) {
                        System.out.println(el);
                    }
                }
            });
            employees.setOnAction(e->{
                if(employees.isSelected()){
                    try {
                        stage.setScene(employeesQueryScene);
                        stage.show();
                    } catch (Exception el) {
                        System.out.println(el);
                    }
                }
            });
            offices.setOnAction(e->{
                if(offices.isSelected()){
                    try {
                        stage.setScene(officesQueryScene);
                        stage.show();
                    } catch (Exception el) {
                        System.out.println(el);
                    }
                }
            });
/******************************************************************************************************************************************/

            /********************************
             * CREATE PRODUCTS QUERY FUNCTION *
             ********************************/
            VBox queryProductInputs = new VBox(15);
            HBox ProductsQueryForm = new HBox(3);
            queryProductInputs.setPadding(new Insets(10,0,10,10));

            Label productsQueryTitle = new Label("Generate Products Reports By Custom Search Fields ");
            productsQueryTitle.setFont(new Font("Sanserif", 18));

            Label searchTitleByCountry = new Label("Search Products By Customer Country or City: ");
            searchTitleByCountry.setFont(new Font("Sanserif", 15));

            TextField customerCountry = new TextField();
            customerCountry.setFont(Font.font("Sanserif", 15));
            customerCountry.setPromptText("Customer Country: ");
            customerCountry.setMaxWidth(200);

            TextField customerCity = new TextField();
            customerCity.setFont(Font.font("Sanserif", 15));
            customerCity.setPromptText("Customer City : ");
            customerCity.setMaxWidth(200);


            Button searchCity = new Button("");
            searchCity.setGraphic(getSearchImage());

            ProductsQueryForm.getChildren().addAll(customerCity,customerCountry, searchCity);
            productsQueryPage.setLeft(queryProductInputs);
            BorderPane.setMargin(queryProductInputs, new Insets(20, 0, 10, 10));
            searchCity.setOnAction(e->{
                TableView tbv = product.getProductsByCustomersCountry(customerCity.getText(),customerCountry.getText());
                productsQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            HBox priceForm = new HBox(2);
            Label searchTitleByPrice = new Label("Search By Price: ");
            searchTitleByPrice.setFont(new Font("Sanserif", 15));

            TextField searchByPriceInput = new TextField();
            searchByPriceInput.setFont(Font.font("Sanserif", 15));
            searchByPriceInput.setMaxWidth(180);
            Button searchPriceBtn = new Button("");
            searchPriceBtn.setGraphic(getSearchImage());
            searchPriceBtn.setOnAction(e->{
                TableView tbv = product.getProductsByPrice(searchByPriceInput.getText());
                productsQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            priceForm.getChildren().addAll(searchByPriceInput,searchPriceBtn);

            HBox descForm = new HBox(2);
            Label descriptionTitleByPrice = new Label("Search By Description Keywords: ");
            descriptionTitleByPrice.setFont(new Font("Sanserif", 15));


            TextField descriptionField = new TextField();
            descriptionField.setFont(Font.font("Sanserif", 15));
            descriptionField.setPromptText("Description Keywords");
            descriptionField.setMaxWidth(200);

            Button searchDescIBtn = new Button("");
            searchDescIBtn.setGraphic(getSearchImage());

            searchDescIBtn.setOnAction(e->{
                TableView tbv = product.getProductsByDescription(descriptionField.getText());
                productsQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            descForm.getChildren().addAll(descriptionField, searchDescIBtn);

            queryProductInputs.getChildren().addAll(productsQueryTitle, searchTitleByCountry,ProductsQueryForm,searchTitleByPrice, priceForm,descriptionTitleByPrice, descForm, backBtn(stage, dashboardScene));

            /********************************
             * CREATE CUSTOMER QUERY FUNCTION *
             ********************************/
            VBox queryCustomerInputs = new VBox(15);
            HBox customersQueryForm = new HBox(3);
            queryCustomerInputs.setPadding(new Insets(10,0,10,10));

            Label customersQueryTitle = new Label("Generate Customers Reports By Custom Search Fields ");
            customersQueryTitle.setFont(new Font("Sanserif", 18));

            Label creditLimits = new Label("Select Credit Limits Range: ");
            creditLimits.setFont(new Font("Sanserif", 16));

            TextField fromLimits = new TextField();
            fromLimits.setFont(Font.font("Sanserif", 15));
            fromLimits.setPromptText("From");
            fromLimits.setMaxWidth(100);

            TextField toLimits = new TextField();
            toLimits.setFont(Font.font("Sanserif", 15));
            toLimits.setPromptText("To  ");
            toLimits.setMaxWidth(100);


            Button searchCredits = new Button("");
            searchCredits.setGraphic(getSearchImage());

            customersQueryForm.getChildren().addAll(fromLimits,toLimits, searchCredits);
            customersQueryPage.setLeft(queryCustomerInputs);
            BorderPane.setMargin(queryCustomerInputs, new Insets(20, 0, 10, 10));
            Label customerCountLabel = new Label();

            searchCredits.setOnAction(e->{
                Integer customersCount = customer.getCustomersByCredit(fromLimits.getText(), toLimits.getText());
                customerCountLabel.setText("The Number of Customers With this Range is: " + customersCount);
                customerCountLabel.setFont(new Font("Sanserif", 12));

            });
            HBox customerProductForm = new HBox(2);
            Label customerProductTitle = new Label("Get All Customer Names and Product   " +
                    "\nNames They had Ordered:");
            customerProductTitle.setFont(new Font("Sanserif", 15));

            Button searchCustPtodeBtn = new Button("Show");
            searchCustPtodeBtn.setOnAction(e->{
                TableView tbv = customer.getCustomersProducts();
                customersQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });

            customerProductForm.getChildren().addAll(customerProductTitle,searchCustPtodeBtn);
            queryCustomerInputs.getChildren().addAll(customersQueryTitle, creditLimits,customersQueryForm,customerCountLabel,customerProductForm, backBtn(stage, dashboardScene));


            /********************************
             * CREATE EMPLOYEES QUERY FUNCTION *
             ********************************/

            VBox emplyeesQueryInputs = new VBox(20);

            HBox employeeOrderForm = new HBox(2);
            Label employeeOrderTitle = new Label("Get All Employees Names and " +
                    "\nThe Orders they Facilitated:");
            employeeOrderTitle.setFont(new Font("Sanserif", 15));

            Button searchEmploOrdBtn = new Button("Show");
            searchEmploOrdBtn.setOnAction(e->{
                TableView tbv = em.getEmployeeOrder();
                employeesQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            employeeOrderForm.getChildren().addAll(employeeOrderTitle,searchEmploOrdBtn);


            HBox employeePriceForm = new HBox(2);
            Label employeePriceTitle = new Label("Get All Employees Names and " +
                    "\nTotal Prices of Products they Sold:");
            employeePriceTitle.setFont(new Font("Sanserif", 15));

            Button searchEmploPriceBtn = new Button("Show");
            searchEmploPriceBtn.setOnAction(e->{
                TableView tbv = em.getTotalEmployeePrice();
                employeesQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            employeePriceForm.getChildren().addAll(employeePriceTitle,searchEmploPriceBtn);


            HBox looslessemployeeForm = new HBox(2);
            Label looslessemployeeTitle = new Label("Get The Names of All Employees " +
                    "\nWho Don't Sell Any Product");
            looslessemployeeTitle.setFont(new Font("Sanserif", 15));

            Button searchuselessemployeeBtn = new Button("Show");
            searchuselessemployeeBtn.setOnAction(e->{
                TableView tbv = em.getuselessEmployees();
                employeesQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            looslessemployeeForm.getChildren().addAll(looslessemployeeTitle,searchuselessemployeeBtn);

            HBox employeeWithProductForm = new HBox(2);
            Label employeeWithProductTitle = new Label("Get The Names of All Employees " +
                    "\nWho Don't Sell Any Product");
            employeeWithProductTitle.setFont(new Font("Sanserif", 15));

            Button searchemployeeWithProductBtn = new Button("Show");
            searchemployeeWithProductBtn.setOnAction(e->{
                TableView tbv = em.getEmployeeswithProducts();
                employeesQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            employeeWithProductForm.getChildren().addAll(employeeWithProductTitle,searchemployeeWithProductBtn);

            employeesQueryPage.setLeft(emplyeesQueryInputs);
            BorderPane.setMargin(emplyeesQueryInputs, new Insets(30, 20, 10, 10));
            emplyeesQueryInputs.getChildren().addAll(employeeOrderForm, employeePriceForm,looslessemployeeForm, employeeWithProductForm, backBtn(stage,dashboardScene));

            /********************************
             * CREATE OFFICES QUERY FUNCTION *
             ********************************/
            VBox officesQueryInputs = new VBox(20);

            HBox officesSalaryForm = new HBox(2);
            Label officesSalaryTitle = new Label("Get All offices names and amount of money earned " +
                    "\n through their sales representatives:");
            employeeOrderTitle.setFont(new Font("Sanserif", 15));

            Button searchOfficesSalesBtn = new Button("Show");
            searchOfficesSalesBtn.setOnAction(e->{
                TableView tbv = office.getTotalOfficesSalary();
                officesQueryPage.setCenter(tbv);
                BorderPane.setMargin(tbv, new Insets(20, 20, 30, 30));
            });
            officesSalaryForm.getChildren().addAll(officesSalaryTitle,searchOfficesSalesBtn);

            officesQueryPage.setLeft(officesQueryInputs);
            BorderPane.setMargin(officesQueryInputs, new Insets(20, 0, 10, 10));
            officesQueryInputs.getChildren().addAll(officesSalaryForm, backBtn(stage,dashboardScene));
 /******************************************************************************************************************************************/


            /********************************
             * CREATE NEW EMPLOYEE FUNCTION *
             ********************************/

            //Create new Employee Form
            VBox createEmployeeForm = new VBox(15);
            createEmployeeForm.setPadding(new Insets(20, 0, 0, 20));

            TextField searchField = new TextField();
            searchField.setFont(Font.font("Sanserif", 18));
            searchField.setPromptText("Search: ");
            searchField.setMaxWidth(300);

            Label title = new Label("Create New Employee: ");
            title.setFont(new Font("Sanserif", 20));

            TextField empFname = new TextField();
            empFname.setFont(Font.font("Sanserif", 20));
            empFname.setPromptText("First Name: ");
            empFname.setMaxWidth(300);

            TextField empLname = new TextField();
            empLname.setFont(Font.font("Sanserif", 20));
            empLname.setPromptText("Last Name: ");
            empLname.setMaxWidth(300);

            TextField jobTitle = new TextField();
            jobTitle.setFont(Font.font("Sanserif", 20));
            jobTitle.setPromptText("Job Title: ");
            jobTitle.setMaxWidth(300);

            ArrayList<String> choices = em.getForeignKey( "employeeNumber", "employees");
            ObservableList empOptions = FXCollections.observableArrayList(choices);
            ComboBox reportsToList = new ComboBox(empOptions);
            reportsToList.setMaxHeight(30);
            reportsToList.setPromptText("Reports To: ");

            Button createEmpBtn = new Button("Save");
            createEmpBtn.setFont(Font.font("Sanserif", 15));
            createEmpBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createEmpBtn.setOnAction(e -> {
                try {
                    if (empFname.getText().isEmpty() || !(isValidString(empFname.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid employee first name");
                        return;
                    }
                    if (empLname.getText().isEmpty() || !(isValidString(empLname.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid employee last name");
                        return;
                    }
                    if (jobTitle.getText().isEmpty() || !(isValidString(jobTitle.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter employee job title");
                        return;
                    }
                    if (reportsToList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please select the office code");
                        return;
                    }

                    em.setLastName(empLname.getText());
                    em.setFirstName(empFname.getText());
                    em.setReportsTo(Integer.parseInt((String)reportsToList.getSelectionModel().getSelectedItem()));
                    em.setJobTitle(jobTitle.getText());
                    em.insertEmployee();
                    em.refreshTable("employees");
                    empFname.clear();
                    empLname.clear();
                    jobTitle.clear();
                    reportsToList.getSelectionModel().clearSelection();

                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            createEmployeeForm.getChildren().addAll(searchField, title, empFname, empLname, jobTitle, reportsToList, createEmpBtn, backBtn(stage, dashboardScene));
            employeePage.setLeft(createEmployeeForm);
            BorderPane.setMargin(createEmployeeForm, new Insets(20, 0, 0, 10));
            employeePage.setRight(em.getTableview());
            BorderPane.setMargin(em.getTableview(), new Insets(20, 30, 30, 0));

            FilteredList filteredData = new FilteredList(em.getData(), e -> true);
            searchField.setOnKeyReleased(e -> {
                searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? extends Object>) employee -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String LCnewValue = newValue.toLowerCase();
                        String[] emp = employee.toString().split(",");
                        if (emp[0].contains(newValue)) {
                            return true;
                        } else if (emp[1].toLowerCase().contains(LCnewValue)) {
                            return true;
                        } else if (emp[2].toLowerCase().contains(LCnewValue)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList sortedData = new SortedList(filteredData);
                sortedData.comparatorProperty().bind(em.getTableview().comparatorProperty());
                em.getTableview().setItems(sortedData);
            });

            /********************************
             * CREATE NEW Customer FUNCTION *
             ********************************/

            //Create new Employee Form
            VBox createCustomerForm = new VBox(15);
            createCustomerForm.setPadding(new Insets(10,0,0,10));

            TextField searchCustomerField = new TextField();
            searchCustomerField.setFont(Font.font("Sanserif", 18));
            searchCustomerField.setPromptText("Search: ");
            searchCustomerField.setMaxWidth(300);

            Label custmTitle = new Label("Create New Customers: ");
            custmTitle.setFont(new Font("Sanserif", 20));

            TextField customerName = new TextField();
            customerName.setFont(Font.font("Sanserif", 20));
            customerName.setPromptText("Customer Name: ");
            customerName.setMaxWidth(300);

            TextField city = new TextField();
            city.setFont(Font.font("Sanserif", 20));
            city.setPromptText("City : ");
            city.setMaxWidth(300);


            TextField country = new TextField();
            country.setFont(Font.font("Sanserif", 20));
            country.setPromptText("Country : ");
            country.setMaxWidth(300);

            TextField creditLimit = new TextField();
            creditLimit.setFont(Font.font("Sanserif", 20));
            creditLimit.setPromptText("Credit Limit : ");
            creditLimit.setMaxWidth(300);

            ArrayList<String> choicesEmpNum = customer.getForeignKey("employeeNumber","employees");
            ObservableList custOptions = FXCollections.observableArrayList(choicesEmpNum);
            ComboBox employeeNumberList = new ComboBox(custOptions);
            employeeNumberList.setMaxHeight(30);
            employeeNumberList.setPromptText("Sales Rep Employee Number : ");


            Button createCustomBtn = new Button("Save");
            createCustomBtn.setFont(Font.font("Sanserif", 15));
            createCustomBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createCustomBtn.setOnAction(e->{
                try {
                    if (customerName.getText().isEmpty() || !isValidString(customerName.getText()) ) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid Customer  Name");
                        return;
                    }
                    if (city.getText().isEmpty() || !isValidString(city.getText()) ) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid City  Name");
                        return;
                    }
                    if (country.getText().isEmpty() || !isValidString(country.getText()) ) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid Country  Name");
                        return;
                    }

                    if(employeeNumberList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please select the employee number");
                        return;
                    }
                    if (creditLimit.getText().isEmpty() || !isValidNumber(creditLimit.getText()) ) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid Credit Limit");
                        return;
                    }
                    customer.setCustomerName(customerName.getText());
                    customer.setCity(city.getText());
                    customer.setCountry(country.getText());
                    customer.setSalesRepEmployeeNumber(Integer.parseInt((String) employeeNumberList.getSelectionModel().getSelectedItem()));
                    customer.setCreditLimit(Double.parseDouble(creditLimit.getText()));
                    customer.insertCustomer();
                    customer.refreshTable("customers");
                    customerName.clear();
                    city.clear();
                    country.clear();
                    employeeNumberList.getSelectionModel().clearSelection();
                    creditLimit.clear();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createCustomerForm.getChildren().addAll(searchCustomerField,custmTitle,customerName,city,country,creditLimit, employeeNumberList,createCustomBtn,backBtn(stage, dashboardScene));
            customerPage.setLeft(createCustomerForm);
            BorderPane.setMargin(createCustomerForm, new Insets(20,0,0,15));
            customerPage.setRight(customer.getTableview());
            BorderPane.setMargin(customer.getTableview(), new Insets(20,10,10,0));

            FilteredList filteredCustomersData = new FilteredList(customer.getData(),e -> true);
            searchCustomerField.setOnKeyReleased(e -> {
                searchCustomerField.textProperty().addListener((observableValue, oldValue, newCustValue) ->{
                    filteredCustomersData.setPredicate((Predicate<? extends Object>) customer ->{
                        if(newCustValue == null || newCustValue.isEmpty()){
                            return true;
                        }
                        String LCnewValue = newCustValue.toLowerCase();
                        String[] cust = customer.toString().split(",");
                        if(cust[0].contains(newCustValue)){
                            return true;
                        }else if(cust[1].toLowerCase().contains(LCnewValue)){
                            return true;
                        }
                        else return cust[2].toLowerCase().contains(LCnewValue);
                    });
                });
                SortedList sortedCustomersData = new SortedList(filteredCustomersData);
                sortedCustomersData.comparatorProperty().bind(customer.getTableview().comparatorProperty());
                customer.getTableview().setItems(sortedCustomersData);
            });

            /********************************
             * CREATE NEW Office FUNCTION *
             ********************************/

            //Create new Employee Form
            VBox createOfficesForm = new VBox(10);
            createOfficesForm.setPadding(new Insets(10,0,0,10));

            TextField searchOfficeField = new TextField();
            searchOfficeField.setFont(Font.font("Sanserif", 18));
            searchOfficeField.setPromptText("Search: ");
            searchOfficeField.setMaxWidth(300);

            Label officeTitle = new Label("Create New Office: ");
            officeTitle.setFont(new Font("Sanserif", 20));


            TextField officeCodeCity = new TextField();
            officeCodeCity.setFont(Font.font("Sanserif", 20));
            officeCodeCity.setPromptText("City : ");
            officeCodeCity.setMaxWidth(300);


            TextField officeCodeState = new TextField();
            officeCodeState.setFont(Font.font("Sanserif", 20));
            officeCodeState.setPromptText("office Code State: ");
            officeCodeState.setMaxWidth(300);

            TextField officeCodeCountry = new TextField();
            officeCodeCountry.setFont(Font.font("Sanserif", 20));
            officeCodeCountry.setPromptText("Country : ");
            officeCodeCountry.setMaxWidth(300);


            Button createOfficeBtn = new Button("Save ");
            createOfficeBtn.setFont(Font.font("Sanserif", 17));
            createOfficeBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createOfficeBtn.setOnAction(e->{
                try {
                    if (officeCodeCity.getText().isEmpty() || !(isValidString(officeCodeCity.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid office city");
                        return;
                    }
                    if (officeCodeCountry.getText().isEmpty() || !(isValidString(officeCodeCity.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter office country");
                        return;
                    } if (officeCodeState.getText().isEmpty() || !(isValidString(officeCodeCity.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter office state");
                        return;
                    }

                    office.setState(officeCodeState.getText());
                    office.setCity(officeCodeCity.getText());
                    office.setCountry(officeCodeCountry.getText());
                    office.insertOffice();
                    office.refreshTable("offices");
                    officeCodeState.clear();
                    officeCodeCity.clear();
                    officeCodeCountry.clear();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createOfficesForm.getChildren().addAll(searchOfficeField,officeTitle,officeCodeCity,officeCodeState,officeCodeCountry,createOfficeBtn,backBtn(stage,dashboardScene));
            officePage.setLeft(createOfficesForm);
            BorderPane.setMargin(createOfficesForm, new Insets(20,0,0,15));
            officePage.setRight(office.getTableview());
            BorderPane.setMargin(office.getTableview(), new Insets(20,10,10,0));

            FilteredList filteredOfficesData = new FilteredList(office.getData(),e -> true);
            searchOfficeField.setOnKeyReleased(e -> {
                searchOfficeField.textProperty().addListener((observableValue, oldValue, newCustValue) ->{
                    filteredOfficesData.setPredicate((Predicate<? extends Object>) office ->{
                        if(newCustValue == null || newCustValue.isEmpty()){
                            return true;
                        }
                        String LCnewValue = newCustValue.toLowerCase();
                        String[] cust = office.toString().split(",");
                        if(cust[0].contains(newCustValue)){
                            return true;
                        }else if(cust[1].toLowerCase().contains(LCnewValue)){
                            return true;
                        }
                        else return cust[2].toLowerCase().contains(LCnewValue);
                    });
                });
                SortedList sortedOfficesData = new SortedList(filteredOfficesData);
                sortedOfficesData.comparatorProperty().bind(office.getTableview().comparatorProperty());
                office.getTableview().setItems(sortedOfficesData);
            });
            /********************************
             * CREATE NEW Order FUNCTION *
             ********************************/

            //Create new Employee Form
            VBox createOrdersForm = new VBox(10);
            createOrdersForm.setPadding(new Insets(10,0,0,10));

            TextField searchOrderField = new TextField();
            searchOrderField.setFont(Font.font("Sanserif", 18));
            searchOrderField.setPromptText("Search: ");
            searchOrderField.setMaxWidth(300);

            Label orderTitle = new Label("Create New Order: ");
            orderTitle.setFont(new Font("Sanserif", 20));

            DatePicker orderDate = new DatePicker(LocalDate.now());
            orderDate.setPromptText("Order Date: ");
            orderDate.setMaxWidth(300);
            orderDate.setStyle("-fx-font-size: 20");

            ArrayList<String> choicesCustomNum = customer.getForeignKey("customerNumber","customers");
            ObservableList custNumOptions = FXCollections.observableArrayList(choicesCustomNum);
            ComboBox customerNumberList = new ComboBox(custNumOptions);
            customerNumberList.setMaxHeight(30);
            customerNumberList.setPromptText("Customer Number : ");

            Button createOrderBtn = new Button("Save ");
            createOrderBtn.setFont(Font.font("Sanserif", 17));
            createOrderBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createOrderBtn.setOnAction(e->{
                try {
                    if (customerNumberList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Enter the Customer Number for this object");
                        return;
                    }
                    if (orderDate.getEditor().getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the order date");
                        return;
                    }
                    java.util.Date date = java.util.Date.from(orderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    order.setOrderDate(sqlDate);
                    order.setCustomerNumber(Integer.parseInt((String) customerNumberList.getSelectionModel().getSelectedItem()));
                    order.insertOrder();
                    order.refreshTable("orders");
                    customerNumberList.getSelectionModel().clearSelection();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createOrdersForm.getChildren().addAll(searchOrderField,orderTitle,orderDate,customerNumberList,createOrderBtn,backBtn(stage,dashboardScene));
            orderPage.setLeft(createOrdersForm);
            BorderPane.setMargin(createOrdersForm, new Insets(20,10,10,15));
            orderPage.setCenter(order.getTableview());
            BorderPane.setMargin(order.getTableview(), new Insets(20,20,10,20));

            FilteredList filteredOrdersData = new FilteredList(order.getData(),e -> true);
            searchOrderField.setOnKeyReleased(e -> {
                searchOrderField.textProperty().addListener((observableValue, oldValue, newCustValue) ->{
                    filteredOrdersData.setPredicate((Predicate<? extends Object>) order ->{
                        if(newCustValue == null || newCustValue.isEmpty()){
                            return true;
                        }
                        String LCnewValue = newCustValue.toLowerCase();
                        String[] cust = order.toString().split(",");
                        if(cust[0].contains(newCustValue)){
                            return true;
                        }else if(cust[2].toLowerCase().contains(LCnewValue)){
                            return true;
                        }
                        else return false;
                    });
                });
                SortedList sortedOrdersData = new SortedList(filteredOrdersData);
                sortedOrdersData.comparatorProperty().bind(order.getTableview().comparatorProperty());
                order.getTableview().setItems(sortedOrdersData);
            });
            /********************************
             * Add new Order Details FUNCTIONS *
             ********************************/

            //Create new Employee Form
            VBox createOrderDetailsForm = new VBox(10);
            createOrderDetailsForm.setPadding(new Insets(10,0,0,10));

            TextField searchOrderDetailsField = new TextField();
            searchOrderDetailsField.setFont(Font.font("Sanserif", 18));
            searchOrderDetailsField.setPromptText("Search: ");
            searchOrderDetailsField.setMaxWidth(300);


            ArrayList<String> choicesOrderNum = orderDetails.getForeignKey("orderNumber","orders");
            ObservableList orderNumOptions = FXCollections.observableArrayList(choicesOrderNum);
            ComboBox orderNumberList = new ComboBox(orderNumOptions);
            orderNumberList.setMaxHeight(30);
            orderNumberList.setPromptText("Order Number : ");

            ArrayList<String> choicesProductCode = orderDetails.getForeignKey("productCode","products");
            ObservableList productCodeOptions = FXCollections.observableArrayList(choicesProductCode);
            ComboBox productCodeList = new ComboBox(productCodeOptions);
            productCodeList.setMaxHeight(30);
            productCodeList.setPromptText("Product Code : ");

            TextField quantityOrdered = new TextField();
            quantityOrdered.setFont(Font.font("Sanserif", 20));
            quantityOrdered.setPromptText("Quantity Ordered: ");
            quantityOrdered.setMaxWidth(300);


            TextField priceEach = new TextField();
            priceEach.setFont(Font.font("Sanserif", 20));
            priceEach.setPromptText("Price : ");
            priceEach.setMaxWidth(300);

            TextField orderLineNumber = new TextField();
            orderLineNumber.setFont(Font.font("Sanserif", 20));
            orderLineNumber.setPromptText("Order Line Number : ");
            orderLineNumber.setMaxWidth(300);

            Button createOrderDetailsBtn = new Button("Save ");
            createOrderDetailsBtn.setFont(Font.font("Sanserif", 17));
            createOrderDetailsBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createOrderDetailsBtn.setOnAction(e->{
                try {
                    if (orderNumberList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the Order Number!");
                        return;
                    }
                    if (quantityOrdered.getText().isEmpty() || !(isValidNumber(quantityOrdered.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid quantity for the order!");
                        return;
                    }
                    if (priceEach.getText().isEmpty() || !(isValidNumber(priceEach.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid price for the order!");
                        return;
                    }
                    if (orderLineNumber.getText().isEmpty() || !(isValidNumber(orderLineNumber.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter valid order Line Number!");
                        return;
                    }
                    if (productCodeList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the Product Code that you want to add to this Order!");
                        return;
                    }
                    orderDetails.setOrderNumber(Integer.parseInt((String) orderNumberList.getSelectionModel().getSelectedItem()));
                    orderDetails.setProductCode((String) productCodeList.getSelectionModel().getSelectedItem());
                    orderDetails.setQuantityOrdered(Integer.parseInt(quantityOrdered.getText()));
                    orderDetails.setPriceEach(Double.parseDouble(priceEach.getText()));
                    orderDetails.setOrderLineNumber(Integer.parseInt(orderLineNumber.getText()));
                    orderDetails.insertOrderDetails();
                    order.refreshTable("orderdetails");
                    orderNumberList.getSelectionModel().clearSelection();
                    productCodeList.getSelectionModel().clearSelection();
                    quantityOrdered.clear();
                    priceEach.clear();
                    orderLineNumber.clear();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createOrderDetailsForm.getChildren().addAll(searchOrderDetailsField,getTitle("Add More Order Details"),orderNumberList,productCodeList,quantityOrdered,priceEach,orderLineNumber,createOrderDetailsBtn,backBtn(stage,dashboardScene));
            orderDetailsPage.setLeft(createOrderDetailsForm);
            BorderPane.setMargin(createOrderDetailsForm, new Insets(20,0,0,15));
            orderDetailsPage.setRight(orderDetails.getTableview());
            BorderPane.setMargin(orderDetails.getTableview(), new Insets(20,10,10,0));


            /********************************
             * CREATE NEW Payment FUNCTIONS *
             ********************************/

            //Create new Employee Form
            VBox createPaymentForm = new VBox(10);
            createPaymentForm.setPadding(new Insets(10,0,0,10));

            TextField searchPaymentField = new TextField();
            searchPaymentField.setFont(Font.font("Sanserif", 18));
            searchPaymentField.setPromptText("Search: ");
            searchPaymentField.setMaxWidth(300);

            ArrayList<String> choicesCustomNumPy = payment.getForeignKey("customerNumber","customers");
            ObservableList custNumOptionsPy = FXCollections.observableArrayList(choicesCustomNumPy);
            ComboBox customerPaymentNumberList = new ComboBox(custNumOptionsPy);
            customerPaymentNumberList.setMaxHeight(30);
            customerPaymentNumberList.setPromptText("Customer Number : ");

            TextField checkNumber = new TextField();
            checkNumber.setFont(Font.font("Sanserif", 20));
            checkNumber.setPromptText("Check Number : ");
            checkNumber.setMaxWidth(300);

            DatePicker paymentDate = new DatePicker(LocalDate.now());
            paymentDate.setPromptText("Payment Date: ");
            paymentDate.setMaxWidth(300);
            paymentDate.setStyle("-fx-font-size: 20");

            TextField amount = new TextField();
            amount.setFont(Font.font("Sanserif", 20));
            amount.setPromptText("Amount : ");
            amount.setMaxWidth(300);

            Button createPaymentBtn = new Button("Save ");
            createPaymentBtn.setFont(Font.font("Sanserif", 17));
            createPaymentBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createPaymentBtn.setOnAction(e->{
                try {
                    if (customerPaymentNumberList.getSelectionModel().getSelectedItem().equals(null)) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Enter the Customer Number for this payment");
                        return;
                    }
                    if (checkNumber.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the check number");
                        return;
                    }

                    if (paymentDate.getEditor().getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the payment date");
                        return;
                    }
                    if (amount.getText().isEmpty() || !isValidDouble(amount.getText())) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the payment amount");
                        return;
                    }
                    payment.setCustomerNumber(Integer.parseInt((String) customerPaymentNumberList.getSelectionModel().getSelectedItem()));
                    payment.setCheckNumber(checkNumber.getText());
                    java.util.Date paymentDateformate = java.util.Date.from(paymentDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    java.sql.Date sqlDatePayment = new java.sql.Date(paymentDateformate.getTime());
                    payment.setPaymentDate(sqlDatePayment);
                    payment.setAmount(Double.parseDouble(amount.getText()));
                    payment.insertPayment();
                    payment.refreshTable("payments");
                    customerPaymentNumberList.getSelectionModel().clearSelection();
                    checkNumber.clear();
                    paymentDate.getEditor().clear();
                    amount.clear();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createPaymentForm.getChildren().addAll(searchPaymentField,getTitle("Add New Payment"),customerPaymentNumberList,checkNumber,paymentDate,amount,createPaymentBtn,backBtn(stage,dashboardScene));
            paymentPage.setLeft(createPaymentForm);
            BorderPane.setMargin(createPaymentForm, new Insets(20,0,0,15));
            paymentPage.setRight(payment.getTableview());
            BorderPane.setMargin(payment.getTableview(), new Insets(20,10,10,0));

            FilteredList filteredPaymentsData = new FilteredList(payment.getData(),e -> true);
            searchPaymentField.setOnKeyReleased(e -> {
                searchPaymentField.textProperty().addListener((observableValue, oldValue, newCustValue) ->{
                    filteredPaymentsData.setPredicate((Predicate<? extends Object>) payment ->{
                        if(newCustValue == null || newCustValue.isEmpty()){
                            return true;
                        }
                        String LCnewValue = newCustValue.toLowerCase();
                        String[] cust = payment.toString().split(",");
                        if(cust[0].contains(newCustValue)){
                            return true;
                        }else if(cust[2].toLowerCase().contains(LCnewValue)){
                            return true;
                        }
                        else if(cust[3].toLowerCase().contains(LCnewValue)){
                            return true;
                        }
                        else return false;
                    });
                });
                SortedList sortedPaymentData = new SortedList(filteredPaymentsData);
                sortedPaymentData.comparatorProperty().bind(payment.getTableview().comparatorProperty());
                payment.getTableview().setItems(sortedPaymentData);
            });
            /********************************
             * CREATE NEW PRODUCT FUNCTIONS *
             ********************************/

            //Create new Employee Form
            VBox createProductForm = new VBox(10);
            createProductForm.setPadding(new Insets(10, 0, 0, 20));

            TextField searchProductField = new TextField();
            searchProductField.setFont(Font.font("Sanserif", 18));
            searchProductField.setPromptText("Search: ");
            searchProductField.setMaxWidth(300);

            TextField productCode = new TextField();
            productCode.setFont(Font.font("Sanserif", 20));
            productCode.setPromptText("Product Code ");
            productCode.setMaxWidth(300);

            TextField productName = new TextField();
            productName.setFont(Font.font("Sanserif", 20));
            productName.setPromptText("Product Name: ");
            productName.setMaxWidth(300);

            ArrayList<String> productLineChoices = em.getForeignKey( "productLine", "productlines");
            ObservableList productLineOptions = FXCollections.observableArrayList(productLineChoices);
            ComboBox productLineList = new ComboBox(productLineOptions);
            productLineList.setMaxHeight(30);
            productLineList.setPromptText("Product Line: ");

            TextArea productDescription = new TextArea();
            productDescription.setFont(Font.font("Sanserif", 20));
            productDescription.setPromptText("Product Description: ");
            productDescription.setMaxWidth(300);

            TextField quantityInStock = new TextField();
            quantityInStock.setFont(Font.font("Sanserif", 20));
            quantityInStock.setPromptText("Product Quantity In Stock: ");
            quantityInStock.setMaxWidth(300);

            TextField buyPrice = new TextField();
            buyPrice.setFont(Font.font("Sanserif", 20));
            buyPrice.setPromptText("Product Price: ");
            buyPrice.setMaxWidth(300);

            Button createProductBtn = new Button("Save");
            createProductBtn.setFont(Font.font("Sanserif", 15));
            createProductBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray;");
            createProductBtn.setOnAction(e -> {
                try {
                    if (productCode.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter unique product code: ");
                        return;
                    }
                    if (productName.getText().isEmpty() || !(isValidString(productName.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter Valid product name");
                        return;
                    }
                    if (productLineList.getSelectionModel().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please add Valid product line");
                        return;
                    }
                    if (productDescription.getText().isEmpty() || !(isValidString(productDescription.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid  Product Description");
                        return;
                    }
                    if (quantityInStock.getText().isEmpty() || !(isValidNumber(quantityInStock.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid Product Quantity");
                        return;
                    }
                    if (buyPrice.getText().isEmpty() || !(isValidDouble(buyPrice.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please Enter Valid Product Price");
                        return;
                    }

                    product.setProductCode(productCode.getText());
                    product.setProductName(productName.getText());
                    product.setProductLine((String)productLineList.getSelectionModel().getSelectedItem());
                    product.setProductDescription(productDescription.getText());
                    product.setQuantityInStock(Integer.parseInt(quantityInStock.getText()));
                    product.setBuyPrice(Double.parseDouble(buyPrice.getText()));
                    product.insertProduct();
                    product.refreshTable("products");
                    productCode.clear();
                    productName.clear();
                    quantityInStock.clear();
                    productLineList.getSelectionModel().clearSelection();
                    productDescription.clear();
                    buyPrice.clear();
                } catch (Exception el) {
                    System.out.println(el);
                }
            });
            createProductForm.getChildren().addAll(searchProductField, getTitle("Add New Product"), productCode, productName, productLineList, productDescription,quantityInStock, buyPrice, createProductBtn, backBtn(stage, dashboardScene));
            productPage.setLeft(createProductForm);
            BorderPane.setMargin(createProductForm, new Insets(10, 0, 0, 10));
            productPage.setRight(product.getTableview());
            BorderPane.setMargin(product.getTableview(), new Insets(20, 30, 30, 0));

            FilteredList filteredProductsData = new FilteredList(product.getData(), e -> true);
            searchProductField.setOnKeyReleased(e -> {
                searchProductField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filteredProductsData.setPredicate((Predicate<? extends Object>) product -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String LCnewValue = newValue.toLowerCase();
                        String[] prod = product.toString().split(",");
                        if (prod[0].contains(newValue)) {
                            return true;
                        } else if (prod[1].toLowerCase().contains(LCnewValue)) {
                            return true;
                        } else if (prod[2].toLowerCase().contains(LCnewValue)) {
                            return true;
                        }
                        else if (prod[5].toLowerCase().contains(LCnewValue)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList sortedProductsData = new SortedList(filteredProductsData);
                sortedProductsData.comparatorProperty().bind(product.getTableview().comparatorProperty());
                product.getTableview().setItems(sortedProductsData);
            });


            /********************************
             * CREATE NEW ProductLine FUNCTIONS *
             ********************************/

            //Create new Employee Form
            VBox createProductLineForm = new VBox(10);
            createProductLineForm.setPadding(new Insets(10,0,0,10));

            TextField searchProdcutLineField = new TextField();
            searchProdcutLineField.setFont(Font.font("Sanserif", 18));
            searchProdcutLineField.setPromptText("Search: ");
            searchProdcutLineField.setMaxWidth(300);

            TextField productLineField = new TextField();
            productLineField.setFont(Font.font("Sanserif", 20));
            productLineField.setPromptText("Product Line: ");
            productLineField.setMaxWidth(300);


            TextArea productLineDescription = new TextArea();
            productLineDescription.setFont(Font.font("Sanserif", 20));
            productLineDescription.setPromptText("Product Description: ");
            productLineDescription.setMaxWidth(300);


            Button createProductLineBtn = new Button("Save ");
            createProductLineBtn.setFont(Font.font("Sanserif", 17));
            createProductLineBtn.setStyle("-fx-background-color: #B3FC78; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            createProductLineBtn.setOnAction(e->{
                try {
                    if (productLineField.getText().isEmpty()  || !(isValidString(productLineField.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Enter Valid Product Line Field");
                        return;
                    }
                    if (productLineDescription.getText().isEmpty()  || !(isValidString(productLineDescription.getText()))) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter the order date");
                        return;
                    }
                    productLine.setProductLine(productLineField.getText());
                    productLine.setTextDescription(productDescription.getText());
                    productLine.insertProductLine();
                    productLine.refreshTable("productlines");
                    productLineField.clear();
                    productDescription.clear();
                }catch (Exception el){
                    System.out.println(el);
                }
            });

            createProductLineForm.getChildren().addAll(searchProdcutLineField, getTitle("Add New Product Line"), productLineField,productLineDescription,createProductLineBtn,backBtn(stage,dashboardScene));
            productLinesPage.setLeft(createProductLineForm);
            BorderPane.setMargin(createProductLineForm, new Insets(20,10,10,15));
            productLinesPage.setCenter(productLine.getTableview());
            BorderPane.setMargin(productLine.getTableview(), new Insets(20,20,10,20));

            FilteredList filteredproductLineData = new FilteredList(productLine.getData(),e -> true);
            searchProdcutLineField.setOnKeyReleased(e -> {
                searchProdcutLineField.textProperty().addListener((observableValue, oldValue, newCustValue) ->{
                    filteredproductLineData.setPredicate((Predicate<? extends Object>) productLine ->{
                        if(newCustValue == null || newCustValue.isEmpty()){
                            return true;
                        }
                        String LCnewValue = newCustValue.toLowerCase();
                        String[] cust = productLine.toString().split(",");
                        if(cust[0].contains(LCnewValue)){
                            return true;
                        }
                        else return false;
                    });
                });
                SortedList sortedproductLineData = new SortedList(filteredproductLineData);
                sortedproductLineData.comparatorProperty().bind(productLine.getTableview().comparatorProperty());
                productLine.getTableview().setItems(sortedproductLineData);
            });

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            chart = new BarChart(xAxis, yAxis ,getChartData());
            chart.setTitle("General Statistics");
            dashboardLayout.setCenter(chart);

            Image appIcon = new Image("file:logo.png");
            stage.getIcons().addAll(appIcon);
            stage.setTitle("Wassilli Admin Panel");
            stage.setResizable(false);
            dashboardScene.setFill(Color.color(0,0,0,0));
            stage.setScene(dashboardScene);
            stage.show();
        }

        private ObservableList<XYChart.Series<String, Double>> getChartData() {
            ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
            XYChart.Series<String, Double> employees = new XYChart.Series<>();
            XYChart.Series<String, Double> customers = new XYChart.Series<>();
            XYChart.Series<String, Double> orders = new XYChart.Series<>();
            XYChart.Series<String, Double> products = new XYChart.Series<>();
            XYChart.Series<String, Double> offices = new XYChart.Series<>();
            XYChart.Series<String, Double> payments = new XYChart.Series<>();

            employees.setName("Employees");
            customers.setName("Customers");
            orders.setName("Orders");
            products.setName("Products");
            offices.setName("Offices");
            payments.setName("Payments");

            employees.getData().add(new XYChart.Data("Employees", 50));
            customers.getData().add(new XYChart.Data("Customer", 70));
            orders.getData().add(new XYChart.Data("orders", 43));
            products.getData().add(new XYChart.Data("Products", 550));
            offices.getData().add(new XYChart.Data("Offices", 170));
            payments.getData().add(new XYChart.Data("Payments", 443));

            data.addAll(employees,customers,orders, products,offices,payments);
            return data;
        }

        private static Button backBtn(Stage stage, Scene dashboardScene){

            Button back = new Button("Back");
            back.setFont(Font.font("Sanserif", 15));
            back.setStyle("-fx-background-color: #6D7C60; -fx-border-color: grey; -fx-border-radius: 5; -fx-base: LightGray");
            back.setOnAction(e -> {
                stage.setScene(dashboardScene);
                stage.show();
            });
            return back;
        }
        private static Label getTitle(String title){
            Label label = new Label(title);
            label.setFont(new Font("Sanserif", 20));
            return label;
        }
        private static ImageView getSearchImage(){
            ImageView searchImg = new ImageView(new Image("file:search.png"));
            searchImg.setFitWidth(20);
            searchImg.setPreserveRatio(true);
            searchImg.setSmooth(true);
            searchImg.setCache(true);
            return searchImg;
        }

        private static void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.show();
        }
        public boolean isValidString(String s){
            String regex="[A-Za-z\\s]+";
            return s.matches(regex);//returns true if input and regex matches otherwise false;
        }
        public boolean isValidDouble(String s){
            return s.matches("^[\\+\\-]{0,1}[0-9]+[\\.\\,][0-9]+$");
        }
        public boolean isValidNumber(String s){
            return s.matches("[0-9]+");
        }


    }