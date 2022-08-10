package utilities;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;

/**
 * Utility class containing universally applicable methods relating to the DayOfWeek enum.
 */
public class DayOfWeekUtils {

    /**
     * Converts day of week enum to a string. Ex: MONDAY -> "Monday".
     *
     * @param dayOfWeek DayOfWeek - Enum representing a day of the week.
     * @return String - string representation of the day of week associated with the dayOfWeek variable.
     */
    public String dayOfWeekToString(DayOfWeek dayOfWeek) {
        LinkedHashMap<DayOfWeek, String> dayMap = new LinkedHashMap<>() {{
            put(DayOfWeek.MONDAY, "Monday");
            put(DayOfWeek.TUESDAY, "Tuesday");
            put(DayOfWeek.WEDNESDAY, "Wednesday");
            put(DayOfWeek.THURSDAY, "Thursday");
            put(DayOfWeek.FRIDAY, "Friday");
            put(DayOfWeek.SATURDAY, "Saturday");
            put(DayOfWeek.SUNDAY, "Sunday");
        }};
        return dayMap.get(dayOfWeek);
    }

    /**
     * Returns an ordered hashmap from the string representation of a day of week to the enum representation.
     *
     * @return LinkedHashMap<String, DayOfWeek> - ordered hashmap where the keys are the string representation of a
     * day of week and the values are the associated DayOfWeek enums.
     */
    public LinkedHashMap<String, DayOfWeek> getDayOfWeekStringToEnumMap() {
        return new LinkedHashMap<>() {{
            put("Monday", DayOfWeek.MONDAY);
            put("Tuesday", DayOfWeek.TUESDAY);
            put("Wednesday", DayOfWeek.WEDNESDAY);
            put("Thursday", DayOfWeek.THURSDAY);
            put("Friday", DayOfWeek.FRIDAY);
            put("Saturday", DayOfWeek.SATURDAY);
            put("Sunday", DayOfWeek.SUNDAY);
        }};
    }
}
