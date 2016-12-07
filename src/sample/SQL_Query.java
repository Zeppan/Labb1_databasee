package sample;

import java.sql.*;

import Model.*;
//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Created by Glantz on 2016-12-06.
 */
public class SQL_Query {


    /**
     * Insert into content, addedBy is a foreign key and requires ths is all ready exist in the user table
     *
     * @param con
     * @param content
     * @throws SQLException
     */
    public void insertIntoContent(Connection con, content content) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO content(title,releaseDate,type,addedBy) VALUES(?,?,?,?)", pstmt.RETURN_GENERATED_KEYS);
            pstmt.setString(1, content.getTitle());
            pstmt.setString(2, content.getReleaseDate());
            pstmt.setString(3, content.getType());
            pstmt.setString(4, content.getAddedBy());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            try {
                if (rs.next()) {
                    content.SetContentID(rs.getInt(1));
                }
            } finally {
                if (rs != null) rs.close();
            }
            pstmt.close();
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    /**
     * Insert into creator, addedBy is a foreign key and requires that it's all ready exist in the user table
     *
     * @param con
     * @param content
     * @throws SQLException
     */
    public void insertIntoCreator(Connection con, content content) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            for (Creator creator : content.getCreators()) {
                pstmt = con.prepareStatement("INSERT INTO creator(name,nationality,role,addedBy) VALUES(?,?,?,?)", pstmt.RETURN_GENERATED_KEYS);
                pstmt.setString(1, creator.getCreatorName());
                pstmt.setString(2, creator.getNationality());
                pstmt.setString(3, creator.getRole());
                pstmt.setString(4, creator.getAddedBy());
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                try {
                    if (rs.next()) {
                        creator.setCreatorID(rs.getInt(1));
                    }
                } finally {
                    if (rs != null) rs.close();
                }
                pstmt.close();
            }

        } finally {
            if (pstmt != null) pstmt.close();

        }
    }

    /**
     * Insert into created content, often used when you add a new movie and add a actor on it. Works the same on CD's
     *
     * @param con
     * @param content
     * @throws SQLException
     */
    public void insertIntoCreatedContent(Connection con, content content) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO CreatedContent VALUES(?,?)");
            for (Creator creator : content.getCreators()) {
                pstmt.setInt(1, content.getContentID());
                pstmt.setInt(2, creator.getCreatorID());
                pstmt.executeUpdate();
            }
            pstmt.close();

        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    /**
     * Insert into contentGenre, used when a content is created. Adding all the genres the movie has.
     *
     * @param con
     * @param content
     * @throws SQLException
     */
    /*public void insertIntoContentGenre(Connection con, content content) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO contentGenre VALUES(?,?)");
            for (genre genre : content.getGenres()) {
                pstmt.setInt(1, content.getContentID());
                pstmt.setString(2, genre.getGenre());
                pstmt.executeUpdate();
            }
            pstmt.close();

        } finally {
            if (pstmt != null) pstmt.close();
        }


    }*/

    /**
     * Display what is stored in the specific table that is set in the query
     *
     * @param con
     * @param query
     * @throws SQLException
     */
    public void SelectQuery(Connection con, String query) throws SQLException {


        Statement stmt = null;
        try {
            // Execute the SQL statement
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Get the attribute names
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();

            // Get the attribute values
            while (rs.next()) {
                for (int c = 1; c <= ccount; c++) {
                    System.out.print(rs.getObject(c) + "\t");
                }
                System.out.println();
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void getContent(Connection con, Model model, String query) throws SQLException {


        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                content tmp = new content();
                tmp.SetContentID(rs.getInt("contentID"));
                tmp.SetTitle(rs.getString("title"));
                tmp.SetReleaseDate(rs.getString("releaseDate"));
                tmp.SetType(rs.getString("type"));
                tmp.SetaddedBy(rs.getString("addedBy"));
                model.content.add(tmp);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void getcontentReviews(Connection con, Model model, String title) throws SQLException {


        PreparedStatement pstmt = null;
        try {
            content tmp = new content();
            pstmt = con.prepareStatement("SELECT title,releaseDate,review,review.date  FROM content,review WHERE content.contentID=review.contentID AND title = ?");
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            tmp.SetTitle(rs.getString("title"));
            tmp.SetReleaseDate(rs.getString("releaseDate"));
            tmp.addReview(rs.getString("date"), rs.getString("review"));
            while (rs.next()) {
                tmp.addReview(rs.getString("date"), rs.getString("review"));
            }
            model.content.add(tmp);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }


}
