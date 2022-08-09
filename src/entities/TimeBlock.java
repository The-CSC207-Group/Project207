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
    public LocalDateTime getStartDateTime() {
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
    public LocalDateTime getEndDateTime() {
        return endTime;
    }

    /**
     * Sets the time block's end time.
     * @param endTime LocalDateTime representing the time block's new end time.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    @Override
    public LocalTime getStartTime() {
        return startTime.toLocalTime();
    }

    @Override
    public LocalTime getEndTime() {
        return endTime.toLocalTime();
    }

    @Override
    public LocalDate getLocalDate() {
        return startTime.toLocalDate();
    }
    @Override
    public DayOfWeek getDayOfWeek() {
        return startTime.getDayOfWeek();
    }
}


