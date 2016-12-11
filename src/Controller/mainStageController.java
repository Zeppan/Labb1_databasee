package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.spec.ECField;
import java.sql.SQLException;

/**
 * Created by niclas on 2016-12-06.
 */
public class mainStageController {






    @FXML
    public void showSearch(ActionEvent e) throws Exception{
        Stage TableView = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/searchBy.fxml"));
        TableView.setScene(new Scene(root));
        TableView.show();
    }

    @FXML
    public void addMedia(ActionEvent e) throws Exception{
        Stage media = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/addMedia.fxml"));
        media.setScene(new Scene(root));
        media.show();
    }
}
