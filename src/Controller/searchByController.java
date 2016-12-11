package Controller;

import Model.content;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.SQL_Query;

import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-07.
 */
public class searchByController {


    @FXML
    private TextField name, genre, title;
    @FXML
    Button Search1, Search2;
    @FXML
    public void searchBy(ActionEvent e) throws Exception {
        SQL_Query sql = new SQL_Query();

        String searchName = name.getText(), searchGenre = genre.getText(), searchTitle = title.getText();

        //Thread
        ArrayList<content> table = sql.search(Controller.con, searchName, searchGenre, searchTitle);

        Stage media = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
        Parent root = fxmlLoader.load();

        tableController tbl = fxmlLoader.<tableController>getController();
        tbl.initialize(table);
        tbl.setArrayList(table);
        media.setScene(new Scene(root));
        media.show();

        Stage stage = (Stage) Search1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private TextField rating;
    @FXML
    public void searchByRating(ActionEvent e) throws Exception {

        SQL_Query sql = new SQL_Query();
        String ratings = rating.getText();

        ArrayList<content> table = sql.searchRating(Controller.con, ratings);
        Stage media = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
        Parent root = fxmlLoader.load();

        tableController tbl = fxmlLoader.<tableController>getController();
        tbl.initialize(table);
        tbl.setArrayList(table);
        media.setScene(new Scene(root));
        media.show();

        Stage stage = (Stage) Search2.getScene().getWindow();
        stage.close();
    }





}
