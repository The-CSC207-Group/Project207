package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a doctor's availability days and times.
 */
public class Availability {
    private final DayOfWeek dayOfWeek;
    private LocalTime doctorStartTime;
    private LocalTime doctorEndTime;

    /**
     * Creates an instance of AvailabilityData
     * @param dayOfWeek The day of the week the doctor is available.
     * @param doctorStartTime The start time of the doctor's availability on day dayOfWeek.
     * @param doctorEndTime The end time of the doctor's availability on day dayOfWeek.
     */
    public Availability(DayOfWeek dayOfWeek, LocalTime doctorStartTime, LocalTime doctorEndTime){
        this.dayOfWeek = dayOfWeek;
        this.doctorStartTime = doctorStartTime;
        this.doctorEndTime = doctorEndTime;
    }

    /**
     * @return Returns the day of the week of this instance of AvailabilityData.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @return Returns the start time of this instance of AvailabilityData.
     */
    public LocalTime getDoctorStartTime() {
        return doctorStartTime;
    }

    /**
     * Sets the start time of this instance of AvailabilityData.
     * @param startTime The new start time of this instance of AvailabilityData.
     */
    public void setDoctorStartTime(LocalTime startTime) {
        this.doctorStartTime = startTime;
    }

    /**
     * @return Returns the end time of this instance of AvailabilityData.
     */
    public LocalTime getDoctorEndTime() {
        return doctorEndTime;
    }

    /**
     * Sets the end time of this instance of AvailabilityData.
     * @param endTime The new end time of this instance of AvailabilityData.
     */
    public void setDoctorEndTime(LocalTime endTime) {
        this.doctorEndTime = endTime;
    }
}
