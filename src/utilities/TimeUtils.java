package utilities;

import dataBundles.TimeBlockData;
import entities.TimeBlock;

import java.time.LocalDateTime;

/**
 * Utility class containing universally applicable methods related to time.
 */
public class TimeUtils {

    /**
     * Method for creating TimeBlockData from a startTime and endTime.
     * @param startTime LocalDateTime - start time of the TimeBlockData.
     * @param endTime LocalDateTime - end time of the TimeBlockData.
     * @return TimeBlockData - the time block data with the given start and end time.
     */
    public TimeBlockData createTimeBlock(LocalDateTime startTime, LocalDateTime endTime){
        return new TimeBlockData(new TimeBlock(startTime, endTime));
    }
}
