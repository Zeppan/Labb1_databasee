package sample;

import Model.*;

import java.sql.Connection;
import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-08.
 */
public interface SQL_Query_IF {
    Boolean login( String username, String password) throws Exception;

    void insert( content content) throws Exception;

    void insertIntoRating( content content) throws Exception;

    void insertIntoReviews( content content) throws Exception;

    ArrayList<content> search( String name, String genre, String title) throws Exception;

    ArrayList<content> searchRating( String rating) throws Exception;


}
