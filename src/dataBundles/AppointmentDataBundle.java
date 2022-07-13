package dataBundles;

import entities.Appointment;
import entities.TimeBlock;


/**
 * Class solely to bundle appointment data and send it around to different controllers
 */
public class AppointmentDataBundle extends DataBundle {
    private final Appointment appointment;

    public AppointmentDataBundle(Integer id, Appointment appointment){
        super(id);
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

    public Integer getAppointmentId() {
        return appointment.getId();
    }

}


