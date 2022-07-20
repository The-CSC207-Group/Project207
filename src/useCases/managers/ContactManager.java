package useCases.managers;

import dataBundles.ContactData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.User;
import java.util.regex.Pattern;

import java.time.LocalDate;

/**
 * Use case class meant for handling user contact info.
 */
public class ContactManager {
    private final DataMapperGateway<Contact> contactDatabase;

    /**
     * Constructor for the contactManager
     * @param database Collection of all databases.
     */
    public ContactManager(Database database){
        this.contactDatabase = database.getContactDatabase();
    }

    /**
     * Returns a ContactData object given a userData<T> object where T extends User .
     * @param userData UserData<T> where T extends User is the data associated with the user whose contacts
     *                 are being accessed.
     * @return ContactData representing the contact data associated with the user.
     * @param <T> extends User
     */
    public <T extends User> ContactData getContactData(UserData<T> userData){
        return new ContactData(contactDatabase.get(userData.getContactInfoId()));
    }

    /**
     * Change the name of a user in the contact database.
     * @param contactData ContactData - Contact data associated with a user.
     * @param newName String - the name we would like to assign to the user.
     * @return boolean - true if the contactData stores an id associated with a valid contact object. false otherwise.
     */
    public boolean changeName(ContactData contactData, String newName){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setName(newName);
        return true;
    }

    /**
     * Changes the email of a user in their associated Contact object.
     * @param contactData ContactData - contactData object associated with a specific user.
     * @param newEmail String - the new email of the user.
     * @return boolean - false if the email is not valid according to a regex or if there is not a contact
     * associated with the id stored in contactData. true otherwise.
     */
    public boolean changeEmail(ContactData contactData, String newEmail){
        Contact contact = contactDatabase.get(contactData.getContactId());
        //got regex from https://regexlib.com/Search.aspx?k=email
        if (contact == null ||
                !Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", newEmail)){
            return false;}
        contact.setEmail(newEmail);
        return true;
    }

    /**
     * Changes the phone number of a user in their associated Contact object.
     * @param contactData ContactData - contactData object associated with a specific user.
     * @param phoneNumber String - the new phone number of the user.
     * @return boolean - false if the phone number does not consist of solely numbers or if there is not a contact
     * associated with the id stored in contactData. true otherwise.
     */
    public boolean changePhoneNumber(ContactData contactData, String phoneNumber){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null || !Pattern.matches("^([0-9])+$", phoneNumber)){return false;}
        contact.setPhoneNumber(phoneNumber);
        return true;
    }

    /**
     * Changes the address of a user in their associated Contact object.
     * @param contactData ContactData - contactData object associated with a specific user.
     * @param address String - the new address of the user.
     * @return false if there is not a contact associated with the id stored in contactData. true otherwise.
     */
    public boolean changeAddress(ContactData contactData, String address){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setAddress(address);
        return true;
    }

    /**
     * Changes the birthday of a user in their associated Contact object.
     * @param contactData ContactData - contactData object associated with a specific user.
     * @param birthday String - the new birthday of the user.
     * @return false if there is not a contact associated with the id stored in contactData. true otherwise.
     */
    public boolean changeBirthday(ContactData contactData, LocalDate birthday){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setBirthday(birthday);
        return true;
    }

    /**
     * Changes the emergency contact email of a user in their associated Contact object.
     * @param contactData ContactData - contactData object associated with a specific user.
     * @param emergencyContactEmail String - the new email of the emergency contact.
     * @return boolean - false if the email is not valid according to a regex or if there is not a contact
     * associated with the id stored in contactData. true otherwise.
     */
    public boolean changeEmergencyContactEmail(ContactData contactData, String emergencyContactEmail){
        Contact contact = contactDatabase.get(contactData.getContactId());
        //got regex from https://regexlib.com/Search.aspx?k=email
        if (contact == null ||
                !Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", emergencyContactEmail)){
            return false;}
        contact.setEmergencyContactEmail(emergencyContactEmail);
        return true;
    }

    /**
     * Change the name of an emergency contact of a user in the contact database.
     * @param contactData ContactData - Contact data associated with a user.
     * @param emergencyContactName String - the name we would like to assign to the emergency contact.
     * @return boolean - true if the contactData stores an id associated with a valid contact object. false otherwise.
     */
    public boolean changeEmergencyContactName(ContactData contactData, String emergencyContactName){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyContactName(emergencyContactName);
        return true;
    }

    /**
     * Change the phone number of an emergency contact of a user in the contact database.
     * @param contactData ContactData - Contact data associated with a user.
     * @param emergencyContactPhoneNumber String - the phone number that we would like to save for the emergency contact.
     * @return boolean - false if the phone number does not consist of solely numbers or if there is not a contact
     * associated with the id stored in contactData. true otherwise.
     */
    public boolean changeEmergencyContactPhoneNumber(ContactData contactData, String emergencyContactPhoneNumber){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null || !Pattern.matches("^([0-9])+$", emergencyContactPhoneNumber)){return false;}
        contact.setEmergencyContactPhoneNumber(emergencyContactPhoneNumber);
        return true;
    }

    /**
     * Change the emergency relationship of a user with their emergency contact in the contact database.
     * @param contactData ContactData - Contact data associated with a user.
     * @param emergencyRelationship String - the relationship that we would like to save for the emergency contact.
     * @return boolean - true if the contactData stores an id associated with a valid contact object. false otherwise.
     */
    public boolean changeEmergencyRelationship(ContactData contactData, String emergencyRelationship){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyRelationship(emergencyRelationship);
        return true;
    }
}
