package c482project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationBox {
    
    static boolean output = false;
    
    public static boolean display(String msg) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Confirm");
        primaryStage.setMinWidth(250);
        
        
        Label label = new Label(msg);
        
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            output = true;
            primaryStage.close();
        });
        
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            output = false;
            primaryStage.close();
        });
        
        
        
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        
        buttonsBox.getChildren().addAll(yesButton, noButton);
        layout.getChildren().addAll(label, buttonsBox);
        
        
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        
        return output;
    }
}
