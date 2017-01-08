package sample;

import Model.content;
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
        Document obj = new Document("name", "anders")
                .append("title", content.getTitle())
                .append("type", content.getType())
                .append("release_date", content.getReleaseDate())
                .append("creator", new Document("", ""))
                .append("addedby", content.getAddedBy());

        coll.insert(obj);


    }

    @Override
    public void insertIntoRating(Connection con, content content) throws Exception {

    }

    @Override
    public void insertIntoReviews(Connection con, content content) throws Exception {

    }

    @Override
    public ArrayList<content> search(Connection con, String name, String genre, String title) throws Exception {

        DBCollection coll = Controller.db.getCollection("content");
        
        return null;
    }

    @Override
    public ArrayList<content> searchRating(Connection con, String rating) throws Exception {
        return null;
    }
}
