package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.LibrarianController;
import sample.Main;

/**
 * Created by Bhavesh on 7/14/2017.
 */
public class Librarian {
    public Librarian(){
        Stage window = new Stage();
        window.setTitle("Librarian Services");

        Button addStudent = new Button("Add Student");
        addStudent.setOnAction(e-> LibrarianController.addStudent());
        Button viewStudents = new Button("View or Delete Students");
        viewStudents.setOnAction(e->LibrarianController.viewStudents());
        Button addBooks = new Button("Add Books");
        addBooks.setOnAction(e->LibrarianController.addBooks());
        Button viewBooks = new Button("View or Delete Books");
        viewBooks.setOnAction(e->LibrarianController.viewBooks());
        Button issueBooks = new Button("Issue Books");
        issueBooks.setOnAction(e->LibrarianController.issueBook());
        Button viewIssuedBooks = new Button("View Issued Books");
        Button returnBook = new Button("Return Book");
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

        lay.setMargin(addStudent, new Insets(0,0,20,0));
        lay.setMargin(viewStudents, new Insets(0,0,20,0));
        lay.setMargin(addBooks, new Insets(0,0,20,0));
        lay.setMargin(viewBooks, new Insets(0,0,20,0));
        lay.setMargin(issueBooks, new Insets(0,0,20,0));
        lay.setMargin(viewIssuedBooks, new Insets(0,0,20,0));
        lay.setMargin(returnBook, new Insets(0,0,20,0));
        lay.getChildren().addAll(addStudent, viewStudents, addBooks, viewBooks, issueBooks, viewIssuedBooks, returnBook, logout);

        Scene sc = new Scene(lay, 400, 550);
        sc.getStylesheets().add("style2.css");
        window.setScene(sc);
        window.showAndWait();
    }
}
