package entities;

import utilities.JsonSerializable;

import java.util.ArrayList;


public abstract class User extends JsonSerializable {

    private final String username;
    private String password;
    private Integer contactInfoId;
    private ArrayList<Integer> logs = new ArrayList<>();

    public User(String username, String password, int contactInfoId) {
        this.username = username;
        this.password = password;
        this.contactInfoId = contactInfoId;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean comparePassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContactInfoId() {
        return contactInfoId;
    }

    public void updateContactInfo(int contactInfo) {
        this.contactInfoId = contactInfo;
    }

    public ArrayList<Integer> getLogs() {
        return this.logs;
    }

    public void addLog(Integer logID) {
        this.logs.add(logID);
    }
}
