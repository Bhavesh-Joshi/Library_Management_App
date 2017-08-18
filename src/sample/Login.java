package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Created by Bhavesh on 7/14/2017.
 */
public class Login {
    public Login(String person){
        VBox layout = new VBox();

        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        HBox lay2 = new HBox();
        lay2.setSpacing(20);
        lay2.setAlignment(Pos.CENTER);

        Label l1 = new Label("Username: ");
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

        if(person.equals("Admin")){
            login.setTitle("Admin - Login Page");
        }else if(person.equals("Librarian")){
            login.setTitle("Librarian - Login Page");
        }else login.setTitle("Student - Login Page");

        login.initModality(Modality.APPLICATION_MODAL);
        login.setOnCloseRequest(e->{
            Stage primaryStage = new Stage();
            login.close();
            try {
                new Main().start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        login.show();
        Button submit = new Button("SUBMIT");
        layout.getChildren().add(submit);


        layout.setMargin(submit, new Insets(30,0,0,0));
        lay2.setMargin(l2,new Insets(10,0,0,0));
        lay2.setMargin(pass,new Insets(10,0,0,0));

        //Validation of person from Databases Records

        submit.setOnAction( e-> {
            try{
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                Statement st = con.createStatement();
                st.execute("create table IF not EXISTS Admin (Name varchar(20), pass varchar(20))");
                PreparedStatement ps;
                ps = con.prepareStatement("insert into Admin values(?,?);");
                ps.setString(1, "Bhavesh");
                ps.setString(2, "root");
                ps.executeUpdate();

                int count=0;

                if(person.equals("Admin")){
                    ps = con.prepareStatement("Select * from Admin where name = ? AND pass = ?;");
                }
                else if(person.equals("Librarian")){
                    ps = con.prepareStatement("Select * from Librarian where name = ? AND pass = ?;");
                }
                else{
                    ps = con.prepareStatement("Select * from Student where name = ? AND pass = ?;");
                }

                ps.setString(1, name.getText());
                ps.setString(2, pass.getText());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){count++;}

                if(count>0){
                    login.close();
                    if(person.equals("Admin")){
                        new Admin();
                    }
                    else if(person.equals("Librarian")){
                        new Librarian();
                    }
                    else{
                        new Student();
                    }
                }
                else{
                    Stage window = new Stage();
                    VBox lay = new VBox();
                    lay.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(lay,300,100);
                    window.setScene(scene);
                    lay.getChildren().add(new Label("Username or Password incorrect!!!!"));
                    window.show();
                    scene.getStylesheets().add("style.css");
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });





    }
}
