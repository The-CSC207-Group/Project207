package presenter.screenViews;

import dataBundles.ContactData;
import presenter.entityViews.ContactView;
import presenter.response.AppointmentPatientDoctorDetails;
import presenter.response.AppointmentTimeDetails;
import presenter.response.UserCredentials;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SecretaryScreenView extends UserScreenView {
    public ContactView contactView = new ContactView();

    public UserCredentials registerPatientAccount() {
        return registerAccountPrompt("patient");
    }

    public String deletePatientPrompt() {
        showIrreversibleActionWarning();
        return input("Enter patient username to delete: ");
    }

    public void showFailedToDeleteUserByUsernameError() {
        errorMessage("Failed to delete account: username does not exist");
    }

    public void showFailedToDeleteUserByAuthorityError() {
        errorMessage("Failed to delete account: can only delete users of type patient");
    }

    public void showDeletePatientSuccess() {
        successMessage("Successfully deleted patient account!");
    }

    public AppointmentPatientDoctorDetails bookAppointmentPatientDoctorPrompt() {
        String patientUsername = enterUsernamePrompt("patient");
        String doctorUsername = enterUsernamePrompt("doctor");
        return new AppointmentPatientDoctorDetails(patientUsername, doctorUsername);
    }

    public void showPatientDoesNotExistError() {
        errorMessage("Appointment booking error: a patient with that username does not exist.");
    }

    public void showDoctorDoesNotExistError() {
        errorMessage("Appointment booking error: a doctor with that username does not exist.");
    }

    public LocalDate bookAppointmentDayPrompt() {
        LocalDate appointmentDate = showLocalDatePrompt();
        if (appointmentDate == null) {
            return null;  // Invalid date
        }
        return appointmentDate;
    }

    public void showNoAvailableAppointmentDayError() {
        errorMessage("Appointment booking error: there are no available appointments on that day.");
    }

    public AppointmentTimeDetails bookAppointmentTimePrompt() {
        Integer hour = inputInt("Enter your desired hour (HH): ");
        if (hour == null) {return null;}
        Integer minute = inputInt("Enter your desired minute (MM): ");
        if (minute == null) {return null;}
        Integer length = inputInt("Enter your desired length in minutes: ");
        if (length == null) {return null;}

        try {
            LocalTime time = LocalTime.of(hour, minute);
            return new AppointmentTimeDetails(time, length);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

    public void showAppointmentConflictError() {
        errorMessage("Appointment booking error: time period unavailability.");
    }

    public void showInvalidDateError() {
        errorMessage("Appointment booking error: invalid date.");
    }

    public void showBookAppointmentSuccess(ContactData patientContact, ContactData doctorContact) {
        String patientName = contactView.viewName(patientContact);
        String doctorName = contactView.viewName(doctorContact);
        successMessage("Successfully booked appointment for " + patientName + "with " + doctorName);
    }
}
