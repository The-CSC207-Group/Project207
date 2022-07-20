package presenter.screenViews;

import dataBundles.*;
import entities.Availability;
import presenter.entityViews.*;
import presenter.response.PrescriptionDetails;
import presenter.response.ReportDetails;

import java.lang.reflect.Array;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class DoctorScreenView extends UserScreenView {

    ContactView contactView = new ContactView();

    private void showDeleteOutOfRangeError(String itemType) {
        errorMessage("Could not delete " + itemType + ": index out of range.");
    }

    private void showDeleteNotAnIntegerError(String itemType) {
        errorMessage("Could not delete " + itemType + ": please input a valid integer.");
    }

    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    /**
     * View used to delete prescriptions relating to a patient. Show an enumeration of all prescriptions
     * and ask user for integer input corresponding to a selection.
     * @param patientContact contact information of patient.
     * @param prescriptionData a list of PrescriptionData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deletePrescriptionPrompt(ContactData patientContact, List<PrescriptionData> prescriptionData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " prescriptions to delete:");
        new PrescriptionView().viewFullAsEnumerationFromList(prescriptionData);
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
     * Error raised when the user input is not an integer.
     */
    public void showDeletePrescriptionNotAnIntegerError() {
        showDeleteNotAnIntegerError("prescription");
    }

    /**
     * Ask doctors for prescription details. Used when creating prescriptions.
     * @return PrescriptionDetails containing header, body and expiry date of prescription.
     */
    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        infoMessage("Enter the expiry date:");
        LocalDate expiryDate = showLocalDatePrompt();
        return new PrescriptionDetails(header, body, expiryDate.atStartOfDay(ZoneId.of("US/Eastern")));
    }

    /**
     * Success message when doctor successfully creates a prescription.
     */
    public void showSuccessfullyCreatedPrescription() {
        successMessage("Successfully created prescription.");
    }

    /**
     * View used to delete reports relating to a patient. Show an enumeration of all reports and ask user for
     * integer input corresponding to a selection.
     * @param patientContact contact information of patient.
     * @param reportData a list of reportData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deleteReportPrompt(ContactData patientContact, List<ReportData> reportData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " reports to delete:");
        new ReportView().viewFullAsEnumerationFromList(reportData);
        return deleteItemFromEnumerationPrompt("report");
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data bundle list
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
     * @return ReportDetails containing header and body of report.
     */
    public ReportDetails reportDetailsPrompt() {
        String header = input("Enter your report header: ");
        String body = input("Enter your report body: ");
        return new ReportDetails(header, body);
    }

    /**
     * View doctor's schedule on a specific day.
     * @return Return the day inputted.
     */
    public LocalDate viewSchedulePrompt() {
        infoMessage("What day's schedule would you like to view?");
        return showLocalDatePrompt();
    }

    public ArrayList<Integer> addAvailabilityPrompt() {
        Integer day = inputInt("Enter the day of the week you would like to add your availability " +
                "time as an integer with 1 being Monday and 7 being Sunday: ");
        Integer hour = inputInt("Enter the starting hour that you are available (HH): ");
        Integer minute = inputInt("Enter the starting minute that you are available (MM): ");
        Integer length = inputInt("Enter the length in minute that you are available: ");
        return new ArrayList<Integer>(Arrays.asList(day, hour, minute, length));
    }

    public Integer deleteAvailabilityPrompt(ContactData doctorData, List<AvailabilityData> availabilityData) {
        String doctorName = contactView.viewName(doctorData);
        infoMessage("Viewing doctor " + doctorName + " availabilities to delete:");
        new AvailabilityView().viewFullAsEnumerationFromList(availabilityData);
        return deleteItemFromEnumerationPrompt("availability");
    }

    public HashMap<ZonedDateTime, Integer> addAbsencePrompt() {
        infoMessage("When would you like to add your absence?");
        LocalDate date = showLocalDatePrompt();
        infoMessage("What time would you like to add your absence?");
        Integer hour = inputInt("Enter start hour of absence (HH): ");
        Integer minute = inputInt("Enter start minute of absence (MM): ");
        LocalTime time = LocalTime.of(hour, minute);
        ZonedDateTime zdt = ZonedDateTime.of(date, time, ZoneId.of("US/Eastern"));
        Integer length = inputInt("Enter length of absence in minutes: ");
        HashMap<ZonedDateTime, Integer> h = new HashMap<>();
        h.put(zdt, length);
        return h;
    }

    /**
     * Load an existing patient.
     * @return patient username inputted by doctor.
     */
    public String loadPatientPrompt() {
        return enterUsernamePrompt("patient");
    }

    public void showErrorLoadingPatient() {
        errorMessage("Error loading patient: a patient with that username does not exist");
    }

    public void showSuccessLoadingPatient(ContactData patientContact) {
        infoMessage("Success loading patient: " + contactView.viewName(patientContact));
    }
}
