package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface UniversalTimeBlockWithDay extends UniversalTimeBlock {
    LocalDate date();

    @Override
    default DayOfWeek dayOfWeek(){
        return date().getDayOfWeek();
    }
}
