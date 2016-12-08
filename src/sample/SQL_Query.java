package sample;

import java.sql.*;
import java.util.ArrayList;

import Model.*;
//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Created by Glantz on 2016-12-06.
 */
public class SQL_Query implements SQL_Query_IF {


 void insertIntoReviews(Connection con, content content) throws Exception {
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

    @Override
    public void insertIntoRating(Connection con, content content) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO rating(username,contentID,rating) VALUES(?,?,?)");
            pstmt.setString(1, content.getObjectRating().getAddedBy());
            pstmt.setInt(2, content.getContentID());
            pstmt.setString(3, content.getRating());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
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


    @Override
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


    @Override
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


    @Override
    public void insertIntoContentGenre(Connection con, content content) throws Exception {
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

    @Override
    public void searchRating(Connection con, Model model, String rating) throws Exception {
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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


}