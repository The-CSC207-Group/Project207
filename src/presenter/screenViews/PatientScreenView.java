package presenter.screenViews;

import dataBundles.PrescriptionData;
import presenter.entityViews.PrescriptionView;

import java.util.List;

public class PatientScreenView extends UserScreenView {

//    public  bookAppointmentPrompt {}

    private void viewPrescriptions(List<PrescriptionData> items, boolean details) {
        String output;
        PrescriptionView prescriptionView = new PrescriptionView();
        if (details) {
            output = prescriptionView.viewFullFromList(items);
        } else {
            output = prescriptionView.viewFromList(items, prescriptionView::viewHeader);
        }
        infoMessage(output);
    }

    public void viewActivePrescriptions(List<PrescriptionData> items, boolean details) {
        infoMessage("List of active prescriptions:");
        viewPrescriptions(items, details);
    }

    public void viewPrescriptionHistory(List<PrescriptionData> items, boolean details) {
        infoMessage("Prescription History:");
        viewPrescriptions(items, details);
    }
}
