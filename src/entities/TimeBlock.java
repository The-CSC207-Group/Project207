package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a time block.
 */
public class TimeBlock implements UniversalTimeBlockWithDay {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Creates an instance of TimeBlock.
     *
     * @param startTime LocalDateTime representing the time block's start time.
     * @param endTime   LocalDateTime representing the time block's end time.
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
     * @return LocalDateTime representing the time block's end time.
     */
    public LocalDateTime getEndDateTime() {
        return endTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getStartTime() {
        return startTime.toLocalTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getEndTime() {
        return endTime.toLocalTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getLocalDate() {
        return startTime.toLocalDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DayOfWeek getDayOfWeek() {
        return startTime.getDayOfWeek();
    }
}


