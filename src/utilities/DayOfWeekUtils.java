package utilities;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;

public class DayOfWeekUtils {


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
