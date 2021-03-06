package c482project;

import java.text.DecimalFormat;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class C482Project extends Application {

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
        inv.addPart(new Inhouse(5, 1, "Engine", 499.99, 1, 1, 5));
        inv.addPart(new Outsourced("Big Tire Company", 2, "Tire", 29.99, 4, 1, 8));
        inv.addPart(new Inhouse(7, 3, "Handlebars", 49.99, 2, 1, 5));


        // TEST 02
        ArrayList<Part> associatedParts = new ArrayList<>();
        associatedParts.add(inv.lookupPart(1));
        associatedParts.add(inv.lookupPart(2));
        associatedParts.add(inv.lookupPart(2));
        associatedParts.add(inv.lookupPart(2));
        associatedParts.add(inv.lookupPart(2));
        inv.addProduct(new Product(associatedParts, 1, "Car", 999.99, 4, 1, 10));
        
        associatedParts = new ArrayList();
        associatedParts.add(inv.lookupPart(3));
        associatedParts.add(inv.lookupPart(2));
        associatedParts.add(inv.lookupPart(2));
        inv.addProduct(new Product(associatedParts, 2, "Bicycle", 149.99, 2, 1, 20));
        
        associatedParts = new ArrayList();
        associatedParts.add(inv.lookupPart(1));
        associatedParts.add(inv.lookupPart(3));
        associatedParts.add(inv.lookupPart(2));
        associatedParts.add(inv.lookupPart(2));
        inv.addProduct(new Product(associatedParts, 2, "Motorcycle", 799.99, 6, 1, 10));


        primaryStage.setScene(mainScreenScene());
        primaryStage.show();
    }

    static Scene mainScreenScene() {
        primaryStage.setTitle("C482 - Main Menu");

        // Setting up layout

        Label titleLabel = new Label("Inventory Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));


        Label partsLabel = new Label("Parts");
        partsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField partsSearchText = new TextField();
        partsSearchText.setPromptText("Search...");
        // Parts TavleView
        TableView<Part> partsTable = createPartTableView(inv.getAllParts());

        // Parts search
        Button partsSearchButton = new Button("Search");
        partsSearchButton.setOnAction(e -> {
            // Searching is repeated a lot. Probably could make this a method
            ArrayList<Part> searchedParts = new ArrayList<>();
            for (int i = 0; i < inv.getAllParts().size(); i++) {
                Part part = inv.getAllParts().get(i);
                if (part.getName().toLowerCase().contains(partsSearchText.getText().toLowerCase())) {
                    searchedParts.add(part);
                }
            }
            partsTable.setItems(getParts(searchedParts));
            partsTable.getSelectionModel().selectFirst();
        });
        partsSearchText.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                ArrayList<Part> searchedParts = new ArrayList<>();
                for (int i = 0; i < inv.getAllParts().size(); i++) {
                    Part part = inv.getAllParts().get(i);
                    if (part.getName().toLowerCase().contains(partsSearchText.getText().toLowerCase())) {
                        searchedParts.add(part);
                    }
                }
                partsTable.setItems(getParts(searchedParts));
                partsTable.getSelectionModel().selectFirst();
            }
        });
        
        
        
        
        // Part buttons
        Button addPartButton = new Button("Add");
        addPartButton.setOnAction(e -> primaryStage.setScene(addPartScene(0)));

        Button modPartButton = new Button("Modify");
        modPartButton.setOnAction(e -> {
            primaryStage.setScene(addPartScene(partsTable.getSelectionModel().getSelectedItem().getPartID()));
            partsTable.setItems(getParts(inv.getAllParts()));
            partsTable.getSelectionModel().selectFirst();
        });
        partsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> modPartButton.setDisable(partsTable.getItems().isEmpty()));
        modPartButton.setDisable(partsTable.getItems().isEmpty());

        Button deletePartButton = new Button("Delete");
        deletePartButton.setOnAction(e -> {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            if (ConfirmationBox.display("Are you sure you want to delete this part: " + selectedPart.getName())) {
                inv.deletePart(selectedPart);
                partsTable.setItems(getParts(inv.getAllParts()));
                partsTable.getSelectionModel().selectFirst();
            }
        });
        partsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> deletePartButton.setDisable(partsTable.getItems().isEmpty()));
        deletePartButton.setDisable(partsTable.getItems().isEmpty());


        Label prodsLabel = new Label("Products");
        prodsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField prodsSearchText = new TextField();
        prodsSearchText.setPromptText("Search...");

        // Products TableView
        TableView<Product> prodsTable = createProductTableView(inv.getAllProducts());

        Button prodsSearchButton = new Button("Search");
        prodsSearchButton.setOnAction(e -> {
            ArrayList<Product> searchedProds = new ArrayList<>();
            for (int i = 0; i < inv.getAllProducts().size(); i++) {
                Product product = inv.getAllProducts().get(i);
                if (product.getName().toLowerCase().contains(prodsSearchText.getText().toLowerCase())) {
                    searchedProds.add(product);
                }
            }
            prodsTable.setItems(getProducts(searchedProds));
            prodsTable.getSelectionModel().selectFirst();
        });
        prodsSearchText.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                ArrayList<Product> searchedProds = new ArrayList<>();
                for (int i = 0; i < inv.getAllProducts().size(); i++) {
                    Product product = inv.getAllProducts().get(i);
                    if (product.getName().toLowerCase().contains(prodsSearchText.getText().toLowerCase())) {
                        searchedProds.add(product);
                    }
                }
                prodsTable.setItems(getProducts(searchedProds));
                prodsTable.getSelectionModel().selectFirst();
            }
        });

        // Product buttons
        Button addProdButton = new Button("Add");
        addProdButton.setOnAction(e -> primaryStage.setScene(addProductScene(0)));

        Button modProdButton = new Button("Modify");
        modProdButton.setOnAction(e -> {
            primaryStage.setScene(addProductScene(prodsTable.getSelectionModel().getSelectedItem().getProductID()));
            prodsTable.setItems(getProducts(inv.getAllProducts()));
            prodsTable.getSelectionModel().selectFirst();
        });
        prodsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> modProdButton.setDisable(prodsTable.getItems().isEmpty()));
        modProdButton.setDisable(prodsTable.getItems().isEmpty());

        Button deleteProdButton = new Button("Delete");
        deleteProdButton.setOnAction(e -> {
            Product selectedProd = prodsTable.getSelectionModel().getSelectedItem();
//            // If they make a stink about having products with 0 associated parts...
//            if (!selectedProd.getAssociatedParts().isEmpty()) {
//                return;
//            }
            if (ConfirmationBox.display("Are you sure you want to delete this product: " + selectedProd.getName())) {
                inv.deleteProduct(selectedProd);
                prodsTable.setItems(getProducts(inv.getAllProducts()));
                prodsTable.getSelectionModel().selectFirst();
            }
        });
        prodsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> deleteProdButton.setDisable(prodsTable.getItems().isEmpty()));
        deleteProdButton.setDisable(prodsTable.getItems().isEmpty());


        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());


        // Set up layout
        HBox centerHBox = new HBox();


        VBox partsVBox = new VBox();

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


        // Border Top
        BorderPane layout = new BorderPane();

        AnchorPane topAnchor = new AnchorPane();
        topAnchor.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel, 0.0);
        AnchorPane.setLeftAnchor(titleLabel, 0.0);
        topAnchor.setPadding(new Insets(10));


        // Border Bottom
        AnchorPane bottomAnchor = new AnchorPane();
        bottomAnchor.getChildren().add(exitButton);
        AnchorPane.setBottomAnchor(exitButton, 0.0);
        AnchorPane.setRightAnchor(exitButton, 0.0);
        bottomAnchor.setPadding(new Insets(10));



        // Putting bottom first allows Exit to be selected automatically
        layout.setBottom(bottomAnchor);
        layout.setTop(topAnchor);
        layout.setCenter(centerHBox);


//        Scene scene = new Scene(layout, 1000, 500);
        Scene scene = new Scene(layout);
        layout.setPadding(new Insets(10, 20, 10, 20));
//        primaryStage.sizeToScene();;

        return scene;
    }
//    static private HBox searchBar(ArrayList<String> searchable) {
//        return new HBox();
//    }

//    static private TableView createPartTableView(ArrayList<Part> includedItems) {
    static private TableView createPartTableView(ArrayList<Part> items) {
//        ArrayList<Part> items = new ArrayList<Part>();
//        items.addAll(includedItems);

        TableView<Part> partsTable = new TableView<>();


        // Parts TableView
        partsTable.setMaxHeight(300);
        partsTable.setMinWidth(400);

        TableColumn<Part, Integer> idColumn = new TableColumn<>("Part ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));

        TableColumn<Part, String> nameColumn = new TableColumn<>("Part Name");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> invColumn = new TableColumn<>("Inventory Level");
        invColumn.setMinWidth(50);
        invColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn<Part, Double> priceColumn = new TableColumn<>("Price/Cost per Unit");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(getParts(items));
        partsTable.getSelectionModel().selectFirst();
        partsTable.getColumns().addAll(idColumn, nameColumn, invColumn, priceColumn);

        return partsTable;
    }

    static private TableView createProductTableView(ArrayList<Product> items) {

        TableView<Product> prodsTable = new TableView<>();
        prodsTable.setMaxHeight(300);
        prodsTable.setMinWidth(400);

        TableColumn<Product, Integer> prodIdColumn = new TableColumn<>("Product ID");
        prodIdColumn.setMinWidth(50);
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn<Product, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setMinWidth(50);
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> prodInvColumn = new TableColumn<>("Inventory Level");
        prodInvColumn.setMinWidth(50);
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn<Product, Double> prodPriceColumn = new TableColumn<>("Price per Unit");
        prodPriceColumn.setMinWidth(50);
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodsTable.setItems(getProducts(items));
        prodsTable.getSelectionModel().selectFirst();
        prodsTable.getColumns().addAll(prodIdColumn, prodNameColumn, prodInvColumn, prodPriceColumn);

        return prodsTable;
    }


    static ObservableList<Part> getParts(ArrayList<Part> parts) {
        ObservableList<Part> observableParts = FXCollections.observableArrayList();
        observableParts.removeAll();
        observableParts.addAll(parts);
        return observableParts;
    }


    static ObservableList<Product> getProducts(ArrayList<Product> products) {
        ObservableList<Product> prods = FXCollections.observableArrayList();
        prods.removeAll();
        prods.addAll(products);
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




        // Objects
        Label addPartLabel = new Label();
        addPartLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addPartLabel.setMinWidth(100);
        addPartLabel.setMaxWidth(100);

        if (newPart) {
            addPartLabel.setText("Add Part");
        } else {
            addPartLabel.setText("Modify Part");
        }

        ToggleGroup deliveryMethodToggle = new ToggleGroup();

        RadioButton inHouseRadio = new RadioButton("In-House");
        inHouseRadio.setToggleGroup(deliveryMethodToggle);
        inHouseRadio.setSelected(true);

        RadioButton outsourcedRadio = new RadioButton("Outsourced");
        outsourcedRadio.setToggleGroup(deliveryMethodToggle);
        HBox radioBox = new HBox(addPartLabel, inHouseRadio, outsourcedRadio);

        Label idLabel = new Label("ID");
        TextField idText = new TextField("Auto Gen - Disabled");
        idLabel.setMinWidth(100);
        idLabel.setMaxWidth(100);
        idText.setDisable(true);
        HBox idBox = new HBox(idLabel, idText);

        Label nameLabel = new Label("Name");
        nameLabel.setMinWidth(100);
        nameLabel.setMaxWidth(100);
        TextField nameText = new TextField();
        nameText.setPromptText("Part Name");
        HBox nameBox = new HBox(nameLabel, nameText);

        Label invLabel = new Label("Inv");
        invLabel.setMinWidth(100);
        invLabel.setMaxWidth(100);
        TextField invText = new TextField();
        invText.setPromptText("Inv");
        HBox invBox = new HBox(invLabel, invText);

        Label priceLabel = new Label("Price/Cost");
        priceLabel.setMinWidth(100);
        priceLabel.setMaxWidth(100);
        TextField priceText = new TextField();
        priceText.setPromptText("Price/Cost");
        HBox priceBox = new HBox(priceLabel, priceText);

        Label maxLabel = new Label("Max");
        maxLabel.setMinWidth(100);
        maxLabel.setMaxWidth(100);
        TextField maxText = new TextField();
        maxText.setMinWidth(100);
        maxText.setMaxWidth(100);
        maxText.setPromptText("Max");

        Label minLabel = new Label("Min");
        minLabel.setMinWidth(50);
        minLabel.setMaxWidth(50);
        TextField minText = new TextField();
        minText.setMinWidth(100);
        minText.setMaxWidth(100);
        minText.setPromptText("Min");
        HBox minMaxBox = new HBox(maxLabel, maxText, minLabel, minText);

        Label distributorLabel = new Label("Machine ID");
        distributorLabel.setMinWidth(100);
        distributorLabel.setMaxWidth(100);

        TextField distributorText = new TextField();
        distributorText.setPromptText("Mach ID");
        HBox distributorBox = new HBox(distributorLabel, distributorText);


        // TODO: Put this action in a method. It's used 4(?) times
        inHouseRadio.setOnAction(e -> {
            if (deliveryMethodToggle.getSelectedToggle() == inHouseRadio) {
                distributorLabel.setText("Machine ID");
                distributorText.setPromptText("Mach ID");
            } else {
                distributorLabel.setText("Company Name");
                distributorText.setPromptText("Comp Nm");
            }
        });
        outsourcedRadio.setOnAction(e -> {
            if (deliveryMethodToggle.getSelectedToggle() == inHouseRadio) {
                distributorLabel.setText("Machine ID");
                distributorText.setPromptText("Mach ID");
            } else {
                distributorLabel.setText("Company Name");
                distributorText.setPromptText("Comp Nm");
            }
        });


        VBox validationBox = new VBox();


        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Validation
            ArrayList<TextField> textFields = new ArrayList<>();
            textFields.add(nameText);
            textFields.add(invText);
            textFields.add(priceText);
            textFields.add(minText);
            textFields.add(maxText);
            textFields.add(distributorText);
            
            ArrayList<String> validatedItems = validatePartScene(textFields, deliveryMethodToggle.getSelectedToggle() == inHouseRadio);
            validationBox.getChildren().clear();
                    
            for (int i = 0; i < validatedItems.size(); i++) {
                validationBox.getChildren().add(new Label("• " + validatedItems.get(i)));
            }
            
            primaryStage.sizeToScene();
            
            if (!validatedItems.isEmpty()) {
                return;
            }

            Part part;

            if (deliveryMethodToggle.getSelectedToggle() == inHouseRadio) {
                part = new Inhouse(Integer.parseInt(distributorText.getText()),
                        partID,
                        nameText.getText(),
                        Double.parseDouble(priceText.getText()),
                        Integer.parseInt(invText.getText()),
                        Integer.parseInt(minText.getText()),
                        Integer.parseInt(maxText.getText())
                );
            } else {
                part = new Outsourced(distributorText.getText(),
                        partID,
                        nameText.getText(),
                        Double.parseDouble(priceText.getText()),
                        Integer.parseInt(invText.getText()),
                        Integer.parseInt(minText.getText()),
                        Integer.parseInt(maxText.getText())
                );
            }
            
            // Checks if part is already an associated part. If it is, checks if the new price is less than product's price. If it isn't print validation message
            if (!newPart) {
                ArrayList<Product> associatedProducts = inv.lookupProductsWithPart(inv.lookupPart(partID));
                
                for (int i = 0; i < associatedProducts.size(); i++) {
                    Product associatedProduct = associatedProducts.get(i);
                    double productPriceSum = 0.;
                    ArrayList<Part> parts = new ArrayList<>();
                    
                    for (int j = 0; j < associatedProduct.getAssociatedParts().size(); j++) {
                        Part currPart = associatedProduct.getAssociatedParts().get(j);
                        if (currPart.getPartID() == partID)
                        {
                            productPriceSum += part.getPrice();
                            parts.add(part);
                        } else {
                            productPriceSum += currPart.getPrice();
                            parts.add(currPart);
                        }
                    }
                    
                    if (productPriceSum > associatedProduct.getPrice()) {
                        DecimalFormat df = new DecimalFormat("#.##");
                        validationBox.getChildren().add(new Label("• Products must be a higher price than the combined price of its associated parts.\n      - Problem Product: " + associatedProduct.getName() + "\n      - Product Current Price: " + associatedProduct.getPrice() + "\n      - Product's Associated Parts Price: >" + df.format(productPriceSum)));
                        primaryStage.sizeToScene();
                        return;
                    }
                    associatedProduct.setAssociatedParts(parts);
                }
            }

            if (newPart) {
                inv.addPart(part);
            } else {
                inv.modifyPart(part);
            }

            primaryStage.setScene(mainScreenScene());
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            if (ConfirmationBox.display("Are you sure you want to cancel?")) {
                primaryStage.setScene(mainScreenScene());
            }
        });

        HBox saveCancelBox = new HBox(saveButton, cancelButton);




        // If part already exists, populate fields
        if (!newPart) {
            if (deliveryMethodToggle.getSelectedToggle() == inHouseRadio) {
                distributorLabel.setText("Machine ID");
                distributorText.setPromptText("Mach ID");
            } else {
                distributorLabel.setText("Company Name");
                distributorText.setPromptText("Comp Nm");
            }
            Part modPart = inv.lookupPart(partID);
            if (modPart.getCompanyName() == null) {
                inHouseRadio.setSelected(true);
                distributorText.setText(Integer.toString(modPart.getMachineID()));
            } else {
                outsourcedRadio.setSelected(true);
                distributorText.setText(modPart.getCompanyName());
            }
            idText.setText(Integer.toString(modPart.getPartID()));
            nameText.setText(modPart.getName());
            invText.setText(Integer.toString(modPart.getInStock()));
            priceText.setText(Double.toString(modPart.getPrice()));
            maxText.setText(Integer.toString(modPart.getMax()));
            minText.setText(Integer.toString(modPart.getMin()));
        }

        // Setting up layout
        VBox layout = new VBox(radioBox, idBox, nameBox, invBox, priceBox, minMaxBox, distributorBox, saveCancelBox, validationBox);

        radioBox.setSpacing(10);
        radioBox.setPadding(new Insets(5, 10, 5, 10));

        idBox.setSpacing(10);
        idBox.setPadding(new Insets(5, 10, 5, 10));

        nameBox.setSpacing(10);
        nameBox.setPadding(new Insets(5, 10, 5, 10));

        invBox.setSpacing(10);
        invBox.setPadding(new Insets(5, 10, 5, 10));

        priceBox.setSpacing(10);
        priceBox.setPadding(new Insets(5, 10, 5, 10));

        minMaxBox.setSpacing(10);
        minMaxBox.setPadding(new Insets(5, 10, 5, 10));

        distributorBox.setSpacing(10);
        distributorBox.setPadding(new Insets(5, 10, 5, 10));

        saveCancelBox.setSpacing(10);
        saveCancelBox.setPadding(new Insets(5, 10, 5, 10));

        validationBox.setSpacing(5);
        validationBox.setPadding(new Insets(5, 10, 5, 10));

        layout.setPadding(new Insets(10, 20, 10, 20));
        Scene scene = new Scene(layout);
//        Scene scene = new Scene(layout, 500, 1000);

        return scene;
    }
    static Scene addProductScene(int prodID) {

        // FIXED: Fix bug: Modifying fields of a part doesn't reflect them in associated parts when modifying parts.
        // This is because they're saved as a different entity. I might need to use associated parts directly from the inventory
        // FIXED: Fix bug: changing part distributor re-adds it to the product menu and makes duplicates
        
        // Fixed: In modifying part, ensure that the new price doesn't exceen any product's associated parts' price
        // FIXED: In modifying product, accurately reflect any modified part's fields

        // Checks if adding or modifying
        boolean newProd = prodID == 0;
        ArrayList<Part> addedParts = new ArrayList<>();

        if (newProd) {
            primaryStage.setTitle("C482 - Add Product");
        } else {
            primaryStage.setTitle("C482 - Modify Product");
        }



        // Objects
        Label addProdLabel = new Label();
        addProdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addProdLabel.setPadding(new Insets(5, 10, 5, 10));
        if (newProd) {
            addProdLabel.setText("Add Product");
        } else {
            addProdLabel.setText("Modify Product");
        }

        Label idLabel = new Label("ID");
        idLabel.setMinWidth(100);
        idLabel.setMaxWidth(100);
        TextField idText = new TextField("Auto Gen - Disabled");
        idText.setDisable(true);
        HBox idBox = new HBox(idLabel, idText);

        Label nameLabel = new Label("Name");
        nameLabel.setMinWidth(100);
        nameLabel.setMaxWidth(100);
        TextField nameText = new TextField();
        nameText.setPromptText("Product Name");
        HBox nameBox = new HBox(nameLabel, nameText);

        Label invLabel = new Label("Inv");
        invLabel.setMinWidth(100);
        invLabel.setMaxWidth(100);
        TextField invText = new TextField();
        invText.setPromptText("Inv");
        HBox invBox = new HBox(invLabel, invText);

        Label priceLabel = new Label("Price/Cost");
        priceLabel.setMinWidth(100);
        priceLabel.setMaxWidth(100);
        TextField priceText = new TextField();
        priceText.setPromptText("Price/Cost");
        HBox priceBox = new HBox(priceLabel, priceText);

        Label maxLabel = new Label("Max");
        maxLabel.setMinWidth(100);
        maxLabel.setMaxWidth(100);
        TextField maxText = new TextField();
        maxText.setMinWidth(50);
        maxText.setMaxWidth(50);
        maxText.setPromptText("Max");

        Label minLabel = new Label("Min");
        minLabel.setMinWidth(50);
        minLabel.setMaxWidth(50);
        TextField minText = new TextField();
        minText.setMinWidth(50);
        minText.setMaxWidth(50);
        minText.setPromptText("Min");
        HBox minMaxBox = new HBox(maxLabel, maxText, minLabel, minText);



        TableView<Part> partsTable1 = createPartTableView(inv.getAllParts());
        TableView<Part> partsTable2 = createPartTableView(addedParts);

        final TableView<Part> partsTable1final = partsTable1;
        final TableView<Part> partsTable2final = partsTable2;

        partsTable2.setItems(getParts(addedParts));
        partsTable2.getSelectionModel().selectFirst();

        partsTable2final.setItems(getParts(addedParts));
        partsTable2final.getSelectionModel().selectFirst();



        // Search bar
        TextField partsSearchText = new TextField();
        partsSearchText.setPromptText("Search...");

        Button partsSearchButton = new Button("Search");
        partsSearchButton.setOnAction(e -> {
            ArrayList<Part> searchedParts = new ArrayList<>();
            for (int i = 0; i < inv.getAllParts().size(); i++) {
                Part part = inv.getAllParts().get(i);
                if (part.getName().toLowerCase().contains(partsSearchText.getText().toLowerCase()) && !addedParts.contains(part)) {
                    searchedParts.add(part);
                }
            }

            partsTable1final.setItems(getParts(searchedParts));
            partsTable1final.getSelectionModel().selectFirst();
        });
        partsSearchText.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
            ArrayList<Part> searchedParts = new ArrayList<>();
            for (int i = 0; i < inv.getAllParts().size(); i++) {
                Part part = inv.getAllParts().get(i);
                if (part.getName().toLowerCase().contains(partsSearchText.getText().toLowerCase()) && !addedParts.contains(part)) {
                    searchedParts.add(part);
                }
            }

            partsTable1final.setItems(getParts(searchedParts));
            partsTable1final.getSelectionModel().selectFirst();
            }
        });

        HBox searchBox = new HBox();
        searchBox.getChildren().addAll(partsSearchButton, partsSearchText);







        // Add and Delete buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            Part part = partsTable1final.getSelectionModel().getSelectedItem();
            addedParts.add(part);
            partsTable2final.setItems(getParts(addedParts));
            partsTable2final.getSelectionModel().selectFirst();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Part part = partsTable2final.getSelectionModel().getSelectedItem();
            addedParts.remove(addedParts.indexOf(part));

            partsTable2final.setItems(getParts(addedParts));
            partsTable2final.getSelectionModel().selectFirst();
        });
        partsTable2final.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> deleteButton.setDisable(partsTable2final.getItems().isEmpty()));
        deleteButton.setDisable(partsTable2final.getItems().isEmpty());



        VBox validationBox = new VBox();
        

        // Save and Cancel buttons
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Validation
            ArrayList<TextField> textFields = new ArrayList<>();
            textFields.add(nameText);
            textFields.add(invText);
            textFields.add(priceText);
            textFields.add(minText);
            textFields.add(maxText);
            
            ArrayList<String> validatedItems = validateProductScene(textFields, addedParts);
            validationBox.getChildren().clear();
                    
            for (int i = 0; i < validatedItems.size(); i++) {
                validationBox.getChildren().add(new Label("• " + validatedItems.get(i)));
            }
            
            if (!validatedItems.isEmpty()) {
                return;
            }
            
            
            ArrayList<Part> associatedParts = new ArrayList<>();

            Product product = new Product(addedParts,
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
        cancelButton.setOnAction(e -> {
            if (ConfirmationBox.display("Are you sure you want to cancel?")) {
                primaryStage.setScene(mainScreenScene());
            }
       });

        HBox saveCancelBox = new HBox();
        // TODO: Anchor to bottom right
        saveCancelBox.getChildren().addAll(saveButton, cancelButton);


        // If product already exists, populate fields
        if (!newProd) {
            Product modProd = inv.lookupProduct(prodID);
            idText.setText(Integer.toString(modProd.getProductID()));
            nameText.setText(modProd.getName());
            invText.setText(Integer.toString(modProd.getInStock()));
            priceText.setText(Double.toString(modProd.getPrice()));
            maxText.setText(Integer.toString(modProd.getMax()));
            minText.setText(Integer.toString(modProd.getMin()));

            addedParts.addAll(modProd.getAssociatedParts());
            TableView<Part> partsTable2Display = partsTable2;
            
            partsTable2Display.setItems(getParts(addedParts));
            partsTable2Display.getSelectionModel().selectFirst();
        }



        VBox productVBox = new VBox(addProdLabel, idBox, nameBox, invBox, priceBox, minMaxBox, validationBox);


        VBox partsVBox = new VBox();
        partsVBox.getChildren().addAll(searchBox);
        partsVBox.getChildren().addAll(partsTable1, addButton);
        partsVBox.getChildren().addAll(partsTable2, deleteButton);
        partsVBox.getChildren().addAll(saveCancelBox);

        HBox layout = new HBox(productVBox, partsVBox);

        idBox.setSpacing(10);
        idBox.setPadding(new Insets(5, 10, 5, 10));

        nameBox.setSpacing(10);
        nameBox.setPadding(new Insets(5, 10, 5, 10));

        invBox.setSpacing(10);
        invBox.setPadding(new Insets(5, 10, 5, 10));

        priceBox.setSpacing(10);
        priceBox.setPadding(new Insets(5, 10, 5, 10));

        minMaxBox.setSpacing(10);
        minMaxBox.setPadding(new Insets(5, 10, 5, 10));


        partsVBox.setSpacing(10);
        partsVBox.setPadding(new Insets(5, 10, 5, 10));

        searchBox.setSpacing(10);

        saveCancelBox.setSpacing(10);
        

        validationBox.setSpacing(5);
        validationBox.setPadding(new Insets(5, 10, 5, 10));

        layout.setPadding(new Insets(10, 20, 10, 20));
        Scene scene = new Scene(layout);

        return scene;
    }
    

    static ArrayList<String> validatePartScene(ArrayList<TextField> textFields, boolean isInHouse) {
        TextField nameText = textFields.get(0);
        TextField invText = textFields.get(1);
        TextField priceText = textFields.get(2);
        TextField minText = textFields.get(3);
        TextField maxText = textFields.get(4);
        TextField distributorText = textFields.get(5);
                
        ArrayList<String> errors = new ArrayList<>();

        if (nameText.getText().isEmpty()) {
            errors.add("Name field cannot be empty");
        }

        int invValue = -1;
        boolean invValueValidated = true;
        if (invText.getText().isEmpty()) {
            errors.add("Inventory field cannot be empty");
            invValueValidated = false;
        } else {
            try {
                invValueValidated = false;
                invValue = Integer.parseInt(invText.getText());
                invValueValidated = true;

                if(invValue < 0) {
                    errors.add("Inventory field must be a number, 0 or greater");
                    invValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Inventory field must be a number, 0 or greater");
                invValueValidated = false;
            }
        }
        
        int minValue = -1;
        boolean minValueValidated = true;
        if (minText.getText().isEmpty()) {
            errors.add("Min field cannot be empty");
            minValueValidated = false;
        } else {
            try {
                minValueValidated = false;
                minValue = Integer.parseInt(minText.getText());
                minValueValidated = true;

                if(minValue < 0) {
                    errors.add("Min field must be a number, 0 or greater");
                    minValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Min field must be a number, 0 or greater");
                minValueValidated = false;
            }
        }
        
        int maxValue = -1;
        boolean maxValueValidated = true;
        if (maxText.getText().isEmpty()) {
            errors.add("Max field cannot be empty");
            maxValueValidated = false;
        } else {
            try {
                maxValueValidated = false;
                maxValue = Integer.parseInt(maxText.getText());
                maxValueValidated = true;

                if(maxValue < 0) {
                    errors.add("Max field must be a number, 0 or greater");
                    maxValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Max field must be a number, 0 or greater");
                maxValueValidated = false;
            }
        }
        
        
        
        if (invValueValidated && minValueValidated && maxValueValidated) {
            if (maxValue < minValue) {
                errors.add("Max value must be more than Min field");
            }
            if (invValue > maxValue) {
                errors.add("Inventory field must be less than Max field");
            }
            if (invValue < minValue) {
                errors.add("Inventory field must be more than Min field");
            }
        }
        
        double priceValue = -1.00;
        if (priceText.getText().isEmpty()) {
            errors.add("Price field cannot be empty");
        } else {
            try {
                priceValue = Double.parseDouble(priceText.getText());

                if(priceValue <= 0) {
                    errors.add("Price field must be a decimal number greater than 0.00");
                }
            } catch(Exception e) {
                errors.add("Price field must be a decimal number greater than 0.00");
            }
        }
        
        int machineIdValue = -1;
        
        if (isInHouse) {
        if (distributorText.getText().isEmpty()) {
            errors.add("MachineID field cannot be empty");
        } else {
            try {
                machineIdValue = Integer.parseInt(distributorText.getText());

                if(machineIdValue <= 0) {
                    errors.add("MachineID field must be a number greater than 0");
                }
            } catch(Exception e) {
                errors.add("MachineID field must be a number greater than 0");
            }
        }
        } else {
            if (distributorText.getText().isEmpty()) {
                errors.add("Company Name field cannot be empty");
            }
        }

        return errors;
    }

    static ArrayList<String> validateProductScene(ArrayList<TextField> textFields, ArrayList<Part> addedParts) {
        TextField nameText = textFields.get(0);
        TextField invText = textFields.get(1);
        TextField priceText = textFields.get(2);
        TextField minText = textFields.get(3);
        TextField maxText = textFields.get(4);
                
        ArrayList<String> errors = new ArrayList<>();
        

        if (addedParts.isEmpty()) {
            errors.add("There must be at least 1 associated part");
        }

        if (nameText.getText().isEmpty()) {
            errors.add("Name field cannot be empty");
        }

        // Might need to fix this
        int invValue = -1;
        boolean invValueValidated = true;
        if (invText.getText().isEmpty()) {
            errors.add("Inventory field cannot be empty");
            invValueValidated = false;
        } else {
            try {
                invValueValidated = false;
                invValue = Integer.parseInt(invText.getText());
                invValueValidated = true;

                if(invValue < 0) {
                    errors.add("Inventory field must be a number, 0 or greater");
                    invValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Inventory field must be a number, 0 or greater");
                invValueValidated = false;
            }
        }
        
        int minValue = -1;
        boolean minValueValidated = true;
        if (minText.getText().isEmpty()) {
            errors.add("Min field cannot be empty");
            minValueValidated = false;
        } else {
            try {
                minValueValidated = false;
                minValue = Integer.parseInt(minText.getText());
                minValueValidated = true;

                if(minValue < 0) {
                    errors.add("Min field must be a number, 0 or greater");
                    minValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Min field must be a number, 0 or greater");
                minValueValidated = false;
            }
        }
        
        int maxValue = -1;
        boolean maxValueValidated = true;
        if (maxText.getText().isEmpty()) {
            errors.add("Max field cannot be empty");
            maxValueValidated = false;
        } else {
            try {
                maxValueValidated = false;
                maxValue = Integer.parseInt(maxText.getText());
                maxValueValidated = true;

                if(maxValue < 0) {
                    errors.add("Max field must be a number, 0 or greater");
                    maxValueValidated = false;
                }
            } catch(Exception e) {
                errors.add("Max field must be a number, 0 or greater");
                maxValueValidated = false;
            }
        }
        
        
        
        if (invValueValidated && minValueValidated && maxValueValidated) {
            if (maxValue < minValue) {
                errors.add("Max value must be more than Min field");
            }
            if (invValue > maxValue) {
                errors.add("Inventory field must be less than Max field");
            }
            if (invValue < minValue) {
                errors.add("Inventory field must be more than Min field");
            }
        }
        
        double priceValue = -1.00;
        if (priceText.getText().isEmpty()) {
            errors.add("Price field cannot be empty");
        } else {
            try {
                priceValue = Double.parseDouble(priceText.getText());

                if(priceValue <= 0) {
                    errors.add("Price field must be a decimal number greater\nthan 0.00");
                } else if (!addedParts.isEmpty()) {
                    double priceSum = 0;
                    for (int i = 0; i < addedParts.size(); i++) {
                        priceSum += addedParts.get(i).getPrice();
                    }
                    if (priceValue < priceSum) {
                        errors.add("The Price field must be more than the\nsum of the combined associated parts price,\nwhich is " + priceSum);
                    }
                }
            } catch(Exception e) {
                errors.add("Price field must be a decimal number greater\nthan 0.00");
            }
        }

        return errors;
    }
}
