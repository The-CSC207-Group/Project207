package dataBundles;

import entities.Availability;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Wrapper class for Availability entity.
 */
public class AvailabilityData {

    private final Availability availability;


    /**
     * Constructor.
     * @param availability Availability - availability entity.
     */
    public AvailabilityData(Availability availability) {
        this.availability = availability;
    }

    /**
     * @return DayOfWeek associated with the stored availability object.
     */
    public DayOfWeek getDayOfWeek() {
        return availability.getDayOfWeek();
    }

    /**
     * @return LocalTime - the start time of the availability.
     */
    public LocalTime getDoctorStartTime() {
        return availability.getDoctorStartTime();
    }

    /**
     * @return LocalTime - the end time of the availability.
     */
    public LocalTime getDoctorEndTime() {
        return availability.getDoctorEndTime();
    }
}