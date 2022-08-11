package presenters.screenViews;

import useCases.dataBundles.AppointmentData;
import useCases.dataBundles.PrescriptionData;
import presenters.entityViews.AppointmentView;
import presenters.entityViews.PrescriptionView;

import java.util.List;

/**
 * The Patient's presenter class.
 */
public class PatientScreenView extends UserScreenView {

    private final PrescriptionView prescriptionView = new PrescriptionView();
    private final AppointmentView appointmentView = new AppointmentView();

    /**
     * Shows a list of prescriptions relating to patients.
     *
     * @param items   List<PrescriptionData> of prescriptions.
     * @param details boolean indicating whether to show the prescription in full (body, expiration ...)
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
     *
     * @param items   List<PrescriptionData> of prescriptions.
     * @param details boolean indicating whether to show the prescription in full (body, expiration ...)
     */
    public void viewActivePrescriptions(List<PrescriptionData> items, boolean details) {
        infoMessage("List of active prescriptions:");
        viewPrescriptions(items, details);
    }

    /**
     * View prescription history of patient. i.e. all prescriptions prescribed to the patient.
     *
     * @param items   List<PrescriptionData> of prescriptions.
     * @param details boolean indicating whether to show the prescription in full (body, expiration ...)
     */
    public void viewPrescriptionHistory(List<PrescriptionData> items, boolean details) {
        infoMessage("Prescription History:");
        viewPrescriptions(items, details);
    }

    /**
     * View all appointments of a patient.
     *
     * @param items List<AppointmentData> of appointments.
     */
    public void viewAppointments(List<AppointmentData> items) {
        String output = appointmentView.viewFullFromList(items);
        infoMessage("Booked Appointments:");
        infoMessage(output);
    }

    /**
     * Message showed when the doctor has no appointments scheduled.
     */
    public void showNoAppointmentsMessage() {
        infoMessage("No appointments scheduled.");
    }

}
