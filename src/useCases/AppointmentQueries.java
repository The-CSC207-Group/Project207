package useCases;

import entities.Appointment;

public class AppointmentQueries {

    private Appointment appointment;

    public AppointmentQueries(Appointment appointment){
        this.appointment = appointment;
    }
    public boolean isDoctorsAppointment(Integer doctorId){
        return appointment.getDoctorId() == doctorId;
    }

}
