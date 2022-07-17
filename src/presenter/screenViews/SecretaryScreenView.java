package presenter.screenViews;

import presenter.response.AppointmentDayDetails;
import presenter.response.AppointmentTimeDetails;
import presenter.response.UserCredentials;

import java.time.LocalDate;

public class SecretaryScreenView extends UserScreenView {

    public UserCredentials registerPatientAccount() {
        return registerAccountPrompt("patient");
    }

    public UserCredentials registerDoctorAccount() {
        return registerAccountPrompt("doctor");
    }

    public AppointmentDayDetails bookAppointmentDayPrompt(int year, int month, int day) {
        String patientUsername = enterPatientUsernamePrompt();
        String doctorUsername = enterDoctorUsernamePrompt();
        infoMessage("Appointment booked for " + patientUsername + "with " + doctorUsername + "on " + day
                + "/" + month + "/" + year + ".");
        return new AppointmentDayDetails(patientUsername, doctorUsername, year, month, day);
    }

    public AppointmentTimeDetails bookAppointmentTimePrompt(int hour, int minute, int length) {
        infoMessage("Appointment booked at " + hour + ":" + minute + "for " + length + "minutes." );
        return new AppointmentTimeDetails(hour, minute, length);
    }
}
