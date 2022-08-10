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
     * @return LocalTime - the start time of the availability.
     */
    @Override
    public LocalTime getStartTime() {
        return availability.getStartTime();
    }

    /**
     * @return LocalTime - the end time of the availability.
     */
    @Override
    public LocalTime getEndTime() {
        return availability.getEndTime();
    }

    /**
     * @return DayOfWeek - the day of the week in availability.
     */
    @Override
    public DayOfWeek getDayOfWeek() {
        return availability.getDayOfWeek();
    }
}
