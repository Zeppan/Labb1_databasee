package Controller;


import java.sql.*;


import com.mongodb.*;
import sample.SQL_Query;

public class Controller {

    public static Connection con;
    public static String usernameLoggedIn;
    public static DB db;
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
           db = (new MongoClient("localhost",27017)).getDB("flomm");

           DBCollection coll = db.getCollection("User");
           BasicDBObject obj = new BasicDBObject();
           obj.append("user_name","hglantz@hotmail.com");
           obj.append("name","Hampus");
           obj.append("password","kanske");
           coll.insert(obj);



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