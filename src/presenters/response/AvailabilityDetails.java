package presenters.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilityDetails {
    DayOfWeek dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;

    public AvailabilityDetails(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime){
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
