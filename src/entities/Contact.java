package entities;

import utilities.JsonSerializable;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Contact extends JsonSerializable {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthday;
    private String emergencyContactName;
    private String emergencyContactEmail;
    private String emergencyContactPhoneNumber;
    private String emergencyRelationship;

    public Contact(String name, String email, String phoneNumber, String address, LocalDate birthday,
                   String emergencyContactName, String emergencyContactEmail,
                   String emergencyContactPhoneNumber, String emergencyRelationship) {
        this.name = name;
        this.email = email;
        setPhoneNumber(phoneNumber);
        this.address = address;
        this.birthday = birthday;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactEmail = emergencyContactEmail;
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
        this.emergencyRelationship = emergencyRelationship;
    }
    public Contact(){

    }

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
        if (Pattern.matches("^([0-9])+$", phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new RuntimeException("Phone number should contain only digits from 0 to 9.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
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
