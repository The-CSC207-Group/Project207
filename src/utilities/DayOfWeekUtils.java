package utilities;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;

public class DayOfWeekUtils {

    /**
     * Converts day of week enum to a string. Ex: MONDAY -> "Monday".
     * @param dayOfWeek DayOfWeek - Enum representing a day of the week.
     * @return String - string representation of the day of week associated with the dayOfWeek variable.
     */
    public String dayOfWeekToString(DayOfWeek dayOfWeek){
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
}
