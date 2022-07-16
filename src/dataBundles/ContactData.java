package dataBundles;

import entities.Contact;

import java.time.LocalDate;

public class ContactData {

    private final Contact contact;

    public ContactData(String name, String email, String phoneNumber, String address, LocalDate birthday,
                       String emergencyContactName, String emergencyContactEmail,
                       String emergencyContactPhoneNumber, String emergencyRelationship) {

        contact = new Contact(name, email, phoneNumber, address, birthday,
                emergencyContactName, emergencyContactEmail,
                emergencyContactPhoneNumber, emergencyRelationship);
    }

    public ContactData(Contact contact) {
        this.contact = contact;
    }

    public String getName() {
        return contact.getName();
    }

    public String getEmail() {
        return contact.getEmail();
    }

    public String getPhoneNumber() {
        return contact.getPhoneNumber();
    }

    public String getAddress() {
        return contact.getAddress();
    }

    public LocalDate getBirthday() {
        return contact.getBirthday();
    }

    public String getEmergencyContactName() {
        return contact.getEmergencyContactName();
    }

    public String getEmergencyContactEmail() {
        return contact.getEmergencyContactEmail();
    }

    public String getEmergencyContactPhoneNumber() {
        return contact.getEmergencyContactPhoneNumber();
    }

    public String getEmergencyRelationship() {
        return contact.getEmergencyRelationship();
    }

    public Integer getContactId() {
        return contact.getId();
    }

}
