package Model;

/**
 * Created by niclas on 2016-11-30.
 */
public class Creator {

    private String creatorName;
    private String nationality;
    private int creatorID;
    private String role;
    private String addedBy;

    public Creator(int creatorID, String creatorName, String nationality, String role,String addedBy){
        this.creatorName = creatorName;
        this.nationality = nationality;
        this.role = role;
        this.creatorID = creatorID;
        this.addedBy = addedBy;
    }


    public String getCreatorName(){
        return creatorName;
    }
    public String getAddedBy(){return addedBy;}
    public String getNationality(){
        return nationality;
    }

    public int getCreatorID(){
        return creatorID;
    }

    public String getRole(){
        return role;
    }

    public void setCreatorName(String creatorName){this.creatorName=creatorName;}

    public void setNationality(String nationality){this.nationality=nationality;}

    public void setCreatorID(int creatorID){this.creatorID=creatorID;}

    public void setRole(String role){this.role=role;}

}
