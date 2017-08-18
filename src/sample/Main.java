package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    static ComboBox<String> login_window;
    static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Library Management");
        VBox lay = new VBox();
        Scene sc = new Scene(lay, 300, 300);
        window.setScene(sc);

        window.show();
        sc.getStylesheets().add("style.css");



        Label l = new Label("Select from category to login... ");
        l.setStyle("-fx-padding: 35");
        lay.getChildren().add(l);

        login_window = new ComboBox<>();
        login_window.setPromptText("Category");

        login_window.getItems().add("Admin");
        login_window.getItems().add("Librarian");
 //       login_window.getItems().add("Student");

        lay.getChildren().add(login_window);
        lay.setAlignment(Pos.TOP_CENTER);

        login_window.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            window.close();
            if (newValue.equals("Admin")) {
                new Login("Admin");

            } else if(newValue.equals("Librarian")) {
                new Login("Librarian");
            }
            else new Login("Student");

        });

    }
}