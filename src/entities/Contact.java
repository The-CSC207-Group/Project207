package entities;

import java.util.Date;

public class Contact {

    // variables

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date birthday;
    private String emergencyContactName;
    private String emergencyContactEmail;
    private String emergencyContactPhoneNumber;
    private String emergencyRelationship;

    // constructor

    public Contact(String name, String email, String phoneNumber, String address, Date birthday,
                   String emergencyContactName, String emergencyContactEmail,
                   String emergencyContactPhoneNumber, String emergencyRelationship) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthday = birthday;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactEmail = emergencyContactEmail;
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
        this.emergencyRelationship = emergencyRelationship;
    }

    // methods


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
    }
}
