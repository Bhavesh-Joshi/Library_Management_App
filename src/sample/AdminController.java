package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Created by Bhavesh on 7/14/2017.
 */
public class AdminController {

    public static void addLibrarian(){
        VBox layout = new VBox();

        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        HBox lay2 = new HBox();
        lay2.setSpacing(20);
        lay2.setAlignment(Pos.CENTER);

        Label l1 = new Label(" Librarian:   ");
        TextField name = new TextField();

        Label l2 = new Label("Password:  ");
        PasswordField pass = new PasswordField();

        lay1.getChildren().addAll(l1, name);
        lay2.getChildren().addAll(l2, pass);

        layout.getChildren().addAll(lay1, lay2);
        layout.setAlignment(Pos.CENTER);

        Stage login = new Stage();
        Scene sc = new Scene(layout, 300, 200);
        sc.getStylesheets().add("style.css");
        login.setScene(sc);

        login.setTitle("Admin Services - Add Librarian");

        login.initModality(Modality.APPLICATION_MODAL);
        login.show();
        Button submit = new Button("ADD");
        layout.getChildren().add(submit);

        layout.setMargin(submit, new Insets(15,0,0,0));
        lay2.setMargin(l2,new Insets(10,0,0,0));
        lay2.setMargin(pass,new Insets(10,0,0,0));

        submit.setOnAction( e-> {
            try{
                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                PreparedStatement st;
                int count=0;

                st = con.prepareStatement("insert into Librarian values (?, ?)");
                st.setString(1, name.getText());
                st.setString(2, pass.getText());
                st.executeUpdate();

                login.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
    public static void viewLibrarian(){
        Stage window= new Stage();
        VBox lay = new VBox();
        Scene sc = new Scene(lay, 400, 300);

        TableView viewLibrarian = new TableView();

        TableColumn name = new TableColumn("Librarian name");
        name.setMinWidth(200);
        TableColumn pass = new TableColumn("Password");
        pass.setMinWidth(200);
        viewLibrarian.getColumns().addAll(name, pass);
        viewLibrarian.setItems(records());
        name.setCellValueFactory(new PropertyValueFactory<ViewRecords,String>("Lname"));
        pass.setCellValueFactory(new PropertyValueFactory<ViewRecords,String>("Lpass"));

        lay.getChildren().add(viewLibrarian);
        window.setScene(sc);
        window.setTitle("Admin Services - Librarians Records");
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
    }

    public static ObservableList<ViewRecords> records(){

        ObservableList<ViewRecords> data = FXCollections.observableArrayList();

        try{

            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Librarian;");
            while(rs.next()){
                data.add(new ViewRecords(rs.getString(1), rs.getString(2)));
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        return data;
    }

    public static void deleteLibrarian(){
        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        Label l1 = new Label(" Librarian:\t");
        TextField name = new TextField();

        lay1.getChildren().addAll(l1, name);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(lay1);

        Button delete = new Button("DELETE");

        layout.getChildren().add(delete);
        layout.setMargin(delete, new Insets(20,0,0,0));
        Stage window = new Stage();
        window.setTitle("Admin Services - Remove Librarian");
        window.initModality(Modality.APPLICATION_MODAL);
        Scene sc = new Scene(layout, 300, 200);
        sc.getStylesheets().add("style.css");
        window.setScene(sc);
        window.show();

        delete.setOnAction(e->{
            try{

                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                PreparedStatement st = con.prepareStatement("delete from librarian where name=?");
                st.setString(1, name.getText());
                st.executeUpdate();
                window.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
