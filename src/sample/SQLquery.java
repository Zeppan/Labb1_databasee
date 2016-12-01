package sample;
import java.sql.Connection;
import  Model.*;
/**
 * Created by Glantz on 2016-12-01.
 */
public abstract interface SQLquery {

    void updateItem(Connection con, String query);
    void insertItem(Connection con, String query);
    void deleteItem(Connection con, String query);
    void selectItem(Connection con, String query);
}
