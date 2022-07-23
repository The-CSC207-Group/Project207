package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.PatientData;
import entities.Patient;
import presenters.screenViews.PatientScreenView;
import useCases.PatientManager;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that a patient passes in.
 */
public class PatientController extends UserController<Patient> {

    // private final PatientScreenView patientScreenView = new PatientScreenView();
    private final PatientData patientData;

    /**
     * Creates a new controller for handling the state of the program when a patient is signed in.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param patientData PatientData - a data  containing the ID and attributes of the current patient user.
     */
    public PatientController(Context context, PatientData patientData) {
        super(context, patientData, new PatientManager(context.getDatabase()), new PatientScreenView());
        this.patientData = patientData;
    }

    /**
     * Creates a linked hashmap of all string representations of patient commands mapped to the method that each
     * command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective patient commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();

        /* PENDING IMPLEMENTATION IN PHASE 2
        commands.put("view appointments", ViewAppointments()); */

        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);
        prescriptionController.AllCommands().forEach((x, y) -> commands.put("view " + x, y));
        commands.putAll(super.AllCommands());
        return commands;
    }

    /* PENDING IMPLEMENTATION IN PHASE 2
    private Command ViewAppointments() {
        AppointmentManager appointmentManager = new AppointmentManager(getDatabase());
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            patientScreenView.viewAppointments(appointments);
        };
    } */

}