package presenter.response;

import java.time.LocalDate;
import java.util.Objects;

public final class AppointmentDayDetails {
    private final String patientUsername;
    private final String doctorUsername;
    private final Integer year;
    private final Integer month;
    private final Integer day;

    public AppointmentDayDetails(String patientUsername, String doctorUsername, Integer year, Integer month,
                                 Integer day) {
        this.patientUsername = patientUsername;
        this.doctorUsername = doctorUsername;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String patientUsername() {
        return patientUsername;
    }

    public String doctorUsername() {
        return doctorUsername;
    }

    public Integer year() {
        return year;
    }

    public Integer month() {
        return month;
    }

    public Integer day() {
        return day;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentDayDetails) obj;
        return Objects.equals(this.patientUsername, that.patientUsername) &&
                Objects.equals(this.doctorUsername, that.doctorUsername) &&
                Objects.equals(this.year, that.year) &&
                Objects.equals(this.month, that.month) &&
                Objects.equals(this.day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientUsername, doctorUsername, year, month, day);
    }

    @Override
    public String toString() {
        return "AppointmentDayDetails[" +
                "patientUsername=" + patientUsername + ", " +
                "doctorUsername=" + doctorUsername + ", " +
                "year=" + year + ", " +
                "month=" + month + ", " +
                "day=" + day + ']';
    }
}
