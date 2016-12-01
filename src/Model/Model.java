package Model;


import java.util.ArrayList;

/**
 * Created by niclas on 2016-11-30.
 */
public class Model {

    private ArrayList<content> content;
    private ArrayList<Creator> creators;
    private ArrayList<rating> ratings;
    private ArrayList<user> user;

    public Model() {
        content = new ArrayList<>();
        creators = new ArrayList<>();
        ratings = new ArrayList<>();
        user = new ArrayList<>();
    }
}
