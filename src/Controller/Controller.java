package Controller;


import java.sql.*;


import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import sample.SQL_Query;

public class Controller {

    public static Connection con;
    public static String usernameLoggedIn;
    public static MongoDatabase db;
    /**
     * Connects to database
     * @param username
     * @param password
     * @param dbName
     * @return
     * @throws Exception
     */
    public static boolean connectToDatabase(String username, String password, String dbName) throws Exception {




       try {
           MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
           db = mongoClient.getDatabase("flomm");




           System.out.println("worked");
           return true;
       }finally{
           System.out.println("Good try");
       }
        // SQL Launch
       /* SQL_Query sql = new SQL_Query();
        usernameLoggedIn = username;
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, "user", "user");
            System.out.println("connected!");
            return sql.login(con, username, password);

        } finally {
            try {
                if (con != null) {

                }
            }finally{

            }


        }*/
    }

    /**
     * Closes connection to database
     * @throws Exception
     */
    public static void closeConnection() throws Exception {
        con.close();
    }

}