package dataBundles;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface UniversalTimeBlock extends UniversalHourBlock{
    LocalDate day();

    @Override
    default DayOfWeek dayOfWeek(){
        return day().getDayOfWeek();
    }
}
