package controllers;


import dataBundles.*;
import entities.Patient;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.managers.*;


import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {


    private final SecretaryData secretaryData;

    private final SecretaryController self = this;


    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    PatientManager patientManager;
    DoctorManager doctorManager;
    SecretaryManager secretaryManager;
    LogManager logManager;

    ContactManager contactManager;


    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", ChangePassword());
        commands.put("create patient", CreatePatientAccount());
        commands.put("get logs", GetLogs());
        commands.put("load patient", new LoadPatient());

        return commands;
    }

    class LoadPatient implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.LoadPatientPrompt();
            PatientData patientData = patientManager.getUserData(username);
            if (patientData != null){
                changeCurrentController(new SecretaryLoadedPatientController(getContext(), self, patientData));
                secretaryScreenView.showSuccessLoadingPatient(contactManager.getContactData(patientData));
            }
            secretaryScreenView.showErrorLoadingPatient();
        }
    }

    private Command CreatePatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!patientManager.doesUserExist(userCredentials.username())) {
                patientManager.createPatient(userCredentials.username(), userCredentials.password());
                secretaryScreenView.showRegisterPatientSuccess();
            } else {
                secretaryScreenView.showRegisterPatientError();
            }

        };
    }

    private Command ChangePassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                secretaryManager.changeUserPassword(secretaryData, passwordResetDetails.password());
                secretaryScreenView.showResetPasswordSuccessMessage();
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
