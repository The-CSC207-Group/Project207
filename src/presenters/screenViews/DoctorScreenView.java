package presenters.screenViews;

import dataBundles.AppointmentData;
import dataBundles.ContactData;
import dataBundles.PrescriptionData;
import dataBundles.ReportData;
import presenters.entityViews.AppointmentView;
import presenters.entityViews.ContactView;
import presenters.entityViews.PrescriptionView;
import presenters.entityViews.ReportView;
import presenters.response.PrescriptionDetails;
import presenters.response.ReportDetails;

import java.time.LocalDate;
import java.util.ArrayList;
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
     * Ask doctors for prescription details. Used when creating prescriptions.
     *
     * @return PrescriptionDetails containing header, body and expiry date of prescription.
     */
    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        infoMessage("Enter the expiry date:");
        LocalDate expiryDate = showLocalDatePrompt();
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
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
     * Error message when the user inputs an invalid date.
     */
    public void showInvalidDateError() {
        errorMessage("Invalid date.");
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


    /**
     * View used to view the doctor's appointments that are given.
     *
     * @param appointments List<AppointmentData> appointments to be viewed.
     */
    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
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
     * Message showed when the doctor has no appointments scheduled.
     */
    public void showNoAppointmentsMessage() {
        infoMessage("No appointments scheduled.");
    }


    /**
     * Views report in full detail.
     *
     * @param report the report to be viewed in full detail.
     */
    public void viewReport(ReportData report, ContactData doctorContact) {
        if (doctorContact == null) {
            warningMessage("Doctor who created the report no longer exists.");
        } else {
            String doctorName = contactView.viewName(doctorContact);
            infoMessage("Report noted by Dr. " + doctorName);
        }
        infoMessage(reportView.viewFull(report));
    }

    /**
     * Displays to the user the header of each report with an index next to it.
     *
     * @param reportData An arraylist of ReportData
     */
    public void viewAllReports(ArrayList<ReportData> reportData) {
        infoMessage(reportView.viewAsEnumerationFromList(reportData, reportView::viewHeader));
    }

    /**
     * Prompts the user to view a certain report in more detail.
     *
     * @return an Integer of the chosen report to be viewed.
     */
    public Integer viewReportPrompt() {
        Integer index = inputInt("Enter the index for the report to view in more detail: ");
        if (index != null) {
            return index - 1;
        } else {
            return null;
        }
    }

    /**
     * Shows the user that an error has occurred due to no reports
     */
    public void showNoReportsError() {
        errorMessage("No reports to view, please create a report");
    }

    /**
     * Shows the user that the index they have inputted is out of range.
     */
    public void showOutOfRangeError() {
        errorMessage("You have inputted an index out of range, please try again.");
    }

    /**
     * Shows the user that the inputted "index" is not a valid integer.
     */
    public void showNotIntegerError() {
        errorMessage("Please input a valid integer");
    }

    /**
     * Shows the user that report has been successfully created.
     */
    public void showReportCreationSuccess() {
        successMessage("Successfully created patient report");
    }

    /**
     * Shows the user that report has been successfully deleted.
     */
    public void showReportDeletionSuccess() {
        successMessage("Successfully deleted patient report");
    }

    public void showNoPrescriptionError() {
        errorMessage("No prescriptions to view, please create a prescription.");
    }
}
