package useCases.query.AvailabilityQueryConditions;

import entities.Appointment;
import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

import java.time.ZonedDateTime;

public class NoAppointmentConflict<T extends Appointment> extends QueryCondition<T> {
    private TimeBlock suggestedTime;
    public NoAppointmentConflict(Boolean desiredStatus,
                                 TimeBlock suggestedTime) {
        super(desiredStatus);
        this.suggestedTime = suggestedTime;
    }

    @Override
    public boolean isTrue(T item) {
        if (suggestedTime.getStartTime().getDayOfYear() == item.getTimeBlock().getStartTime().getDayOfYear()){
            if (suggestedTime.getStartTime().isBefore(item.getTimeBlock().getStartTime()) &
                    suggestedTime.getEndTime().isAfter(item.getTimeBlock().getStartTime())){
                return false;
            }
            return !(suggestedTime.getStartTime().isAfter(item.getTimeBlock().getStartTime()) &
                    suggestedTime.getStartTime().isBefore(item.getTimeBlock().getEndTime()));
        }


        return true;
    }
}
