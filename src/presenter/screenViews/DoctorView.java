package presenter.screenViews;

import entities.Appointment;
import presenter.entityViews.AppointmentView;
import presenter.response.PrescriptionDetails;

import java.util.List;

public class DoctorView extends ScreenView {

    public void viewAppointments(List<Appointment> appointments) {
        infoMessage(new AppointmentView().viewFromList(appointments));
    }

    public PrescriptionDetails askForPrescriptionDetails() {
        String header = input("Enter your prescription header: ");
        String body = input("Enter your prescription body: ");
        return new PrescriptionDetails(header, body);
    }
}
