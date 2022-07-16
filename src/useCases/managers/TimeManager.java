package useCases.managers;

import entities.TimeBlock;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeManager {

    public TimeBlock createTimeBlock(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer LenOfAppointment){
        TimeBlock proposedTime = new TimeBlock(createZonedDataTime(year, month, day, hour, minute),
                createZonedDataTime(year, month, day, hour, LenOfAppointment));
        if (proposedTime.getStartTime().isBefore(ZonedDateTime.now())){
            proposedTime.getStartTime().plusYears(1);
            proposedTime.getEndTime().plusYears(1);
            return proposedTime;
        }
        return proposedTime;
    }
    public LocalDate createLocalDate(Integer year, Integer month, Integer dayOfMonth){
        return LocalDate.of(year, month, dayOfMonth);
    }
    public ZonedDateTime createZonedDataTime(Integer year, Integer month, Integer dayOfMonth, Integer hour,
                                             Integer minute){
        return ZonedDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0, ZoneId.of("US/Eastern"));
    }
}
