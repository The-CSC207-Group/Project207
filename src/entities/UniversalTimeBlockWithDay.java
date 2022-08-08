package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface UniversalTimeBlockWithDay extends UniversalTimeBlock {
    /**
     * returns the date of the time block
     * @return LocalDate
     */
    LocalDate date();

    /**
     *
     * {@inheritDoc}
     */
    @Override
    default DayOfWeek dayOfWeek(){
        return date().getDayOfWeek();
    }


}
