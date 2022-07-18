package useCases.managers;

import dataBundles.ContactData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.User;

import java.time.LocalDate;

public class ContactManager {
    DataMapperGateway<Contact> contactDatabase;

    public ContactManager(Database database){
        this.contactDatabase = database.getContactDatabase();
    }

    public <T extends User> ContactData getContactData(UserData<T> userData){
        return new ContactData(contactDatabase.get(userData.getContactInfoId()));
    }
//    public boolean changeContactInfo(ContactData contactData, String name, String email, String phoneNumber, String address, LocalDate birthday,
//                                     String emergencyContactName, String emergencyContactEmail,
//                                     String emergencyContactPhoneNumber, String emergencyRelationship){
//
//        Contact contact = contactDatabase.get(contactData.getContactId());
//        if (contact == null){return false;}
//
//        if (name != null){contact.setName(name);}
//        if (email != null){contact.setEmail(email);}
//        if (phoneNumber != null){contact.setPhoneNumber(phoneNumber);}
//        if (address != null){contact.setAddress(address);}
//        if (birthday != null){contact.setBirthday(birthday);}
//        if (emergencyContactEmail != null){contact.setEmergencyContactEmail(emergencyContactEmail);}
//        if (emergencyContactName != null){contact.setEmergencyContactName(emergencyContactName);}
//        if (emergencyContactPhoneNumber != null){contact.setEmergencyContactPhoneNumber(emergencyContactPhoneNumber);}
//        if (emergencyRelationship != null){contact.setEmergencyRelationship(emergencyRelationship);}
//        return true;
//    }
    public boolean changeName(ContactData contactData, String newName){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setName(newName);
        return true;
    }
    public boolean changeEmail(ContactData contactData, String newEmail){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmail(newEmail);
        return true;
    }
    public boolean changePhoneNumber(ContactData contactData, String phoneNumber){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setPhoneNumber(phoneNumber);
        return true;
    }
    public boolean changeAddress(ContactData contactData, String address){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setAddress(address);
        return true;
    }
    public boolean changeBirthday(ContactData contactData, LocalDate birthday){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setBirthday(birthday);
        return true;
    }
    public boolean changeEmergencyContactEmail(ContactData contactData, String emergencyContactEmail){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyContactEmail(emergencyContactEmail);
        return true;
    }
    public boolean changeEmergencyContactName(ContactData contactData, String emergencyContactName){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyContactName(emergencyContactName);
        return true;
    }
    public boolean changeEmergencyContactPhoneNumber(ContactData contactData, String emergencyContactPhoneNumber){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyContactPhoneNumber(emergencyContactPhoneNumber);
        return true;
    }
    public boolean changeEmergencyRelationship(ContactData contactData, String emergencyRelationship){
        Contact contact = contactDatabase.get(contactData.getContactId());
        if (contact == null){return false;}
        contact.setEmergencyRelationship(emergencyRelationship);
        return true;
    }
}
