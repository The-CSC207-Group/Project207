package presenter.screenViews;

import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.ContactView;
import presenter.entityViews.PrescriptionView;
import presenter.entityViews.ReportView;
import presenter.response.PrescriptionDetails;
import presenter.response.ReportDetails;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public LocalDate viewSchedulePrompt() {
        infoMessage("What day's schedule would you like to view?");
        return showLocalDatePrompt();
    }

    public String LoadPatientPrompt() {
        return enterUsernamePrompt("patient");
    }
}
