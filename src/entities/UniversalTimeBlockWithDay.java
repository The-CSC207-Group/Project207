package entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Contains methods relating to getting time block information such as start time and date.
 */
public interface UniversalTimeBlockWithDay extends UniversalTimeBlock {
    /**
     * Returns the date of the time block.
     *
     * @return LocalDate
     */
    LocalDate getLocalDate();

    /**
     * {@inheritDoc}
     */
    @Override
    default DayOfWeek getDayOfWeek() {
        return getLocalDate().getDayOfWeek();
    }
}
