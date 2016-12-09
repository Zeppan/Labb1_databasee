package Controller;


import java.sql.*;

import Model.*;
import sample.SQL_Query;

public class Controller implements Runnable {


    public static void connectToDatabase(String username, String password, String dbName) throws Exception {
        String server = "jdbc:mysql://localhost:3306/" + dbName + "?UseClientEnc=UTF8";
        Connection con = null;
        SQL_Query sql = new SQL_Query();

        Model model = new Model();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server, "root", "glantz");
            System.out.println("connected!");
            PreparedStatement pstmt = null;
            try {
                pstmt = con.prepareStatement("SELECT * FROM user WHERE userName_email =? AND password = Password(?)");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                try {
                    if (rs.next()) {
                        if (rs.getInt(1) == 1) {

                        } else {
                            System.out.println(" GFUS");
                        }
                    }
                } finally {
                    if (rs != null) rs.close();
                }
                pstmt.close();
            } finally {
                if (pstmt != null) pstmt.close();


            }

            // TEST STATEMENTS
            // ****************************
            //sql.getcontentReviews(con, model, "elias");
            //******************************
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