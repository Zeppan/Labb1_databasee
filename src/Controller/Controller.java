package Controller;


import java.sql.*;


import sample.SQL_Query;

public class Controller {

    public static Connection con;
    public static String usernameLoggedIn;

    /**
     * Connects to database
     * @param username
     * @param password
     * @param dbName
     * @return
     * @throws Exception
     */
    public static boolean connectToDatabase(String username, String password, String dbName) throws Exception {
        SQL_Query sql = new SQL_Query();
        usernameLoggedIn = username;
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, "root", "glantz");
            System.out.println("connected!");

            return sql.login(con, username, password);

        } finally {
            try {
                if (con != null) {

                }
            }finally{

            }


        }
    }

    /**
     * Closes connection to database
     * @throws Exception
     */
    public static void closeConnection() throws Exception {
        con.close();
    }

}