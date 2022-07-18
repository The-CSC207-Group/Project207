package controllers;

import dataBundles.AppointmentData;
import dataBundles.LogData;
import dataBundles.PatientData;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.PatientScreenView;
import useCases.accessClasses.PatientAccess;
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
        commands.put("logs", GetLogs());
        commands.put("appointments", ViewAppointments());
        commands.put("prescriptions", ViewPrescriptions());
        commands.put("sign out", signOut());
        commands.put("cancel appointment", notImplemented());

        return commands;
    }

    private Command ChangePassword() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            PasswordResetDetails passwordResetDetails = patientScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                patientManager.changeUserPassword(patientData, passwordResetDetails.password());
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

    private Command ViewPrescriptions() {
        return (x) -> {
            PrescriptionController prescriptionController = new PrescriptionController(context, patientData, self);
            changeCurrentController(prescriptionController);
        };
    }

    private Command GetLogs() {
        LogManager logManager = new LogManager(getDatabase().getLogDatabase());
        return (x) -> {
            ArrayList<LogData> logs = logManager.getUserLogs(patientData);
            patientScreenView.viewUserLogs(logs);
        };
    }
}