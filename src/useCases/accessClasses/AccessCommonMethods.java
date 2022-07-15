package useCases.accessClasses;

import database.DataMapperGateway;
import entities.User;
import useCases.managers.LogManager;

class AccessCommonMethods {
    /**
     * Adds a sign in log reference to the user if it exists in the user database and a log with that id reference
     * to the log database.
     * @param database Database the user exists in.
     * @param userId Id of the user to which the log should be attached to.
     * @param logManager LogManager that will be used to add logs to the database.
     * @param <T> Type of the user who the log will be attached to.
     */
    protected  <T extends User> void attachUserSignInLog(DataMapperGateway<T> database, Integer userId, LogManager logManager) {
        User user = database.get(userId);
        if (user != null){
            logManager.addLog(user.getUsername() + " signed in", userId, database);
        }
    }
}
