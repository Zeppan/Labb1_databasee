package Model;


import java.util.ArrayList;

/**
 * Created by niclas on 2016-11-30.
 */
public class Model {

    public ArrayList<content> content;
    private user user;

    public Model() {
        content = new ArrayList<>();

        user = new user();
    }
}
