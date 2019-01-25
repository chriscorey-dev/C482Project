package c482project;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class C482Project extends Application {
    
    private static Stage primaryStage;
        
    private static Inventory inv = new Inventory();
    
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
        
//        primaryStage.setScene(addPartScene());
        primaryStage.setScene(mainScreenScene());
        primaryStage.show();
    }
    
    static Scene mainScreenScene() {
        primaryStage.setTitle("C482 - Main Menu");
        
        // TEST: Display all parts
        System.out.print("Parts: ");
        for (int i = 0; i < inv.getAllParts().size(); i++) {
            System.out.print(inv.getAllParts().get(i).getName() + ", ");
        }
        System.out.println();
        
        // Setting up layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        
        
        
        
        
        
        
        // TableView
        TableView<Part> partsTable= new TableView<Part>();
        GridPane.setConstraints(partsTable, 0, 0);
//        partsTable.setMinWidth(200);
        
        TableColumn<Part, Integer> idColumn = new TableColumn<>("Part ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        
        TableColumn<Part, Integer> nameColumn = new TableColumn<>("Part Name");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Part, Integer> invColumn = new TableColumn<>("Inventory Level");
        invColumn.setMinWidth(50);
        invColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        
        TableColumn<Part, Integer> priceColumn = new TableColumn<>("Price/Cost per Unit");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.setItems(getParts());
        partsTable.getColumns().addAll(idColumn, nameColumn, invColumn, priceColumn);
        
        

        // Objects
        Button addPartButton = new Button("Add Part");
        GridPane.setConstraints(addPartButton, 0, 1);
        addPartButton.setOnAction(e -> primaryStage.setScene(addPartScene(0)));
        
        Button modPartButton = new Button("Modify Part");
        GridPane.setConstraints(modPartButton, 0, 2);
        modPartButton.setOnAction(e -> {
            primaryStage.setScene(addPartScene(partsTable.getSelectionModel().getSelectedItem().getPartID()));
            partsTable.setItems(getParts());
        });
        
        Button deletePartButton = new Button("Delete Part");
        GridPane.setConstraints(deletePartButton, 0, 3);
        deletePartButton.setOnAction(e -> {
            // TODO: Maybe make a class for confirimation
            inv.deletePart(partsTable.getSelectionModel().getSelectedItem());
            partsTable.setItems(getParts());
        });
        
        Button addProdButton = new Button("Add Product");
        GridPane.setConstraints(addProdButton, 1, 1);
//        addProdButton.setOnAction(e -> primaryStage.setScene(addProdScene()));
        
        Button modProdButton = new Button("Modify Product");
        GridPane.setConstraints(modProdButton, 1, 2);
//        modProdButton.setOnAction(e -> primaryStage.setScene(modProdScene()));
        
        Button deleteProdButton = new Button("Delete Product");
        GridPane.setConstraints(deleteProdButton, 1, 3);
//        deleteProdButton.setOnAction(e -> inv.removeProduct(1));
        
        Button exitButton = new Button("Exit");
        GridPane.setConstraints(exitButton, 0, 4);
        exitButton.setOnAction(e -> primaryStage.close());
        
        
        
        
        
        

        // Adding objects to layout, and layout to scene
        grid.getChildren().addAll(addPartButton, modPartButton, deletePartButton, addProdButton, modProdButton, deleteProdButton, exitButton);
        grid.getChildren().addAll(partsTable);
        Scene scene = new Scene(grid, 600, 500);
        
        return scene;
    }
    
    
    static private ObservableList<Part> getParts() {
        // ObservableList
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.removeAll();
        parts.addAll(inv.getAllParts());
        return parts;
    }
    
    static Scene addPartScene(int partID) {
        
        // Checks if adding or modifying
        boolean newPart = (partID == 0) ? true : false;
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
        
        Label addPartLabel = new Label("Add Part");
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
        idText.setPromptText("Auto Gen - Disabled");
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
//        System.out.println("New part?: " + Boolean.toString(newPart));
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
