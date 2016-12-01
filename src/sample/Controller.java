package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Model.*;

public class Controller implements Runnable{

    private static String username;
    private static String password;

    private Model model;

    public Controller(){
        model = new Model();
    }

    @FXML
    TextField txt_usr_name, txt_pass;
    @FXML
    public void login(ActionEvent e) throws Exception {
        this.username = txt_usr_name.getText();
        this.password = txt_pass.getText();
        connectToDatabase(e);
    }

    public void connectToDatabase(ActionEvent e) throws Exception{
        String database = "shit";
        String server = "jdbc:mysql://localhost:3306/" + database + "?UseClientEnc=UTF8";
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server, username, password);
            System.out.println("connected!");
            //executeQuery(con, "INSERT INTO user(userName_email, name, password, privilage) VALUES ('niclawsge@kth.se', 'Ni3clas Gernandt', 'paass', 2)");
            switchSceneMain(e);

        } finally{
            try{
                if(con != null){
                    con.close();
                    System.out.println("Connection closed");
                }
            }catch(SQLException ev){

            }
        }
    }

    public void switchSceneMain(ActionEvent e) throws IOException {
        Stage mainStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        mainStage.setTitle("Main Program");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    public static void executeQuery(Connection con, String query) throws SQLException{

        Statement stmt = null;
        try{
            //Execute the SQL statement
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        }finally{
            if(stmt != null){
                stmt.close();
            }
        }
    }

    @Override
    public void run() {

    }
}