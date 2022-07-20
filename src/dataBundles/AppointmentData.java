package dataBundles;

import entities.Appointment;
import entities.TimeBlock;


/**
 * Wrapper class for appointment entity.
 */
public class AppointmentData {

    private final Appointment appointment;

    /**
     * Constructor.
     * @param appointment Appointment - appointment entity.
     */
    public AppointmentData(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * Get the TimeBlock associated with an appointment.
     * @return TimeBlock - the stored appointment's timeblock.
     */
    public TimeBlock getTimeBlock() {
        return appointment.getTimeBlock();
    }

    /**
     * Get the doctor id associated with an appointment.
     * @return Integer - the doctor associated with the appointment's id.
     */
    public Integer getDoctorId() {
        return appointment.getDoctorId();
    }

    /**
     * Get the patient id associated with an appointment.
     * @return Integer - the patient associated with the appointment's id.
     */
    public Integer getPatientId() {
        return appointment.getPatientId();
    }

    /**
     * Get the stored appointment's id.
     * @return Integer - the appointment's id.
     */
    public Integer getAppointmentId() {
        return appointment.getId();
    }


}


