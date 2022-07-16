package dataBundles;

import entities.Appointment;
import entities.TimeBlock;


/**
 * Class solely to bundle appointment data and send it around to different controllers
 */
public class AppointmentData {
    private final Appointment appointment;

    public AppointmentData(Appointment appointment) {
        this.appointment = appointment;
    }

    public TimeBlock getTimeBlock() {
        return appointment.getTimeBlock();
    }

    public Integer getDoctorID() {
        return appointment.getDoctorId();
    }

    public Integer getPatientId() {
        return appointment.getPatientId();
    }

    public Integer getAppointmentId() {
        return appointment.getId();
    }


}


