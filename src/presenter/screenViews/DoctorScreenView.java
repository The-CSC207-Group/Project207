package presenter.screenViews;

import dataBundles.AppointmentData;
import presenter.entityViews.AppointmentView;
import presenter.response.PrescriptionDetails;
import presenter.response.ReportDetails;

import java.util.List;

public class DoctorScreenView extends UserScreenView {

    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        return new PrescriptionDetails(header, body);
    }

    public ReportDetails reportDetailsPrompt() {
        String header = input("Enter your report header: ");
        String body = input("Enter your report body: ");
        return new ReportDetails(header, body);
    }
}
