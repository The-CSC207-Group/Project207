package database;

import entities.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;


public class UserJsonDatabase implements DataMapperGateway<User> {
    private final String filename;

    public UserJsonDatabase() {
        this("./src/user_database.json");
    }

    public UserJsonDatabase(String filename) {
        this.filename = filename;
    }

    @Override
    public HashSet<Integer> getAllIds() {
        return null;
    }

    @Override
    public User get(Integer id) {
        return null;
    }

    @Override
    public Integer add(User item) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    public void save() {

    }
}
