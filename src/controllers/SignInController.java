package controllers;

import dataBundles.*;
import entities.Clinic;
import presenter.response.UserCredentials;
import presenter.screenViews.SignInScreenView;
import useCases.accessClasses.SignInAccess;

import java.util.ArrayList;
import java.util.HashMap;


public class SignInController extends TerminalController {

    SignInAccess signInAccess;
    SignInScreenView signInScreenView = new SignInScreenView();

    public SignInController(Context parent) {
        super(parent);
        this.signInAccess = new SignInAccess(getDatabase());
        signInScreenView.welcomeMessage();
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", SignInCommand());
        return commands;
    }

    private Command SignInCommand() {
        return (x) -> {
            UserCredentials userCredentials = signInScreenView.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();

            if (signInAccess.adminSignIn(username, password) != null) {
                AdminData adminData = signInAccess.adminSignIn(username, password);
                AdminController adminController = new AdminController(getContext(), adminData);
                changeCurrentController(adminController);

            } else if (signInAccess.patientSignIn(username, password) != null) {
                PatientData patientData = signInAccess.patientSignIn(username, password);
                PatientController patientController = new PatientController(getContext(), patientData);
                changeCurrentController(patientController);

            } else if (signInAccess.doctorSignIn(username, password) != null) {
                DoctorData doctorData = signInAccess.doctorSignIn(username, password);
                DoctorController doctorController = new DoctorController(getContext(), doctorData);
                changeCurrentController(doctorController);

            } else if (signInAccess.secretarySignIn(username, password) != null) {
                SecretaryData secretaryData = signInAccess.secretarySignIn(username, password);
                SecretaryController secretaryController = new SecretaryController(getContext(), secretaryData);
                changeCurrentController(secretaryController);

            } else {
                signInScreenView.showLoginError();
            }
        };
    }


    class BackCommand implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
        }
    }



}
