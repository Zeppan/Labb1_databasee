package sample;

import Model.*;
import java.sql.Connection;



/**
 * Created by nicla on 2016-12-08.
 */
public interface SQL_Query_IF {

    void insert(Connection con,content content) throws Exception;

    void insertIntoRating(Connection con, content content) throws Exception;

    void insertIntoReviews(Connection con, content content) throws Exception;

    void search(Connection con, Model model, String name, String genre, String title) throws Exception;

    void searchRating(Connection con, Model model, String rating) throws Exception;


}
