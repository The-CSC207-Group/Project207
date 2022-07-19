package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.AppointmentData;
import dataBundles.LogData;
import dataBundles.PatientData;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.PatientScreenView;
import useCases.managers.AppointmentManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientController extends TerminalController {
    private PatientScreenView patientScreenView = new PatientScreenView();
    private PatientData patientData;
    private PatientController self = this;

    public PatientController(Context context, PatientData patientData) {
        super(context);
        this.patientData = patientData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", ChangePassword());
        commands.put("view logs", GetLogs());
        commands.put("view appointments", ViewAppointments());
        commands.put("sign out", signOut());

        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);
        HashMap<String, Command> prescriptionCommands = prescriptionController.AllCommands();
        for (String key : prescriptionCommands.keySet()) {
            commands.put("view " + key, prescriptionCommands.get(key));
        }

        return commands;
    }

    private Command ChangePassword() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            PasswordResetDetails passwordResetDetails = patientScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                patientManager.changeUserPassword(patientData, passwordResetDetails.password());
                patientScreenView.showResetPasswordSuccessMessage();
            } else {
                patientScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command ViewAppointments() {
        AppointmentManager appointmentManager = new AppointmentManager(getDatabase());
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            patientScreenView.viewAppointments(appointments);
        };
    }

    private Command GetLogs() {
        LogManager logManager = new LogManager(getDatabase());
        return (x) -> {
            ArrayList<LogData> logs = logManager.getUserLogs(patientData);
            patientScreenView.viewUserLogs(logs);
        };
    }
}