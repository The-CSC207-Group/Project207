package controllers;

import dataBundles.AppointmentData;
import dataBundles.LogData;
import dataBundles.PatientData;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.PatientScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.PatientAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientController extends TerminalController {
    private PatientAccess patientAccess;
    private PatientScreenView patientScreenView = new PatientScreenView();
    private SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private PatientData patientData;
    private PatientController self = this;

    public PatientController(Context context, PatientData patientData) {
        super(context);
        this.patientAccess = new PatientAccess(getDatabase());
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
        return (x) -> {
            PasswordResetDetails passwordResetDetails = patientScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                patientAccess.changeCurrentUserPassword(patientData, passwordResetDetails.password());
            } else {
                patientScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = patientAccess.getAppointments(patientData);
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
        return (x) -> {
            ArrayList<LogData> logs = patientAccess.getLogs(patientData);
            patientScreenView.viewUserLogs(logs);
        };
    }
}