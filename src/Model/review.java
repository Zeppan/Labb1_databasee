package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class review {

    private String date;
    private String review;

    public review(String date, String review){
        this.date = date;
        this.review = review;
    }

    public String getDate(){
        return date;
    }

    public String getReview(){
        return review;
    }

    public void setDate(String date){this.date=date;}

    public void setReview(String review){this.review=review;}

}
