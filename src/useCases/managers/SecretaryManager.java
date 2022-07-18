package useCases.managers;

import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Secretary;

import java.time.LocalDateTime;

public class SecretaryManager extends UserManager<Secretary> {

    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Contact> contactDatabase;


    /**
     * Stores the databases.
     *
     */

    public SecretaryManager(Database database) {
        super(database.getSecretaryDatabase(), database);
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
    public SecretaryData toSecretaryData(Secretary secretary){
        if (secretary == null){
            return null;
        } else {
            return new SecretaryData(secretary);
        }
    }

    @Override
    public SecretaryData signIn(String userName, String password) {
        return toSecretaryData(signInHelper(userName, password));

    }

    }
