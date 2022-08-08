// PHASE 2 FILE

package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a doctor's availability days and times.
 */
public class Availability implements UniversalTimeBlock {

    private final DayOfWeek dayOfWeek;
    private LocalTime doctorStartTime;
    private LocalTime doctorEndTime;

    /**
     * Creates an instance of Availability.
     * @param dayOfWeek DayOfWeek that the doctor is available.
     * @param doctorStartTime LocalTime representing the start time of the doctor's availability on day dayOfWeek.
     * @param doctorEndTime LocalTime representing the end time of the doctor's availability on day dayOfWeek.
     */
    public Availability(DayOfWeek dayOfWeek, LocalTime doctorStartTime, LocalTime doctorEndTime){
        this.dayOfWeek = dayOfWeek;
        this.doctorStartTime = doctorStartTime;
        this.doctorEndTime = doctorEndTime;
    }

    /**
     * @return DayOfWeek of this instance of AvailabilityData.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }


    /**
     * Sets the start time of this instance of AvailabilityData.
     * @param startTime LocalTime representing the new start time of this instance of AvailabilityData.
     */
    public void setDoctorStartTime(LocalTime startTime) {
        this.doctorStartTime = startTime;
    }

    /**
     * @return LocalTime representing the end time of this instance of AvailabilityData.
     */
    public void setDoctorEndTime(LocalTime endTime) {
        this.doctorEndTime = endTime;
    }

    @Override
    public LocalTime startTime() {
        return doctorStartTime;
    }

    @Override
    public LocalTime endTime() {
        return doctorEndTime;
    }

    @Override
    public DayOfWeek dayOfWeek() {
        return dayOfWeek;
    }
}
