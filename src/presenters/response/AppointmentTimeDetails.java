package presenters.response;

import java.time.LocalTime;
import java.util.Objects;

/**
 * An appointment's time details as a response.
 */
public final class AppointmentTimeDetails {
    private final LocalTime time;
    private final Integer length;

    /**
     * Creates an instance of AppointmentTimeDetails.
     *
     * @param time   LocalTime representing the time of the appointment.
     * @param length Integer representing the length of the appointment in minutes.
     */
    public AppointmentTimeDetails(LocalTime time, Integer length) {
        this.time = time;
        this.length = length;
    }

    /**
     * @return LocalTime representing the time of this appointment.
     */
    public LocalTime time() {
        return time;
    }

    /**
     * @return Integer representing the length of this appointment.
     */
    public Integer length() {
        return length;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of AppointmentTimeDetails.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentTimeDetails) obj;
        return Objects.equals(this.time, that.time) &&
                Objects.equals(this.length, that.length);
    }

    /**
     * @return int representing the hash code value for this instance of AppointmentTimeDetails.
     */
    @Override
    public int hashCode() {
        return Objects.hash(time, length);
    }

    /**
     * @return String representation of this instance of AppointmentTimeDetails.
     */
    @Override
    public String toString() {
        return "AppointmentTimeDetails[" +
                "time=" + time + ", " +
                "length=" + length + ']';
    }

}
