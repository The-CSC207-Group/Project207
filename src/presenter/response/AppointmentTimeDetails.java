package presenter.response;

import java.util.Objects;

public final class AppointmentTimeDetails {
    private final Integer hour;
    private final Integer minute;
    private final Integer length;

    public AppointmentTimeDetails(Integer hour, Integer minute, Integer length) {
        this.hour = hour;
        this.minute = minute;
        this.length = length;
    }

    public Integer hour() {
        return hour;
    }

    public Integer minute() {
        return minute;
    }

    public Integer length() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentTimeDetails) obj;
        return Objects.equals(this.hour, that.hour) &&
                Objects.equals(this.minute, that.minute) &&
                Objects.equals(this.length, that.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, length);
    }

    @Override
    public String toString() {
        return "AppointmentTimeDetails[" +
                "hour=" + hour + ", " +
                "minute=" + minute + ", " +
                "length=" + length + ']';
    }

}
