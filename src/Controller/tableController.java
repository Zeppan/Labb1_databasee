package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * Created by Niclas on 2016-12-06.
 */
public class tableController {

    private ArrayList<content> contents;

    @FXML
    private TableView<content> tblView;
    @FXML
    private Label title;

    public void initialize(ArrayList<content> info){
        tblView.setEditable(true);

        ObservableList<content> data = FXCollections.observableArrayList(info);


        TableColumn Title = new TableColumn("Title");
        Title.setCellValueFactory(new PropertyValueFactory<content,String>("Title"));

        TableColumn ReleaseDate = new TableColumn("ReleaseDate");
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<content,String>("releaseDate"));

        TableColumn Type = new TableColumn("Type");
        Type.setCellValueFactory(new PropertyValueFactory<content,String>("type"));

        TableColumn ratings = new TableColumn("Rating");
        ratings.setCellValueFactory(new PropertyValueFactory<content,String>("rating"));

        TableColumn genres = new TableColumn("Genres");
        genres.setCellValueFactory(new PropertyValueFactory<content, String>("genres"));

        tblView.setItems(data);
        tblView.getColumns().addAll(Title, ReleaseDate, Type, ratings, genres);


        title.setText("Search Results");

    }

    public void setArrayList(ArrayList<content> information){
        this.contents = new ArrayList<>();
        this.contents = information;
    }

}
