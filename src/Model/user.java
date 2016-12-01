package Model;

/**
 * Created by nicla on 2016-11-30.
 */
public class user {

    private String username;
    private String password;
    private String name;
    private int privilage;

    public user(String username, String password, String name, int privilage){
        this.username = username;
        this.password = password;
        this.name = name;
        this.privilage = privilage;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public int getPrivilage(){
        return privilage;
    }

}
