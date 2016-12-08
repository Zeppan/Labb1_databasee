package Controller;

import Model.content;
import Model.review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * Created by nicla on 2016-12-08.
 */
public class reviewTableController {

    @FXML
    private TableView reviewTable;
    @FXML
    Label title;

    public void initialize(ArrayList<review> info, String titleText){



        ObservableList<review> data = FXCollections.observableArrayList(info);

        TableColumn review = new TableColumn("Review");
        review.setCellValueFactory(new PropertyValueFactory<review,String>("review"));

        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<review,String>("date"));

        reviewTable.setItems(data);
        reviewTable.getColumns().addAll(review, date);

        title.setText(titleText);

    }

}
