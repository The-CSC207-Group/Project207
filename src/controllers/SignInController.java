package controllers;

import dataBundles.AdminData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import presenters.response.UserCredentials;
import presenters.screenViews.SignInScreenView;
import useCases.*;

import java.util.LinkedHashMap;

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
     *
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
        signInScreenView.showWelcomeMessage();
        super.welcomeMessage();
    }

    /**
     * Creates a linked hashmap of all string representations of sign in commands mapped to the method that each command
     * calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective sign in commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("sign in", SignInCommand());
        commands.put("view clinic info", ViewClinicInformation());
        commands.putAll(super.AllCommands());
        return commands;
    }

    private boolean adminSignIn(String username, String password) {
        if (adminManager.canSignIn(username, password)) {
            AdminData adminData = adminManager.signIn(username, password);
            AdminController adminController = new AdminController(getContext(), adminData);
            signInScreenView.showSuccessLogin(adminData.getUsername());
            changeCurrentController(adminController);
            return true;
        }
        return false;
    }

    private boolean patientSignIn(String username, String password) {
        if (patientManager.canSignIn(username, password)) {
            PatientData patientData = patientManager.signIn(username, password);
            PatientController patientController = new PatientController(getContext(), patientData);
            signInScreenView.showSuccessLogin(patientData.getUsername());
            changeCurrentController(patientController);
            return true;
        }
        return false;
    }

    private boolean doctorSignIn(String username, String password) {
        if (doctorManager.canSignIn(username, password)) {
            DoctorData doctorData = doctorManager.signIn(username, password);
            DoctorController doctorController = new DoctorController(getContext(), doctorData);
            signInScreenView.showSuccessLogin(doctorData.getUsername());
            changeCurrentController(doctorController);
            return true;
        }
        return false;
    }

    private boolean secretarySignIn(String username, String password) {
        if (secretaryManager.canSignIn(username, password)) {
            SecretaryData secretaryData = secretaryManager.signIn(username, password);
            SecretaryController secretaryController = new SecretaryController(getContext(), secretaryData);
            signInScreenView.showSuccessLogin(secretaryData.getUsername());
            changeCurrentController(secretaryController);
            return true;
        }
        return false;
    }

    private void signIn(String username, String password) {
        boolean signedIn = adminSignIn(username, password) ||
                patientSignIn(username, password) ||
                doctorSignIn(username, password) ||
                secretarySignIn(username, password);
        if (!signedIn) {
            signInScreenView.showLoginError();
        }
    }

    private Command SignInCommand() {
        return (x) -> {
            UserCredentials userCredentials = signInScreenView.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();

            signIn(username, password);
        };
    }

    private Command ViewClinicInformation() {
        return (x) -> {
            ClinicManager clinicManager = new ClinicManager(getDatabase());
            signInScreenView.viewClinicInfo(clinicManager.clinicData());
        };
    }

}
