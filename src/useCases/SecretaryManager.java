package useCases;

import dataBundles.AdminData;
import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Secretary;

import java.util.regex.Pattern;

/**
 * Use case class for handling operations and data of secretary users.
 */
public class SecretaryManager extends UserManager<Secretary> {

    private final DataMapperGateway<Secretary> secretaryDatabase;

    /**
     * Initializes the secretary manager. Stores secretary and contact databases.
     * @param database - collection of entity databases in the program.
     */
    public SecretaryManager(Database database) {
        super(database.getSecretaryDatabase(), database);
        this.secretaryDatabase = database.getSecretaryDatabase();
    }

    /**
     * Creates a new secretary and stores it in the database.
     * @param username String - username of the new secretary, cannot exist in the database yet.
     * @param password String password for the user.
     * @return SecretaryData - data consisting of information for this secretary,
     * null if username exists in database.
     */
    public SecretaryData createSecretary(String username, String password) {
        if (Pattern.matches("^[a-zA-Z0-9]{6,}$", username) && Pattern.matches("^.{8,}$", password)) {
            Secretary secretary = new Secretary(username, password);
            if (secretaryDatabase.add(secretary) != null) {
                secretary.setContactInfoId(newContactInDatabase());
                return new SecretaryData(secretary);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates and returns a data of the secretary associated with the login details passed in.
     * @param userName String - the username of the secretary that wants to sign in.
     * @return SecretaryData - the data of the secretary that wants to sign in.
     */
    @Override
    public SecretaryData signIn(String userName, String password) {
        return toSecretaryData(signInHelper(userName, password));

    }

    /**
     * Creates and returns a data of the secretary associated with the username passed in.
     * @param username String - username of the specified user.
     * @return SecretaryData - data of the secretary associated with the username passed in.
     */
    @Override
    public SecretaryData getUserData(String username) {
        return getUserHelper(username).map(SecretaryData::new).orElse(null);
    }

    private SecretaryData toSecretaryData(Secretary secretary){
        if (secretary == null){
            return null;
        } else {
            return new SecretaryData(secretary);
        }
    }

}
