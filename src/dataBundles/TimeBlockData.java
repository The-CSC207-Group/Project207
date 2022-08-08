// PHASE 2 FILE

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
    public LocalDateTime getStartTime(){
        return timeBlock.getStartTime();
    }

    /**
     * @return LocalDateTime - end time of the time block.
     */
    public LocalDateTime getEndTime(){
        return timeBlock.getEndTime();
    }

    @Override
    public LocalTime startTime() {
        return timeBlock.startTime();
    }

    @Override
    public LocalTime endTime() {
        return timeBlock.endTime();
    }

    @Override
    public LocalDate date() {
        return timeBlock.date();
    }
}

