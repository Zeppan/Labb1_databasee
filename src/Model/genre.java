package Model;

/**
 * Created by Glantz on 2016-12-06.
 */
public class genre {
    private String genre;
    private String addedBy;

    public genre(String genre, String addedBy){
        this.genre = genre;
        this.addedBy = addedBy;
    }

    public genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public String getAddedBy(){return addedBy;}

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

