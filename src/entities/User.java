package entities;

import java.util.ArrayList;


public class User {

    private final int id;
    private final String userID;
    private String password;
    private String type;
    private int contactInfo;
    private ArrayList<Integer> logs = new ArrayList<>();

    public User(int id, String userID, String password, String type, int contactInfo) {
        this.id = id;
        this.userID = userID;
        this.password = password;
        this.type = type;
        this.contactInfo = contactInfo;
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

    public ArrayList<Integer> getLogs() {
        return this.logs;
    }

    public void addLog(Integer logID) {
        this.logs.add(logID);
    }
}
