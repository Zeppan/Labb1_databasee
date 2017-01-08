package sample;

import java.sql.*;
import java.util.ArrayList;

import Model.*;


/**
 * Created by Glantz on 2016-12-06.
 */
public class SQL_Query implements SQL_Query_IF {
    /**
     * Logs in a specific user, that user has to exist in the database in beforehand
     *
     * @param con      Connection
     * @param username String
     * @param password String
     * @return
     * @throws Exception
     */
    @Override
    public Boolean login(Connection con, String username, String password) throws Exception {

        PreparedStatement pstmt = null;
        try {

            pstmt = con.prepareStatement("SELECT COUNT(*) FROM user WHERE userName_email = ? AND password = Password(?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            try {
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                if (rs != null) rs.close();
            }
            pstmt.close();
        } finally {
            if (pstmt != null) pstmt.close();
        }
        return false;
    }

    /**
     * A transaction that inserts a new content with creators and genre.
     *
     * @param con     Connection
     * @param content
     * @throws Exception
     */
    @Override
    public void insert(Connection con, content content) throws Exception {
        con.setAutoCommit(false);
        try {
            insertIntoContent(con, content);
            insertIntoCreator(con, content);
            insertIntoCreatedContent(con, content);
            insertIntoContentGenre(con, content);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.getMessage();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    /**
     * Inserts a newly made review to the database
     *
     * @param con
     * @param content
     * @throws Exception
     */
    @Override
    public void insertIntoReviews(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO review(userName,contentID,review) VALUES(?,?,?)");
            pstmt.setString(1, content.getReviewsArray().get(content.getReviewsArray().size() - 1).getAddedBy());

            pstmt.setInt(2, content.getContentID());
            pstmt.setString(3, content.getReviewsArray().get(content.getReviewsArray().size() - 1).getReview());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    /**
     * Insert a newly created raiting to the database
     *
     * @param con
     * @param content
     * @throws Exception
     */
    @Override
    public void insertIntoRating(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO rating(username,contentID,rating) VALUES(?,?,?)");
            pstmt.setString(1, content.getObjectRating().getAddedBy());
            pstmt.setInt(2, content.getContentID());
            pstmt.setString(3, content.getRating());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    /**
     * A private method used in the insert, this specific part insert the newly made content into the database
     *
     * @param con
     * @param content
     * @throws Exception
     */
    private void insertIntoContent(Connection con, content content) throws Exception {
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
     * A private method used in the insert, this specific method inserts a newly made creator. If the creator already exist the creatorID is collected for further use
     *
     * @param con
     * @param content
     * @throws Exception
     */
    private void insertIntoCreator(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            for (Creator creator : content.getCreators()) {
                if (checkcreator(con, creator) == 0) {
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
                } else {
                    creator.setCreatorID(checkcreator(con, creator));
                }
            }

        } finally {
            if (pstmt != null) pstmt.close();

        }
    }

    /**
     * A private method used in insert, this specific method collects the contentID and the creatorID and adds them together in a single table
     *
     * @param con
     * @param content
     * @throws Exception
     */
    private void insertIntoCreatedContent(Connection con, content content) throws Exception {
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
     * A private method used in insert, this specific method collect the contentID and pair it with the genre, then this is inserted into a specific table
     *
     * @param con
     * @param content
     * @throws Exception
     */
    private void insertIntoContentGenre(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO contentGenre VALUES(?,?,?)");
            for (genre genre : content.getGenres()) {
                pstmt.setInt(1, content.getContentID());
                pstmt.setString(2, genre.getGenre());
                pstmt.setString(3, genre.getAddedBy());
                pstmt.executeUpdate();
            }
            pstmt.close();

        } finally {
            if (pstmt != null) pstmt.close();
        }


    }

    /**
     * Private method used bu insertcreator, this method checks if the creator already exist and if so returns its creatorID
     *
     * @param con
     * @param creator
     * @return
     * @throws Exception
     */
    private int checkcreator(Connection con, Creator creator) throws Exception {
        int tmpCreatorID = 0;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("SELECT creatorID FROM creator WHERE name = ? AND nationality = ? AND role = ?");
            pstmt.setString(1, creator.getCreatorName());
            pstmt.setString(2, creator.getNationality());
            pstmt.setString(3, creator.getRole());
            ResultSet rs = pstmt.executeQuery();
            try {
                if (rs.next()) {
                    tmpCreatorID = rs.getInt(1);
                }

            } finally {
                if (rs != null) rs.close();
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
        if (tmpCreatorID != 0) {
            return tmpCreatorID;
        } else {
            return 0;
        }

    }

    /**
     * search after titel, name and genre. all possible matches are placed into an arraylist and then returned.
     *
     * @param con
     * @param name
     * @param genre
     * @param title
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<content> search(Connection con, String name, String genre, String title) throws Exception {
        ArrayList<content> tmparr = new ArrayList<>();
        con.setAutoCommit(false);
        PreparedStatement pstmt = null;
        try {

            pstmt = con.prepareStatement("SELECT DISTINCT content.contentID, title, content.releaseDate,content.type,content.addedBy FROM content, contentGenre,creator,CreatedContent WHERE content.contentID = CreatedContent.contentID AND content.contentID = contentGenre.contentID AND creator.creatorID =createdcontent.creatorID AND creator.name LIKE ? AND contentgenre.genre LIKE ? AND content.title LIKE ?");
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, "%" + genre + "%");
            pstmt.setString(3, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    content tmp = new content();
                    tmp.SetContentID(rs.getInt("contentID"));
                    tmp.SetTitle(rs.getString("title"));
                    tmp.SetReleaseDate(rs.getString("releaseDate"));
                    tmp.SetType(rs.getString("type"));
                    tmp.SetaddedBy(rs.getString("addedBy"));
                    tmp.SetAvarageRatingScore(avgRating(con, rs.getInt("contentID")));
                    tmp.Setgenres(getGenres(con, rs.getInt("contentID")));
                    tmp.SetCreators(getCreators(con, rs.getInt("contentID")));
                    tmp.SetReviews(getReviews(con, rs.getInt("contentID")));
                    tmparr.add(tmp);
                    con.commit();
                }
            } finally {
                if (rs != null) rs.close();
            }

        } catch (Exception e) {
            con.rollback();
            e.getMessage();
            throw e;
        } finally {
            con.setAutoCommit(true);
            if (pstmt != null) pstmt.close();
        }
        return tmparr;
    }

    /**
     * Search after a rating all the matches are stored in one arraylist and then returned
     *
     * @param con
     * @param rating
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<content> searchRating(Connection con, String rating) throws Exception {
        ArrayList<content> tmparr = new ArrayList<>();
        con.setAutoCommit(false);
        PreparedStatement pstmt = null;
        try {

            pstmt = con.prepareStatement("SELECT DISTINCT content.contentID, title, content.releaseDate, content.type, content.addedBy, AVG(rating.rating) AS rating FROM content,rating WHERE content.contentID = rating.contentID GROUP BY content.contentID HAVING AVG(rating.rating) LIKE ?");
            pstmt.setString(1, rating + "%");

            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    content tmp = new content();
                    tmp.SetContentID(rs.getInt("contentID"));
                    tmp.SetTitle(rs.getString("title"));
                    tmp.SetReleaseDate(rs.getString("releaseDate"));
                    tmp.SetType(rs.getString("type"));
                    tmp.SetaddedBy(rs.getString("addedBy"));
                    tmp.SetAvarageRatingScore(avgRating(con, rs.getInt("contentID")));
                    tmp.Setgenres(getGenres(con, rs.getInt("contentID")));
                    tmp.SetCreators(getCreators(con, rs.getInt("contentID")));
                    tmp.SetReviews(getReviews(con, rs.getInt("contentID")));
                    tmparr.add(tmp);
                }
            } finally {
                if (rs != null) rs.close();
            }

        } catch (Exception e) {
            con.rollback();
            e.getMessage();
            throw e;
        } finally {
            con.setAutoCommit(true);
            if (pstmt != null) pstmt.close();
        }
        return tmparr;

    }

    /**
     * private method that collects all the genres connected to a specific contentID and the returned in a arraylist
     *
     * @param con
     * @param contentID
     * @return
     * @throws Exception
     */
    private ArrayList<genre> getGenres(Connection con, int contentID) throws Exception {
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

    /**
     * Private method that calculates the avg rating for a specific contentID and then returns it
     *
     * @param con
     * @param contentID
     * @return
     * @throws Exception
     */
    private String avgRating(Connection con, int contentID) throws Exception {
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
        if (tmp != null) {
            return tmp;
        } else {
            tmp = "no rating";
            return tmp;
        }

    }

    /**
     * Private method that gets all creators connected to a specific contentID and returned in a arraylist
     *
     * @param con
     * @param contentID
     * @return
     * @throws Exception
     */
    private ArrayList<Creator> getCreators(Connection con, int contentID) throws Exception {
        PreparedStatement pstmt = null;
        ArrayList<Creator> Creators = new ArrayList<>();
        Creator tmp = new Creator();
        try {
            pstmt = con.prepareStatement("SELECT * FROM creator,createdcontent WHERE creator.creatorID = createdcontent.creatorID AND createdcontent.contentID = ?");
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

    /**
     * Private method that gets all the reviews that are connected to a specific contentID
     *
     * @param con
     * @param contentID
     * @return
     * @throws Exception
     */
    private ArrayList<review> getReviews(Connection con, int contentID) throws Exception {
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


}