package Controller;


import java.sql.*;


import sample.SQL_Query;

public class Controller implements Runnable {


    public static boolean connectToDatabase(String username, String password, String dbName) throws Exception {
        SQL_Query sql = new SQL_Query();

        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, "root", "root");
            System.out.println("connected!");

            return sql.loggin(con, username, password);

        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed");
                }
            } catch (SQLException ev) {

            }
        }
    }

    @Override
    public void run() {

    }
}