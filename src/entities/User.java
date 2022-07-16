package entities;

import utilities.JsonSerializable;

import java.util.ArrayList;


public abstract class User extends JsonSerializable {

    private final String username;
    private String password;
    private Integer contactInfoId;

    public User(String username, String password, Integer contactInfoId) {
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

    public Integer getContactInfoId() {
        return contactInfoId;
    }

    public void updateContactInfo(Integer contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

}
