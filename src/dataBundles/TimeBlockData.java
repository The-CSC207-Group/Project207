package dataBundles;

import entities.Secretary;
import entities.TimeBlock;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Wrapper class for a TimeBlock entity.
 */
public class TimeBlockData {
    private final TimeBlock timeBlock;

    /**
     * Constructor.
     * @param timeBlock TimeBlock - time block to be stored.
     */
    public TimeBlockData(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    /**
     * @return ZonedDateTime - start time of the time block.
     */
    public ZonedDateTime getStartTime(){
        return timeBlock.getStartTime();
    }
    /**
     * @return ZonedDateTime - end time of the time block.
     */
    public ZonedDateTime getEndTime(){
        return timeBlock.getEndTime();
    }

    }

