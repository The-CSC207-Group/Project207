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

    public AppointmentDayDetails bookAppointmentDayPrompt() {
        String patientUsername = enterUsernamePrompt("patient");
        String doctorUsername = enterUsernamePrompt("doctor");
        String year = enterYearPrompt();
        String month = enterMonthPrompt();
        String day = enterDayPrompt();
        infoMessage("Appointment booked for " + patientUsername + "with " + doctorUsername + "on " + day
                + "/" + month + "/" + year + ".");
        return new AppointmentDayDetails(patientUsername, doctorUsername, Integer.valueOf(year),
                Integer.valueOf(month), Integer.valueOf(day));
    }

    public AppointmentTimeDetails bookAppointmentTimePrompt() {
        String hour = enterHourPrompt();
        String minute = enterMinutePrompt();
        String length = enterLengthPrompt();
        infoMessage("Appointment booked at " + hour + ":" + minute + "for " + length + "minutes." );
        return new AppointmentTimeDetails(Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(length));
    }
}
