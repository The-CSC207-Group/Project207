package presenter.screenViews;

import dataBundles.AppointmentData;
import dataBundles.PrescriptionData;
import dataBundles.ReportData;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.PrescriptionView;
import presenter.entityViews.ReportView;
import presenter.response.PrescriptionDetails;
import presenter.response.ReportDetails;

import java.util.InputMismatchException;
import java.util.List;

public class DoctorScreenView extends UserScreenView {

    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    /**
     * Delete prescription view. Show an enumeration of all prescription and ask user for integer input corresponding
     * to a selection.
     * @param prescriptionData a list of PrescriptionData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deletePrescriptionPrompt(List<PrescriptionData> prescriptionData) {
        new PrescriptionView().viewFullAsEnumerationFromList(prescriptionData);
        warningMessage("This action cannot be undone!");
        try {
            return inputInt("Input prescription number to delete: ");
        } catch (InputMismatchException e) {
            return null;
        }
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
     * Delete report view. Show an enumeration of all reports and ask user for integer input corresponding
     * to a selection.
     * @param reportData a list of reportData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deleteReportPrompt(List<ReportData> reportData) {
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
