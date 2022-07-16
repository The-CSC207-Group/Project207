package dataBundles;

import entities.Secretary;
import entities.TimeBlock;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TimeBlockData {
    private final TimeBlock timeBlock;

    public TimeBlockData(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public ZonedDateTime getStartTime(){
        return timeBlock.getStartTime();
    }
    public ZonedDateTime getEndTime(){
        return timeBlock.getEndTime();
    }

    }

