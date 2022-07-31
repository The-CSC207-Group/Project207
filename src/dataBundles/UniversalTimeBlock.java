package dataBundles;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface UniversalTimeBlock {
    LocalTime startTime();
    LocalTime endTime();
    DayOfWeek dayOfWeek();

}
