package controllers.common;

import controllers.Command;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.Database;
import presenter.screenViews.PatientScreenView;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PrescriptionListCommands {

    private final PatientData patientData;
    private final PatientScreenView patientScreenView = new PatientScreenView();
    private final PrescriptionManager prescriptionManager;

    public PrescriptionListCommands(Database database, PatientData patientData) {
        this.patientData = patientData;
        this.prescriptionManager = new PrescriptionManager(database);
    }

    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("active prescriptions", new ViewPrescriptions(true, false));
        commands.put("all prescriptions", new ViewPrescriptions(false, false));
        commands.put("active prescriptions detailed", new ViewPrescriptions(true, true));
        commands.put("all prescriptions detailed", new ViewPrescriptions(false, true));
        return commands;
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
