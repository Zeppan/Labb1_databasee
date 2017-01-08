package sample;

import Model.*;
import Controller.*;
import com.mongodb.*;
import org.bson.*;
//import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

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

        MongoCollection<Document> collection = Controller.db.getCollection("test");
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
