package sample;

import Model.*;
import Controller.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;


/**
 * Created by Glantz on 2017-01-08.
 */
public class NoSql implements SQL_Query_IF {


    @Override
    public Boolean login( String username, String password) throws Exception {

        return null;
    }

    @Override
    public void insert( content content) throws Exception {

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        Document document = new Document("Title", content.getTitle());
        document.append("type", content.getType());
        document.append("release_date", content.getReleaseDate());
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
    public void insertIntoRating( content content) throws Exception {

    }

    @Override
    public void insertIntoReviews(content content) throws Exception {

    }

    @Override
    public ArrayList<content> search( String name, String genre, String title) throws Exception {
        return null;
    }

    @Override
    public ArrayList<content> searchRating( String rating) throws Exception {
        return null;
    }
}
