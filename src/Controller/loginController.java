package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Controller.Controller;

/**
 * Created by nicla on 2016-12-06.
 */
public class loginController {

    @FXML
    TextField txt_usr_name, txt_pass, databasename;
    @FXML
    public void login(ActionEvent e) throws Exception {
        String username = txt_usr_name.getText();
        String password = txt_pass.getText();
        String dbName = databasename.getText();
        Controller.connectToDatabase(e, username, password, dbName);


    }
}
