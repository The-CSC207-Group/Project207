package useCases.query.AvailabilityQueryConditions;

import entities.Doctor;
import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

public class NoAbsenceConflict<T extends TimeBlock> extends QueryCondition<T> {
    private DataMapperGateway<Doctor> doctorDatabase;
    private Integer doctorId;
    public NoAbsenceConflict(Boolean desiredStatus, DataMapperGateway<Doctor> doctorDatabase, Integer doctorId) {
        super(desiredStatus);
        this.doctorDatabase = doctorDatabase;
        this.doctorId = doctorId;
    }

    @Override
    public boolean isTrue(T item) {
        for (TimeBlock absence: doctorDatabase.get(doctorId).getAbsence()){
            if (absence.getStartTime().isBefore(item.getEndTime()) | absence.getEndTime().isAfter(item.getStartTime())){
                return false;
            }
        }
        return true;
    }
}
