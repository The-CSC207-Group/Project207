package useCases.query.AvailabilityQueryConditions;

import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

public class NoTimeConflict<T extends TimeBlock> extends QueryCondition<T> {
    public NoTimeConflict(Boolean desiredStatus) {
        super(desiredStatus);
    }

    @Override
    public boolean isTrue(T item) {
        return false;
    }
}
