package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Bhavesh on 7/14/2017.
 */
public class Admin {
    public Admin(){
        Stage window = new Stage();
        window.setTitle("Admin Services");

        Button addLibrarian = new Button("Add Librarian");
        addLibrarian.setOnAction(e->AdminController.addLibrarian());
        Button viewLibrarian = new Button("View Librarian");
        viewLibrarian.setOnAction(e->AdminController.viewLibrarian());
        Button deleteLibrarian = new Button("Delete Librarian");
        deleteLibrarian.setOnAction(e->AdminController.deleteLibrarian());
        Button logout = new Button("Logout");
        logout.setOnAction(e->{
            window.close();
            Main obj = new Main();
            Stage primaryStage = new Stage();
            try{
                obj.start(primaryStage);
            }catch(Exception ex){
                ex.printStackTrace();
            }

        });
        VBox lay = new VBox();
        lay.setAlignment(Pos.CENTER);
        lay.setPadding(new Insets(20,20,20,20));

        lay.setMargin(addLibrarian, new Insets(0,0,20,0));
        lay.setMargin(viewLibrarian, new Insets(0,0,20,0));
        lay.setMargin(deleteLibrarian, new Insets(0,0,20,0));

        lay.getChildren().addAll(addLibrarian, viewLibrarian, deleteLibrarian, logout);

        Scene sc = new Scene(lay, 400, 550);
        sc.getStylesheets().add("style2.css");
        window.setScene(sc);
        window.showAndWait();
    }
}
