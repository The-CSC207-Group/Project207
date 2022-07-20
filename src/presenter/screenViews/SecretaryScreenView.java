package presenter.screenViews;

import dataBundles.AppointmentData;
import dataBundles.ContactData;
import dataBundles.PrescriptionData;
import entities.Availability;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.ContactView;
import presenter.entityViews.PrescriptionView;
import presenter.response.AppointmentTimeDetails;
import presenter.response.UserCredentials;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

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
     * Ask for doctor username to book appointment.
     * @return string representing the doctors' username.
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
        successMessage("Successfully booked appointment for " + patientName + "with " + doctorName);
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
        infoMessage("Viewing patient " + patientName + "appointments to delete:");
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
        infoMessage("Viewing patient " + patientName + "appointments to reschedule:");
        new AppointmentView().viewFullAsEnumerationFromList(appointmentData);
        return rescheduleItemFromEnumerationPrompt();
    }

    /**
     * Helper method to reschedule appointment from enumeration.
     * @return an index corresponding to the selected appointment
     *         or null, if index is malformed/typed incorrectly by user.
     */
    private Integer rescheduleItemFromEnumerationPrompt() {
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
     * @param patientContact Contact information of loaded patient.
     */
    public void showSuccessLoadingPatient(ContactData patientContact) {
        infoMessage("Success loading patient: " + contactView.viewName(patientContact));
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

    public String getTargetDoctor() {
        return input("Enter doctor username: ");
    }

    public LocalTime addDoctorAvailabilityTime() {
        return LocalTime.of(inputInt("Hour: "), inputInt("Minute: "));
    }


    public Integer addDoctorAvailableLength() {
        return inputInt("Length of Available time: ");
    }

    public Integer getIndexToRemove() {
        return inputInt("Select an index to remove: ");
    }

    public void viewPrescriptionsDetailed(ArrayList<PrescriptionData> prescriptionData) {
        for (PrescriptionData data : prescriptionData) {
            prescriptionView.viewFull(data);
        }
    }

    public DayOfWeek getDay() {
        return DayOfWeek.of(inputInt("Enter Day of the week (int): "));
    }

    public void viewAvailabilityFull(ArrayList<Availability> availabilities) {
        for (int i=0; i<availabilities.size(); i++){
            infoMessage(i + ": " + availabilities.get(i));
        }
    }

    public ZonedDateTime addZoneDateTimeStart() {
        infoMessage("You are about to add the START time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        ZoneId zoneId = ZoneId.of(input("Zone ID: "));
        return ZonedDateTime.of(localDate, localTime, zoneId);
    }

    public ZonedDateTime addZoneDateTimeEnd() {
        infoMessage("You are about to add the END time: ");
        LocalDate localDate = LocalDate.of(inputInt("year: "), inputInt("month: "),
                inputInt("day: "));
        LocalTime localTime = LocalTime.MIDNIGHT;
        ZoneId zoneId = ZoneId.of(input("Zone ID: "));
        return ZonedDateTime.of(localDate, localTime, zoneId);
    }




}
