package useCases.managers;

import dataBundles.LogData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Log;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class LogManager {
    DataMapperGateway<Log> logDatabase;


    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /***
     * Stores the logDatabase and the database of a type that extends user for deleting and adding references to logs.
     * @param database The entire database.
     */
    public LogManager(Database database){
        this.logDatabase = database.getLogDatabase();
    }


    protected LogData addLog(String message, UserData userData){
        Log log = new Log(userData.getId(), message);
        logDatabase.add(log);
        return new LogData(log);
    }
    protected LogData addLog(String message, User user){
        Log log = new Log(user.getId(), message);
        logDatabase.add(log);
        return new LogData(log);
    }

    protected void removeUserLogs(Integer userId){
        ArrayList<Integer> logsToBeRemoved = logDatabase.stream().
                filter(log -> log.getUserId().equals(userId)).map(log -> log.getId()).
                collect(Collectors.toCollection(ArrayList::new));
        for (Integer id : logsToBeRemoved){
            logDatabase.remove(id);
        }
    }

    public <T extends User> ArrayList<LogData> getUserLogs(UserData<T> userDataBundle){
        return databaseUtils.toArrayList(logDatabase.stream().
                filter(log -> log.getId().equals(userDataBundle.getId())).map(LogData::new));

    }


}
