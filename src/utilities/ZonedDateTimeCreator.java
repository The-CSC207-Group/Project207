package utilities;

import java.time.ZonedDateTime;
import entities.Clinic;

public class ZonedDateTimeCreator {

    public ZonedDateTimeCreator(){
        
    }

    public ZonedDateTime createZonedDataTime(Integer year, Integer month, Integer dayOfMonth, Integer hour, Integer minute, Clinic
            clinic){
        return ZonedDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0, clinic.getTimeZone());
    }
}
