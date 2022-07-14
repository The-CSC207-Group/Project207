package useCases.managers;

import dataBundles.ContactDataBundle;
import dataBundles.SecretaryDataBundle;
import database.DataMapperGateway;
import entities.*;

public class SecretaryManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Contact> contactDatabase;

    GenericUserManagerMethods<Secretary> secretaryMethods;

    public SecretaryManager(DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Contact> contactDatabase){
        this.secretaryDatabase = secretaryDatabase;
        this.secretaryMethods = new GenericUserManagerMethods<>(secretaryDatabase);
        this.contactDatabase = contactDatabase;
    }

    public SecretaryDataBundle createSecretary(String username, String password, ContactDataBundle contactDataBundle){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Secretary secretary = new Secretary(username, password, contactId);
        secretaryDatabase.add(secretary);
        return new SecretaryDataBundle(secretary.getId(), secretary);
    }

    public void changeUserPassword(Integer userId, String newPassword){
        secretaryMethods.changePassword(userId, newPassword);
    }

    public void deleteSecretary(Integer userId){
        secretaryMethods.deleteUser(userId);
    }
    public Secretary getSecretary(Integer userId){
        return secretaryMethods.getUser(userId);
    }

    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle){
        return new Contact(contactDataBundle.getName(),
                contactDataBundle.getEmail(),
                contactDataBundle.getPhoneNumber(),
                contactDataBundle.getAddress(),
                contactDataBundle.getBirthday(),
                contactDataBundle.getEmergencyContactName(),
                contactDataBundle.getEmergencyContactEmail(),
                contactDataBundle.getEmergencyContactPhoneNumber(),
                contactDataBundle.getEmergencyRelationship());
    }


}
