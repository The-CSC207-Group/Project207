package useCases;

import dataBundles.LogDataBundle;
import database.DataMapperGateway;
import entities.Log;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.logQueryConditions.LogBelongsToSpecifiedUser;

import java.util.ArrayList;

public class LogManager {
    DataMapperGateway<Log> logDatabase;

    public LogManager(DataMapperGateway<Log> logDatabase){
        this.logDatabase = logDatabase;
    }

    public void deleteLog(Integer id){
        logDatabase.remove(id);
    }
    public void addLog(String message){
        logDatabase.add(new Log(message));
    }


    public ArrayList<LogDataBundle> getLogsByUserId(String userId){
        Query<Log> query = new Query<>();
        ArrayList<QueryCondition> queryConditions = new ArrayList<>();
        queryConditions.add(new LogBelongsToSpecifiedUser(userId, true));
        ArrayList<Log> logs = query.returnAllMeetingConditions(logDatabase, queryConditions);
        return convertLogToLogDataBundleInArraylist(logs);
    }
    private ArrayList<LogDataBundle> convertLogToLogDataBundleInArraylist(ArrayList<Log> logs){
        ArrayList<LogDataBundle> res = new ArrayList<>();
        for (Log log : logs){
            res.add(new LogDataBundle(log));
        }
        return res;
    }
}
