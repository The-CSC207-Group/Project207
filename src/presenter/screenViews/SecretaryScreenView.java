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
        String year = input("Enter the year of your desired appointment (YYYY): ");
        String month = input("Enter the month of your desired appointment (MM): ");
        String day = input("Enter the day of your desired appointment (DD): ");
        infoMessage("Appointment booked for " + patientUsername + "with " + doctorUsername + "on " + day
                + "/" + month + "/" + year + ".");
        return new AppointmentDayDetails(patientUsername, doctorUsername, Integer.valueOf(year),
                Integer.valueOf(month), Integer.valueOf(day));
    }

    public AppointmentTimeDetails bookAppointmentTimePrompt() {
        String hour = input("Enter the hour of your desired appointment (HH): ");
        String minute = input("Enter the minute of your desired appointment (MM): ");
        String length = input("Enter the length of your desired appointment in minutes: ");
        infoMessage("Appointment booked at " + hour + ":" + minute + "for " + length + "minutes." );
        return new AppointmentTimeDetails(Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(length));
    }

    public String enterPatientUsernamePrompt() {
        return enterUsernamePrompt("patient");
    }

    public String enterDoctorUsernamePrompt() {
        return enterUsernamePrompt("doctor");
    }
}
