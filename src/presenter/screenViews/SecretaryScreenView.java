package presenter.screenViews;

import dataBundles.AppointmentData;
import dataBundles.ContactData;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.ContactView;
import presenter.response.AppointmentPatientDoctorDetails;
import presenter.response.AppointmentTimeDetails;
import presenter.response.UserCredentials;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SecretaryScreenView extends UserScreenView {
    public ContactView contactView = new ContactView();

    /**
     * Create a new patient prompt.
     * @return UserCredentials containing username and password.
     */
    public UserCredentials registerPatientAccount() {
        return registerAccountPrompt("patient");
    }

    /**
     * Ask to delete patient by username.
     * @return username of patient.
     */
    public String showDeletePatientPrompt() {
        showIrreversibleActionWarning();
        return input("Enter patient username to delete: ");
    }

    /**
     * Show error when cannot delete due to username not existing in patient database.
     */
    public void showFailedToDeleteUserByUsernameError() {
        errorMessage("Failed to delete account: patient of that username does not exist");
    }

    /**
     * Show successfully deleted patient message.
     */
    public void showDeletePatientSuccess() {
        successMessage("Successfully deleted patient account!");
    }

    /**
     * Ask for doctor and patient username to book appointment.
     * @return AppointmentPatientDoctorDetails
     */
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

    public Integer deleteAppointmentPrompt(ContactData patientContact, List<AppointmentData> appointmentData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + "appointments to delete:");
        new AppointmentView().viewFullAsEnumerationFromList(appointmentData);
        return deleteItemFromEnumerationPrompt("appointment");
    }

    public Integer rescheduleAppointmentPrompt(ContactData patientContact, List<AppointmentData> appointmentData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + "appointments to reschedule:");
        new AppointmentView().viewFullAsEnumerationFromList(appointmentData);
        return rescheduleItemFromEnumerationPrompt();
    }

    private Integer rescheduleItemFromEnumerationPrompt() {
        warningMessage("This action cannot be undone!");
        return inputInt("Input appointment number to reschedule: ");
    }

    public String LoadPatientPrompt() {
        return enterUsernamePrompt("patient");
    }

    public void showErrorLoadingPatient() {
        errorMessage("Error loading patient: a patient with that username does not exist");
    }

    public void showSuccessLoadingPatient(ContactData patientContact) {
        infoMessage("Success loading patient: " + contactView.viewName(patientContact));
    }
}
