package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * Created by Niclas on 2016-12-06.
 */
public class addController{

    private ArrayList<TextField> creators;
    private ArrayList<TextField> nationalities;
    private ArrayList<TextField> jobs;

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
    Button addMore;
    @FXML
    VBox creatorBox;

    @FXML
    public void addCreator(ActionEvent e){
        Label creator = new Label("Creator:");
        TextField creatorTxtField = new TextField();
        creators.add(creatorTxtField);

        Label nationality = new Label("Nationality:");
        TextField nationalityTxtField = new TextField();
        nationalities.add(nationalityTxtField);

        HBox more = new HBox();
        Label job = new Label("Job:");
        TextField jobTxtField = new TextField();
        Button addAnother = new Button("+");
        jobs.add(jobTxtField);

        more.getChildren().addAll(jobTxtField, addAnother);

        creator.setFont(new Font("Arial", 16));
        nationality.setFont(new Font("Arial", 16));
        job.setFont(new Font("Arial", 16));

        creatorBox.getChildren().addAll(creator, creatorTxtField, nationality, nationalityTxtField, job, more);
    }


    @FXML
    private TextField contentTitle, genre;
    @FXML
    private DatePicker date;

    @FXML
    public void getInformation(){
    //This function will get the information from the media
        for(int i = 0; i < creators.size(); i++){
            System.out.print(creators.get(i).getText()+"");
            System.out.print(nationalities.get(i).getText()+"");
            System.out.println(jobs.get(i).getText()+"");
        }

        System.out.println(contentTitle.getText());
        System.out.println(genre.getText());
        System.out.println(date.getValue());


    }
}
