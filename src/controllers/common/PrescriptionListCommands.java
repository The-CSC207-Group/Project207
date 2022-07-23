package controllers.common;

import controllers.Command;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.Database;
import presenter.screenViews.PatientScreenView;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class consisting of commands relating to listing prescriptions
 */
public class PrescriptionListCommands {

    private final PatientData patientData;
    private final PatientScreenView patientScreenView = new PatientScreenView();
    private final PrescriptionManager prescriptionManager;

    /**
     * Initializes the prescription list commands class
     * @param database Database - collection of all entity databases in the program
     * @param patientData PatientData - data bundle consisting of the information of the patient whose prescriptions
     *                    are to be listed by this class
     */
    public PrescriptionListCommands(Database database, PatientData patientData) {
        this.patientData = patientData;
        this.prescriptionManager = new PrescriptionManager(database);
    }

    /**
     * Creates a hashmap of all string representations of prescription list commands mapped to the method that each
     * command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective prescription list commands.
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("active prescriptions", new ViewPrescriptions(true, false));
        commands.put("all prescriptions", new ViewPrescriptions(false, false));
        commands.put("active prescriptions detailed", new ViewPrescriptions(true, true));
        commands.put("all prescriptions detailed", new ViewPrescriptions(false, true));
        return commands;
    }
//    private Command viewPrescriptions(boolean active, boolean detail){
//        return (x) -> {
//            ArrayList<PrescriptionData> prescriptions;
//            if (active) {
//                prescriptions = prescriptionManager.getAllActivePrescriptions(patientData);
//                patientScreenView.viewActivePrescriptions(prescriptions, detail);
//
//            }
//            else {
//                prescriptions = prescriptionManager.getAllPrescriptions(patientData);
//                patientScreenView.viewPrescriptionHistory(prescriptions, detail);
//            }
//        };
//    }

    private class ViewPrescriptions implements Command {
        private final boolean active;
        private final boolean detail;

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
