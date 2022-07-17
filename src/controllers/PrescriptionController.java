package controllers;

import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import presenter.screenViews.PatientScreenView;
import useCases.accessClasses.PatientAccess;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PrescriptionController extends TerminalController {

    private PatientData patientData;
    private PatientScreenView patientScreenView = new PatientScreenView();
    private PatientController patientController;
    private PrescriptionManager prescriptionManager;

    public PrescriptionController(Context parent, PatientData patientData, PatientController patientController) {
        super(parent);
        this.patientData = patientData;
        this.patientController = patientController;
        this.prescriptionManager = new PrescriptionManager(getDatabase().getPrescriptionDatabase());
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> h = super.AllCommands();
        h.put("back", back(patientController));
        h.put("active", new ViewPrescriptions(true, false));
        h.put("all", new ViewPrescriptions(false, false));
        h.put("active detailed", new ViewPrescriptions(true, true));
        h.put("all detailed", new ViewPrescriptions(false, true));
        return h;
    }

    class ViewPrescriptions implements Command {
        private boolean active;
        private boolean detail;

        public ViewPrescriptions(boolean active, boolean detail) {
            this.active = active;
            this.detail = detail;
        }

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions;
            if (this.active) {
                prescriptions = prescriptionManager.getAllActivePrescriptions(patientData);
                patientScreenView.viewActivePrescriptions(prescriptions, this.detail);

            }
            else {
                prescriptions = prescriptionManager.getAllPrescriptions(patientData);
                patientScreenView.viewPrescriptionHistory(prescriptions, this.detail);
            }
        }
    }
}
