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
public class LibrarianController {

    public static void addStudent() {
        VBox layout = new VBox();

        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        HBox lay2 = new HBox();
        lay2.setSpacing(20);
        lay2.setAlignment(Pos.CENTER);

        Label l1 = new Label(" Student:\t");
        TextField name = new TextField();

        Label l2 = new Label("Password:\t");
        PasswordField pass = new PasswordField();

        lay1.getChildren().addAll(l1, name);
        lay2.getChildren().addAll(l2, pass);

        layout.getChildren().addAll(lay1, lay2);
        layout.setAlignment(Pos.CENTER);

        Stage login = new Stage();
        Scene sc = new Scene(layout, 300, 200);
        sc.getStylesheets().add("style.css");
        login.setScene(sc);

        login.setTitle("Librarian Services - Add Student");

        login.initModality(Modality.APPLICATION_MODAL);
        login.show();
        Button submit = new Button("ADD");
        layout.getChildren().add(submit);

        layout.setMargin(submit, new Insets(15, 0, 0, 0));
        lay2.setMargin(l2, new Insets(10, 0, 0, 0));
        lay2.setMargin(pass, new Insets(10, 0, 0, 0));

        submit.setOnAction(e -> {
            try {
                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                PreparedStatement st;
                int count = 0;

                st = con.prepareStatement("insert into Student values (?, ?)");
                st.setString(1, name.getText());
                st.setString(2, pass.getText());
                st.executeUpdate();

                login.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public static void viewStudents() {
        Stage window = new Stage();
        VBox lay = new VBox();
        Scene sc = new Scene(lay, 400, 300);

        TableView viewLibrarian = new TableView();

        TableColumn name = new TableColumn("Student Name");
        name.setMinWidth(200);
        TableColumn pass = new TableColumn("Password");
        pass.setMinWidth(200);
        viewLibrarian.getColumns().addAll(name, pass);
        viewLibrarian.setItems(records());
        name.setCellValueFactory(new PropertyValueFactory<ViewRecords, String>("Lname"));
        pass.setCellValueFactory(new PropertyValueFactory<ViewRecords, String>("Lpass"));

        Button delete = new Button("DELETE");

        delete.setStyle("-fx-min-width: 250; -fx-background-color: #698aff; -fx-font-size: 20; -fx-text-fill: white");

        lay.setMargin(delete, new Insets(20,0,20,75));
        delete.setAlignment(Pos.BOTTOM_CENTER);

        delete.setOnAction(e-> {
            int selectedIndex = viewLibrarian.getSelectionModel().getSelectedIndex();
            int count=0;
            String del="";

            try {
                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from Student;");
                while(rs.next())
                {
                    count++;
                    if(count-1==selectedIndex){
                        del = rs.getString(1);
                    }

                }

                PreparedStatement ps = con.prepareStatement("delete from Student where name =?");
                ps.setString(1, del);
                ps.executeUpdate();
                con.close();

                viewLibrarian.getItems().remove(selectedIndex);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        lay.getChildren().addAll(viewLibrarian, delete);
        window.setScene(sc);

        window.setTitle("Librarian Services - Student Records");
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
    }

    public static ObservableList<ViewRecords> records() {

        ObservableList<ViewRecords> data = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Student;");
            while (rs.next()) {
                data.add(new ViewRecords(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public static void addBooks() {
        VBox layout = new VBox();

        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        Label l1 = new Label("Name:\t");
        TextField name = new TextField();

        HBox lay2 = new HBox();
        lay2.setSpacing(20);
        lay2.setAlignment(Pos.CENTER);

        Label l2 = new Label("Author:\t");
        TextField author = new TextField();

        HBox lay3 = new HBox();
        lay3.setSpacing(20);
        lay3.setAlignment(Pos.CENTER);

        Label l3 = new Label("Publisher:\t");
        TextField publisher = new TextField();

        HBox lay4 = new HBox();
        lay4.setSpacing(20);
        lay4.setAlignment(Pos.CENTER);

        Label l4 = new Label("Quantity:\t");
        TextField quant = new TextField();


        lay1.getChildren().addAll(l1, name);
        lay2.getChildren().addAll(l2, author);
        lay3.getChildren().addAll(l3, publisher);
        lay4.getChildren().addAll(l4, quant);

        layout.getChildren().addAll(lay1, lay2, lay3, lay4);
        layout.setAlignment(Pos.CENTER);

        Stage login = new Stage();
        Scene sc = new Scene(layout, 400, 200);
        sc.getStylesheets().add("style.css");
        login.setScene(sc);

        login.setTitle("Librarian Services - Add Books");

        login.initModality(Modality.APPLICATION_MODAL);
        login.show();
        Button submit = new Button("ADD");
        layout.getChildren().add(submit);

        layout.setMargin(submit, new Insets(15, 0, 0, 0));
        lay2.setMargin(l2, new Insets(10, 0, 0, 0));
        lay2.setMargin(author, new Insets(10, 0, 0, 0));
        lay2.setMargin(publisher, new Insets(10, 0, 0, 0));
        lay2.setMargin(quant, new Insets(10, 0, 0, 0));

        submit.setOnAction(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
                PreparedStatement st = conn.prepareStatement("insert into Books values (?, ?, ?, ?)");
                st.setString(1, name.getText());
                st.setString(2, author.getText());
                st.setString(3, publisher.getText());
                st.setString(4, quant.getText());
                st.executeUpdate();

                login.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public static void viewBooks() {
        Stage window = new Stage();
        VBox lay = new VBox();
        Scene sc = new Scene(lay, 800, 350);

        TableView viewBook = new TableView();

        TableColumn name = new TableColumn("Book Name");
        name.setMinWidth(200);
        TableColumn author = new TableColumn("Author");
        author.setMinWidth(200);
        TableColumn publisher = new TableColumn("Publisher");
        publisher.setMinWidth(200);
        TableColumn quant = new TableColumn("Quantity");
        quant.setMinWidth(200);

        viewBook.getColumns().addAll(name, author, publisher, quant);
        viewBook.setItems(bookRecords());
        name.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bname"));
        author.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bauthor"));
        publisher.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bpublisher"));
        quant.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bquant"));

        Button delete = new Button("DELETE");
        delete.setStyle("-fx-min-width: 250; -fx-background-color: #698aff; -fx-font-size: 20; -fx-text-fill: white");

        lay.setMargin(delete, new Insets(20,0,20,275));
        delete.setOnAction(e-> {
            int selectedIndex = viewBook.getSelectionModel().getSelectedIndex();
            int count=0;
            String del="";

            try {
                Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from Books;");
                while(rs.next())
                {
                    count++;
                    if(count-1==selectedIndex){
                        del = rs.getString(1);
                    }

                }

                PreparedStatement ps = con.prepareStatement("delete from Books where name =?");
                ps.setString(1, del);
                ps.executeUpdate();
                con.close();

                viewBook.getItems().remove(selectedIndex);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        lay.getChildren().addAll(viewBook, delete);
        window.setScene(sc);
        window.setTitle("Librarian Services - Books Records");
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
    }

    public static ObservableList<ViewBooks> bookRecords() {

        ObservableList<ViewBooks> data = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Books;");
            while (rs.next()) {
                data.add(new ViewBooks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void issueBook(){

        VBox layout = new VBox();

        HBox lay0 = new HBox();
        lay0.setSpacing(20);
        lay0.setAlignment(Pos.CENTER);

        Label l0 = new Label("Student:\t");
        TextField SName = new TextField();

        HBox lay1 = new HBox();
        lay1.setSpacing(20);
        lay1.setAlignment(Pos.CENTER);

        Label l1 = new Label("Book:\t");
        TextField BName = new TextField();

        HBox lay2 = new HBox();
        lay2.setSpacing(20);
        lay2.setAlignment(Pos.CENTER);

        Label l2 = new Label("Author:\t");
        TextField author = new TextField();

        HBox lay3 = new HBox();
        lay3.setSpacing(20);
        lay3.setAlignment(Pos.CENTER);

        Label l3 = new Label("Publisher:\t");
        TextField publisher = new TextField();

        HBox lay4 = new HBox();
        lay4.setSpacing(20);
        lay4.setAlignment(Pos.CENTER);

        Label l4 = new Label("Quantity:\t");
        TextField quant = new TextField();


        lay0.getChildren().addAll(l0, SName);
        lay1.getChildren().addAll(l1, BName);
        lay2.getChildren().addAll(l2, author);
        lay3.getChildren().addAll(l3, publisher);
        lay4.getChildren().addAll(l4, quant);

        layout.getChildren().addAll(lay0, lay1, lay2, lay3, lay4);
        layout.setAlignment(Pos.CENTER);

        Stage login = new Stage();
        Scene sc = new Scene(layout, 400, 250);
        sc.getStylesheets().add("style.css");
        login.setScene(sc);

        login.setTitle("Librarian Services - Issue Books");

        login.initModality(Modality.APPLICATION_MODAL);
        login.show();
        Button submit = new Button("ISSUE");
        layout.getChildren().add(submit);

        layout.setMargin(submit, new Insets(15, 0, 0, 0));
        lay1.setMargin(BName, new Insets(10, 0, 0, 0));
        lay2.setMargin(author, new Insets(10, 0, 0, 0));
        lay3.setMargin(publisher, new Insets(10, 0, 0, 0));
        lay4.setMargin(quant, new Insets(10, 0, 0, 0));

        submit.setOnAction(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
                PreparedStatement st;
                st = conn.prepareStatement("select * from Books where Name=? AND author=? AND publisher=? AND quant>=?");
                st.setString(1, BName.getText());
                st.setString(2, author.getText());
                st.setString(3, publisher.getText());
                st.setInt(4, Integer.parseInt(quant.getText()));
                int count = 0;
                ResultSet rs = st.executeQuery();
                while(rs.next()){
                    count++;
                }
                int stud=0;

                st = conn.prepareStatement("select * from Student where Name=?");
                st.setString(1, SName.getText());
                rs = st.executeQuery();
                while(rs.next()){
                    stud++;
                }
                int n = 0;
                if(stud==0){
                    Stage window = new Stage();
                    VBox lay = new VBox();
                    lay.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(lay,300,100);
                    window.setScene(scene);
                    lay.getChildren().add(new Label("Invalid Student Name!!!!"));
                    window.show();
                    scene.getStylesheets().add("style.css");
                }
                else if(count==0){
                    Stage window = new Stage();
                    VBox lay = new VBox();
                    lay.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(lay,300,100);
                    window.setScene(scene);
                    lay.getChildren().add(new Label("Specified Book is not Available in Library!!!"));
                    window.show();
                    scene.getStylesheets().add("style.css");

                }
                else
                {
                    n = Integer.parseInt(quant.getText());
                    st = conn.prepareStatement("update Books set quant = quant-? where name=?");
                    st.setInt(1,n);
                    st.setString(2, BName.getText());

                    st.executeUpdate();

                    st = conn.prepareStatement("insert into issueBooks values (?,?, ?, ?, ?)");
                    st.setString(1, SName.getText());
                    st.setString(2, BName.getText());
                    st.setString(3, author.getText());
                    st.setString(4, publisher.getText());
                    st.setString(5, quant.getText());
                    st.executeUpdate();
                    Stage window = new Stage();
                    VBox lay = new VBox();
                    lay.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(lay,300,100);

                    window.setScene(scene);
                    lay.getChildren().add(new Label("Book Issued to "+SName.getText()));
                    window.show();
                    scene.getStylesheets().add("style.css");
                }
                login.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void viewIssueBooks(){
        Stage window = new Stage();
        VBox lay = new VBox();
        Scene sc = new Scene(lay, 800, 350);

        TableView viewIssueBook = new TableView();


        TableColumn SName = new TableColumn("Student Name");
        SName.setMinWidth(200);

        TableColumn BName = new TableColumn("Book Name");
        BName.setMinWidth(200);
        TableColumn author = new TableColumn("Author");
        author.setMinWidth(200);
        TableColumn publisher = new TableColumn("Publisher");
        publisher.setMinWidth(200);
        TableColumn quant = new TableColumn("Quantity");
        quant.setMinWidth(200);

        viewIssueBook.getColumns().addAll(SName, BName, author, publisher, quant);
        viewIssueBook.setItems(issueBookRecords());
        SName.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Sname"));
        BName.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bname"));
        author.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bauthor"));
        publisher.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bpublisher"));
        quant.setCellValueFactory(new PropertyValueFactory<ViewBooks, String>("Bquant"));

        lay.getChildren().addAll(viewIssueBook);
        window.setScene(sc);
        window.setTitle("Librarian Services - Issued Book Records");
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();

    }
    public static ObservableList<ViewBooks> issueBookRecords() {

        ObservableList<ViewBooks> data = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Books;");
            while (rs.next()) {
                data.add(new ViewBooks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}