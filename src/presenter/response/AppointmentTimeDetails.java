package presenter.response;

import java.time.LocalTime;
import java.util.Objects;

public final class AppointmentTimeDetails {
    private final LocalTime time;
    private final Integer length;

    public AppointmentTimeDetails(LocalTime time, Integer length) {
        this.time = time;
        this.length = length;
    }

    public LocalTime time() {
        return time;
    }

    public Integer length() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentTimeDetails) obj;
        return Objects.equals(this.time, that.time) &&
                Objects.equals(this.length, that.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, length);
    }

    @Override
    public String toString() {
        return "AppointmentTimeDetails[" +
                "time=" + time + ", " +
                "length=" + length + ']';
    }

}
