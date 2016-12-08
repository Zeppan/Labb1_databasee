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

        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));
        tabl.add(new content(1, "hej", "shit", "potatis", 3));

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
