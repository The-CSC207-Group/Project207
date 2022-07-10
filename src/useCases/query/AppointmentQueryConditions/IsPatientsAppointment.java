package useCases.query.AppointmentQueryConditions;

import entities.Appointment;
import useCases.query.QueryCondition;

public class IsPatientsAppointment<T extends Appointment> extends QueryCondition<T> {
    Integer patientId;

    public IsPatientsAppointment(Integer patientId, Boolean desiredStatus){
        super(desiredStatus);
        this.patientId = patientId;
    }
    @Override
    public boolean isTrue(T item) {
        return patientId.equals((item).getPatientID());
    }
}
