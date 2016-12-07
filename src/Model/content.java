package Model;
import java.util.ArrayList;

/**
 * Created by nicla on 2016-11-30.
 */
public class content {
    private int contentID;
    private int rating;
    private String title;
    private String releaseDate;
    private String type;
    private String addedBy;
    private ArrayList<Creator> creators;;
    private ArrayList<genre> genres;
    private ArrayList<review> reviews;

    public content(int contentID, String title, String releaseDate, String type ,int rating){
        creators = new ArrayList<>();
        genres = new ArrayList<>();
        reviews = new ArrayList<>();
        this.rating = rating;
        this.title = title;
        this.releaseDate = releaseDate;
        this.type = type;
    }
    public content (){
        creators = new ArrayList<>();
        genres = new ArrayList<>();
        reviews = new ArrayList<>();
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
    public String getAddedBy(){return addedBy;}
    public int getContentID(){return contentID;}
    public int getRating(){return rating;}
    public String getReviews(){

        String tmp = "";
        for(review rev : reviews){
            tmp += rev.getReview() + ", ";
        }
        return tmp;}


    public void SetTitle(String Title){this.title=Title;}
    public void SetReleaseDate(String releaseDate){this.releaseDate=releaseDate;}

    public void SetType(String type){this.type=type; }

    public void SetaddedBy(String addedBy){this.addedBy=addedBy;}
    public void SetContentID(int contentID){this.contentID=contentID;}


    public void addCreator(int creatorID,String name, String nationality, creatorRole role){
        creators.add(new Creator(creatorID,name,nationality,role));
    }
    public void addGenre(String genr){genres.add(new genre(genr));}
    public void addReview(String date,String review){reviews.add(new review(date,review));}
    public void addReview(String rev){
        reviews.add(new review(rev));
    }

}
