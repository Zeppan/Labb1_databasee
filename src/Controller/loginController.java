package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by niclas on 2016-12-06.
 */
public class loginController {

    @FXML
    TextField txt_usr_name, txt_pass, databasename;
    @FXML
    public void login(ActionEvent e) throws Exception {
        String username = txt_usr_name.getText();
        String password = txt_pass.getText();
        String dbName = databasename.getText();
        Controller.connectToDatabase(username, password, dbName);
        switchSceneMain(e);
    }

    public void switchSceneMain(ActionEvent e) throws IOException {
        Stage mainStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/mainStage.fxml"));
        mainStage.setTitle("Main Program");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}
