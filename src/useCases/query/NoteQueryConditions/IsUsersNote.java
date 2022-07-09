package useCases.query.NoteQueryConditions;

import entities.Note;
import useCases.query.QueryCondition;

public class IsUsersNote extends QueryCondition {
    Integer userId;

    public IsUsersNote(Integer userId, Boolean desiredStatus){
        super(desiredStatus);
        this.userId = userId;
    }
    @Override
    public <T> boolean isTrue(T item) {
        return userId.equals(((Note)item).getPatientID());
    }

}
