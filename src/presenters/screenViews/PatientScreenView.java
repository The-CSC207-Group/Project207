package presenters.screenViews;

import dataBundles.AppointmentData;
import dataBundles.PrescriptionData;
import presenters.entityViews.AppointmentView;
import presenters.entityViews.PrescriptionView;

import java.util.List;

/**
 * The Patient's presenter class.
 */
public class PatientScreenView extends UserScreenView {

    /**
     * PrescriptionView that will be used by the patient's presenter.
     */
    PrescriptionView prescriptionView = new PrescriptionView();

    /**
     * AppointmentView that will be used by the patient's presenter.
     */
    AppointmentView appointmentView = new AppointmentView();

    /**
     * Shows a list of prescriptions relating to patients.
     * @param items List<PrescriptionData> of prescriptions.
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
     * @param items List<PrescriptionData> of prescriptions.
     * @param details boolean indicating whether to show the prescription in full (body, expiration ...)
     */
    public void viewActivePrescriptions(List<PrescriptionData> items, boolean details) {
        infoMessage("List of active prescriptions:");
        viewPrescriptions(items, details);
    }

    /**
     * View prescription history of patient. i.e. all prescriptions prescribed to the patient.
     * @param items List<PrescriptionData> of prescriptions.
     * @param details boolean indicating whether to show the prescription in full (body, expiration ...)
     */
    public void viewPrescriptionHistory(List<PrescriptionData> items, boolean details) {
        infoMessage("Prescription History:");
        viewPrescriptions(items, details);
    }

    // ALL METHODS BELOW ARE PENDING PHASE 2 IMPLEMENTATION

    /**
     * View all appointments of a patient.
     * @param items List<AppointmentData> of appointments.
     */
    public void viewAppointments(List<AppointmentData> items) {
        String output = appointmentView.viewFullFromList(items);
        infoMessage("Booked Appointments:");
        infoMessage(output);
    }

}
