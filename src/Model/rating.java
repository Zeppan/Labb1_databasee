package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class rating {

    private String date;
    private int rating;
    private String addedBy;

    public rating(String date, int rating){
        this.date = date;
        this.rating = rating;
    }
    public String getAddedBy(){return addedBy;}
    public String getDate(){
        return date;
    }

    public int getRating(){
        return rating;
    }


    @Override
    public String toString(){
        return date.toString();
    }
}
