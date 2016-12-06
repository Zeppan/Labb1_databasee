package Model;

/**
 * Created by niclas on 2016-11-30.
 */
public class Creator {

    private String creatorName;
    private String nationality;
    private int creatorID;
    private creatorRole role;

    public Creator(int creatorID, String creatorName, String nationality, creatorRole role){
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

    public int getCreatorID(){
        return creatorID;
    }

    public creatorRole getRole(){
        return role;
    }

    public void setCreatorName(String creatorName){this.creatorName=creatorName;}

    public void setNationality(String nationality){this.nationality=nationality;}

    public void setCreatorID(int creatorID){this.creatorID=creatorID;}

    public void setRole(creatorRole role){this.role=role;}

}
