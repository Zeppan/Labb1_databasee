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

    public void setUsername(String username){this.username=username;}

    public void setPassword(String password){this.username=password;}

    public void setName(String name){this.name=name;}

    public void setPrivilage(int privilage){this.privilage=privilage;}


}
