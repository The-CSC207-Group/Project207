package dataBundles;

import entities.Availability;
import entities.UniversalTimeBlock;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Wrapper class for Availability entity.
 */
public class AvailabilityData implements UniversalTimeBlock {

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


    /**
     * @return LocalTime - the start time of the availability.
     */
    public LocalTime getDoctorStartTime() {
        return availability.getStartTime();
    }

    /**
     * @return LocalTime - the end time of the availability.
     */
    public LocalTime getDoctorEndTime() {
        return availability.getEndTime();
    }

    @Override
    public LocalTime getStartTime() {
        return availability.getStartTime();
    }

    @Override
    public LocalTime getEndTime() {
        return availability.getEndTime();
    }

    @Override
    public DayOfWeek getDayOfWeek() {
        return availability.getDayOfWeek();
    }
}
