package entities;

import utilities.JsonSerializable;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appointment.
 */
public class Appointment extends JsonSerializable implements UniversalTimeBlockWithDay {

    private final TimeBlock timeBlock;
    private final Integer doctorId;
    private final Integer patientId;

    /**
     * Creates an instance of Appointment.
     *
     * @param timeBlock TimeBlock corresponding to the appointment.
     * @param doctorId  Integer representing the id of the appointment's doctor.
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
     * @return Returns the Integer representing the id of the appointment's doctor.
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * @return Returns the Integer representing the id of the appointment's patient.
     */
    public Integer getPatientId() {
        return patientId;
    }

    @Override
    public LocalTime getStartTime() {
        return timeBlock.getStartTime();
    }

    @Override
    public LocalTime getEndTime() {
        return timeBlock.getEndTime();
    }

    @Override
    public LocalDate getLocalDate() {
        return timeBlock.getLocalDate();
    }
}
