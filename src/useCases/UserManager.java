package useCases;

import database.DataMapperGateway;
import entities.Log;
import entities.User;

import java.util.ArrayList;


public class UserManager {

    private User currentUser;
    private DataMapperGateway<User> database;

    public UserManager(String username, DataMapperGateway<User> database) {
        this.database = database;
        currentUser = database.get(username);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Remove current user from database.
     */
    public void deleteCurrentUser() {
        // Not viable for synchronous activity
        database.remove(currentUser.getUsername());
    }

    /**
     *
     * @return current user's logs as an arraylist of logs.
     */
    public ArrayList<Log> getCurrentUserLogs() {
        return currentUser.getLogs();
    }

    /**
     * Changes the user's password in database.
     * @param newPassword new password
     */
    public void changeCurrentUserPassword(String newPassword) {
        currentUser.setPassword(newPassword);
    }

    /**
     * Sets current user to null.
     */
    public void signOut() {
        currentUser = null;
    }
}
