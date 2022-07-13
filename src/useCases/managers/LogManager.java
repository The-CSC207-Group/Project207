package useCases.managers;

import dataBundles.LogDataBundle;
import database.DataMapperGateway;
import entities.Log;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.logQueryConditions.LogBelongsToSpecifiedUser;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogManager {
    DataMapperGateway<Log> logDatabase;

    public LogManager(DataMapperGateway<Log> logDatabase){
        this.logDatabase = logDatabase;
    }

    public void deleteLog(Integer id){
        logDatabase.remove(id);
    }
    public Integer addLog(String message){
        return logDatabase.add(new Log(message));
    }

    public ArrayList<LogDataBundle> getLogDataBundlesFromLogIDs(ArrayList<Integer> logIDs){
            ArrayList<LogDataBundle> logs = logIDs.stream().map(x -> new LogDataBundle(x, logDatabase.get(x))).
                    collect(Collectors.toCollection(ArrayList::new));
            return logs;
    }

}
