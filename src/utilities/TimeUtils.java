// PHASE 2 FILE

package utilities;

import entities.TimeBlock;

import java.time.*;

/**
 * Utility class of methods relating to usage of time, used anything related to appointments, absence, availability, and
 * time blocks.
 */
public class TimeUtils {
    /**
     * A method that creates a time block without requiring an input of LocalDateTime.
     * @param year              an integer that represents the year for a time block to exist.
     * @param month             an integer that represents the month of the given year.
     * @param day               an integer that represents a day of a given month.
     * @param hour              an integer that represents an hour of a given day.
     * @param minute            an integer that represents a minute of a given hour.
     * @param lenOfAppointment  an integer that represents the length of the appointment in terms of minutes.
     * @return a TimeBlock grouping together all the inputted data.
     */
    public TimeBlock createTimeBlock(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer lenOfAppointment){
        LocalDateTime startTime = createLocalDateTime(year, month, day, hour, minute);
        TimeBlock timeBlock = new TimeBlock(startTime, startTime.plusMinutes(lenOfAppointment));
        return timeBlock;
    }

    /**
     * Creates a local date from inputted data, independent of time.
     * @param year          An integer that represents the year for a time block to exist.
     * @param month         An integer that represents the month of the given year.
     * @param dayOfMonth    An integer that represents a day of a given month.
     * @return a LocalDate of the inputted data.
     */
    public LocalDate createLocalDate(Integer year, Integer month, Integer dayOfMonth){
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Creates a local time, independent of a date.
     * @param hour      an integer that represents an hour of the day.
     * @param minute    an integer that represents a minute of a given hour.
     * @param second    an integer that represents a second of a given minute.
     * @return a LocalTime from inputted data.
     */
    public LocalTime createLocalTime(Integer hour, Integer minute, Integer second){
        return LocalTime.of(hour, minute, second);
    }

    /**
     *Creates a local date time which holds both a time and a date.
     * @param year          an integer that represents the year for a time block to exist.
     * @param month         an integer that represents the month of the given year.
     * @param dayOfMonth    an integer that represents a day of a given month.
     * @param hour          an integer that represents an hour of a given day.
     * @param minute        an integer that represents a minute of a given hour.
     * @return a LocalDateTime from inputted data.
     */
    public LocalDateTime createLocalDateTime(Integer year, Integer month, Integer dayOfMonth, Integer hour,
                                             Integer minute){
        return LocalDateTime.of(year, month, dayOfMonth, hour,
                minute, 0, 0);
    }

    /**
     * gets the current local date time of the system.
     * @return a LocalDateTime from the system.
     */
    public LocalDateTime getCurrentLocalDateTime(){
        return LocalDateTime.now();
    }

    /**
     * gets the current local time of the system.
     * @return a LocalTime from the system.
     */
    public LocalTime getCurrentLocalTime(){
        return LocalTime.now();
    }

}
