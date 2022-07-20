package entities;

import utilities.JsonSerializable;

/**
 * Represents an appointment.
 */
public class Appointment extends JsonSerializable {

    private TimeBlock timeBlock;
    private Integer doctorId;
    private Integer patientId;

    /**
     * Creates an instance of Appointment.
     * @param timeBlock TimeBlock corresponding to the appointment.
     * @param doctorId Integer representing the id of the appointment's doctor.
     * @param patientId Integer representing the id of the appointment's patient.
     */
    public Appointment(TimeBlock timeBlock, Integer doctorId, Integer patientId) {
        this.timeBlock = timeBlock;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    /**
     * @return Returns the appointment's TimeBlock object.
     */
    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    /**
     * Sets the appointment's time block.
     * @param timeBlock The new TimeBlock for the appointment.
     */
    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    /**
     * @return Returns the Integer representing the id of the appointment's doctor.
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the appointment's doctor id.
     * @param doctorId The Integer representing the id of the appointment's new doctor.
     */
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * @return Returns the Integer representing the id of the appointment's patient.
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * Sets the appointment's patient id.
     * @param patientId The Integer representing the id of the appointment's new patient.
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}
