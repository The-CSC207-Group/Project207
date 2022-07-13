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
        return new SecretaryDataBundle(secretary.getId(), secretary);
    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        secretaryMethods.changePassword(IDUser, newPassword);
    }

    public void deleteSecretary(Integer idUser){
        secretaryMethods.deleteUser(idUser);
    }
    public Secretary getSecretary(Integer idUser){
        return secretaryMethods.getUser(idUser);
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
