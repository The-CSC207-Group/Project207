package dataBundles;

import entities.Contact;

import java.time.LocalDate;

/**
 * Wrapper class for Contact entity.
 */
public class ContactData extends DataBundle {

    private final Contact contact;

    /**
     * Constructor.
     *
     * @param contact Contact - Contact entity.
     */
    public ContactData(Contact contact) {
        super(contact);
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

}
