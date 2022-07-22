// PHASE 2 FILE

package entities;

import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Represents a time block.
 */
public class TimeBlock {

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    //private Date date; (optional, default = null)
    //private Time time; (required)
    //private Day day; (optional, represents Enum of the days the week, default = null)

    /**
     * Creates an instance of TimeBlock.
     * @param startTime ZonedDateTime representing the time block's start time.
     * @param endTime ZonedDateTime representing the time block's end time.
     */
    public TimeBlock(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return ZonedDateTime representing the time block's start time.
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the time block's start time.
     * @param startTime ZonedDateTime representing the time block's new start time.
     */
    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return ZonedDateTime representing the time block's end time.
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the time block's end time.
     * @param endTime ZonedDateTime representing the time block's new end time.
     */
    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts the ZonedDateTime start time to LocalTime.
     * @return LocalTime representing the time block's start time in LocalTime.
     */
    public LocalTime startTimeToLocal(){
        return startTime.toLocalTime();
    }

    /**
     * Converts the ZonedDateTime end time to LocalTime.
     * @return LocalTime representing the time block's end time in LocalTime.
     */
    public LocalTime endTimeToLocal(){
        return startTime.toLocalTime();
    }

}

