package useCases.dataBundles;

import entities.Appointment;
import entities.UniversalTimeBlockWithDay;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Wrapper class for appointment entity.
 */
public class AppointmentData extends DataBundle implements UniversalTimeBlockWithDay {

    private final Appointment appointment;

    /**
     * Constructor.
     *
     * @param appointment Appointment - appointment entity.
     */
    public AppointmentData(Appointment appointment) {
        super(appointment);
        this.appointment = appointment;
    }

    /**
     * Get the TimeBlockData associated with an appointment.
     *
     * @return TimeBlockData - the stored appointment's timeblock.
     */
    public TimeBlockData getTimeBlock() {
        return new TimeBlockData(appointment.getTimeBlock());
    }

    /**
     * Get the doctor id associated with an appointment.
     *
     * @return Integer - the doctor associated with the appointment's id.
     */
    public Integer getDoctorId() {
        return appointment.getDoctorId();
    }

    /**
     * Get the patient id associated with an appointment.
     *
     * @return Integer - the patient associated with the appointment's id.
     */
    public Integer getPatientId() {
        return appointment.getPatientId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getStartTime() {
        return appointment.getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getEndTime() {
        return appointment.getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getLocalDate() {
        return appointment.getLocalDate();
    }
}


