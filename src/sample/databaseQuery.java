package sample;

/**
 * Created by Glantz on 2016-12-01.
 */
public class databaseQuery {

    public String getCdByArtist(String artistname){

        return "SELECT title,releaseDate" +
                "FROM content" +
                "WHERE contentID IN" +
                "(SELECT contentID FROM CreatedContent WHERE creatorID =" +
                "(SELECT creatorID FROM creator WHERE name = " + artistname +
                "AND role = 'Artist') AND content.type = 'CD' );";
    }
    public String getMovieByActor(String artistname){
        return  "SELECT title,releaseDate" +
                "FROM content" +
                "WHERE contentID IN" +
                "(SELECT contentID FROM CreatedContent WHERE creatorID =" +
                "(SELECT creatorID FROM creator WHERE name = " + artistname +
                " AND role = 'Actor') AND content.type = 'MOVIE' );" ;

    }
    public String getMovieByGenre(){
        return "SELECT title,releaseDate" +
                "FROM content" +
                "WHERE contentID IN" +
                "(SELECT contentID FROM contentGenre WHERE genre = “” AND content.type = 'MOVIE');";
    }
    public String getCdByTitle(){
        return  "SELECT title" +
                "FROM content" +
                "WHERE  type = 'CD' AND UPPER('title') = UPPER('Idiot'); ";
    }
    public String getMovieBytitle(String title){
       return "SELECT title" +
               "FROM content" +
               "WHERE  type = 'MOVIE' AND UPPER('title') = UPPER("+title+");";
    }
}
