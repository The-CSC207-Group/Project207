package controllers;


import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.SecretaryAccess;


import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {


    private final SecretaryAccess secretaryAccess;
    private final SecretaryData secretaryData;
    private final SecretaryController self = this;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AppointmentView appointmentView = new AppointmentView();
    private final AdminScreenView adminScreenVIew = new AdminScreenView();


    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", ChangePassword());
        commands.put("create patient", CreatePatientAccount());
        commands.put("create doctor", CreateDoctorAccount());
        commands.put("get logs", GetLogs());
        commands.put("load patient", new LoadPatient());
        return commands;
    }

    class LoadPatient implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.enterPatientUsernamePrompt();
            secretaryAccess.getPatient(username).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new SecretaryLoadedPatientController(getContext(), self, patientData));
                    }
            );
        }
    }

    private Command CreatePatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!secretaryAccess.doesPatientExist(userCredentials.username())) {
                secretaryAccess.createPatient(userCredentials.username(), userCredentials.password());
                adminScreenVIew.successCreateAccount();
            } else {
                adminScreenVIew.failedCreateAccount();
            }

        };
    }

    private Command CreateDoctorAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerDoctorAccount();
            if (!secretaryAccess.doesDoctorExist(userCredentials.username())) {
                secretaryAccess.createDoctor(userCredentials.username(), userCredentials.password());
            } else {
                // need warning message
                adminScreenVIew.failedCreateAccount();
            }
        };
    }

    private Command ChangePassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                secretaryAccess.changeSecretaryPassword(secretaryData, passwordResetDetails.password());
                // need success message
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command GetLogs() {
        return (x) -> {
            ArrayList<LogData> logs = secretaryAccess.getLogs(secretaryData);
            secretaryScreenView.viewUserLogs(logs);
        };
    }


}
