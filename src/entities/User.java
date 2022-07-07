package entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class User implements JsonSerializable<User> {

    // variables

    private final String username;
    private String password;
    private String type;
    private Contact contactInfo;
    private ArrayList<Log> logs = new ArrayList<>();

    // constructors

    public User(String username, String password, String type, Contact contactInfo) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.contactInfo = contactInfo;
    }

    public User(JSONObject json) {
        this.username = (String) json.get("username");
        this.password = (String) json.get("password");
        this.type = (String) json.get("type");
        this.contactInfo = (Contact) json.get("contactInfo");
        for (Object jsonLog : (JSONArray) json.get("logs")) {
            addLog(new Log((JSONObject) jsonLog));
        }
    }

    // methods

    public String getUsername() {
        return this.username;
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

    public Contact getContactInfo() {
        return contactInfo;
    }

    public void updateContactInfo(Contact contactInfo) {
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
        json.put("username", username);
        json.put("password", password);
        json.put("type", type);

        JSONArray jsonLogs = new JSONArray();
        for (Log log : logs) {
            jsonLogs.put(log.encodeToJson());
        }
        json.put("logs", jsonLogs);

        return json;
    }
}
