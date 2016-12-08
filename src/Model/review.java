package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class review {

    private String date;
    private String review;
    private String addedBy;

    public review(String date, String review,String addedBy) {
        this.date = date;
        this.review = review;
        this.addedBy = addedBy;
    }

    public review(String review) {
        this.review = review;
    }


    public String getDate() {
        return date;
    }

    public String getReview() {
        return review;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void setReview(String review) {
        this.review = review;
    }


}
