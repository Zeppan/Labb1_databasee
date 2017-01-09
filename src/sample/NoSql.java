package sample;

import Model.*;
import Controller.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
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
        for(Document doc:users){
           String tmp = doc.getString("Name");
            System.out.println(tmp);
        }
        if (users.size() == 1) {

            return true;
        } else
            return false;
    }

    @Override
    public void insert(content content) throws Exception {

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        try {

           Document document = new Document("Title", content.getTitle());
           document.append("Genres",new Document("Genre", content.getGenres().get(0).getGenre()).append(
                   "addedBy", content.getGenres().get(0).getAddedBy()));
           document.append("Type", content.getType());
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
       }catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void insertIntoRating(content content) throws Exception {

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        Document review = new Document()
                .append("Rating", new Document("rating", content.getRating())
                        .append("addedBy", content.getObjectRating().getAddedBy()));
        coll.updateOne(eq("_id", new ObjectId(content.getContentID())), Updates.addToSet("Rating", review));

    }

    @Override
    public void insertIntoReviews(content content) throws Exception {
        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        Document review = new Document()
                .append("Reviews", new Document("review", content.getReviewsArray().get(content.getReviewsArray().size() - 1).getReview())
                        .append("addedBy", content.getReviewsArray().get(content.getReviewsArray().size() - 1).getAddedBy()));

        coll.updateOne(eq("_id", new ObjectId(content.getContentID())), Updates.addToSet("Reviews", review));

    }
   // and(eq("title", title),
   // eq("genre", genre))
    @Override
    public ArrayList<content> search(String name, String genre, String title) throws Exception {
        ArrayList<content> contentarray = new ArrayList<>();
        ArrayList<Creator> creatorArray = new ArrayList<>();
        content tmp = new content();
        Creator tmpcreator = new Creator();

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        List<Document> users = (List<Document>) coll.find().into(new ArrayList<Document>());
        for(Document doc:users){
            System.out.println(doc);
            tmp.SetContentID(doc.get("_id").toString());
            tmp.SetTitle(doc.getString("Title"));
            Document tmpdoc = (Document)doc.get("Genres");
            tmp.setGenre(tmpdoc.getString("Genre"),tmpdoc.getString("addedBy"));
            tmp.SetType(doc.getString("Type"));
            tmp.SetReleaseDate(doc.getString("release_date"));

            ArrayList<Document> creators = (ArrayList<Document>)doc.get("Creators");
            for(Document document: creators){
                tmpcreator.setCreatorName(document.getString("Name"));
                tmpcreator.setNationality(document.getString("Nationality"));
                tmpcreator.setRole(document.getString("Role"));
                tmpcreator.setAddedBy(document.getString("addedBy"));
                creatorArray.add(tmpcreator);
            }
            tmp.SetCreators(creatorArray);
            tmp.SetaddedBy(doc.getString("addedby"));
            tmp.SetAvarageRatingScore("ingen");



            contentarray.add(tmp);

        }


        /*
        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        coll.find(or(eq("name", name), eq("genre"), eq("title", title)));*/
        return contentarray;
    }

    @Override
    public ArrayList<content> searchRating(String rating) throws Exception {



        return null;
    }
}
