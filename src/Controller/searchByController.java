package Controller;

import Model.content;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by nicla on 2016-12-07.
 */
public class searchByController {


    @FXML
    public void searchByName(ActionEvent e) throws IOException {
        Stage media = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));


        Parent root = fxmlLoader.load();

        ArrayList<content> tabl = new ArrayList<>();

        /*

            Query här och sätt in värdena in i tabl array listan.

         */

        tabl.add(new content(1, "Pingu!", "2003-02-01", "Movie", "7"));
        tabl.add(new content(2, "Byggare Bob", "1834-01-01", "CD", "3"));

        tabl.get(0).addReview("1337", "Denna film suger kuk");
        tabl.get(0).addReview("1234", "10/10 would see again");
        tabl.get(0).addReview("1993-03-01", "Denna film är inte att rekommendera....");
        tabl.get(1).addReview("1234", "Denna film är bäst!");

        tableController table = fxmlLoader.<tableController>getController();
        table.initialize(tabl);
        table.setArrayList(tabl);
        media.setScene(new Scene(root));
        media.show();
    }

    @FXML
    public void searchByGenre(ActionEvent e){

    }

    @FXML
    public void searchByRating(ActionEvent e){

    }

    @FXML
    public void SearchByTitle(ActionEvent e){

    }

}
