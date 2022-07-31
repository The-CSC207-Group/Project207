package dataBundles;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface UniversalTimeBlockWithDay extends UniversalTimeBlock {
    LocalDate day();

    @Override
    default DayOfWeek dayOfWeek(){
        return day().getDayOfWeek();
    }
}
