package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by nicla on 2016-12-06.
 */
public class tableController {

    @FXML
    TableView tblView;

    public void initialize(){
        //For dynamic calculate later on
        TableColumn clm1 = new TableColumn("Test");
        TableColumn clm2 = new TableColumn("Test");
        TableColumn clm3 = new TableColumn("Test");
        TableColumn clm4 = new TableColumn("Test");

        tblView.getColumns().addAll(clm1, clm2, clm3, clm4);
    }
}
