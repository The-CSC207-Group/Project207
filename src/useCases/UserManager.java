package useCases;

import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.User;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Abstract use case class for handling operations and data of users of all types.
 *
 * @param <T> User - the entity class of the type of user that a specific manager class manages.
 */
public abstract class UserManager<T extends User> {

    private final DataMapperGateway<T> typeTDatabase;
    private final DataMapperGateway<Contact> contactDatabase;
    private final ContactManager contactManager;
    private final LogManager logManager;

    /**
     * Initializes the user manager.
     *
     * @param typeTDatabase DataMapperGateway<T> where T extends User - the database of the specified T user type.
     * @param database      Database - the collection of all entity databases in the program.
     */
    public UserManager(DataMapperGateway<T> typeTDatabase, Database database) {
        this.typeTDatabase = typeTDatabase;
        this.contactDatabase = database.getContactDatabase();
        this.contactManager = new ContactManager(database);
        this.logManager = new LogManager(database);
    }

    /**
     * Change the password of a user.
     *
     * @param userData<T> where T extends User - the data associated with a user.
     * @param password    String - new password of the user.
     * @return boolean - true if the user exists in the database and operation is carried out, false if the user associated
     * with the userId stored in userData does not exist in the database.
     */
    public boolean changeUserPassword(UserData<T> userData, String password) {
        T user = typeTDatabase.get(userData.getId());
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }

    /***
     * Checks if username and password adhere to regex pattern.
     * @param username username of new account.
     * @param password password of new account.
     * @return boolean whether or not the username and password adhere to the regex pattern.
     */
    public boolean regexCheck(String username, String password) {
        return Pattern.matches("^[a-zA-Z0-9]{6,}$", username) && Pattern.matches("^.{8,}$", password);
    }

    /**
     * Delete a user from their respective database. Their contact info is also removed from the database.
     *
     * @param username String - username of the user to be deleted.
     * @return boolean - true if the user exists in the database and operation is carried out, false if the user associated
     * with the userId stored in userData does not exist in the database.
     */
    public Boolean deleteUser(String username) {
        T user = getUser(username);
        if (user != null) {
            typeTDatabase.remove(user.getId());
            contactDatabase.remove(user.getContactInfoId());
            return true;
        }
        return false;
    }

    /**
     * Deletes a user passed into the parameters
     *
     * @param user UserData<T> where T extends User - the data representing the user that is to be deleted.
     */
    public void deleteUserByData(UserData<T> user) {
        deleteUser(user.getUsername());
    }

    private T getUser(String username) {
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username));
    }

    /**
     * Checks if a user exists in their respective database.
     *
     * @param username String - username of the potential user.
     * @return Boolean - true if the user exists in its respective database, false otherwise.
     */
    public Boolean doesUserExist(String username) {
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username)) != null;
    }

    /**
     * Returns the data representing the user associated with the passed in username is the password is correct.
     *
     * @param username String - username of the user that wants to sign in.
     * @param password String - password of the user that wants to sign in.
     * @return UserData<T> where T extends User - the data representing the user that wants to sign in.
     */
    public abstract UserData<T> signIn(String username, String password);

    /**
     * Returns the data representing the user associated with the passed in username.
     *
     * @param username String - username of the specified user.
     * @return UserData<T> where T extends User - the data representing the specified user.
     */
    public abstract UserData<T> getUserData(String username);

    protected T signInHelper(String username, String password) {
        if (canSignIn(username, password)) {
            T user = getUser(username);
            logManager.addLog("signed in", user.getId());
            return user;
        } else {
            return null;
        }
    }

    /**
     * Used to see if a combination of username and password can sign in.
     *
     * @param username a string that represents the unique account username.
     * @param password a string that represents a key required to log into an account.
     * @return a boolean representing if the given username and password can log into an account.
     */
    public boolean canSignIn(String username, String password) {
        T user = getUser(username);

        if (user == null)
            return false;

        return user.comparePassword(password);
    }

    protected Optional<T> getUserHelper(String username) {
        return Optional.ofNullable(getUser(username));
    }

    /**
     * Used by subclasses to create a new contact in the database.
     *
     * @return Integer - id of the new empty contact added to the database.
     */
    protected Integer newContactInDatabase() {
        return contactManager.addEmptyContactToDatabase();
    }

}
