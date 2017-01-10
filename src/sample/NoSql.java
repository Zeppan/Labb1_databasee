package sample;

import Model.*;
import Controller.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;


/**
 * Created by Glantz on 2017-01-08.
 */
public class NoSql implements SQL_Query_IF {


    @Override
    public Boolean login(String username, String password) throws Exception {

        try {
            MongoCollection<Document> coll = Controller.db.getCollection("User");
            List<Document> users = (List<Document>) coll.find(and(eq("password", password),
                    eq("user_name", username))).into(new ArrayList<Document>());

            if (users.size() == 1) {

                return true;
            } else
                return false;
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    @Override
    public void insert(content content) throws Exception {

        MongoCollection<Document> coll = Controller.db.getCollection("Movie");
        try {

            Document document = new Document("Title", content.getTitle());
            document.append("Genres", new Document("Genre", content.getGenres().get(0).getGenre()).append(
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
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void insertIntoRating(content content) throws Exception {

        try {
            MongoCollection<Document> coll = Controller.db.getCollection("Movie");
            Document review = new Document("rating", content.getRating())
                    .append("addedBy", content.getObjectRating().getAddedBy());
            coll.updateOne(eq("_id", new ObjectId(content.getContentID())), Updates.addToSet("Rating", review));
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    @Override
    public void insertIntoReviews(content content) throws Exception {

        try {
            MongoCollection<Document> coll = Controller.db.getCollection("Movie");
            Document review = new Document("review", content.getReviewsArray().get(content.getReviewsArray().size() - 1).getReview())
                    .append("addedBy", content.getReviewsArray().get(content.getReviewsArray().size() - 1).getAddedBy());

            coll.updateOne(eq("_id", new ObjectId(content.getContentID())), Updates.addToSet("Reviews", review));
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }

    }

    @Override
    public ArrayList<content> search(String name, String genre, String title) throws Exception {
        ArrayList<content> contentarray = new ArrayList<>();


        content tmp = new content();


        Document query = new Document();
        query.append("Title", new Document("$regex", title));
        query.append("Genres.Genre", new Document("$regex", genre));
        query.append("Creators.Name", new Document("$regex", name));

        try {
            MongoCollection<Document> coll = Controller.db.getCollection("Movie");
            List<Document> users = (List<Document>) coll.find(query).into(new ArrayList<Document>());
            for (Document doc : users) {
                tmp.SetContentID(doc.get("_id").toString());
                tmp.SetTitle(doc.getString("Title"));
                Document tmpdoc = (Document) doc.get("Genres");
                tmp.setGenre(tmpdoc.getString("Genre"), tmpdoc.getString("addedBy"));
                tmp.SetType(doc.getString("Type"));
                tmp.SetReleaseDate(doc.getString("release_date"));
                tmp.SetCreators(getCreators((ArrayList<Document>) doc.get("Creators")));
                tmp.SetaddedBy(doc.getString("addedby"));
                tmp.SetReviews(getReviews((ArrayList<Document>) doc.get("Reviews")));
                tmp.SetAvarageRatingScore(getAvgRating((ArrayList<Document>) doc.get("Rating")));
                contentarray.add(tmp);

            }
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
        return contentarray;
    }

    @Override
    public ArrayList<content> searchRating(String rating) throws Exception {
        ArrayList<content> contentarray = new ArrayList<>();
        ArrayList<content> contentAVG = new ArrayList<>();
        int searchrating;
        content tmp = new content();


        try {
            MongoCollection<Document> coll = Controller.db.getCollection("Movie");
            List<Document> users = (List<Document>) coll.find().into(new ArrayList<Document>());
            for (Document doc : users) {
                tmp.SetContentID(doc.get("_id").toString());
                tmp.SetTitle(doc.getString("Title"));
                Document tmpdoc = (Document) doc.get("Genres");
                tmp.setGenre(tmpdoc.getString("Genre"), tmpdoc.getString("addedBy"));
                tmp.SetType(doc.getString("Type"));
                tmp.SetReleaseDate(doc.getString("release_date"));
                tmp.SetCreators(getCreators((ArrayList<Document>) doc.get("Creators")));
                tmp.SetaddedBy(doc.getString("addedby"));
                tmp.SetReviews(getReviews((ArrayList<Document>) doc.get("Reviews")));
                tmp.SetAvarageRatingScore(getAvgRating((ArrayList<Document>) doc.get("Rating")));
                contentarray.add(tmp);

            }
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }

        searchrating = Integer.parseInt(rating);
        for (content content : contentarray) {
            System.out.println(content.getRating());

            int contentrating = (int) Double.parseDouble(content.getRating());
            if (((searchrating >= contentrating) && (searchrating < (contentrating + 1))) || rating == null) ;
            {
                contentAVG.add(content);
            }
        }

        return contentAVG;
    }

    private ArrayList getReviews(ArrayList<Document> reviews) {

        ArrayList<review> reviewArray = new ArrayList<>();
        for (Document document : reviews) {
            reviewArray.add(new review(document.getString("review"), document.getString("addedBy")));
        }
        return reviewArray;
    }

    private ArrayList getCreators(ArrayList<Document> creators) {

        ArrayList<Creator> creatorArray = new ArrayList<>();
        Creator tmpcreator = new Creator();
        for (Document document : creators) {
            tmpcreator.setCreatorName(document.getString("Name"));
            tmpcreator.setNationality(document.getString("Nationality"));
            tmpcreator.setRole(document.getString("Role"));
            tmpcreator.setAddedBy(document.getString("addedBy"));
            creatorArray.add(tmpcreator);
        }
        System.out.println(creatorArray);
        return creatorArray;
    }

    private String getAvgRating(ArrayList<Document> ratings) {
        ArrayList<Integer> avgrating = new ArrayList<>();
        for (Document document : ratings) {
            avgrating.add(Integer.parseInt(document.getString("rating")));
        }
        if (avgrating != null) {
            double sum = 0;
            int n = avgrating.size();
            for (int i : avgrating) {
                sum += i;
            }
            sum = sum / n;
            return Double.toString(sum);
        } else {
            return "No rating";
        }
    }
}
