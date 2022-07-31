package presenters.screenViews;

import dataBundles.*;
import entities.Report;
import presenters.entityViews.*;
import presenters.response.PrescriptionDetails;
import presenters.response.ReportDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Doctor's presenter class.
 */
public class DoctorScreenView extends UserScreenView {

    /**
     * The contact view that will be used by the doctor's presenter.
     */
    private final ContactView contactView = new ContactView();
    private final ReportView reportView = new ReportView();

    private void showDeleteOutOfRangeError(String itemType) {
        errorMessage("Could not delete " + itemType + ": index out of range.");
    }

    private void showDeleteNotAnIntegerError(String itemType) {
        errorMessage("Could not delete " + itemType + ": please input a valid integer.");
    }

    /**
     * View used to delete prescriptions relating to a patient. Show an enumeration of all prescriptions
     * and ask user for integer input corresponding to a selection.
     *
     * @param patientContact   contact information of patient.
     * @param prescriptionData a list of PrescriptionData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     * null, if and only if the user input is not an integer.
     */
    public Integer deletePrescriptionPrompt(ContactData patientContact, List<PrescriptionData> prescriptionData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " prescriptions to delete:");
        infoMessage(new PrescriptionView().viewFullAsEnumerationFromList(prescriptionData));
        return deleteItemFromEnumerationPrompt("prescription");
    }

    /**
     * Success message when doctor successfully deletes a prescription.
     */
    public void showSuccessfullyDeletedPrescription() {
        successMessage("Successfully deleted prescription.");
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data bundle list
     */
    public void showDeletePrescriptionOutOfRangeError() {
        showDeleteOutOfRangeError("prescription");
    }

    /**
     * Ask doctors for prescription details. Used when creating prescriptions.
     *
     * @return PrescriptionDetails containing header, body and expiry date of prescription.
     */
    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        infoMessage("Enter the expiry date:");
        LocalDate expiryDate = showLocalDatePrompt();
        if (expiryDate == null) {
            return null;
        }
        return new PrescriptionDetails(header, body, expiryDate);
    }

    /**
     * Success message when doctor successfully creates a prescription.
     */
    public void showSuccessfullyCreatedPrescription() {
        successMessage("Successfully created prescription.");
    }

    /**
     * Error message when the inputted expiry date is not valid.
     */
    public void showInvalidPrescriptionDateError() {
        errorMessage("Cannot create prescription: invalid expiry date.");
    }

    /**
     * Load an existing patient.
     *
     * @return patient username inputted by doctor.
     */
    public String loadPatientPrompt() {
        return enterUsernamePrompt("patient");
    }

    /**
     * Shows an error when loading a patient with a username that does not exist.
     */
    public void showErrorLoadingPatient() {
        errorMessage("Error loading patient: a patient with that username does not exist");
    }

    /**
     * Shows a success message when loading a patient.
     *
     * @param patientContact ContactData of the patient being loaded.
     */
    public void showSuccessLoadingPatient(ContactData patientContact) {
        successMessage("Success loading patient: " + contactView.viewName(patientContact));
    }

    // ALL CODE BELOW PENDING FOR PHASE 2

    /**
     * View used to view the doctor's appointments that are given.
     *
     * @param appointments List<AppointmentData> appointments to be viewed.
     */
    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }


    /**
     * Error raised when the user input is not an integer.
     */
    public void showDeletePrescriptionNotAnIntegerError() {
        showDeleteNotAnIntegerError("prescription");
    }

    /**
     * View used to delete reports relating to a patient. Show an enumeration of all reports and ask user for
     * integer input corresponding to a selection.
     *
     * @param patientContact contact information of patient.
     * @param reportData     a list of reportData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     * null, if and only if the user input is not an integer.
     */
    public Integer deleteReportPrompt(ContactData patientContact, List<ReportData> reportData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " reports to delete:");
        infoMessage(new ReportView().viewFullAsEnumerationFromList(reportData));
        return deleteItemFromEnumerationPrompt("report");
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data list
     */
    public void showDeleteReportOutOfRangeError() {
        showDeleteOutOfRangeError("report");
    }

    /**
     * Error raised when the user input is not an integer.
     */
    public void showDeleteReportNotAnIntegerError() {
        showDeleteNotAnIntegerError("report");
    }

    /**
     * Ask doctors for report details. Used when creating a report.
     *
     * @return ReportDetails containing header and body of report.
     */
    public ReportDetails reportDetailsPrompt() {
        String header = input("Enter your report header: ");
        String body = input("Enter your report body: ");
        return new ReportDetails(header, body);
    }

    /**
     * View doctor's schedule on a specific day.
     *
     * @return Return the day inputted.
     */
    public LocalDate viewSchedulePrompt() {
        infoMessage("What day's schedule would you like to view?");
        return showLocalDatePrompt();
    }

    /**
     * Ask doctors for availability details. Used when creating a new availability time slot.
     *
     * @return ArrayList<Integer> containing the day of the week as an Integer, the starting hour, starting minute and
     * length of the availability time slot.
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
     * View used to delete a doctor's availabilities. Show an enumeration of all availabilities and ask doctor for
     * integer input corresponding to a selection.
     *
     * @param doctorData       ContactData of doctor.
     * @param availabilityData List<AvailabilityData> of availability data to display.
     * @return Integer representing the selected AvailabilityData from the list. null, if and only if the user input
     * is not an integer.
     */
    public Integer deleteAvailabilityPrompt(ContactData doctorData, List<AvailabilityData> availabilityData) {
        String doctorName = contactView.viewName(doctorData);
        infoMessage("Viewing doctor " + doctorName + " availabilities to delete:");
        new AvailabilityView().viewFullAsEnumerationFromList(availabilityData);
        return deleteItemFromEnumerationPrompt("availability");
    }

    /**
     * Success message when doctor successfully deletes an availability.
     */
    public void showSuccessfullyDeletedAvailability() {
        successMessage("Successfully deleted availability.");
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data list.
     */
    public void showDeleteAvailabilityOutOfRangeError() {
        showDeleteOutOfRangeError("availability");
    }

    /**
     * Error raised when the user input is not an integer.
     */
    public void showDeleteAvailabilityNotAnIntegerError() {
        showDeleteNotAnIntegerError("availability");
    }

    /**
     * Ask doctors for absence details. Used when creating a new absence time slot.
     *
     * @return ArrayList<Integer> containing the year, month as an integer, day of month and length of the absence in
     * days.
     */
    public ArrayList<Integer> addAbsencePrompt() {
        infoMessage("When is the first day of your absence?");
        LocalDate date = showLocalDatePrompt();
        Integer length = inputInt("How many days will you be absent?: ");
        return new ArrayList<>(Arrays.asList(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), length));
    }

    /**
     * View used to delete a doctor's absence. Show an enumeration of all absences and ask doctor for
     * integer input corresponding to a selection.
     *
     * @param doctorData    ContactData of doctor.
     * @param timeBlockData List<TimeBlockData> of time block data to display.
     * @return Integer representing the selected TimeBlockData from the list. null, if and only if the user input
     * is not an integer.
     */
    public Integer deleteAbsencePrompt(ContactData doctorData, List<TimeBlockData> timeBlockData) {
        String doctorName = contactView.viewName(doctorData);
        infoMessage("Viewing doctor " + doctorName + " absences to delete:");
        new TimeBlockView().viewFullAsEnumerationFromList(timeBlockData);
        return deleteItemFromEnumerationPrompt("absence");
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data list.
     */
    public void showDeleteAbsenceOutOfRangeError() {
        showDeleteOutOfRangeError("report");
    }

    /**
     * Error raised when the user input is not an integer.
     */
    public void showDeleteAbsenceNotAnIntegerError() {
        showDeleteNotAnIntegerError("report");
    }

    public void viewReport(ReportData report) {
        infoMessage(reportView.viewFull(report));

    }

    public void viewAllReports(ArrayList<ReportData> reportData) {
        infoMessage(reportView.viewFullAsEnumerationFromList(reportData));
    }

    public Integer viewReportPrompt() {
        return inputInt("Enter the index of the report to view in detail ");
    }

    public void noReports() {
        infoMessage("No reports to view, please add a report");
    }

}
