package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by niclas on 2016-12-06.
 */
public class loginController {

    private String username;

    @FXML
    TextField txt_usr_name, txt_pass, databasename;
    @FXML
    Button btn_login;

    /**
     * Logs in when button is pressed
     * @param e
     * @throws Exception
     */
    @FXML
    public void login(ActionEvent e) throws Exception {
        username = txt_usr_name.getText();
        String password = txt_pass.getText();
        String dbName = databasename.getText();

        //Thread here later!!!
        if(Controller.connectToDatabase(username, password, dbName)){
            switchSceneMain(e);

            //close down login window!
            Stage stage = (Stage) btn_login.getScene().getWindow();
            stage.close();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Login unsuccessful...");
            alert.setContentText("Wrong username or password...");
            alert.showAndWait();
        }


    }

    /**
     * Switches GUI to main stage
     * @param e
     * @throws IOException
     */
    public void switchSceneMain(ActionEvent e) throws IOException {
        Stage mainStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/mainStage.fxml"));
        Parent root = fxmlLoader.load();
        mainStageController main = fxmlLoader.<mainStageController>getController();
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}
