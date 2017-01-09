package Controller;


import java.sql.*;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sample.NoSql;
import sample.SQL_Query;

import static com.mongodb.client.model.Filters.eq;

public class Controller {

    public static Connection con;
    public static String usernameLoggedIn;
    public static MongoDatabase db;

    /**
     * Connects to database
     *
     * @param username
     * @param password
     * @param dbName
     * @return
     * @throws Exception
     */
    public static boolean connectToDatabase(String username, String password, String dbName) throws Exception {


        NoSql noSql = new NoSql();
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("flomm");
            if (noSql.login(username, password)) {
                return true;
            } else {
                System.out.println("failed to log in!");
                return false;
            }
        } catch (Exception e) {
            throw e;
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
     *
     * @throws Exception
     */
    public static void closeConnection() throws Exception {
        con.close();
    }

}