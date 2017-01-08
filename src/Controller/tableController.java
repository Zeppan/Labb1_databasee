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
import sample.SQL_Query;

import java.util.ArrayList;

/**
 * Created by Niclas on 2016-12-06.
 */
public class tableController {

    private ArrayList<content> contents;
    private Alerts a;

    @FXML
    private TableView<content> tblView;
    @FXML
    private Label title;
    @FXML
    private ChoiceBox<String> Rating;

    /**
     * Generates the whole table with the search results
     * @param info
     */
    public void initialize(ArrayList<content> info) {

        a = new Alerts();

        for (content content : info) {
            content.setGenresString();
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


        TableColumn Title = new TableColumn("Title");
        Title.setCellValueFactory(new PropertyValueFactory<content, String>("Title"));

        TableColumn ReleaseDate = new TableColumn("ReleaseDate");
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<content, String>("releaseDate"));

        TableColumn Type = new TableColumn("Type");
        Type.setCellValueFactory(new PropertyValueFactory<content, String>("type"));

        TableColumn ratings = new TableColumn("Rating");
        ratings.setCellValueFactory(new PropertyValueFactory<content, String>("rating"));

        TableColumn genres = new TableColumn("Genres");
        genres.setCellValueFactory(new PropertyValueFactory<content, String>("genreString"));

        tblView.setItems(data);
        tblView.getColumns().addAll(Title, ReleaseDate, Type, ratings, genres);


        title.setText("Search Results");

    }


    public void setArrayList(ArrayList<content> information) {
        this.contents = new ArrayList<>();
        this.contents = information;
    }

    @FXML
    private TextArea reviewTextByUser;

    /**
     * Takes the text from the @TextArea and tries to add it to the database for the specific content
     */
    @FXML
    public void ReviewSelected() {


        ObservableList<content> item;
        item = tblView.getSelectionModel().getSelectedItems();
        String reviewText = reviewTextByUser.getText();
        content addingReview = item.get(0);
        addingReview.addReview(reviewText, Controller.usernameLoggedIn);
        SQL_Query sql = new SQL_Query();

        try {
            new Thread() {
                boolean success = false;
                Exception error;
                public void run() {
                    //Statement function here!
                    try {
                        sql.insertIntoReviews(addingReview);
                        success = true;
                    } catch (Exception e) {
                        error = e;
                    }
                    javafx.application.Platform.runLater(() -> {
                        //when done
                        if (success)
                            a.successAlert("Content reviewed successfully!");
                        else
                            a.errorAlert(error, "You have already reviewed this content!");
                    });
                }
            }.start();
        } catch (Exception Ex) {
            System.out.println("Fail");
        }
    }

    /**
     * Takes the rating from the ChoiceBox and tries to add it to the database for the specific content
     */
    @FXML
    public void rateContent() {


        ObservableList<content> revs;
        revs = tblView.getSelectionModel().getSelectedItems();
        String rate = Rating.getValue();
        content content = revs.get(0);
        content.SetRatingScore(rate, Controller.usernameLoggedIn);

        SQL_Query sql = new SQL_Query();


        try {
            new Thread() {
                boolean success = false;
                Exception error;

                public void run() {
                    //Statement function here!
                    try {
                        sql.insertIntoRating( content);
                        success = true;
                    } catch (Exception e) {
                        error = e;
                    }

                    javafx.application.Platform.runLater(new Runnable() {
                        public void run() {
                            //when done
                            if (success)
                                a.successAlert("Content rated successfully!");
                            else
                                a.errorAlert(error, "You have already rated this content!");
                        }

                    });
                }
            }.start();
        } catch (Exception Ex) {
            System.out.println(Ex);
        }


    }

    /**
     * Get selected row from table and then gets the title, Then shows all reviews for the specific content
     * @throws Exception
     */
    @FXML
    public void getSelectedRow() throws Exception {

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
