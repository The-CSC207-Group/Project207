package useCases.managers;

import database.Database;
import entities.TimeBlock;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeBlockManager {

    public TimeBlock createTimeBlock(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer LenOfAppointment){
        return new TimeBlock(createZonedDataTime(year, month, day, hour, minute),
                createZonedDataTime(year, month, day, hour, LenOfAppointment));
    }
    public TimeBlock createTimeBlock(Integer month, Integer day, Integer hour, Integer minute, Integer LenOfAppointment){
        return new TimeBlock(createZonedDataTime(ZonedDateTime.now().getYear(), month, day, hour, minute),
                createZonedDataTime(ZonedDateTime.now().getYear(), month, day, hour, LenOfAppointment));
    }
    public ZonedDateTime createZonedDataTime(Integer year, Integer month, Integer dayOfMonth, Integer hour,
                                             Integer minute){
        return ZonedDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0, ZoneId.of("US/Eastern"));
    }
}
