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

    public SecretaryData createSecretary(String username, String password){
        Secretary secretary = new Secretary(username, password);
        secretaryDatabase.add(secretary);
        return new SecretaryData(secretary);
    }

//    /**
//     * Change the secretary/patient's password if they exist in their respective databases.
//     * @param userId id of the user whose password we want to change within the database.
//     * @param newPassword the password we want to change.
//     */
//
//    public void changeUserPassword(Integer userId, String newPassword){
//        secretaryMethods.changePassword(userId, newPassword);
//    }


}
