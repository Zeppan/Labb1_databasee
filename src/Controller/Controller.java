package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import Model.*;
import sample.SQL_Query;

public class Controller implements Runnable{

<<<<<<< Updated upstream:src/sample/Controller.java
    private static String username;
    private static String password;
    private static String dbName;



    //


    private Model model = new Model();

=======
    private Model model;
>>>>>>> Stashed changes:src/Controller/Controller.java

    public Controller(){
        model = new Model();
    }

    public static void connectToDatabase(ActionEvent e, String username, String password, String dbName) throws Exception{
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        Connection con = null;
        SQL_Query sql = new SQL_Query();

        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server,username,password);
            System.out.println("connected!");

            // TEST STATEMENTS
            // ****************************
<<<<<<< Updated upstream:src/sample/Controller.java
            // sql.SelectQuery(con,"SELECT * FROM content");
           // insertIntoContent(con);
           // insertIntoCreator(con,"Mattias Kågström","SWE","Actor","hglantz@kth.se");
           // sql.insertIntoCreatedContent(con,6,1);
          //  sql.SelectQuery(con,"SELECT * FROM content");
           // sql.getsomething(con,"SELECT * FROM content");
=======
             //sql.SelectQuery(con,"SELECT * FROM content");
           // insertIntoContent(con);
           // insertIntoCreator(con,"Mattias Kågström","SWE","Actor","hglantz@kth.se");
            //sql.insertIntoCreatedContent(con,6,1);
            //sql.SelectQuery(con,"SELECT * FROM content");
>>>>>>> Stashed changes:src/Controller/Controller.java
            //******************************
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
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/mainStage.fxml"));
        mainStage.setTitle("Main Program");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
    @Override
    public void run() {

    }
}