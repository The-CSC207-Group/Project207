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
     * @param startTime The time block's start time.
     * @param endTime The time block's end time.
     */
    public TimeBlock(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return Returns the time block's start time.
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the time block's start time.
     * @param startTime The time block's new start time.
     */
    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return Returns the time block's end time.
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the time block's end time.
     * @param endTime The time block's new end time.
     */
    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts the ZonedDateTime start time to LocalTime.
     * @return Returns the time block's start time in LocalTime.
     */
    public LocalTime startTimeToLocal(){
        return startTime.toLocalTime();
    }

    /**
     * Converts the ZonedDateTime end time to LocalTime.
     * @return Returns the time block's end time in LocalTime.
     */
    public LocalTime endTimeToLocal(){
        return startTime.toLocalTime();
    }
}

