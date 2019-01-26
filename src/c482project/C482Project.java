package c482project;

import java.awt.Color;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class C482Project extends Application {
    
    // TODO:
    // Better layout
    // Tie in associated parts to products
    // Tie in in-house & outsourced to parts
    // Search bars
    // Validation & error handling
    // Testing
    // Cleanup
    
    private static Stage primaryStage;
    private static final Inventory inv = new Inventory();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("C482");
        
        
        // TEST 01
        inv.addPart(new Part(1, "Engine", 499.99, 1, 1, 5));
        inv.addPart(new Part(2, "Tire", 49.99, 4, 1, 8));
        
        
        // TEST 02
        // (ArrayList<Part> associatedParts, int productID, String name, double price, int inStock, int min, int max)
        ArrayList<Part> associatedParts = new ArrayList<>(); // TODO: Build out associated parts
        associatedParts.add(inv.lookupPart(1));
        associatedParts.add(inv.lookupPart(2));
        inv.addProduct(new Product(associatedParts, 1, "Product 1", 99.99, 4, 1, 10));
        inv.addProduct(new Product(associatedParts, 2, "Product 2", 2.99, 2, 1, 20));
        
        
        primaryStage.setScene(mainScreenScene());
        primaryStage.show();
    }
    
    static Scene mainScreenScene() {
        primaryStage.setTitle("C482 - Main Menu");
        
        // Setting up layout

        Label titleLabel = new Label("Inventory Management System");
//        Text titleText = new Text("Inventory Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        
        Label partsLabel = new Label("Parts");
        partsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Button partsSearchButton = new Button("Search");
        
        TextField partsSearchText = new TextField();
        partsSearchText.setPromptText("Search...");
        
        
        // Parts TableView
        TableView<Part> partsTable= new TableView<>();
        partsTable.setMaxHeight(150);
        
        TableColumn<Part, Integer> idColumn = new TableColumn<>("Part ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        
        TableColumn<Part, String> nameColumn = new TableColumn<>("Part Name");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Part, Integer> invColumn = new TableColumn<>("Inventory Level");
        invColumn.setMinWidth(50);
        invColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        
        TableColumn<Part, Integer> priceColumn = new TableColumn<>("Price/Cost per Unit");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.setItems(getParts());
        partsTable.getSelectionModel().selectFirst();
        partsTable.getColumns().addAll(idColumn, nameColumn, invColumn, priceColumn);
        
        // Part buttons
        Button addPartButton = new Button("Add");
        addPartButton.setOnAction(e -> primaryStage.setScene(addPartScene(0)));
        
        Button modPartButton = new Button("Modify");
        modPartButton.setOnAction(e -> {
            primaryStage.setScene(addPartScene(partsTable.getSelectionModel().getSelectedItem().getPartID()));
            partsTable.setItems(getParts());
            partsTable.getSelectionModel().selectFirst();
        });
        
        Button deletePartButton = new Button("Delete");
        deletePartButton.setOnAction(e -> {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            // TODO: Shoudn't be able to delete or modify if there are no parts
            if (ConfirmationBox.display("Are you sure you want to delete this part: " + selectedPart.getName())) {
                inv.deletePart(selectedPart);
                partsTable.setItems(getParts());
                partsTable.getSelectionModel().selectFirst();
            }
        });
        
        
        Label prodsLabel = new Label("Products");
        prodsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Button prodsSearchButton = new Button("Search");
        
        TextField prodsSearchText = new TextField();
        prodsSearchText.setPromptText("Search...");
        
        
        // Products TableView
        TableView<Product> prodsTable= new TableView<>();
        prodsTable.setMaxHeight(150);
        
        TableColumn<Product, Integer> prodIdColumn = new TableColumn<>("Product ID");
        prodIdColumn.setMinWidth(50);
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        
        TableColumn<Product, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setMinWidth(50);
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Product, Integer> prodInvColumn = new TableColumn<>("Inventory Level");
        prodInvColumn.setMinWidth(50);
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        
        TableColumn<Product, Integer> prodPriceColumn = new TableColumn<>("Price per Unit");
        prodPriceColumn.setMinWidth(50);
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        prodsTable.setItems(getProducts());
        prodsTable.getSelectionModel().selectFirst();
        prodsTable.getColumns().addAll(prodIdColumn, prodNameColumn, prodInvColumn, prodPriceColumn);
        
        
        // Product buttons
        Button addProdButton = new Button("Add");
        addProdButton.setOnAction(e -> primaryStage.setScene(addProductScene(0)));
        
        Button modProdButton = new Button("Modify");
        modProdButton.setOnAction(e -> {
            primaryStage.setScene(addProductScene(prodsTable.getSelectionModel().getSelectedItem().getProductID()));
            prodsTable.setItems(getProducts());
            prodsTable.getSelectionModel().selectFirst();
        });

        Button deleteProdButton = new Button("Delete");        deleteProdButton.setOnAction(e -> {
            Product selectedProd = prodsTable.getSelectionModel().getSelectedItem();
            // TODO: Shoudn't be able to delete or modify if there are no products
            if (ConfirmationBox.display("Are you sure you want to delete this product: " + selectedProd.getName())) {
//                inv.removeProduct(selectedProd.getProductID());
                inv.deleteProduct(selectedProd);
                prodsTable.setItems(getProducts());
                prodsTable.getSelectionModel().selectFirst();
            }
        });
        

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        
        
        // Set up layout
        HBox centerHBox = new HBox();
        
        
        VBox partsVBox = new VBox();
        partsVBox.setStyle("-fx-border-color: black");
        
        HBox partsHBox1 = new HBox();
        HBox partsHBox2 = new HBox();
        HBox partsHBox3 = new HBox();
        partsHBox1.setSpacing(10);
        partsHBox1.setPadding(new Insets(10));
        partsHBox2.setSpacing(10);
        partsHBox2.setPadding(new Insets(10));
        partsHBox3.setSpacing(10);
        partsHBox3.setPadding(new Insets(10));
        
        partsHBox1.getChildren().addAll(partsLabel, partsSearchButton, partsSearchText);
        partsHBox2.getChildren().addAll(partsTable);
        partsHBox3.getChildren().addAll(addPartButton, modPartButton, deletePartButton);
        
        partsVBox.getChildren().addAll(partsHBox1, partsHBox2, partsHBox3);
        
        
        VBox prodsVBox = new VBox();
        prodsVBox.setStyle("-fx-border-color: black");
        
        HBox prodsHBox1 = new HBox();
        HBox prodsHBox2 = new HBox();
        HBox prodsHBox3 = new HBox();
        prodsHBox1.setSpacing(10);
        prodsHBox1.setPadding(new Insets(10));
        prodsHBox2.setSpacing(10);
        prodsHBox2.setPadding(new Insets(10));
        prodsHBox3.setSpacing(10);
        prodsHBox3.setPadding(new Insets(10));
        
        prodsHBox1.getChildren().addAll(prodsLabel, prodsSearchButton, prodsSearchText);
        prodsHBox2.getChildren().addAll(prodsTable);
        prodsHBox3.getChildren().addAll(addProdButton, modProdButton, deleteProdButton);
        
        prodsVBox.getChildren().addAll(prodsHBox1, prodsHBox2, prodsHBox3);
        
        
        centerHBox.getChildren().addAll(partsVBox, prodsVBox);
        
        
        BorderPane layout = new BorderPane();
        
        AnchorPane topAnchor = new AnchorPane();
        topAnchor.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel, 8.0);
        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        layout.setTop(topAnchor);
        
        AnchorPane bottomAnchor = new AnchorPane();
        bottomAnchor.getChildren().add(exitButton);
        AnchorPane.setBottomAnchor(exitButton, 8.0);
        AnchorPane.setRightAnchor(exitButton, 5.0);
        layout.setBottom(bottomAnchor);
        
        layout.setCenter(centerHBox);
    
    
        Scene scene = new Scene(layout, 1000, 500);
        
        return scene;
    }
    
    
    // TEMP: maybe. Refreshes list view
    static ObservableList<Part> getParts() {
        // ObservableList
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.removeAll();
        parts.addAll(inv.getAllParts());
        return parts;
    }
    
    
    // TEMP: maybe. Refreshes list view
    static ObservableList<Product> getProducts() {
        // ObservableList
        ObservableList<Product> prods = FXCollections.observableArrayList();
        prods.removeAll();
        prods.addAll(inv.getAllProducts());
        return prods;
    }
    
    static Scene addPartScene(int partID) {
        
        // Checks if adding or modifying
        boolean newPart = partID == 0;
        if (newPart) {
            primaryStage.setTitle("C482 - Add Part");
        } else {
            primaryStage.setTitle("C482 - Modify Part");
        }
        
        // Setting up layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        


        // Objects
        
        Label addPartLabel = new Label();
        if (newPart) {
            addPartLabel.setText("Add Part");
        } else {
            addPartLabel.setText("Modify Part");
        }
        GridPane.setConstraints(addPartLabel, 0, 0);
//        addPartLabel.setScaleX(1.5);
//        addPartLabel.setScaleY(1.5);
        
        ToggleGroup deliveryMethodToggle = new ToggleGroup();
        
        RadioButton inHouseRadio = new RadioButton("In-House");
        GridPane.setConstraints(inHouseRadio, 1, 0);
        inHouseRadio.setToggleGroup(deliveryMethodToggle);
        inHouseRadio.setSelected(true);
        
        RadioButton outsourcedRadio = new RadioButton("Outsourced");
        GridPane.setConstraints(outsourcedRadio, 2, 0);
        outsourcedRadio.setToggleGroup(deliveryMethodToggle);
        
        Label idLabel = new Label("ID");
        GridPane.setConstraints(idLabel, 0, 1);
        TextField idText = new TextField("Auto Gen - Disabled");
        GridPane.setConstraints(idText, 1, 1);
//        idText.setPromptText("Auto Gen - Disabled");
        idText.setDisable(true);
        
        Label nameLabel = new Label("Name");
        GridPane.setConstraints(nameLabel, 0, 2);
        TextField nameText = new TextField();
        GridPane.setConstraints(nameText, 1, 2);
        nameText.setPromptText("Name");
        
        Label invLabel = new Label("Inv");
        GridPane.setConstraints(invLabel, 0, 3);
        TextField invText = new TextField();
        GridPane.setConstraints(invText, 1, 3);
        invText.setPromptText("Inv");
        
        Label priceLabel = new Label("Price/Cost");
        GridPane.setConstraints(priceLabel, 0, 4);
        TextField priceText = new TextField();
        GridPane.setConstraints(priceText, 1, 4);
        priceText.setPromptText("Price/Cost");
        
        Label maxLabel = new Label("Max");
        GridPane.setConstraints(maxLabel, 0, 5);
        TextField maxText = new TextField();
        GridPane.setConstraints(maxText, 1, 5);
        maxText.setPromptText("Max");
        
        Label minLabel = new Label("Min");
        GridPane.setConstraints(minLabel, 2, 5);
        TextField minText = new TextField();
        GridPane.setConstraints(minText, 3, 5);
        minText.setPromptText("Min");
        
        Label distributorLabel = new Label("Company Name");
        GridPane.setConstraints(distributorLabel, 0, 6);
        TextField distributorText = new TextField();
        GridPane.setConstraints(distributorText, 1, 6);
        distributorText.setPromptText("Company Name");
        
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 0, 7);
        saveButton.setOnAction(e -> {
        // TODO: Proper validation
        Part part = new Part(partID, nameText.getText(), Double.parseDouble(priceText.getText()), Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()));
            if (newPart) {
                inv.addPart(part);
            } else {
                inv.modifyPart(part);
            }
            primaryStage.setScene(mainScreenScene());
        });
        
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 1, 7);
        cancelButton.setOnAction(e -> primaryStage.setScene(mainScreenScene()));
        

        // If part already exists, populate fields
        if (!newPart) {
            Part modPart = inv.lookupPart(partID);
            idText.setText(Integer.toString(modPart.getPartID()));
            nameText.setText(modPart.getName());
            invText.setText(Integer.toString(modPart.getInStock()));
            priceText.setText(Double.toString(modPart.getPrice()));
            maxText.setText(Integer.toString(modPart.getMax()));
            minText.setText(Integer.toString(modPart.getMin()));
        }

        // Adding objects to layout, and layout to scene
        grid.getChildren().addAll(addPartLabel, idLabel, nameLabel, invLabel, priceLabel, maxLabel, minLabel, distributorLabel);
        grid.getChildren().addAll(idText, nameText, invText, priceText, maxText, minText, distributorText);
        grid.getChildren().addAll(saveButton, cancelButton);
        grid.getChildren().addAll(inHouseRadio, outsourcedRadio);
        
        Scene scene = new Scene(grid, 600, 500);
        
        return scene;
    }
    
    static Scene addProductScene(int prodID) {
        
        // Checks if adding or modifying
        boolean newProd = prodID == 0;
        if (newProd) {
            primaryStage.setTitle("C482 - Add Product");
        } else {
            primaryStage.setTitle("C482 - Modify Product");
        }
        
        // Setting up layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        


        // Objects
        
        Label addProdLabel = new Label();
        if (newProd) {
            addProdLabel.setText("Add Product");
        } else {
            addProdLabel.setText("Modify Product");
        }
        GridPane.setConstraints(addProdLabel, 0, 0);
//        addProdLabel.setScaleX(1.5);
//        addProdLabel.setScaleY(1.5);
        
        Label idLabel = new Label("ID");
        GridPane.setConstraints(idLabel, 0, 1);
        TextField idText = new TextField("Auto Gen - Disabled");
        GridPane.setConstraints(idText, 1, 1);
//        idText.setPromptText("Auto Gen - Disabled");
        idText.setDisable(true);
        
        Label nameLabel = new Label("Name");
        GridPane.setConstraints(nameLabel, 0, 2);
        TextField nameText = new TextField();
        GridPane.setConstraints(nameText, 1, 2);
        nameText.setPromptText("Name");
        
        Label invLabel = new Label("Inv");
        GridPane.setConstraints(invLabel, 0, 3);
        TextField invText = new TextField();
        GridPane.setConstraints(invText, 1, 3);
        invText.setPromptText("Inv");
        
        Label priceLabel = new Label("Price/Cost");
        GridPane.setConstraints(priceLabel, 0, 4);
        TextField priceText = new TextField();
        GridPane.setConstraints(priceText, 1, 4);
        priceText.setPromptText("Price/Cost");
        
        Label maxLabel = new Label("Max");
        GridPane.setConstraints(maxLabel, 0, 5);
        TextField maxText = new TextField();
        GridPane.setConstraints(maxText, 1, 5);
        maxText.setPromptText("Max");
        
        Label minLabel = new Label("Min");
        GridPane.setConstraints(minLabel, 2, 5);
        TextField minText = new TextField();
        GridPane.setConstraints(minText, 3, 5);
        minText.setPromptText("Min");
        
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 0, 7);
        saveButton.setOnAction(e -> {
        // TODO: Proper validation
        
        // (ArrayList<Part> associatedParts, int productID, String name, double price, int inStock, int min, int max)
        ArrayList<Part> associatedParts = new ArrayList<>();
        associatedParts.add(inv.lookupPart(1));
        associatedParts.add(inv.lookupPart(2));
        
        Product product = new Product(associatedParts, // TODO: Get associated parts working
                                      prodID,
                                      nameText.getText(),
                                      Double.parseDouble(priceText.getText()),
                                      Integer.parseInt(invText.getText()),
                                      Integer.parseInt(minText.getText()),
                                      Integer.parseInt(maxText.getText())
                                    );
            if (newProd) {
                inv.addProduct(product);
            } else {
                inv.modifyProduct(product);
            }
            primaryStage.setScene(mainScreenScene());
        });
        
        
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 1, 7);
        cancelButton.setOnAction(e -> primaryStage.setScene(mainScreenScene()));
        

        // If product already exists, populate fields
        if (!newProd) {
            Product modProd = inv.lookupProduct(prodID);
            System.out.println(modProd.getName());
            idText.setText(Integer.toString(modProd.getProductID()));
            nameText.setText(modProd.getName());
            invText.setText(Integer.toString(modProd.getInStock()));
            priceText.setText(Double.toString(modProd.getPrice()));
            maxText.setText(Integer.toString(modProd.getMax()));
            minText.setText(Integer.toString(modProd.getMin()));
        }

        // Adding objects to layout, and layout to scene
        grid.getChildren().addAll(addProdLabel, idLabel, nameLabel, invLabel, priceLabel, maxLabel, minLabel);
        grid.getChildren().addAll(idText, nameText, invText, priceText, maxText, minText);
        grid.getChildren().addAll(saveButton, cancelButton);
        
        Scene scene = new Scene(grid, 600, 500);
        
        return scene;
    }
    
    
//    static Scene loginScene() {
//        // Setting up layout
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(10,10,10,10));
//        grid.setVgap(8);
//        grid.setHgap(10);
//        
//
//
//        // Objects
//        Label loginLabel = new Label("Login");
//        GridPane.setConstraints(loginLabel, 0, 0);
//
//        Label usernameLabel = new Label("Username");
//        GridPane.setConstraints(usernameLabel, 0, 1);
//        
//        TextField usernameText = new TextField();
//        usernameText.setPromptText("username");
//        GridPane.setConstraints(usernameText, 1, 1);
//        
//        Label passwordLabel = new Label("Password");
//        GridPane.setConstraints(passwordLabel, 0, 2);
//        
//        TextField passwordText = new TextField();
//        passwordText.setPromptText("Password");
//        GridPane.setConstraints(passwordText, 1, 2);
//        
//        Button loginButton = new Button("Login");
//        GridPane.setConstraints(loginButton, 1, 3);
//        loginButton.setOnAction(e -> System.out.println(usernameText.getText() + " " + passwordText.getText()));
//            
//
//
//        // Adding objects to layout, and layout to scene
//        grid.getChildren().addAll(usernameLabel, passwordLabel, usernameText, passwordText, loginButton, loginLabel);
//        Scene scene = new Scene(grid, 300, 250);
//        
//        return scene;
//    }
}
