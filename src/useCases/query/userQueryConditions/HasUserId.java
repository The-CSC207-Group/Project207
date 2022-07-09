package useCases.query.userQueryConditions;

import entities.User;
import useCases.query.QueryCondition;

public class HasUserId extends QueryCondition {
    String id;
    public HasUserId(String id){
        this.id = id;
    }

    public <T> boolean isTrue(T item) {
        return ((User)item).getUserID().equals(this.id);
    }

}
