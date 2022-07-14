package useCases.query.AppointmentQueryConditions;

import entities.Appointment;
import useCases.query.QueryCondition;

public class IsDoctorsAppointment<T extends Appointment> extends QueryCondition<T> {
    Integer doctorId;

    public IsDoctorsAppointment(Integer doctorId, Boolean desiredStatus){
        super(desiredStatus);
        this.doctorId = doctorId;
    }
    @Override
    public boolean isTrue(T item) {
        return doctorId.equals((item).getDoctorId());
    }
}
