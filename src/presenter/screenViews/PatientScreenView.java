package presenter.screenViews;

import dataBundles.PrescriptionData;
import presenter.entityViews.PrescriptionView;

import java.util.List;

public class PatientScreenView extends UserScreenView {

//    public  bookAppointmentPrompt {}

    private void viewPrescriptions(List<PrescriptionData> items, boolean details) {
        if (details) {
            new PrescriptionView().viewFullFromList(items);
        } else {
            new PrescriptionView().viewHeaderFromList(items);
        }
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
