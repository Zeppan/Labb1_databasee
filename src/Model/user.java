package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class user {

    private String username;
    private String name;

    public user(String username, String name){
        this.username = username;
        this.name = name;
    }
    public user(){}

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public void setUsername(String username){this.username=username;}

    public void setName(String name){this.name=name;}


}
