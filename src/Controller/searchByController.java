package Controller;

import Model.content;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.NoSql;
import sample.SQL_Query;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Niclas on 2016-12-07.
 */
public class searchByController {


    @FXML
    private TextField name, genre, title;
    @FXML
    Button Search1, Search2;

    /**
     * Takes in the text from textfield and then search for those items in the database
     *
     * @param e
     * @throws Exception
     */
    @FXML
    public void searchBy(ActionEvent e) throws Exception {
        SQL_Query sql = new SQL_Query();
        NoSql noSql = new NoSql();

        String searchName = name.getText(), searchGenre = genre.getText(), searchTitle = title.getText();

        try {
            new Thread() {
                ArrayList<content> table;

                public void run() {
                    //Statement function here!
                    try {
                        table = noSql.search( searchName, searchGenre, searchTitle);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    javafx.application.Platform.runLater(new Runnable() {
                        public void run() {
                            //when done
                            Stage media = new Stage();

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            tableController tbl = fxmlLoader.<tableController>getController();
                            tbl.initialize(table);
                            tbl.setArrayList(table);
                            media.setScene(new Scene(root));
                            media.show();

                            Stage stage = (Stage) Search1.getScene().getWindow();
                            stage.close();
                        }

                    });
                }
            }.start();
        } catch (Exception Ex) {
            System.out.println(Ex);
        }
    }

    @FXML
    private TextField rating;

    /**
     * Takes in the rating from textfield and then search for matching items in database
     *
     * @param e
     * @throws Exception
     */
    @FXML
    public void searchByRating(ActionEvent e) throws Exception {

        SQL_Query sql = new SQL_Query();
        String ratings = rating.getText();


        try {
            new Thread() {
                ArrayList<content> table;

                public void run() {
                    //Statement function here!
                    try {
                        table = sql.searchRating( ratings);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    javafx.application.Platform.runLater(new Runnable() {
                        public void run() {
                            //when done
                            Stage media = new Stage();

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/tableView.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            tableController tbl = fxmlLoader.<tableController>getController();
                            tbl.initialize(table);
                            tbl.setArrayList(table);
                            media.setScene(new Scene(root));
                            media.show();

                            Stage stage = (Stage) Search2.getScene().getWindow();
                            stage.close();
                        }

                    });
                }
            }.start();
        } catch (Exception Ex) {
            System.out.println(Ex);
        }
    }
}
