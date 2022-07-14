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
     * Deletes a log from ONLY the log database. If the log idLog isn't associated with a log, nothing happens.
     * @param idLog Id of the log to be deleted from the log database.
     */
    public void deleteLog(Integer idLog){
        if (idLog != null) {
            logDatabase.remove(idLog);
        }

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
     * @param logIDs List of logIds.
     * @return LogDataBundle of the logs in the database associated with logIds given.
     * NOTE: Runtime error is thrown if one of the log IDs is not associated with a log in the database or the id
     * is null.
     */
    public ArrayList<LogDataBundle> getLogDataBundlesFromLogIDs(ArrayList<Integer> logIDs){
        Stream<LogDataBundle> logStream = databaseUtils.getItemsWithIds(logDatabase, logIDs).
                map(x -> new LogDataBundle(x.getId(), x));
        return databaseUtils.toArrayList(logStream);

    }

}
