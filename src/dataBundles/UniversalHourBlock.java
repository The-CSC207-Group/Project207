package dataBundles;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface UniversalHourBlock {
    LocalTime startTime();
    LocalTime endTime();
    DayOfWeek dayOfWeek();

}
