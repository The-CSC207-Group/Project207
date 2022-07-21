package utilities;

import entities.TimeBlock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtils {

    public TimeBlock createTimeBlock(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer lenOfAppointment){
        ZonedDateTime starTime = createZonedDataTime(year, month, day, hour, minute);
        TimeBlock proposedTime = new TimeBlock(createZonedDataTime(year, month, day, hour, minute),
                starTime.plusMinutes(lenOfAppointment));
        if (proposedTime.getStartTime().isBefore(getCurrentZonedDateTime())){
            proposedTime.getStartTime().plusYears(1);
            proposedTime.getEndTime().plusYears(1);
            return proposedTime;
        }
        return proposedTime;
    }

    public LocalDate createLocalDate(Integer year, Integer month, Integer dayOfMonth){
        return LocalDate.of(year, month, dayOfMonth);
    }

    public LocalTime createLocalTime(Integer hour, Integer minute, Integer second){
        return LocalTime.of(hour, minute, second);
    }

    public ZonedDateTime createZonedDataTime(Integer year, Integer month, Integer dayOfMonth, Integer hour,
                                             Integer minute){
        return ZonedDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0, ZoneId.of("US/Eastern"));
    }

    public ZonedDateTime getCurrentZonedDateTime(){
        return ZonedDateTime.now(ZoneId.of("US/Eastern"));
    }

    public LocalTime getCurrentLocalTime(){
        return LocalTime.now();
    }

}
