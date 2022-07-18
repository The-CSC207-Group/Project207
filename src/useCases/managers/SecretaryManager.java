package useCases.managers;

import dataBundles.ContactData;
import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.*;

public class SecretaryManager extends UserManager<Secretary> {

    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Contact> contactDatabase;


    /**
     * Stores the databases.
     *
     * @param secretaryDatabase DataMapperGateway<Secretary>
     * @param contactDatabase   DataMapperGateway<Contact>
     */

    public SecretaryManager(Database database) {
        super(database.getSecretaryDatabase());
        this.secretaryDatabase = database.getSecretaryDatabase();
        this.contactDatabase = database.getContactDatabase();
    }

    /**
     * Creates a new secretary and stores it in the database.
     *
     * @param username String username of the new secretary, cannot exist in the database yet.
     * @param password String password for the user.
     * @return Secretary Data consisting all information for this secretary.
     */
    public SecretaryData createSecretary(String username, String password) {
        Secretary secretary = new Secretary(username, password);
        secretaryDatabase.add(secretary);
        return new SecretaryData(secretary);
    }

}
