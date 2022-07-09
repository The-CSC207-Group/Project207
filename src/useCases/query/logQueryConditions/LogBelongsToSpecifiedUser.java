package useCases.query.logQueryConditions;

import entities.Log;
import useCases.query.QueryCondition;

public class LogBelongsToSpecifiedUser<T extends Log> extends QueryCondition<T> {
    private String userId;
    public LogBelongsToSpecifiedUser(String userId, Boolean desiredStatus){
        super(desiredStatus);
        this.userId = userId;
    }
    @Override
    public boolean isTrue(T item) {
//        return item.getUserId().equals(userId);
        return false;
    }
}
