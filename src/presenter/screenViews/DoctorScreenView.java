package presenter.screenViews;

import dataBundles.AppointmentData;
import presenter.entityViews.AppointmentView;
import presenter.response.PrescriptionDetails;

import java.util.List;

public class DoctorScreenView extends ScreenView {

    public void viewAppointments(List<AppointmentData> appointments) {
        infoMessage(new AppointmentView().viewFullFromList(appointments));
    }

    public PrescriptionDetails prescriptionDetailsPrompt() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        return new PrescriptionDetails(header, body);
    }
}
