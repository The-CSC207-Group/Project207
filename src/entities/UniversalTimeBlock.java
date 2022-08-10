package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Interface that provides methods relating to getting the time and date of TimeBlocks such as appointment and
 * availability.
 */
public interface UniversalTimeBlock {
    /**
     * Returns the start time of the TimeBlock.
     *
     * @return LocalTime
     */
    LocalTime getStartTime();

    /**
     * Returns the end time of the TimeBlock.
     *
     * @return LocalTime
     */
    LocalTime getEndTime();

    /**
     * returns the day of week of the appointment
     *
     * @return LocalTime
     */
    DayOfWeek getDayOfWeek();

}
