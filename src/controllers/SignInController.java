package controllers;

import dataBundles.AdminData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import presenter.response.UserCredentials;
import presenter.screenViews.SignInScreenView;
import useCases.managers.*;

import java.util.ArrayList;
import java.util.HashMap;


public class SignInController extends TerminalController {

    private SignInScreenView signInScreenView = new SignInScreenView();

    /**
     * creates a sign in controller for allowing users to sign in
     * @param context
     */
    public SignInController(Context context) {
        super(context);
        signInScreenView.welcomeMessage();
    }

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

    class BackCommand implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
        }
    }



}
