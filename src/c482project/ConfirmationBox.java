package c482project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ConfirmationBox {
    
    static boolean output = false;
    
    public static boolean display(String msg) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Confirm");
        primaryStage.setMinWidth(250);
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
       
        
        
        
        // TODO: Update positioning etc.
        Label label = new Label(msg);
        GridPane.setConstraints(label, 0, 0);
        
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            output = true;
            primaryStage.close();
        });
        GridPane.setConstraints(yesButton, 0, 1);
        
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            output = false;
            primaryStage.close();
        });
        GridPane.setConstraints(noButton, 1, 1);
        
        
        
        
        
        grid.getChildren().addAll(label, yesButton, noButton);
        
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        
        return output;
    }
}
