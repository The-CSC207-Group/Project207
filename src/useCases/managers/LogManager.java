package useCases.managers;

import dataBundles.LogData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Log;
import entities.User;
import utilities.DatabaseQueryUtility;
import utilities.JsonSerializable;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Use case class meant for handling logs.
 */
public class LogManager {
    private final DataMapperGateway<Log> logDatabase;


    private final DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /***
     * Stores the logDatabase and the database of a type that extends user for deleting and adding references to logs.
     * @param database The collection of databases.
     */
    public LogManager(Database database){
        this.logDatabase = database.getLogDatabase();
    }

    /**
     * Method for adding a log with some message to the user. Assumes userId is valid.
     * @param message String - Message attached with to the log.
     * @param userId Integer - Id of the user that the log will be attached to.
     * @return LogData representing the log.
     */
    protected LogData addLog(String message, Integer userId){
        Log log = new Log(userId, message);
        logDatabase.add(log);
        return new LogData(log);
    }

    /**
     * Method for adding a log with some message to the user. Assumes userData has a valid id.
     * @param message String - Message attached with to the log.
     * @param userData UserData - Data associated with some user.
     * @return LogData representing the log.
     */
    protected LogData addLog(String message, UserData userData){
        Log log = new Log(userData.getId(), message);
        logDatabase.add(log);
        return new LogData(log);
    }

    /**
     * Method for removing all logs that are associated with some user.
     * @param userId The id of the user whose logs we would like to remove.
     */
    protected void removeUserLogs(Integer userId){
        ArrayList<Integer> logsToBeRemoved = logDatabase.stream().
                filter(log -> log.getUserId().equals(userId)).map(JsonSerializable::getId).
                collect(Collectors.toCollection(ArrayList::new));
        for (Integer id : logsToBeRemoved){
            logDatabase.remove(id);
        }
    }

    /**
     * Gets an arraylist of LogData associated with the user.
     * @param userData UserData<T extends User> The data associated with the user.
     * @return ArrayList<LogData> List of LogData that belong to a user.
     * @param <T> Extends User.
     */

    public <T extends User> ArrayList<LogData> getUserLogs(UserData<T> userData){
        return databaseUtils.toArrayList(logDatabase.stream().
                filter(log -> log.getId().equals(userData.getId())).map(LogData::new));

    }


}
