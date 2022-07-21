package presenter.screenViews;

import dataBundles.AppointmentData;
import dataBundles.PrescriptionData;
import presenter.entityViews.AppointmentView;
import presenter.entityViews.PrescriptionView;

import java.util.List;

public class PatientScreenView extends UserScreenView {

    PrescriptionView prescriptionView = new PrescriptionView();
    AppointmentView appointmentView = new AppointmentView();

    /**
     * Shows a list of prescriptions relating to patients.
     * @param items list of prescriptions.
     * @param details whether to show the prescription in full (body, expiration ...)
     */
    private void viewPrescriptions(List<PrescriptionData> items, boolean details) {
        String output;
        if (details) {
            output = prescriptionView.viewFullFromList(items);
        } else {
            output = prescriptionView.viewFromList(items, prescriptionView::viewHeader);
        }
        infoMessage(output);
    }

    /**
     * View active prescriptions of patient. i.e. unexpired prescriptions.
     * @param items list of prescriptions.
     * @param details whether to show the prescription in full (body, expiration ...)
     */
    public void viewActivePrescriptions(List<PrescriptionData> items, boolean details) {
        infoMessage("List of active prescriptions:");
        viewPrescriptions(items, details);
    }

    /**
     * View prescription history of patient. i.e. all prescriptions prescribed to the patient.
     * @param items list of prescriptions.
     * @param details whether to show the prescription in full (body, expiration ...)
     */
    public void viewPrescriptionHistory(List<PrescriptionData> items, boolean details) {
        infoMessage("Prescription History:");
        viewPrescriptions(items, details);
    }

    /**
     * View all appointments of a patient.
     * @param items list of appointments.
     */
    public void viewAppointments(List<AppointmentData> items) {
        String output = appointmentView.viewFullFromList(items);
        infoMessage("Booked Appointments:");
        infoMessage(output);
    }

}
