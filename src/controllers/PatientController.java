package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.AppointmentData;
import dataBundles.PatientData;
import entities.Patient;
import presenter.screenViews.PatientScreenView;
import useCases.managers.AppointmentManager;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class that processes the commands that a patient passes in.
 */
public class PatientController extends UserController<Patient> {

    private final PatientScreenView patientScreenView = new PatientScreenView();
    private final PatientData patientData;

    /**
     * Creates a new controller for handling the state of the program when a patient is signed in.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param patientData PatientData - a data bundle containing the ID and attributes of the current patient user.
     */
    public PatientController(Context context, PatientData patientData) {
        super(context, patientData, new PatientManager(context.getDatabase()), new PatientScreenView());
        this.patientData = patientData;
    }

    /**
     * Creates a hashmap of all string representations of patient commands mapped to the method that each
     * command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective patient commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("view appointments", ViewAppointments());

        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);
        HashMap<String, Command> prescriptionCommands = prescriptionController.AllCommands();
        for (String key : prescriptionCommands.keySet()) {
            commands.put("view " + key, prescriptionCommands.get(key));
        }

        return commands;
    }

    private Command ViewAppointments() {
        AppointmentManager appointmentManager = new AppointmentManager(getDatabase());
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            patientScreenView.viewAppointments(appointments);
        };
    }

}