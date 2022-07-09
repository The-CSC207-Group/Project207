package useCases.query.logQueryConditions;

import entities.Log;
import useCases.query.QueryCondition;

public class LogBelongsToSpecifiedUser extends QueryCondition {
    private String userId;
    public LogBelongsToSpecifiedUser(String userId, Boolean desiredStatus){
        super(desiredStatus);
        this.userId = userId;
    }
    @Override
    public <T> boolean isTrue(T item) {
//        return ((Log)item).getUserId().equals(userId);
        return false;
    }
}
