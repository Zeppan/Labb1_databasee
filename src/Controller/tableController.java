package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
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
    @FXML
    private ChoiceBox<String> Rating;

    public void initialize(ArrayList<content> info){

        for(content content : info){
            content.setGenresString();
            //System.out.println(content.getShit());
        }

        Rating.getItems().add("1");
        Rating.getItems().add("2");
        Rating.getItems().add("3");
        Rating.getItems().add("4");
        Rating.getItems().add("5");
        Rating.getItems().add("6");
        Rating.getItems().add("7");
        Rating.getItems().add("8");
        Rating.getItems().add("9");
        Rating.getItems().add("10");

        tblView.setEditable(true);

        ObservableList<content> data = FXCollections.observableArrayList(info);
       // ObservableList<genre> genre = FXCollections.observableArrayList(info.get(0).getGenres());


        TableColumn Title = new TableColumn("Title");
        Title.setCellValueFactory(new PropertyValueFactory<content,String>("Title"));

        TableColumn ReleaseDate = new TableColumn("ReleaseDate");
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<content,String>("releaseDate"));

        TableColumn Type = new TableColumn("Type");
        Type.setCellValueFactory(new PropertyValueFactory<content,String>("type"));

        TableColumn ratings = new TableColumn("Rating");
        ratings.setCellValueFactory(new PropertyValueFactory<content,String>("rating"));

        TableColumn genres = new TableColumn("Genres");
        genres.setCellValueFactory(new PropertyValueFactory<content, String>("genreString"));

        tblView.setItems(data);
        //tblView.setItems(genre);
        tblView.getColumns().addAll(Title, ReleaseDate, Type, ratings, genres);


        title.setText("Search Results");

    }


    public void setArrayList(ArrayList<content> information){
        this.contents = new ArrayList<>();
        this.contents = information;
    }

    @FXML
    private TextArea reviewTextByUser;
    @FXML
    public void ReviewSelected(){
            ObservableList<content> item;
            item = tblView.getSelectionModel().getSelectedItems();
            String reviewText = reviewTextByUser.getText();
            item.get(0).addReview(reviewText);
            content cnt = item.get(0);
    }

    @FXML
    ChoiceBox<String> rating;
    @FXML
    public void rateContent(){
        ObservableList<content> revs;
        revs = tblView.getSelectionModel().getSelectedItems();
        //revs.get(0).SetRatingScore(rating.getValue());
    }

    @FXML
    public void getSelectedRow() throws IOException {

        Stage reviewsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/reviewTable.fxml"));
        Parent root = fxmlLoader.load();

        ObservableList<content> revs;
        revs = tblView.getSelectionModel().getSelectedItems();
        ArrayList<review> reviewsForSelectedRow = tblView.getSelectionModel().getSelectedItems().get(0).getReviewsArray();
        String title = revs.get(0).getTitle();

        reviewTableController table = fxmlLoader.<reviewTableController>getController();
        table.initialize(reviewsForSelectedRow, title);

        reviewsStage.setScene(new Scene(root));
        reviewsStage.show();

    }

}
