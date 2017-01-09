package sample;

import Model.*;
import Controller.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.and;


/**
 * Created by Glantz on 2017-01-08.
 */
public class NoSql implements SQL_Query_IF {


    @Override
    public Boolean login(String username, String password) throws Exception {
        MongoCollection<Document> coll = Controller.db.getCollection("User");
        List<Document> users = (List<Document>) coll.find(and(eq("password", password),
                eq("user_name", username))).into(new ArrayList<Document>());
        if (users.size() == 1) {
            return true;
        } else
            return false;
    }

    @Override
    public void insert(content content) throws Exception {

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        Document document = new Document("Title", content.getTitle());
        document.append("type", content.getType());
        document.append("release_date", content.getReleaseDate());
        List<Document> creators = new ArrayList<>();
        for (Creator creator : content.getCreators()) {
            creators.add(new Document("Name", creator.getCreatorName())
                    .append("Nationality", creator.getNationality())
                    .append("Role", creator.getRole())
                    .append("addedBy", creator.getAddedBy()));
        }
        document.append("Creators", creators);
        document.append("addedby", content.getAddedBy());
        document.append("Reviews", Arrays.asList());
        document.append("Rating", Arrays.asList());
        coll.insertOne(document);


    }

    @Override
    public void insertIntoRating(content content) throws Exception {
        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
    }

    @Override
    public void insertIntoReviews(content content) throws Exception {

    }

    @Override
    public ArrayList<content> search(String name, String genre, String title) throws Exception {
        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        coll.find(or(eq("name", name), eq("genre"), eq("title", title)));
        return null;
    }

    @Override
    public ArrayList<content> searchRating(String rating) throws Exception {
        return null;
    }
}
