package dataBundles;

import entities.Appointment;
import entities.TimeBlock;


/**
 * Class solely to bundle appointment data and send it around to different controllers
 */
public class AppointmentDataBundle {
    private final Appointment appointment;

    public AppointmentDataBundle(Appointment appointment){
        this.appointment = appointment;
    }

    public TimeBlock getTimeBlock() {
        return appointment.getTimeBlock();
    }

    public Integer getDoctorID() {
        return appointment.getDoctorID();
    }

    public Integer getPatientID() {
        return appointment.getPatientID();
    }

}


