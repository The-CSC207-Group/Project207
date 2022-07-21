package dataBundles;

import entities.Contact;

import java.time.LocalDate;

/**
 * Wrapper class for Contact entity.
 */
public class ContactData {

    private final Contact contact;

    /**
     * Constructor. Creates and stores a contact entity within this class. It is not added to the database.
     * @param name The name of the contact.
     * @param email The email of the contact.
     * @param phoneNumber The phone number of the contact.
     * @param address The address of the contact.
     * @param birthday The birthday of the contact.
     * @param emergencyContactName The emergency contact name of the contact.
     * @param emergencyContactEmail The emergency contact email of the contact.
     * @param emergencyContactPhoneNumber The emergency contact phone number of the contact.
     * @param emergencyRelationship The emergency contact's relationship to the contact.
     */
    public ContactData(String name, String email, String phoneNumber, String address, LocalDate birthday,
                       String emergencyContactName, String emergencyContactEmail,
                       String emergencyContactPhoneNumber, String emergencyRelationship) {

        contact = new Contact(name, email, phoneNumber, address, birthday,
                emergencyContactName, emergencyContactEmail,
                emergencyContactPhoneNumber, emergencyRelationship);
    }

    /**
     * Constructor.
     * @param contact Contact - Contact entity.
     */
    public ContactData(Contact contact) {
        this.contact = contact;
    }

    /**
     * @return String - name attribute of the stored contact object.
     */
    public String getName() {
        return contact.getName();
    }

    /**
     * @return String - email attribute of the stored contact object.
     */
    public String getEmail() {
        return contact.getEmail();
    }

    /**
     * @return String - phone number attribute of the stored contact object.
     */
    public String getPhoneNumber() {
        return contact.getPhoneNumber();
    }

    /**
     * @return String - Address attribute of the stored contact object.
     */
    public String getAddress() {
        return contact.getAddress();
    }

    /**
     * @return LocalDate - birthday attribute of the stored contact object.
     */
    public LocalDate getBirthday() {
        return contact.getBirthday();
    }

    /**
     * @return String - emergency contact name attribute of the stored contact object.
     */
    public String getEmergencyContactName() {
        return contact.getEmergencyContactName();
    }

    /**
     * @return String - emergency contact email attribute of the stored contact object.
     */
    public String getEmergencyContactEmail() {
        return contact.getEmergencyContactEmail();
    }

    /**
     * @return String - emergency contact phone number attribute of the stored contact object.
     */
    public String getEmergencyContactPhoneNumber() {
        return contact.getEmergencyContactPhoneNumber();
    }

    /**
     * @return String - emergency relationship attribute of the stored contact object.
     */
    public String getEmergencyRelationship() {
        return contact.getEmergencyRelationship();
    }

    /**
     * @return Integer - id of the stored contact.
     */
    public Integer getContactId() {
        return contact.getId();
    }

}
