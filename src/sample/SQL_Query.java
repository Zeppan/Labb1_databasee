package sample;

import java.sql.*;
import java.util.ArrayList;

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
    public void insertIntoReviews(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSER INTO review(userName,contentID,addedBY) VALUES(?,?,?)");
            pstmt.setInt(1, content.getContentID());
            pstmt.setString(2, content.getReviewsArray().get(content.getReviewsArray().size() - 1).getReview());
            pstmt.setString(3, content.getReviewsArray().get(content.getReviewsArray().size() - 1).getAddedBy());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    public void insertIntoRating(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("");
            //pstmt.setString(1, content.);
            pstmt.setInt(2, content.getContentID());
            pstmt.setString(3, content.getRating());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    public void insertIntoContent(Connection con, content content) throws Exception {
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
    public void insertIntoCreator(Connection con, content content) throws Exception {
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
    public void insertIntoCreatedContent(Connection con, content content) throws Exception {
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
    public void insertIntoContentGenre(Connection con, content content) throws Exception {
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


    }

    /**
     * Display what is stored in the specific table that is set in the query
     *
     * @param con
     * @param query
     * @throws SQLException
     */
    public void SelectQuery(Connection con, String query) throws Exception {


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

    public void getContent(Connection con, Model model, String query) throws Exception {


        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
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


    public void search(Connection con, Model model, String name, String genre, String title) throws Exception {
        PreparedStatement pstmt = null;
        try {
            content tmp = new content();
            pstmt = con.prepareStatement("SELECT DISTINCT content.contentID,title, content.releaseDate,content.type FROM content, contentGenre,creator,CreatedContent,rating WHERE content.contentID = CreatedContent.contentID AND content.contentID = contentGenre.contentID  AND creator.name LIKE ? AND content.title LIKE ? AND contentGenre.genre LIKE ? GROUP BY contentID");
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, "%+" + genre + "%");
            pstmt.setString(3, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    tmp.SetContentID(rs.getInt("content.contentID"));
                    tmp.SetTitle(rs.getString("title"));
                    tmp.SetReleaseDate(rs.getString("content.releaseDate"));
                    tmp.SetType(rs.getString("content.type"));
                    tmp.SetaddedBy(rs.getString("content.addedBy"));
                    tmp.SetRatingScore(avgRating(con, rs.getInt("content.contentID")));
                    tmp.Setgenres(getGenres(con, rs.getInt("content.contentID")));
                    tmp.SetCreators(getCreators(con, rs.getInt("content.contentID")));
                    tmp.SetReviews(getReviews(con, rs.getInt("content.contentID")));
                    model.content.add(tmp);
                }
            } finally {
                if (rs != null) rs.close();
            }

        } finally {
            if (pstmt != null) pstmt.close();
        }

    }

    public ArrayList<genre> getGenres(Connection con, int contentID) throws Exception {
        PreparedStatement pstmt = null;
        ArrayList<genre> genres = new ArrayList<genre>();
        try {
            pstmt = con.prepareStatement("SELECT * FROM contentGenre WHERE contentID = ?");
            pstmt.setInt(1, contentID);
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    genres.add(new genre(rs.getString("genre")));
                }

            } finally {
                if (rs != null) rs.close();
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
        return genres;
    }

    public String avgRating(Connection con, int contentID) throws Exception {
        PreparedStatement pstmt = null;
        String tmp;
        try {
            pstmt = con.prepareStatement("SELECT contentID, AVG(rating) AS rating FROM rating WHERE contentID = ?");
            pstmt.setInt(1, contentID);
            ResultSet rs = pstmt.executeQuery();
            try {
                rs.next();
                tmp = rs.getString("rating");
            } finally {
                if (rs != null) rs.close();
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
        return tmp;
    }

    public ArrayList<Creator> getCreators(Connection con, int contentID) throws Exception {
        PreparedStatement pstmt = null;
        ArrayList<Creator> Creators = new ArrayList<>();
        Creator tmp = new Creator();
        try {
            pstmt = con.prepareStatement("SELECT * FROM creator WHERE creatorID = (SELECT creatorID FROM CreatedContent WHERE contentID = ?)");
            pstmt.setInt(1, contentID);
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    tmp.setCreatorName(rs.getString("name"));
                    tmp.setCreatorID(rs.getInt("creatorID"));
                    tmp.setNationality(rs.getString("nationality"));
                    tmp.setRole(rs.getString("role"));
                    tmp.setAddedBy(rs.getString("addedBy"));
                    Creators.add(tmp);
                }
            } finally {
                if (rs != null) rs.close();
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
        return Creators;
    }

    public ArrayList<review> getReviews(Connection con, int contentID) throws Exception {
        PreparedStatement pstmt = null;
        ArrayList<review> reviews = new ArrayList<>();
        try {
            pstmt = con.prepareStatement("SELECT * FROM review WHERE contentID = ?");
            pstmt.setInt(1, contentID);
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    reviews.add(new review(rs.getString("date"), rs.getString("review"), rs.getString("userName")));
                }

            } finally {
                if (rs != null) rs.close();
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
        return reviews;
    }

    public void searchRating(Connection con, Model model, String rating) throws Exception {
        PreparedStatement pstmt = null;
        try {
            content tmp = new content();
            pstmt = con.prepareStatement("SELECT DISTINCT content.contentID, title, content.releaseDate, content.type, content.addedBy, AVG(rating.rating) AS rating FROM content,rating WHERE content.contentID = rating.contentID GROUP BY content.contentID HAVING AVG(rating.rating) LIKE ?");
            pstmt.setString(1, rating + "%");

            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next())
                {
                    tmp.SetContentID(rs.getInt("contentID"));
                    tmp.SetTitle(rs.getString("title"));
                    tmp.SetReleaseDate(rs.getString("content.releaseDate"));
                    tmp.SetType(rs.getString("content.type"));
                    tmp.SetaddedBy(rs.getString("addedBy"));
                    tmp.SetRatingScore(avgRating(con, rs.getInt("content.contentID")));
                    tmp.Setgenres(getGenres(con, rs.getInt("content.contentID")));
                    tmp.SetCreators(getCreators(con, rs.getInt("content.contentID")));
                    tmp.SetReviews(getReviews(con, rs.getInt("content.contentID")));
                    model.content.add(tmp);
                }
            } finally {
                if (rs != null) rs.close();
            }

        } finally {
            if (pstmt != null) pstmt.close();
        }


    }
}