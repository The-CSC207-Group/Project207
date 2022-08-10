package useCases;

import dataBundles.LogData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Log;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;

/**
 * Use case class meant for handling logs.
 */
public class LogManager {
    private final DataMapperGateway<Log> logDatabase;
    private final DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /***
     * Stores the logDatabase and the database of a type that extends user for deleting and adding references to logs.
     * @param database Database - the collection of all entity databases in the program.
     */
    public LogManager(Database database) {
        this.logDatabase = database.getLogDatabase();
    }

    /**
     * Method for adding a log with some message to the user. Assumes userId is valid.
     *
     * @param userData UserData - data bundle of the user that the log will be attached to.
     * @param message  String - message attached with to the log.
     * @return LogData - data representing a log with the information passed in.
     */
    public <T extends User> LogData addLog(UserData<T> userData, String message) {
        Log log = new Log(userData.getId(), message);
        logDatabase.add(log);
        return new LogData(log);
    }

    /**
     * Gets an arraylist of LogData associated with the user.
     *
     * @param userData UserData<T extends User> The data associated with the user.
     * @param <T>      Extends User.
     * @return ArrayList<LogData> - list of LogData that belong to a user.
     */
    public <T extends User> ArrayList<LogData> getUserLogs(UserData<T> userData) {
        return databaseUtils.toArrayList(logDatabase.stream().
                filter(log -> log.getUserId().equals(userData.getId())).map(LogData::new));
    }

}
