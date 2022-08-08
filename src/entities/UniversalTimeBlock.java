package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface UniversalTimeBlock {
    /**
     * returns the start time of the timeblock
     * @return
     */
    LocalTime startTime();

    /**
     * returns the end time of the timeblock
     * @return
     */
    LocalTime endTime();
    DayOfWeek dayOfWeek();

}
