package dataBundles;

import entities.TimeBlock;
import entities.UniversalTimeBlockWithDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Wrapper class for a TimeBlock entity.
 */
public class TimeBlockData implements UniversalTimeBlockWithDay {

    private final TimeBlock timeBlock;

    /**
     * Constructor.
     * @param timeBlock TimeBlock - time block to be stored.
     */
    public TimeBlockData(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    /**
     * @return LocalDateTime - start time of the time block.
     */
    public LocalDateTime getStartDateTime(){
        return timeBlock.getStartDateTime();
    }

    /**
     * @return LocalDateTime - end time of the time block.
     */
    public LocalDateTime getEndDateTime(){
        return timeBlock.getEndDateTime();
    }

    @Override
    public LocalTime getStartTime() {
        return timeBlock.getStartTime();
    }

    @Override
    public LocalTime getEndTime() {
        return timeBlock.getEndTime();
    }

    @Override
    public LocalDate getLocalDate() {
        return timeBlock.getLocalDate();
    }
}

