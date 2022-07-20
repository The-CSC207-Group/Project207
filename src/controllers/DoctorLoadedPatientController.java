package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.*;
import presenter.response.PrescriptionDetails;
import presenter.screenViews.DoctorScreenView;
import useCases.managers.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class that process the commands a doctor would use on a specific patient that they loaded.
 */
public class DoctorLoadedPatientController extends TerminalController {
    private final PatientData patientData;
    private final DoctorData doctorData;
    private final PrescriptionManager prescriptionManager;
    private final DoctorScreenView doctorView = new DoctorScreenView();
    private final DoctorController previousController;

    /**
     * Creates a new controller for handling the state of the program when a doctor has loaded a specific patient.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     * @param previousController stores the previous controller, allows you to easily go back to it via the back command.
     * @param doctorData a data bundle containing the ID and attributes of the current doctor user.
     * @param patientData a data bundle containing the ID and attributes of the current loaded patient user.
     */
    public DoctorLoadedPatientController(Context context, DoctorController previousController, DoctorData doctorData,
                                         PatientData patientData) {
        super(context);
        this.patientData = patientData;
        this.doctorData = doctorData;
        this.prescriptionManager = new PrescriptionManager(getDatabase());
        this.previousController = previousController;
    }

    /**
     * Creates a hashmap of all string representations of doctor loaded patient commands mapped to the method that each
     * command calls.
     * @return HashMap of strings mapped to their respective doctor loaded patient commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);

        HashMap<String, Command> commands = super.AllCommands();
        commands.put("unload patient", back(previousController));
        commands.put("view appointments", ViewPatientAppointments());
        commands.put("view reports", getReport());
        commands.put("create report", createReport());
        commands.put("delete report", deleteReport());

        HashMap<String, Command> prescriptionCommands = prescriptionController.AllCommands();
        for (String key : commands.keySet()) {
            commands.put("view " + key, prescriptionCommands.get(key));
        }

        commands.put("create prescription", CreatePatientPrescription());
        commands.put("delete prescription", DeletePatientPrescription());
        return commands;
    }

    private Command CreatePatientPrescription() {
        return (x) -> {
            PrescriptionDetails prescriptionDetails = doctorView.prescriptionDetailsPrompt();
            prescriptionManager.createPrescription(prescriptionDetails.header(), prescriptionDetails.body(),
                    patientData, doctorData, prescriptionDetails.expiryDate());
            doctorView.showSuccessfullyCreatedPrescription();
        };
    }

    private Command DeletePatientPrescription() {
        ContactManager contactManager = new ContactManager(getDatabase());
        ContactData patientContactData = contactManager.getContactData(patientData);
        ArrayList<PrescriptionData> prescriptionDataList = prescriptionManager.getAllPrescriptions(patientData);
        return (x) -> {
            Integer deleteIndex = doctorView.deletePrescriptionPrompt(patientContactData, prescriptionDataList);
            if (0 <= deleteIndex && deleteIndex < prescriptionDataList.size()) {
                prescriptionManager.removePrescription(prescriptionDataList.get(deleteIndex));
                doctorView.showSuccessfullyDeletedPrescription();
            } else {
                doctorView.showDeletePrescriptionOutOfRangeError();
            }
        };
    }

    private Command ViewPatientAppointments() {
        return (x) -> doctorView.viewAppointments(new AppointmentManager(getDatabase())
                .getPatientAppointments(patientData));
    }

    private Command getReport() {
        return (x) -> new ReportManager(getDatabase()).getReportData(patientData);
    }

    private Command createReport() {
        return (x) -> new ReportManager(getDatabase()).addReport(patientData, doctorData,
                doctorView.reportDetailsPrompt().header(), doctorView.reportDetailsPrompt().body());
    }

    private Command deleteReport() {
        return (x) -> {
            Integer deleteIndex = doctorView.deleteReportPrompt(new ContactManager(getDatabase())
                    .getContactData(patientData), new ReportManager(getDatabase()).getReportData(patientData));
            getDatabase().getReportDatabase().remove(deleteIndex);
        };
    }
}
