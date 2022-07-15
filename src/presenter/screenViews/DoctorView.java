package presenter.screenViews;

import entities.Appointment;
import presenter.entityViews.AppointmentView;

import java.util.List;

public class DoctorView extends View {
    public void viewAppointments(List<Appointment> appointments) {
        infoMessage(new AppointmentView().viewFromList(appointments));
    }
}
