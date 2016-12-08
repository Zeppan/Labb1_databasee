package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class rating {

    private String date;
    private String rating;
    private String addedBy;

    public rating(String date, String rating) {
        this.date = date;
        this.rating = rating;
    }

    public rating() {
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return date.toString();
    }
}
