package Controller;

import Model.content;
import Model.rating;
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
    public void searchBy(ActionEvent e) throws IOException {
        Stage media = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
        Parent root = fxmlLoader.load();

        ArrayList<content> tabl = new ArrayList<>();

        /*

            Query här och sätt in värdena in i tabl array listan.

         */

        tabl.add(new content(1, "Nalle Puh", "1993-05-14", "Movie", new rating("", "5")));
        tabl.add(new content(1, "Donkey Kong", "1997-05-14", "Movie", new rating("", "7")));
        tabl.add(new content(1, "Alladin", "1993-05-14", "CD", new rating("", "8")));
        tabl.add(new content(1, "Pirates", "1993-05-14", "Movie", new rating("", "10")));
        tabl.add(new content(1, "Hitz for Kidz", "1993-05-14", "Movie", new rating("", "2")));

        tableController table = fxmlLoader.<tableController>getController();
        table.initialize(tabl);
        table.setArrayList(tabl);
        media.setScene(new Scene(root));
        media.show();
    }


    @FXML
    public void searchByRating(ActionEvent e){

    }



}
