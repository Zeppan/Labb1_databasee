package Controller;

import javafx.scene.control.Alert;

/**
 * Created by nicla on 2016-12-11.
 */
public class Alerts {

    public void errorAlert(Exception e, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(e.getMessage());
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void successAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Success!");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
