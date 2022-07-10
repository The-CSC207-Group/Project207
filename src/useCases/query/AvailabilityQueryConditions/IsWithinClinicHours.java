package useCases.query.AvailabilityQueryConditions;

import entities.Clinic;
import entities.TimeBlock;
import useCases.query.QueryCondition;

public class IsWithinClinicHours <T extends TimeBlock> extends QueryCondition<T> {

    public Clinic clinic;

    public IsWithinClinicHours(Boolean desiredStatus, Clinic clinic) {
        super(desiredStatus);
        this.clinic = clinic;

    }
    //item here should be a TimeBlock
    @Override
    public boolean isTrue(T item) {
        return item.getStartTime().isBefore(clinic.getClinicHours().getStartTime()) |
                item.getEndTime().isBefore(clinic.getClinicHours().getEndTime());
    }
}
