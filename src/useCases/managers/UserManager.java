package useCases.managers;

import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.User;

import java.util.Optional;

public abstract class UserManager<T extends User> {
    private final DataMapperGateway<T> typeTDatabase;
    private final DataMapperGateway<Contact> contactDatabase;

    private final ContactManager contactManager;

    private final LogManager logManager;
    public UserManager(DataMapperGateway<T> typeTDatabase, Database database){
        this.typeTDatabase = typeTDatabase;
        this.contactDatabase = database.getContactDatabase();
        this.contactManager = new ContactManager(database);
        this.logManager = new LogManager(database);
    }

    /**
     * Change the password of a user.
     * @param dataBundle UserData<T> where T extends User - the data bundle associated with a user.
     * @param password String - new password of the user.
     * @return boolean - true if the user exists in the database and operation is carried out, false if the user associated
     * with the userId stored in userData does not exist in the database.
     */
    public boolean changeUserPassword(UserData<T> dataBundle, String password){
        T user = typeTDatabase.get(dataBundle.getId());
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }
    public boolean changeUserPassword(String name, String password){
        T user = getUser(name);
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }

    /**
     * Delete a user from their respective database. Their contact info is also removed from the database.
     * @param username String - username of the user to be deleted.
     * @return boolean - true if the user exists in the database and operation is carried out, false if the user associated
     * with the userId stored in userData does not exist in the database.
     */
    public Boolean deleteUser(String username){
        T user = getUser(username);
        if (user != null){
            typeTDatabase.remove(user.getId());
            contactDatabase.remove(user.getContactInfoId());
            return true;
        }
        return false;
    }
    public Boolean deleteUserByData(UserData<T> user){
        return deleteUser(user.getUsername());
    }

    private T getUser(String username){
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username));
    }

    /**
     * Checks if a user exists in their respective database.
     * @param username String - username of the potential user.
     * @return Boolean - true if the user exists in its respective database, false otherwise.
     */
    public Boolean doesUserExist(String username){
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username)) != null;
    }
    protected T signInHelper(String username, String password){
        T user = getUser(username);
        if (user == null){return null;}
        if (user.comparePassword(password)){
            logManager.addLog("signed in", user.getId());
            return user;}
        return null;
    }
    public boolean canSignIn(String username, String password) {
        T user = getUser(username);

        if (user == null)
            return false;

        return user.comparePassword(password);
    }
    public abstract UserData<T> signIn(String username, String password);
    public abstract UserData<T> getUserData(String username);
    protected Optional<T> getUserHelper(String username){
        return Optional.ofNullable(getUser(username));
    }

    protected Integer newContactInDatabase(){return contactManager.addEmptyContactToDatabase();}

}
