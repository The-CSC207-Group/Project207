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
        commands.put("change password", new ChangePassword());
        commands.put("logs", new GetLogs());
        commands.put("appointments", new ViewAppointments());
        commands.put("prescriptions", new ViewPrescriptions());
        commands.put("sign out", signOut());
        commands.put("cancel appointment", notImplemented());

        return commands;
    }

    class ChangePassword implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            PasswordResetDetails passwordResetDetails = patientScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                patientAccess.changeCurrentUserPassword(patientData, passwordResetDetails.password());
            } else {
                patientScreenView.showResetPasswordMismatchError();
            }
        }
    }

    class ViewAppointments implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<AppointmentData> appointments = patientAccess.getAppointments(patientData);
            patientScreenView.viewAppointments(appointments);
        }
    }

    class ViewPrescriptions implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            PrescriptionController prescriptionController = new PrescriptionController(context, patientData, self);
            changeCurrentController(prescriptionController);
        }
    }

    class GetLogs implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<LogData> logs = patientAccess.getLogs(patientData);
            patientScreenView.viewUserLogs(logs);
        }
    }
}
