// PHASE 2 FILE

package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a time block.
 */
public class TimeBlock implements UniversalTimeBlockWithDay {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //private Date date; (optional, default = null)
    //private Time time; (required)
    //private Day day; (optional, represents Enum of the days the week, default = null)

    /**
     * Creates an instance of TimeBlock.
     * @param startTime LocalDateTime representing the time block's start time.
     * @param endTime LocalDateTime representing the time block's end time.
     */
    public TimeBlock(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return LocalDateTime representing the time block's start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the time block's start time.
     * @param startTime LocalDateTime representing the time block's new start time.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return LocalDateTime representing the time block's end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the time block's end time.
     * @param endTime LocalDateTime representing the time block's new end time.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts the LocalDateTime start time to LocalTime.
     * @return LocalTime representing the time block's start tim.
     */
    public LocalTime startTimeToLocal(){
        return startTime.toLocalTime();
    }

    /**
     * Converts the LocalDateTime end time to LocalTime.
     * @return LocalTime representing the time block's end time.
     */
    public LocalTime endTimeToLocal(){
        return startTime.toLocalTime();
    }

    @Override
    public LocalTime startTime() {
        return startTime.toLocalTime();
    }

    @Override
    public LocalTime endTime() {
        return endTime.toLocalTime();
    }

    @Override
    public LocalDate date() {
        return startTime.toLocalDate();
    }
    @Override
    public DayOfWeek dayOfWeek() {
        return startTime.getDayOfWeek();
    }
}


