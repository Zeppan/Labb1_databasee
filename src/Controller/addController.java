package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

/**
 * Created by Niclas on 2016-12-06.
 */
public class addController{

    @FXML
    private ChoiceBox<String> contentType;

    public void initialize(){
        contentType.getItems().add("Movie");
        contentType.getItems().add("CD");
    }
}
