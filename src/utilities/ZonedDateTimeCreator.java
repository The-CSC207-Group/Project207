package utilities;

import entities.Clinic;

import java.time.ZonedDateTime;

public class ZonedDateTimeCreator {

    public ZonedDateTimeCreator(){
    }

    public ZonedDateTime createZonedDataTime(Integer year, Integer month, Integer dayOfMonth, Integer hour,
                                             Integer minute, Clinic clinic){
        return ZonedDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0, clinic.getTimeZone());
    }
}
