package sample;

import Model.*;
import Model.content;

import java.sql.Connection;
import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-08.
 */
public interface SQL_Query_IF {
    void insertIntoRating(Connection con, content content) throws Exception;

    void insertIntoContent(Connection con, content content) throws Exception;

    void insertIntoCreator(Connection con, content content) throws Exception;

    void insertIntoCreatedContent(Connection con, content content) throws Exception;

    void insertIntoContentGenre(Connection con, content content) throws Exception;

    void search(Connection con, Model model, String name, String genre, String title) throws Exception;

    void searchRating(Connection con, Model model, String rating) throws Exception;

    ArrayList<genre> getGenres(Connection con, int contentID) throws Exception;

    String avgRating(Connection con, int contentID) throws Exception;

    ArrayList<Creator> getCreators(Connection con, int contentID) throws Exception;

    ArrayList<review> getReviews(Connection con, int contentID) throws Exception;
}
