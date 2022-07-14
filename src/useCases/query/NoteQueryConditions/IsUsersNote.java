package useCases.query.NoteQueryConditions;

import entities.Note;
import useCases.query.QueryCondition;

public class IsUsersNote<T extends Note> extends QueryCondition<T> {
    Integer userId;

    public IsUsersNote(Integer userId, Boolean desiredStatus){
        super(desiredStatus);
        this.userId = userId;
    }

    @Override
    public boolean isTrue(T item) {
        return userId.equals(item.getPatientId());
    }
}
