package Controller;


import java.sql.*;


import sample.SQL_Query;

public class Controller implements Runnable {

    public static Connection con;
    public static String usernameLoggedIn;

    public static boolean connectToDatabase(String username, String password, String dbName) throws Exception {
        SQL_Query sql = new SQL_Query();
        usernameLoggedIn = username;
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, "root", "root");
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

    public static void closeConnection() throws Exception {
        con.close();
    }

    @Override
    public void run() {

    }
}