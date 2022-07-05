package entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class User implements JsonSerializable<User> {

    private final String username;
    private String password;
    private boolean banned = false;
    private boolean admin;
    private ArrayList<Log> logs = new ArrayList<>();

    public User(String username, String password, Boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(JSONObject json) {
        this.username = (String) json.get("username");
        this.password = (String) json.get("password");
        this.banned = (Boolean) json.get("banned");
        this.admin = (Boolean) json.get("isAdmin");
        for (Object jsonLog : (JSONArray) json.get("logs")) {
            addLog(new Log((JSONObject) jsonLog));
        }
    }

    public boolean comparePassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    public void addLog(Log log) {
        this.logs.add(log);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBanned(boolean Banned) {
        this.banned = Banned;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public ArrayList<Log> getLogs() {
        return this.logs;
    }

    public JSONObject encodeToJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("banned", banned);
        json.put("isAdmin", admin);

        JSONArray jsonLogs = new JSONArray();
        for (Log log : logs) {
            jsonLogs.put(log.encodeToJson());
        }
        json.put("logs", jsonLogs);

        return json;
    }
}
