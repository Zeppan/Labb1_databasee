package Controller;

import Model.content;
import Model.rating;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.SQL_Query;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-07.
 */
public class searchByController {


    @FXML
    private TextField name, genre, title;
    @FXML
    public void searchBy(ActionEvent e) throws Exception {
        SQL_Query sql = new SQL_Query();

        String searchName = name.getText(), searchGenre = genre.getText(), searchTitle = title.getText();

        //Thread
        ArrayList<content> table = sql.search(Controller.con, searchName, searchGenre, searchTitle);
        System.out.println(table);

        Stage media = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
        Parent root = fxmlLoader.load();

        tableController tbl = fxmlLoader.<tableController>getController();
        tbl.initialize(table);
        tbl.setArrayList(table);
        media.setScene(new Scene(root));
        media.show();
    }


    @FXML
    public void searchByRating(ActionEvent e){

    }



}
