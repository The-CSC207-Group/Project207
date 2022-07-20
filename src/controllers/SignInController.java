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

    private SignInScreenView signInScreenView = new SignInScreenView();

    /**
     * Creates a new controller for handling the state of the program when a user is signing in.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     */
    public SignInController(Context context) {
        super(context);
        signInScreenView.welcomeMessage();
    }

    /**
     * Creates a hashmap of all string representations of sign in commands mapped to the method that each command calls.
     * @return HashMap of strings mapped to their respective sign in commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", SignInCommand());
        commands.put("view clinic info", ViewClinicInformation());
        return commands;
    }

    private Command SignInCommand() {
        return (x) -> {
            PatientManager patientManager = new PatientManager(getDatabase());
            AdminManager adminManager = new AdminManager(getDatabase());
            SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
            DoctorManager doctorManager = new DoctorManager(getDatabase());
            UserCredentials userCredentials = signInScreenView.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();

            if (adminManager.signIn(username, password) != null) {
                AdminData adminData = adminManager.signIn(username, password);
                AdminController adminController = new AdminController(getContext(), adminData);
                changeCurrentController(adminController);

            } else if (patientManager.signIn(username, password) != null) {
                PatientData patientData = patientManager.signIn(username, password);
                PatientController patientController = new PatientController(getContext(), patientData);
                changeCurrentController(patientController);

            } else if (doctorManager.signIn(username, password) != null) {
                DoctorData doctorData = doctorManager.signIn(username, password);
                DoctorController doctorController = new DoctorController(getContext(), doctorData);
                changeCurrentController(doctorController);

            } else if (secretaryManager.signIn(username, password) != null) {
                SecretaryData secretaryData = secretaryManager.signIn(username, password);
                SecretaryController secretaryController = new SecretaryController(getContext(), secretaryData);
                changeCurrentController(secretaryController);

            } else {
                signInScreenView.showLoginError();
            }
        };
    }

    private Command ViewClinicInformation() {
        ClinicManager clinicManager = new ClinicManager(getDatabase());
        return (x) -> {
            signInScreenView.viewClinicInfo(clinicManager.clinicData());
        };
    }
}
