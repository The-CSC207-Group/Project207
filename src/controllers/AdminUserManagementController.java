package controllers;

import dataBundles.*;
import presenters.response.UserCredentials;
import presenters.screenViews.AdminScreenView;
import useCases.AdminManager;
import useCases.DoctorManager;
import useCases.PatientManager;
import useCases.SecretaryManager;

import java.util.LinkedHashMap;

public class AdminUserManagementController extends TerminalController {

    private final AdminController previousController;
    private final AdminData adminData;
    private final AdminScreenView adminScreenView = new AdminScreenView();
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;
    private final SecretaryManager secretaryManager;
    private final AdminManager adminManager;
    /**
     * Creates a new controller for handling the state of the program where commands are being passed into the terminal.
     *
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     */
    public AdminUserManagementController(Context context, AdminController previousController, AdminData adminData) {
        super(context);
        this.previousController = previousController;
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        this.secretaryManager = new SecretaryManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.adminManager = new AdminManager(getDatabase());
    }

    @Override
    public LinkedHashMap<String, Command> AllCommands (){
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("back", Back(previousController));
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        //commands.put("delete user", DeleteUser());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command CreateAdmin(){
        return (x) -> {

        };
    }

    private Command CreateDoctor(){
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {

        };
    }

    private Command CreatePatient(){
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {

        };
    }

    private Command CreateSecretary(){
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {

        };
    }

    private void DisplaySuccessOnCreateAccount(UserData<?> user) {

    }


}
