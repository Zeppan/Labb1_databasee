package Model;

/**
 * Created by niclas on 2016-11-30.
 */
public class Creator {

    private String creatorName;
    private String nationality;
    private String creatorID;
    private creatorRole role;

    public Creator(String creatorID, String creatorName, String nationality, creatorRole role){
        this.creatorName = creatorName;
        this.nationality = nationality;
        this.role = role;
        this.creatorID = creatorID;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getNationality(){
        return nationality;
    }

    public String getCreatorID(){
        return creatorID;
    }

    public creatorRole getRole(){
        return role;
    }
}
