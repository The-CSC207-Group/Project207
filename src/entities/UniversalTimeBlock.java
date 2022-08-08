package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface UniversalTimeBlock {
    /**
     * returns the start time of the timeblock
     * @return LocalTime
     */
    LocalTime startTime();

    /**
     * returns the end time of the timeblock
     * @return LocalTime
     */
    LocalTime endTime();

    /**
     * returns the day of week of the appointment
     * @return LocalTime
     */
    DayOfWeek dayOfWeek();

}
