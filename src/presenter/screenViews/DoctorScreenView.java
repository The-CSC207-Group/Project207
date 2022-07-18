package presenter.screenViews;

import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.ContactView;
import presenter.entityViews.ReportView;
import presenter.response.PrescriptionDetails;
import presenter.response.ReportDetails;
import presenter.screenViews.common.PrescriptionScreenView;

import java.util.InputMismatchException;
import java.util.List;

public class DoctorScreenView extends UserScreenView {
    PrescriptionScreenView prescriptionScreenView = new PrescriptionScreenView();
    ContactView contactView = new ContactView();

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
    public Integer deletePatientPrescriptionPrompt(ContactData patientContact, List<PrescriptionData> prescriptionData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " reports to delete:");
        return prescriptionScreenView.deletePrescriptionPrompt(prescriptionData);
    }

    /***
     * Error raised when the user inputted integer is outside the size of the given data bundle list
     */
    public void showDeletePrescriptionOutOfRangeError() {
        errorMessage("Could not delete prescription: index out of range.");
    }

    /***
     * Error raised when the user input is not an integer.
     */
    public void showDeletePrescriptionNotAnIntegerError() {
        errorMessage("Could not delete prescription: please input a valid integer.");
    }

    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        return new PrescriptionDetails(header, body);
    }

    /**
     * View used to delete reports relating to a patient. Show an enumeration of all reports and ask user for
     * integer input corresponding to a selection.
     * @param patientContact contact information of patient.
     * @param reportData a list of reportData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deletePatientReportPrompt(ContactData patientContact, List<ReportData> reportData) {
        String patientName = contactView.viewName(patientContact);
        infoMessage("Viewing patient " + patientName + " reports to delete:");

        // TODO create common class for reports? idk. will see.
        new ReportView().viewFullAsEnumerationFromList(reportData);
        warningMessage("This action cannot be undone!");
        try {
            return inputInt("Input report number to delete: ");
        } catch (InputMismatchException e) {
            return null;
        }
    }

    public void showDeleteReportError() {
        errorMessage("Could not delete report.");
    }

    public ReportDetails reportDetailsPrompt() {
        String header = input("Enter your report header: ");
        String body = input("Enter your report body: ");
        return new ReportDetails(header, body);
    }
}
