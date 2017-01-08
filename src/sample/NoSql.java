package sample;

import Model.*;
import Controller.*;
import com.mongodb.*;
import org.bson.Document;
//import com.mongodb.DBCollection;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Glantz on 2017-01-08.
 */
public class NoSql implements SQL_Query_IF {


    @Override
    public Boolean login(Connection con, String username, String password) throws Exception {

        return null;
    }

    @Override
    public void insert(Connection con, content content) throws Exception {

        DBCollection coll = Controller.db.getCollection("movie");
        Document document = new Document("Title", content.getTitle())
                .append("type", content.getType())
                .append("release_date", content.getReleaseDate());
        for (Creator creator : content.getCreators()) {
            document.append("creator", new Document("Name", creator.getCreatorName())
                    .append("Nationality", creator.getNationality())
                    .append("Role", creator.getRole())
                    .append("addedBy", creator.getAddedBy()));
        }
        document.append("addedby", content.getAddedBy());
        coll.insertOne(document);


    }

    @Override
    public void insertIntoRating(Connection con, content content) throws Exception {

    }

    @Override
    public void insertIntoReviews(Connection con, content content) throws Exception {

    }

    @Override
    public ArrayList<content> search(Connection con, String name, String genre, String title) throws Exception {
        return null;
    }

    @Override
    public ArrayList<content> searchRating(Connection con, String rating) throws Exception {
        return null;
    }
}
