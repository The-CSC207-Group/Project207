package utilities;

import dataBundles.TimeBlockData;
import entities.TimeBlock;

import java.time.LocalDateTime;

public class TimeUtils {
    public TimeBlockData createTimeBlock(LocalDateTime startTime, LocalDateTime endTime){
        return new TimeBlockData(new TimeBlock(startTime, endTime));
    }
}
