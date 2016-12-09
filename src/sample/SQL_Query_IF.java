package sample;

import Model.*;

import java.sql.Connection;
import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-08.
 */
public interface SQL_Query_IF {
    Boolean loggin(Connection con, String username, String password) throws Exception;

    void insert(Connection con, content content) throws Exception;

    void insertIntoRating(Connection con, content content) throws Exception;

    void insertIntoReviews(Connection con, content content) throws Exception;

    ArrayList<content> search(Connection con, String name, String genre, String title) throws Exception;

    ArrayList<content> searchRating(Connection con, String rating) throws Exception;


}
