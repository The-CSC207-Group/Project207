package presenter.screenViews;

import dataBundles.AppointmentData;
import dataBundles.PrescriptionData;
import dataBundles.ReportData;
import entities.Patient;
import entities.Prescription;
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

    public Integer deletePrescriptionPrompt(List<PrescriptionData> prescriptionData) {
        PrescriptionView prescriptionView = new PrescriptionView();
        for (int i = 1; i <= prescriptionData.size(); i++) {
            infoMessage(i + ":");
            prescriptionView.viewFull(prescriptionData.get(i - 1));
        }

        warningMessage("This action cannot be undone!");
        try {
            return inputInt("Input prescription number to delete: ");
        } catch (InputMismatchException e) {
            return null;
        }
    }

    public void showDeletePrescriptionError() {
        errorMessage("Could not delete prescription.");
    }

    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        return new PrescriptionDetails(header, body);
    }

    public Integer deleteReportPrompt(List<ReportData> reportData) {
        ReportView reportView = new ReportView();
        for (int i = 1; i <= reportData.size(); i++) {
            infoMessage(i + ":");
            reportView.viewFull(reportData.get(i - 1));
        }

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
