package useCases.query.userQueryConditions;

import entities.User;
import useCases.query.QueryCondition;

public class HasUserId<T extends User> extends QueryCondition<T> {
    String id;
    public HasUserId(String id, Boolean desiredStatus){
        super(desiredStatus);
        this.id = id;
    }
    @Override
    public boolean isTrue(User item) {
        return item.getUsername().equals(this.id);
    }
}
