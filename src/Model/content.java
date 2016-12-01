package Model;

import java.util.Date;

/**
 * Created by nicla on 2016-11-30.
 */
public class content {

    private String title;
    private String releaseDate;
    private String type;
    private String genre;

    public content(String title, String releaseDate, String type, String genre){
        this.title = title;
        this.releaseDate = releaseDate;
        this.type = type;
        this.genre = genre;
    }

    public String getTitle(){
        return title;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getType(){
        return type;
    }

    public String getGenre(){
        return genre;
    }

}
