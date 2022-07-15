package useCases.managers;

import database.DataMapperGateway;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;

public class GenericUserManagerMethods<T extends User> {
    DataMapperGateway<T> database;

    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /**
     * Initializes the class with a database of a generic type T that extends user. Assumes userId is not null.
     * @param database
     */
    public GenericUserManagerMethods(DataMapperGateway<T> database){
        this.database = database;
    }

    /**
     * Changes the password of the user associated with the given userId.
     * If the user with this userId does not exist, nothing happens. Assumes userId is not null.
     * @param userId The id of the user whose password is to be changed from the database.
     * @param password The new password of the user.
     */
    public void changePassword(Integer userId, String password){
        T user = database.get(userId);
        if (user != null) {
            user.setPassword(password);
        }
    }

    /**
     * Delete the user with the given userId from the database. If the user does not exist, nothing happens. Assumes
     * userId is not null.
     * @param userId The id of the user whose password is to be changed from the database.
     */
    public void deleteUser(Integer userId){
        database.remove(userId);
    }

    /**
     * Get the user with the given userId from the database. If the user does not exist, null is returned. Assumes
     * userId is not null.
     * @param userId The id associated with the user we are trying to get from the database.
     * @return The user associated with the id from the database or null if the user doesn't exist.
     */
    public T getUser(Integer userId){
        return database.get(userId);
    }




}
