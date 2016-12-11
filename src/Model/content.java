package Model;

import java.util.ArrayList;

/**
 * Created by nicla on 2016-11-30.
 */
public class content {
    private int contentID;
    private rating rating;
    private String title;
    private String releaseDate;
    private String type;
    private String addedBy;
    private ArrayList<Creator> creators;
    private ArrayList<genre> genres;
    private ArrayList<review> reviews;
    private String genreString;

    public content(int contentID, String title, String releaseDate, String type, rating rating) {
        creators = new ArrayList<>();
        genres = new ArrayList<>();
        reviews = new ArrayList<>();
        this.rating = rating;
        this.title = title;
        this.releaseDate = releaseDate;
        this.type = type;
        this.contentID = contentID;
    }

    public content() {
        creators = new ArrayList<>();
        genres = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public String getGenreString(){return genreString;}

    public ArrayList<genre> getGenres() {
        return genres;
    }

    public ArrayList<Creator> getCreators() {
        return creators;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getType() {
        return type;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public int getContentID() {
        return contentID;
    }

    public String getRating() {
        return rating.getRating();
    }

    public rating getObjectRating() {
        return this.rating;
    }

    public String getReviews() {
        String tmp = "";
        for (review rev : reviews) {
            tmp += rev.getReview() + ", ";
        }
        return tmp;
    }

    public void setGenresString() {
        String tmp = "";
        for (genre gen : genres) {
            tmp += gen.getGenre() + ", ";
        }
        this.genreString = tmp;
    }

    public ArrayList<review> getReviewsArray() {
        return reviews;
    }

    public void SetTitle(String Title) {
        this.title = Title;
    }

    public void SetReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void SetType(String type) {
        this.type = type;
    }

    public void SetaddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void SetContentID(int contentID) {
        this.contentID = contentID;
    }

    public void SetRatingScore(String rating, String addedBy) {
        this.rating = new rating(rating, addedBy);
    }

    public void SetAvarageRatingScore(String rating) {
        this.rating = new rating(rating);
    }

    public void Setgenres(ArrayList<genre> genres) {
        this.genres =genres;
    }

    public void SetReviews(ArrayList<review> review) {
        this.reviews.addAll(review);
    }

    public void SetCreators(ArrayList<Creator> creators) {
        this.creators.addAll(creators);
    }


    public void setGenre(String genr, String addedBy) {
        genres.add(new genre(genr, addedBy));
    }

    public void addReview(String review, String addedBy) {
        reviews.add(new review(review, addedBy));
    }


}
