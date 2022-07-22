package presenter.response;

import java.util.Objects;

/**
 * An appointment's patient and doctor details as a response.
 */
public final class AppointmentPatientDoctorDetails {

    private final String patientUsername;
    private final String doctorUsername;

    /**
     * Creates an instance of AppointmentPatientDoctorDetails.
     * @param patientUsername String representing the appointment's patient's username.
     * @param doctorUsername String representing the appointment's doctor's username.
     */
    public AppointmentPatientDoctorDetails(String patientUsername, String doctorUsername) {
        this.patientUsername = patientUsername;
        this.doctorUsername = doctorUsername;
    }

    /**
     * @return String representing the appointment's patient's username.
     */
    public String patientUsername() {
        return patientUsername;
    }

    /**
     * @return String representing the appointment's doctor's username.
     */
    public String doctorUsername() {
        return doctorUsername;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of AppointmentPatientDoctorDetails.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AppointmentPatientDoctorDetails) obj;
        return Objects.equals(this.patientUsername, that.patientUsername) &&
                Objects.equals(this.doctorUsername, that.doctorUsername);
    }

    /**
     * @return int representing the hash code value for this instance of AppointmentPatientDoctorDetails.
     */
    @Override
    public int hashCode() {
        return Objects.hash(patientUsername, doctorUsername);
    }

    /**
     * @return String representing the string representation of this instance of AppointmentPatientDoctorDetails.
     */
    @Override
    public String toString() {
        return "AppointmentPatientDoctorDetails[" +
                "patientUsername=" + patientUsername + ", " +
                "doctorUsername=" + doctorUsername + ']';
    }

}
