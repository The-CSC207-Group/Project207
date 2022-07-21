package entities;

import utilities.JsonSerializable;

import java.time.LocalDate;

/**
 * Represents a contact.
 */
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

    /**
     * Creates an instance of Contact.
     * @param name String representing the name of the contact.
     * @param email String representing the  email of the contact.
     * @param phoneNumber String representing the phone number of the contact.
     * @param address String representing the address of the contact.
     * @param birthday LocalDate representing the birthday of the contact.
     * @param emergencyContactName String representing the emergency contact name of the contact.
     * @param emergencyContactEmail String representing the emergency contact email of the contact.
     * @param emergencyContactPhoneNumber String representing the emergency contact phone number of the contact.
     * @param emergencyRelationship String representing the emergency contact's relationship to the contact.
     */
    public Contact(String name, String email, String phoneNumber, String address, LocalDate birthday,
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

    /**
     * Creates an empty instance of Contact.
     */
    public Contact(){
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.address = "";
        this.birthday = LocalDate.of(1, 1, 1);
        this.emergencyContactName = "";
        this.emergencyContactEmail = "";
        this.emergencyContactPhoneNumber = "";
        this.emergencyRelationship = "";
    }

    /**
     * @return String representing the name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     * @param name String representing the new name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String representing the email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the contact.
     * @param email String representing the new email of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String representing the phone number of the contact.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the contact.
     * @param phoneNumber String representing the new phone number of the contact.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return String representing the address of the contact.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the contact.
     * @param address String representing the new address of the contact.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return LocalDate representing the date of birth of the contact.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the date of birth of the contact.
     * @param birthday LocalDate representing the new date of birth of the contact.
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * @return String representing the emergency contact's name.
     */
    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    /**
     * Sets the emergency contact's name.
     * @param emergencyContactName String representing the new emergency contact's name.
     */
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    /**
     * @return String representing the emergency contact's email.
     */
    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    /**
     * Sets the emergency contact's email.
     * @param emergencyContactEmail String representing the new emergency contact's email.
     */
    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    /**
     * @return String representing the emergency contact's phone number.
     */
    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    /**
     * Sets the emergency contact's phone number
     * @param emergencyContactPhoneNumber String representing the new emergency contact's phone number.
     */
    public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    /**
     * @return String representing the emergency contact's relationship to this contact.
     */
    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    /**
     * Sets the emergency contact's relationship to this contact.
     * @param emergencyRelationship String representing the new emergency contact's relationship to this contact.
     */
    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
    }

}
