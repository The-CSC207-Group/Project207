package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a doctor's availability days and times.
 */
public class Availability implements UniversalTimeBlock {

    private final DayOfWeek dayOfWeek;
    private final LocalTime doctorStartTime;
    private final LocalTime doctorEndTime;

    /**
     * Creates an instance of Availability.
     *
     * @param dayOfWeek       DayOfWeek that the doctor is available.
     * @param doctorStartTime LocalTime representing the start time of the doctor's availability on day dayOfWeek.
     * @param doctorEndTime   LocalTime representing the end time of the doctor's availability on day dayOfWeek.
     */
    public Availability(DayOfWeek dayOfWeek, LocalTime doctorStartTime, LocalTime doctorEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.doctorStartTime = doctorStartTime;
        this.doctorEndTime = doctorEndTime;
    }

    @Override
    public LocalTime getStartTime() {
        return doctorStartTime;
    }

    @Override
    public LocalTime getEndTime() {
        return doctorEndTime;
    }

    @Override
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
