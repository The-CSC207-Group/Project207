package entities;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class TimeBlock {

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    //private Date date; (optional, default = null)
    //private Time time; (required)
    //private Day day; (optional, represents Enum of the days the week, default = null)


    public TimeBlock(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }
    public LocalTime startTimeToLocal(){
        return startTime.toLocalTime();
    }
    public LocalTime endTimeToLocal(){
        return startTime.toLocalTime();
    }
}

