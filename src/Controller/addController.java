package Controller;

import Model.Creator;
import Model.content;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.*;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.SQL_Query;

import java.util.ArrayList;

/**
 * Created by Niclas on 2016-12-06.
 */
public class addController{

    private ArrayList<TextField> creators;
    private ArrayList<TextField> nationalities;
    private ArrayList<TextField> jobs;
    private int nr = 2;

    @FXML
    private ChoiceBox<String> contentType;
    @FXML
    private TextField name, nationality, job;

    public void initialize(){
        contentType.getItems().add("Movie");
        contentType.getItems().add("CD");

        creators = new ArrayList<>();
        nationalities = new ArrayList<>();
        jobs = new ArrayList<>();

        creators.add(name);
        nationalities.add(nationality);
        jobs.add(job);

    }

    @FXML
    VBox creatorBox;

    @FXML
    public void addCreator(ActionEvent e){
        Label creator = new Label("Creator " + nr + ":");
        TextField creatorTxtField = new TextField();
        creators.add(creatorTxtField);

        Label nationality = new Label("Nationality:");
        TextField nationalityTxtField = new TextField();
        nationalities.add(nationalityTxtField);

        Label job = new Label("Job:");
        TextField jobTxtField = new TextField();
        jobs.add(jobTxtField);

        creator.setFont(new Font("Arial", 16));
        nationality.setFont(new Font("Arial", 16));
        job.setFont(new Font("Arial", 16));

        creatorBox.getChildren().addAll(creator, creatorTxtField, nationality, nationalityTxtField, job, jobTxtField);
        nr++;
    }


    @FXML
    private TextField contentTitle, genre;
    @FXML
    private DatePicker date;

    @FXML
    public void getInformation() throws Exception {
    //This function will get the information from the media
        SQL_Query sql = new SQL_Query();

        ArrayList<Creator> creatorTmp = new ArrayList<>();
        for(int i = 0; i < creators.size(); i++){
            creatorTmp.add(new Creator());
            creatorTmp.get(i).setCreatorName(creators.get(i).getText());
            creatorTmp.get(i).setNationality(nationalities.get(i).getText());
            creatorTmp.get(i).setRole(jobs.get(i).getText());
            creatorTmp.get(i).setAddedBy(Controller.usernameLoggedIn);
        }

        content contentTmp = new content();
        contentTmp.SetCreators(creatorTmp);
        contentTmp.setGenre(genre.getText(), Controller.usernameLoggedIn);

        contentTmp.SetTitle(contentTitle.getText());
        contentTmp.SetType(contentType.getValue());
        contentTmp.SetReleaseDate(date.getValue().toString());
        contentTmp.SetaddedBy(Controller.usernameLoggedIn);

        sql.insert(Controller.con, contentTmp);
    }
}
