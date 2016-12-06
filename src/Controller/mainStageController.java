package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by nicla on 2016-12-06.
 */
public class mainStageController {

    @FXML
    public void showTable(ActionEvent e) throws Exception{
        Stage TableView = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/tableView.fxml"));
        TableView.setScene(new Scene(root));
        TableView.show();
    }
}
