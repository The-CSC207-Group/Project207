package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.AppointmentData;
import dataBundles.LogData;
import dataBundles.PatientData;
import entities.Patient;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.PatientScreenView;
import useCases.managers.AppointmentManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientController extends UserController<Patient> {
    private PatientScreenView patientScreenView = new PatientScreenView();
    private PatientData patientData;
    private PatientController self = this;

    public PatientController(Context context, PatientData patientData) {
        super(context, patientData, new PatientManager(context.getDatabase()), new PatientScreenView());
        this.patientData = patientData;
    }

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