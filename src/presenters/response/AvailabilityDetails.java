package presenters.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * An availability's details as a response.
 */
public final class AvailabilityDetails {
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Constructor for the availability response.
     *
     * @param dayOfWeek DayOfWeek - The day of week of the availability.
     * @param startTime LocalDateTime - The start time of the availability.
     * @param endTime   LocalDateTime - The end time of the availability.
     */
    public AvailabilityDetails(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Return Day of week contained within the response.
     *
     * @return DayOfWeek - day of week of the availability.
     */
    public DayOfWeek dayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Return start time contained within the response.
     *
     * @return LocalTime - start time of the availability.
     */
    public LocalTime startTime() {
        return startTime;
    }

    /**
     * Return end time contained within the response.
     *
     * @return LocalTime - end time of the availability.
     */

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