package useCases.query.AvailabilityQueryConditions;

import entities.AvailabilityData;
import entities.Doctor;
import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

import java.util.Objects;

public class NoDoctorAvailabilityConflict<T extends Doctor> extends QueryCondition<T> {
    private DataMapperGateway<Doctor> doctorDatabase;
    private Integer doctorId;
    private TimeBlock suggestedTime;

    public NoDoctorAvailabilityConflict(Boolean desiredStatus, Integer doctorId, TimeBlock suggestedTime) {
        super(desiredStatus);
        this.doctorId = doctorId;
        this.suggestedTime = suggestedTime;
    }

    @Override
    public boolean isTrue(T item) {
        if (Objects.equals(item.getId(), doctorId)) {
            for (AvailabilityData availability : item.getAvailability()) {
                if (availability.getDoctorStartTime().getHour() <= suggestedTime.getStartTime().getHour()
                        & availability.getDoctorEndTime().getHour() < suggestedTime.getEndTime().getHour()) {
                    return false;
                } else if (availability.getDoctorStartTime().getHour() > suggestedTime.getStartTime().getHour()
                        & availability.getDoctorEndTime().getHour() >= suggestedTime.getEndTime().getHour()) {
                    return false;
                }
            }

        }
        return true;
    }
}
