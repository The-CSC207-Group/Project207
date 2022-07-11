package useCases.query.AvailabilityQueryConditions;

import entities.AvailabilityData;
import entities.Doctor;
import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

public class NoDoctorAvailabilityConflict<T extends TimeBlock> extends QueryCondition<T> {
    private DataMapperGateway<Doctor> doctorDatabase;
    private Integer doctorId;
    public NoDoctorAvailabilityConflict(Boolean desiredStatus, DataMapperGateway<Doctor> doctorDatabase,
                                        Integer doctorId) {
        super(desiredStatus);
        this.doctorDatabase = doctorDatabase;
        this.doctorId = doctorId;
    }

    @Override
    public boolean isTrue(T item) {
        for (AvailabilityData availabilityData: doctorDatabase.get(doctorId).getAvailability()){
            if (item.getStartTime().getDayOfYear() % availabilityData.getDayOfWeek().getValue() == 0){
                return item.getStartTime().getHour() >= availabilityData.getDoctorStartTime().getHour() &
                        item.getEndTime().getHour() <= availabilityData.getDoctorEndTime().getHour();
            }
        }
        return false;
    }
}
