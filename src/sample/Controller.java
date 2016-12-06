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
import java.sql.*;

import Model.*;

public class Controller implements Runnable{

    private static String username;
    private static String password;
    private static String dbName;



    //


    private Model model = new Model();


    public Controller(){
        model = new Model();
    }

    @FXML
    TextField txt_usr_name, txt_pass, databasename;
    @FXML
    public void login(ActionEvent e) throws Exception {
        this.username = txt_usr_name.getText();
        this.password = txt_pass.getText();
        this.dbName = databasename.getText();
        connectToDatabase(e);
    }

    public void connectToDatabase(ActionEvent e) throws Exception{
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        Connection con = null;
        SQL_Query sql = new SQL_Query();

        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server,username,password);
            System.out.println("connected!");
            switchSceneMain(e);


            // TEST STATEMENTS
            // ****************************
            // sql.SelectQuery(con,"SELECT * FROM content");
           // insertIntoContent(con);
           // insertIntoCreator(con,"Mattias Kågström","SWE","Actor","hglantz@kth.se");
           // sql.insertIntoCreatedContent(con,6,1);
          //  sql.SelectQuery(con,"SELECT * FROM content");
           // sql.getsomething(con,"SELECT * FROM content");
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
        Parent root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        mainStage.setTitle("Main Program");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
    @Override
    public void run() {

    }
}