package controllers;


import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.SecretaryAccess;
import useCases.managers.*;


import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {



    private final SecretaryData secretaryData;

    private final SecretaryController self = this;


    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AdminScreenView adminScreenView = new AdminScreenView();
    PatientManager patientManager;
    DoctorManager doctorManager;
    SecretaryManager secretaryManager;
    LogManager logManager;


    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
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
            doctorManager.getPatient(username).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new SecretaryLoadedPatientController(getContext(), self, patientData));
                    }
            );
        }
    }

    private Command CreatePatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!patientManager.doesUserExist(userCredentials.username())) {
                patientManager.createPatient(userCredentials.username(), userCredentials.password());
                adminScreenView.successCreateAccount();
            } else {
                adminScreenView.failedCreateAccount();
            }

        };
    }

    private Command CreateDoctorAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerDoctorAccount();
            if (!doctorManager.doesUserExist(userCredentials.username())) {
                doctorManager.createDoctor(userCredentials.username(), userCredentials.password());
            } else {
                // need warning message
                adminScreenView.failedCreateAccount();
            }
        };
    }

    private Command ChangePassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                secretaryManager.changeUserPassword(secretaryData, passwordResetDetails.password());
                // need success message
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command GetLogs() {
        return (x) -> {
            ArrayList<LogData> logs = logManager.getUserLogs(secretaryData);
            secretaryScreenView.viewUserLogs(logs);
        };
    }


}
