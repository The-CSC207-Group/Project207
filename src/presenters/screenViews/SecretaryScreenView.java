package presenters.screenViews;

import dataBundles.*;
import presenters.entityViews.AppointmentView;
import presenters.entityViews.AvailabilityView;
import presenters.entityViews.ContactView;
import presenters.response.AppointmentTimeDetails;
import presenters.response.PasswordResetDetails;
import presenters.response.UserCredentials;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Secretary's presenter class.
 */
public class SecretaryScreenView extends UserScreenView {

    private final ContactView contactView = new ContactView();

    /**
     * Create a new patient prompt.
     * @return UserCredentials containing username and password.
     */
    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    /**
     * Show success message when patient is created successfully.
     */
    public void showRegisterPatientSuccess() {
        successMessage("Created patient account successfully!");
    }

    /**
     * Show a failed to create patient error that is thrown when username or password is in an incorrect format.
     */
    public void showIncorrectPatientFormatError() {
        errorMessage("Failed to register patient account. Make sure:" +
                "\n1. Username is 6 characters long and only contains letters and numbers." +
                "\n2. Password is 8 characters long.");
    }

    /**
     * Show a failed to create patient error that is thrown when username is already in use.
     */
    public void showPatientUsernameInUseError() {
        errorMessage("Failed to register patient account: username is already in use.");
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
        errorMessage("Error: a doctor with that username does not exist.");
    }

    public void showDoctorNoLongerExists(){
        errorMessage("The doctor with whom the appointment was scheduled is no longer a part of the clinic.");
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
        infoMessage("Booking appointment - Time:");
        Integer hour = inputInt("Enter hour (HH): ");
        if (hour == null) {return null;}
        Integer minute = inputInt("Enter minute (MM): ");
        if (minute == null) {return null;}
        Integer length = inputInt("Enter duration in minutes: ");
        if (length == null) {return null;}

        try {
            LocalTime time = LocalTime.of(hour, minute);
            return new AppointmentTimeDetails(time, length);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

    /**
     * Show error when appointment does not overlap with availability or another appointment.
     */
    public void showAppointmentBookingError() {
        errorMessage("The doctor is not available during this time.");
    }

    /**
     * Inputted date and time is in the past.
     */
    public void showDayPassedError(){errorMessage("The date and time inputted has passed.");}

    /**
     * Show message when user has no appointments.
     */
    public void showNoUserAppointmentsMessage(){
        infoMessage("This user has no appointments.");
    }

    /**
     * Doctor has no appointments scheduled.
     */
    public void showNoDoctorAppointmentsMessage(){infoMessage("No appointments have been scheduled with this doctor.");}

    /**
     * Show invalid date error when user inputs the wrong date format.
     */
    public void showInvalidDateError() {
        errorMessage("Error: invalid date.");
    }

    /**
     * Show error for incorrectly inputted time info.
     */
    public void showInvalidTimeError(){errorMessage("Error: invalid time.");}

    /**
     * Success message shown when appointment is rescheduled.
     */
    public void showRescheduleAppointmentSuccess(){successMessage("Successfully rescheduled appointment.");}

    /**
     * Error message shown when appointment can not be scheduled due to conflicts with another appointment on not
     * falling within availability.
     */
    public void showRescheduleAppointmentError(){errorMessage("Failed to reschedule appointment as the doctor is not" +
            " available during this time");}

    /**
     * Show appointment spanning multiple days error when it does so.
     */
    public void showSpanningMultipleDaysError(){
        errorMessage("Error: appointment spans multiple days.");
    }

    /**
     * Show success message when appointment is booked successfully.
     * @param patientContact Contact information of patient.
     * @param doctorContact Contact information of doctor.
     */
    public void showBookAppointmentSuccess(ContactData patientContact, ContactData doctorContact) {
        String patientName = contactView.viewName(patientContact);
        String doctorName = contactView.viewName(doctorContact);
        successMessage("Successfully booked appointment for " + patientName + " with Dr." + doctorName);
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
        infoMessage(new AppointmentView().viewFullAsEnumerationFromList(appointmentData));
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
        infoMessage(new AppointmentView().viewFullAsEnumerationFromList(appointmentData));
        return rescheduleAppointmentFromEnumerationPrompt();
    }

    /**
     * Helper method to reschedule appointment from enumeration.
     * @return an index corresponding to the selected appointment
     *         or null, if index is malformed/typed incorrectly by user.
     */
    private Integer rescheduleAppointmentFromEnumerationPrompt() {
        warningMessage("This action cannot be undone!");
        return inputInt("Input appointment number to reschedule: ") - 1;
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

    public void showCancelAppointmentSuccess(){
        successMessage("Successfully canceled appointment.");
    }

    /**
     * Message displayed when booking an appointment and showing clinic's availability on a given day, where there
     * is no availability.
     */
    public void showNoAvailabilityError(ContactData userContact){
        errorMessage("Doctor is not available on this day.");
    }

    /**
     * View appointments relating to doctor or patient.
     * @param userContact Contacting info of patient.
     * @param appointments list of appointments.
     */
    public void viewPatientAppointments(ContactData userContact, List<AppointmentData> appointments) {
        infoMessage("Viewing appointments for " + contactView.viewName(userContact) + ":");
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    /**
     * View appointments for a given doctor.
     * @param userContact ContactData - contact data for a given doctor.
     * @param appointments List<AppointmentData> list of data relating to all appointments scheduled with a doctor.
     */
    public void viewDoctorAppointments(ContactData userContact, List<AppointmentData> appointments) {
        infoMessage("Viewing appointments for Dr. " + contactView.viewName(userContact) + ":");
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    /**
     * View a doctor's availabilities.
     * @param userContact String representing the username of the doctor who the availabilities belong to.
     * @param availabilityData List<AvailabilityData> of the doctor's availabilities.
     */
    public void viewDoctorAvailability(ContactData userContact, List<AvailabilityData> availabilityData) {
        infoMessage("Viewing availabilities for Dr." + contactView.viewName(userContact) + ":");
        infoMessage(new AvailabilityView().viewFullFromList(availabilityData));
    }

    /**
     * View one of a doctor's availabilities.
     * @param userContact String representing the username of the doctor who the availability belong to.
     * @param availabilityData AvailabilityData - Availability data for a given day.
     */
    public void viewDoctorAvailability(ContactData userContact, AvailabilityData availabilityData) {
        infoMessage("Viewing availabilities for Dr." + contactView.viewName(userContact) + ":");
        infoMessage(new AvailabilityView().viewFull(availabilityData) + "\n");
    }

    /**
     * Asks user for input and gets the target doctor username.
     * @return String representing username of the doctor.
     */
    public String getTargetDoctor() {
        return input("Enter doctor username: ");
    }

    /**
     * Prompts user to add a local start time
     * @return LocalDateTime object with the desired input from the user.
     */
    public LocalDateTime addLocalDateTimeStart() {
        infoMessage("You are about to add the START time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        return LocalDateTime.of(localDate, localTime);
    }

    /**
     * Prompts user to add a local end time
     * @return LocalDateTime object with the desired input from the user.
     */
    public LocalDateTime addLocalDateTimeEnd() {
        infoMessage("You are about to add the END time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        return LocalDateTime.of(localDate, localTime);
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