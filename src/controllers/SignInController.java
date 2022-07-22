package controllers;

import dataBundles.AdminData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import presenter.response.UserCredentials;
import presenter.screenViews.SignInScreenView;
import useCases.managers.*;

import java.util.HashMap;

/**
 * Controller class that processes the commands that users pass in before signing in to their accounts.
 */
public class SignInController extends TerminalController {

    private final PatientManager patientManager;
    private final AdminManager adminManager;
    private final SecretaryManager secretaryManager;
    private final DoctorManager doctorManager;

    private final SignInScreenView signInScreenView = new SignInScreenView();

    /**
     * Creates a new controller for handling the state of the program when a user is signing in.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     */
    public SignInController(Context context) {
        super(context);
        patientManager = new PatientManager(getDatabase());
        adminManager = new AdminManager(getDatabase());
        secretaryManager = new SecretaryManager(getDatabase());
        doctorManager = new DoctorManager(getDatabase());
    }

    @Override
    public void welcomeMessage() {
        signInScreenView.welcomeMessage();
        super.welcomeMessage();
    }

    /**
     * Creates a hashmap of all string representations of sign in commands mapped to the method that each command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective sign in commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", SignInCommand());
        commands.put("view clinic info", ViewClinicInformation());
        return commands;
    }

    private void adminSignIn(String username, String password) {
        AdminData adminData = adminManager.signIn(username, password);
        AdminController adminController = new AdminController(getContext(), adminData);
        signInScreenView.showSuccessLogin(adminData.getUsername());
        changeCurrentController(adminController);
    }

    private void patientSignIn(String username, String password) {
        PatientData patientData = patientManager.signIn(username, password);
        PatientController patientController = new PatientController(getContext(), patientData);
        signInScreenView.showSuccessLogin(patientData.getUsername());
        changeCurrentController(patientController);
    }

    private void doctorSignIn(String username, String password) {
        DoctorData doctorData = doctorManager.signIn(username, password);
        DoctorController doctorController = new DoctorController(getContext(), doctorData);
        signInScreenView.showSuccessLogin(doctorData.getUsername());
        changeCurrentController(doctorController);
    }

    private void secretarySignIn(String username, String password) {
        SecretaryData secretaryData = secretaryManager.signIn(username, password);
        SecretaryController secretaryController = new SecretaryController(getContext(), secretaryData);
        signInScreenView.showSuccessLogin(secretaryData.getUsername());
        changeCurrentController(secretaryController);
    }

    private Command SignInCommand() {
        return (x) -> {
            UserCredentials userCredentials = signInScreenView.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();

            if (adminManager.canSignIn(username, password)) {
                adminSignIn(username, password);
            } else if (patientManager.canSignIn(username, password)) {
                patientSignIn(username, password);
            } else if (doctorManager.canSignIn(username, password)) {
                doctorSignIn(username, password);
            } else if (secretaryManager.canSignIn(username, password)) {
                secretarySignIn(username, password);
            } else {
                signInScreenView.showLoginError();
            }
        };
    }

    private Command ViewClinicInformation() {
        ClinicManager clinicManager = new ClinicManager(getDatabase());
        return (x) -> signInScreenView.viewClinicInfo(clinicManager.clinicData());
    }

}
