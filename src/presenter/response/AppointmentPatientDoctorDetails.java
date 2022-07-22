package presenter.response;

import java.util.Objects;

public final class AppointmentPatientDoctorDetails {

    private final String patientUsername;
    private final String doctorUsername;

    public AppointmentPatientDoctorDetails(String patientUsername, String doctorUsername) {
        this.patientUsername = patientUsername;
        this.doctorUsername = doctorUsername;
    }

    public String patientUsername() {
        return patientUsername;
    }

    public String doctorUsername() {
        return doctorUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentPatientDoctorDetails) obj;
        return Objects.equals(this.patientUsername, that.patientUsername) &&
                Objects.equals(this.doctorUsername, that.doctorUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientUsername, doctorUsername);
    }

    @Override
    public String toString() {
        return "AppointmentPatientDoctorDetails[" +
                "patientUsername=" + patientUsername + ", " +
                "doctorUsername=" + doctorUsername + ']';
    }

}
