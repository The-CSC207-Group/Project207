package presenter.screenViews;

import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.AvailabilityView;
import presenter.entityViews.ContactView;
import presenter.entityViews.PrescriptionView;
import presenter.response.AppointmentTimeDetails;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Secretary's presenter class.
 */
public class SecretaryScreenView extends UserScreenView {

    public ContactView contactView = new ContactView();
    public PrescriptionView prescriptionView = new PrescriptionView();

    /**
     * Create a new patient prompt.
     * @return UserCredentials containing username and password.
     */
    public UserCredentials registerPatientAccount() {
        return registerAccountPrompt("patient");
    }

    /**
     * Show success message when patient is created successfully.
     */
    public void showRegisterPatientSuccess() {
        successMessage("Patient create successfully!");
    }

    /**
     * Show error message when patient cannot be created due to non-unique username.
     */
    public void showRegisterPatientError() {
        errorMessage("Could not create patient: a user with this username already exists");
    }

    /**
     * Show secretary a password reset prompt for a patient with a confirmation.
     * @return PasswordResetDetails containing new password and confirmed new password.
     */
    public PasswordResetDetails resetPatientPasswordPrompt() {
        infoMessage("You are about to reset the patient's password.");
        String username = input("Enter new password: ");
        String password = input("Confirm new password: ");
        return new PasswordResetDetails(username, password);
    }

    /**
     * Ask to delete patient by username.
     * @return String representing the username of patient.
     */
    public String showDeletePatientPrompt() {
        showIrreversibleActionWarning();
        return input("Enter patient username to delete: ");
    }

    /**
     * Show error when cannot delete due to username not existing in patient database.
     */
    public void showFailedToDeletePatientError() {
        errorMessage("Failed to delete account: patient of that username does not exist");
    }

    /**
     * Show successfully deleted patient message.
     */
    public void showDeletePatientSuccess() {
        successMessage("Successfully deleted patient account!");
    }

    /**
     * Load a patient from secretary.
     * @return username of patient.
     */
    public String loadPatientPrompt() {
        return enterUsernamePrompt("patient");
    }

    /**
     * Error displayed when patient cannot be loaded due to username not existing.
     */
    public void showErrorLoadingPatient() {
        errorMessage("Error loading patient: a patient with that username does not exist");
    }

    /**
     * Show success message if loaded patient.
     * @param patientData Data of the patient.
     */
    public void showSuccessLoadingPatient(PatientData patientData) {
        successMessage("Success loading patient: " + patientData.getUsername());
    }

    // ALL METHODS BELOW ARE PENDING PHASE 2 IMPLEMENTATION

    /**
     * Ask for doctor username to book appointment.
     * @return String representing the doctor's username.
     */
    public String bookAppointmentDoctorPrompt() {
        return enterUsernamePrompt("doctor");
    }

    /**
     * Show doctor does not exist error when booking appointment.
     */
    public void showDoctorDoesNotExistError() {
        errorMessage("Appointment booking error: a doctor with that username does not exist.");
    }

    /**
     * Show book appointment day prompt
     * @return LocalDate if inputted date is valid
     *         null if inputted date is invalid
     */
    public LocalDate bookAppointmentDayPrompt() {
        infoMessage("Booking appointment day:");
        return showLocalDatePrompt();
    }

    /**
     * Show error when they are no available appointments on that day.
     */
    public void showNoAvailableAppointmentDayError() {
        errorMessage("Appointment booking error: there are no available appointments on that day.");
    }

    /**
     * Ask user for the appointment time and duration.
     * @return AppointmentTimeDetails containing time and length of appointment.
     */
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

    /**
     * Show error when appointment overlaps with absence or unavailability or another appointment.
     */
    public void showAppointmentConflictError() {
        errorMessage("Appointment booking error: time period unavailability.");
    }

    /**
     * Show invalid date error when user inputs the wrong date format.
     */
    public void showInvalidDateError() {
        errorMessage("Appointment booking error: invalid date.");
    }

    /**
     * Show success message when appointment is booked successfully.
     * @param patientContact Contact information of patient.
     * @param doctorContact Contact information of doctor.
     */
    public void showBookAppointmentSuccess(ContactData patientContact, ContactData doctorContact) {
        String patientName = contactView.viewName(patientContact);
        String doctorName = contactView.viewName(doctorContact);
        successMessage("Successfully booked appointment for " + patientName + " with " + doctorName);
    }

    /**
     * Delete appointment from a patient.
     * @param patientContact Contact information of patient.
     * @param appointmentData List of appointments the patient has.
     * @return the index of the appointment to delete.
     *         or null, if the index user inputs is malformed.
     */
    public Integer deleteAppointmentPrompt(ContactData patientContact, List<AppointmentData> appointmentData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " appointments to delete:");
        new AppointmentView().viewFullAsEnumerationFromList(appointmentData);
        return deleteItemFromEnumerationPrompt("appointment");
    }

    /**
     * Reschedule an existing appointment enumeration
     * @param patientContact the patient contact information
     * @param appointmentData the list of appointments of that patient
     * @return an index corresponding to the selected appointment
     *         or null, if index is malformed/typed incorrectly by user.
     */
    public Integer rescheduleAppointmentPrompt(ContactData patientContact, List<AppointmentData> appointmentData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " appointments to reschedule:");
        new AppointmentView().viewFullAsEnumerationFromList(appointmentData);
        return rescheduleAppointmentFromEnumerationPrompt();
    }

    /**
     * Helper method to reschedule appointment from enumeration.
     * @return an index corresponding to the selected appointment
     *         or null, if index is malformed/typed incorrectly by user.
     */
    private Integer rescheduleAppointmentFromEnumerationPrompt() {
        warningMessage("This action cannot be undone!");
        return inputInt("Input appointment number to reschedule: ");
    }

    /**
     * Error displayed when user inputs integer that is out of range of appointment list
     */
    public void showRescheduleOutOfRangeError() {
        errorMessage("Could not reschedule appointment: index out of range.");
    }

    /**
     * Error displayed when user does not input a valid integer when rescheduling an appointment
     */
    public void showRescheduleNotAnIntegerError(String itemType) {
        errorMessage("Could not reschedule appointment: please input a valid integer.");
    }

    /**
     * Error displayed when user inputs integer that is out of range of appointment list
     */
    public void showDeleteOutOfRangeError() {
        errorMessage("Could not delete appointment: index out of range.");
    }

    /**
     * Error displayed when user does not input a valid integer when deleting an appointment
     */
    public void showDeleteNotAnIntegerError(String itemType) {
        errorMessage("Could not delete appointment: please input a valid integer.");
    }

    /**
     * View appointments relating to doctor or patient.
     * @param userContact Contacting info of patient.
     * @param appointments list of appointments.
     */
    public void viewAppointments(ContactData userContact, List<AppointmentData> appointments) {
        infoMessage("Viewing appointments for " + contactView.viewName(userContact) + ":");
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    /**
     * View a doctor's availabilities
     * @param doctorUsername String representing the username of the doctor who the availabilities belong to.
     * @param availabilityData List<AvailabilityData> of the doctor's availabilities.
     */
    public void viewDoctorAvailability(String doctorUsername, List<AvailabilityData> availabilityData) {
        infoMessage("Viewing availabilities for Dr." + doctorUsername + ":");
        infoMessage(new AvailabilityView().viewFullFromList(availabilityData));
    }

    /**
     * Asks user for input and gets the target doctor username.
     * @return String representing username of the doctor.
     */
    public String getTargetDoctor() {
        return input("Enter doctor username: ");
    }

    /**
     * Prompts user to add a timezone, for START time
     * @return ZonedDateTime object with the desired input from the user.
     */
    public ZonedDateTime addZoneDateTimeStart() {
        infoMessage("You are about to add the START time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        ZoneId zoneId = ZoneId.of(input("Zone ID: "));
        return ZonedDateTime.of(localDate, localTime, zoneId);
    }

    /**
     * Prompts user to add a timezone for END time
     * @return ZonedDateTime object with the desired input from the user.
     */
    public ZonedDateTime addZoneDateTimeEnd() {
        infoMessage("You are about to add the END time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        ZoneId zoneId = ZoneId.of(input("Zone ID: "));
        return ZonedDateTime.of(localDate, localTime, zoneId);
    }

    /**
     * Prompts user to add availability
     * @return ArrayList<Integer> consisting day, hour, minute, and length.
     */
    public ArrayList<Integer> addAvailabilityPrompt() {
        Integer day = inputInt("Enter the day of the week you would like to add your availability " +
                "time as an integer with 1 being Monday and 7 being Sunday: ");
        Integer hour = inputInt("Enter the starting hour that you are available (HH): ");
        Integer minute = inputInt("Enter the starting minute that you are available (MM): ");
        Integer length = inputInt("Enter the length in minute that you are available: ");
        return new ArrayList<>(Arrays.asList(day, hour, minute, length));
    }

    /**
     * Displays prompt for the user to select an index of availability to delete.
     * @param contactData ContactData for the desired doctor.
     * @param availabilityData List<AvailabilityData> representing the doctor's availabilities.
     * @return Integer representing the index of the desired availability to be deleted.
     */
    public Integer deleteAvailabilityPrompt(ContactData contactData, List<AvailabilityData> availabilityData) {
        String doctorName = contactView.viewName(contactData);
        infoMessage("Viewing doctor " + doctorName + " availabilities to delete:");
        new AvailabilityView().viewFullAsEnumerationFromList(availabilityData);
        return deleteItemFromEnumerationPrompt("availability");
    }

}