package useCases.managers;

import dataBundles.LogData;
import dataBundles.UserData;
import database.DataMapperGateway;
import entities.Log;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;
import java.util.stream.Stream;


public class LogManager {
    DataMapperGateway<Log> logDatabase;


    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /**
     * Stores the logDatabase and the database of a type that extends user for deleting and adding references to logs.
     * @param logDatabase DataMapperGateway<Log> log database
     */
    public LogManager(DataMapperGateway<Log> logDatabase){
        this.logDatabase = logDatabase;
    }

//    /**
//     * Deletes a log from the log database and from its associated user given the user belongs in the database specified.
//     * If the log logId isn't associated with a log from the database, nothing happens. If the userId isn't specified, nothing happens.
//     * If the wrong DataMapperGateway is provided, nothing happens. Assumes userId is not null.
//     * @param logId Id of the log to be deleted from the log database. Assumes logId is not null.
//     */
//    public <T extends User> void deleteLog(Integer userId, Integer logId, DataMapperGateway<T> userDatabase){
//            logDatabase.remove(logId);
//            User user = userDatabase.get(userId);
//            if (user != null) {
//                user.getLogIds().remove(logId);
//            }
//    }
    public boolean deleteLog(Integer logId){
        return logDatabase.remove(logId);
    }

//    /**
//     * Adds log to the log database and its Id to the user associated with the userId from the given database. If the
//     * userId does not belong to the userDatabase, null is returned.
//     * @param message Message that the log should store and that will be displayed to anyone who looks at past logs.
//     * @return LogDataBundle associated with the log
//     */
//    public <T extends User>LogDataBundle addLog(String message, Integer userId, DataMapperGateway<T> userDatabase){
//        Log log = new Log(message);
//        Integer logId = logDatabase.add(log);
//        User user = userDatabase.get(userId);
//        if (user == null) {return null;}
//        user.addLogId(logId);
//        return new LogDataBundle(log);
//    }
    public LogData addLog(String message, Integer userId){
        Log log = new Log(userId, message);
        logDatabase.add(log);
        return new LogData(log);
    }

//    /**
//     * Gets all log data bundles associated with a user given a username and the database the user belongs to.
//     * If a log reference stored in the user does not exist in the log database, return null.
//     * @param username username of user whose logs we would like to obtain.
//     * @param userDatabase database that stores the user.
//     * @return null if user does not exist in userDatabase or if the log references stored in the user do not
//     * exist in the log database. Return the logdatabundles representing the user's logs otherwise
//     * @param <T> the type of the database.
//     */
//    public <T extends User> ArrayList<LogDataBundle> getLogDataBundlesFromUsername(String username, DataMapperGateway<T> userDatabase){
//        User user = getUserByUsername(userDatabase, username);
//        if (user == null){return null;}
//        Stream<Log> logStream = databaseUtils.getItemsWithIds(logDatabase, user.getLogIds());
//        if (logStream == null){return null;}
//        return databaseUtils.toArrayList(logStream.map(x -> new LogDataBundle(x)));
//    }
    public <T extends User> ArrayList<LogData> getLogDataBundlesFromUserDataBundle(UserData<T> userDataBundle){
        return databaseUtils.toArrayList(logDatabase.stream().
                filter(log -> log.getId().equals(userDataBundle.getId())).map(LogData::new));

    }
//    private  <T extends User> T getUserByUsername(DataMapperGateway<T> database, String username){
//        return databaseUtils.getUserByUsername(database, username);
//    }

}
