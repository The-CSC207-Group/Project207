package entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class User implements JsonSerializable<User> {

    private final int id;
    private final String userID;
    private String password;
    private String type;
    private int contactInfo;
    private ArrayList<Log> logs = new ArrayList<>();

    public User(int id, String userID, String password, String type, int contactInfo) {
        this.id = id;
        this.userID = userID;
        this.password = password;
        this.type = type;
        this.contactInfo = contactInfo;
    }

    public User(JSONObject json) {
        this.id = (int) json.get("id");
        this.userID = (String) json.get("userID");
        this.password = (String) json.get("password");
        this.type = (String) json.get("type");
        this.contactInfo = (int) json.get("contactInfo");
        for (Object jsonLog : (JSONArray) json.get("logs")) {
            addLog(new Log((JSONObject) jsonLog));
        }
    }

    public int getId() {
        return id;
    }

    public String getUserID() {
        return this.userID;
    }

    public boolean comparePassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public void updateContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ArrayList<Log> getLogs() {
        return this.logs;
    }

    public void addLog(Log log) {
        this.logs.add(log);
    }

    public JSONObject encodeToJson() {
        JSONObject json = new JSONObject();
        json.put("userID", userID);
        json.put("password", password);
        json.put("type", type);
        json.put("contact", contactInfo);

        JSONArray jsonLogs = new JSONArray();
        for (Log log : logs) {
            jsonLogs.put(log.encodeToJson());
        }
        json.put("logs", jsonLogs);

        return json;
    }
}
