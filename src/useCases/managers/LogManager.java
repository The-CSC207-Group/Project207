package useCases.managers;

import dataBundles.LogDataBundle;
import database.DataMapperGateway;
import entities.Log;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogManager {
    DataMapperGateway<Log> logDatabase;

    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /**
     * Stores the logDatabase.
     * @param logDatabase
     */
    public LogManager(DataMapperGateway<Log> logDatabase){
        this.logDatabase = logDatabase;
    }

    /**
     * Deletes a log from ONLY the log database. If the log logId isn't associated with a log, nothing happens.
     * @param logId Id of the log to be deleted from the log database. Assumes logId is not null.
     */
    public void deleteLog(Integer logId){
            logDatabase.remove(logId);
    }

    /**
     * Adds log ONLY to the log database. This does not store the reference to the log in the logIds attribute under user.
     * @param message Message that the log should store and that will be displayed to anyone who looks at past logs.
     * @return LogDataBundle associated with the log
     */
    public LogDataBundle addLog(String message){
        Log log = new Log(message);
        Integer logId = logDatabase.add(log);
        return new LogDataBundle(logId, log);
    }

    /**
     *
     * @param logIds ArrayList of logIds. Assumes none are null.
     * @return LogDataBundle of the logs in the database associated with logIds given.
     * Null is returned if one of the logs does not exist in the database.
     */
    public ArrayList<LogDataBundle> getLogDataBundlesFromLogIDs(ArrayList<Integer> logIds){
        Stream<Log> logStream = databaseUtils.getItemsWithIds(logDatabase, logIds);
        if (logStream == null){return null;}
        return databaseUtils.toArrayList(logStream.map(x -> new LogDataBundle(x.getId(), x)));
    }

}
