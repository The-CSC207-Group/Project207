package presenters.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public final class AvailabilityDetails {
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public AvailabilityDetails(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek dayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime startTime() {
        return startTime;
    }

    public LocalTime endTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AvailabilityDetails) obj;
        return Objects.equals(this.dayOfWeek, that.dayOfWeek) &&
                Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, startTime, endTime);
    }

    @Override
    public String toString() {
        return "AvailabilityDetails[" +
                "dayOfWeek=" + dayOfWeek + ", " +
                "startTime=" + startTime + ", " +
                "endTime=" + endTime + ']';
    }
}