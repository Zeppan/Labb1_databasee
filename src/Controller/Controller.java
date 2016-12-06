package Controller;


import java.sql.*;

import Model.*;
import sample.SQL_Query;

public class Controller implements Runnable{




    public static void connectToDatabase(String username, String password, String dbName) throws Exception{
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        Connection con = null;
        SQL_Query  sql=new SQL_Query();

       Model model = new Model();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server,username,password);
            System.out.println("connected!");

            // TEST STATEMENTS
            // ****************************
            //sql.getcontentReviews(con,model,"elias");

             //sql.SelectQuery(con,"SELECT * FROM content");
           // insertIntoContent(con);
           // insertIntoCreator(con,"Mattias Kågström","SWE","Actor","hglantz@kth.se");
           // sql.insertIntoCreatedContent(con,6,1);
          //  sql.SelectQuery(con,"SELECT * FROM content");
            //sql.getsomething(con, model,"SELECT * FROM content");

             //sql.SelectQuery(con,"SELECT * FROM content");
           // insertIntoContent(con);
           // insertIntoCreator(con,"Mattias Kågström","SWE","Actor","hglantz@kth.se");
            //sql.insertIntoCreatedContent(con,6,1);
            //sql.SelectQuery(con,"SELECT * FROM content");

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
    @Override
    public void run() {

    }
}