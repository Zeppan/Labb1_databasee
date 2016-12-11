package sample;

import java.sql.*;
import java.util.ArrayList;

import Model.*;


/**
 * Created by Glantz on 2016-12-06.
 */
public class SQL_Query implements SQL_Query_IF {

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


    private void insertIntoCreator(Connection con, content content) throws Exception {
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

    @Override
    public ArrayList<content> searchRating(Connection con, String rating) throws Exception {
        ArrayList<content> tmparr = new ArrayList<>();
        con.setAutoCommit(false);
        PreparedStatement pstmt = null;
        try {
            content tmp = new content();
            pstmt = con.prepareStatement("SELECT DISTINCT content.contentID, title, content.releaseDate, content.type, content.addedBy, AVG(rating.rating) AS rating FROM content,rating WHERE content.contentID = rating.contentID GROUP BY content.contentID HAVING AVG(rating.rating) LIKE ?");
            pstmt.setString(1, rating + "%");

            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
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
        if (tmp !=null) {
            return tmp;
        } else {
            tmp = "no rating";
            return tmp;
        }

    }


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