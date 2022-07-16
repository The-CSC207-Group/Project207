package useCases.managers;

import dataBundles.ContactData;
import dataBundles.SecretaryData;
import database.DataMapperGateway;
import entities.*;

public class SecretaryManager extends UserManager<Secretary>{

    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Contact> contactDatabase;

    GenericUserManagerMethods<Secretary> secretaryMethods;

    /**
     * Stores the databases.
     * @param secretaryDatabase DataMapperGateway<Secretary>
     * @param contactDatabase DataMapperGateway<Contact>
     */

    public SecretaryManager(DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Contact> contactDatabase){
        super(secretaryDatabase);
        this.secretaryDatabase = secretaryDatabase;
        this.secretaryMethods = new GenericUserManagerMethods<>(secretaryDatabase);
        this.contactDatabase = contactDatabase;
    }

    public SecretaryData createSecretary(String username, String password, ContactData contactData){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactData));
        Secretary secretary = new Secretary(username, password, contactId);
        secretaryDatabase.add(secretary);
        return new SecretaryData(secretary);
    }

    /**
     * Change the secretary/patient's password if they exist in their respective databases.
     * @param userId id of the user whose password we want to change within the database.
     * @param newPassword the password we want to change.
     */

    public void changeUserPassword(Integer userId, String newPassword){
        secretaryMethods.changePassword(userId, newPassword);
    }

    /**
     * Delete the secretary from the database. If it doesn't exist in the database, nothing happens
     * @param userId id of the secretary we want to delete.
     */
    public void deleteSecretary(Integer userId){
        secretaryMethods.deleteUser(userId);
    }

    /**
     * get a secretary from the database.
     * @param userId id of the secretary we want to get.
     * @return the secretary if it exists in the database, null otherwise.
     */
    public Secretary getSecretary(Integer userId){
        return secretaryMethods.getUser(userId);
    }

    private Contact contactDataBundleToContactEntity(ContactData contactData){
        return new Contact(contactData.getName(),
                contactData.getEmail(),
                contactData.getPhoneNumber(),
                contactData.getAddress(),
                contactData.getBirthday(),
                contactData.getEmergencyContactName(),
                contactData.getEmergencyContactEmail(),
                contactData.getEmergencyContactPhoneNumber(),
                contactData.getEmergencyRelationship());
    }


}
