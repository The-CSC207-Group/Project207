package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * has methods relating to getting time block information such as start time and date.
 */
public interface UniversalTimeBlockWithDay extends UniversalTimeBlock {
    /**
     * returns the date of the time block
     * @return LocalDate
     */
    LocalDate getLocalDate();

    /**
     *
     * {@inheritDoc}
     */
    @Override
    default DayOfWeek getDayOfWeek(){
        return getLocalDate().getDayOfWeek();
    }
}
