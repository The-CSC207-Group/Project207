package database;

import entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;


public class UserJsonDatabase implements DataMapperGateway<User> {
    private final String filename;
    private HashMap<String, User> users = new HashMap<>();

    public UserJsonDatabase() {
        this("./src/user_database.json");
    }

    public UserJsonDatabase(String filename) {
        this.filename = filename;
        load();
    }

    private void load() {
        Path filePath = Path.of(filename);
        String content;
        try {
            boolean created = filePath.toFile().createNewFile();
            if (created) {
                content = "[]";
            } else {
                content = Files.readString(filePath);
                if (content.isBlank()){
                    content = "[]";
                }
            }
        } catch (IOException e) {
            // this will only throw on malfunctioning systems or
            // if 207-Project isn't the root folder (cwd) of the current intellij window.
            throw new RuntimeException(e);
        }

        JSONArray json = new JSONArray(content);
        for (Object obj : json) {
            User userObj = new User((JSONObject) obj);
            users.put(userObj.getUsername(), userObj);
        }

    }

    @Override
    public void save() {
        JSONArray json = new JSONArray();
        for (User user : users.values()) {
            json.put(user.encodeToJson());
        }

        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(json.toString(2));
        } catch (FileNotFoundException e) {
            // this throw should not execute since a file is created at startup
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashSet<String> getAllIds() {
        return new HashSet<>(users.keySet());
    }

    @Override
    public User get(String id) {
        return users.get(id);
    }

    @Override
    public boolean add(User item) {
        if (!getAllIds().contains(item.getUsername())) {
            users.put(item.getUsername(), item);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        if (getAllIds().contains(id)) {
            users.remove(id);
            return true;
        }
        return true;
    }
}
