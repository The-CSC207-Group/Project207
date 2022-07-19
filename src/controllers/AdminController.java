package controllers;

import dataBundles.*;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;

import useCases.managers.*;

import java.util.HashMap;
import java.util.Optional;

public class AdminController extends TerminalController{

    private AdminData adminData;
    PatientManager patientManager;
    DoctorManager doctorManager;
    SecretaryManager secretaryManager;
    AdminManager adminManager;
    AdminScreenView adminScreenView = new AdminScreenView();

    public AdminController(Context parent, AdminData adminData) {
        super(parent);
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        secretaryManager = new SecretaryManager(getDatabase());
        doctorManager = new DoctorManager(getDatabase());
        adminManager = new AdminManager(getDatabase());

    }
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("change password", ChangePassword());
        commands.put("change user password", ChangePassword());
        commands.put("get logs", getLogs());
        commands.put("sign out", signOut());
        commands.put("delete user", deleteUser());
        return commands;
    }

    Command CreateSecretary(){
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            UserCredentials c = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateDoctor(){
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(doctor);
        };
    }
    private Command CreateAdmin(){
        return (x) -> {

            UserCredentials userCred = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(admin);
        };
    }
    private Command CreatePatient(){
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerPatientPrompt();
            PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(patient);
        };
    }
    private void displaySuccessOnCreateAcount(UserData user){
        if (user == null){
            adminScreenView.showFailedToRegisterUserError();
        } else {
            adminScreenView.showRegisterUserSuccess();
        }
    }

    private Command ChangePassword(){

        return (x) -> {
            PasswordResetDetails passwordResetDetails = adminScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())){
                adminManager.changeUserPassword(adminData, passwordResetDetails.password());
                adminScreenView.changePasswordSuccess();
            }
            else {
                adminScreenView.showResetPasswordMismatchError();
            }};
    }
    private Command getLogs (){
        LogManager logManager = new LogManager(getDatabase());
        return (x) -> {
            adminScreenView.viewAllLogs(logManager.getUserLogs(adminData));
        };
    }
    private boolean deleteUserHelper(String username){

        if (patientManager.deleteUser(username)){
            return true;
        } else if (doctorManager.deleteUser(username)){
            return true;
        } else if (secretaryManager.deleteUser(username)) {
            return true;
        } else if (adminManager.deleteUser(username)){
            return true;
        } else {
            return false;
        }

    }
    private Command deleteUser (){
        return (x) -> {
            String user = adminScreenView.deleteUserPrompt();
            if (deleteUserHelper(user)){
                adminScreenView.showDeleteUserSuccess();
            } else {
                adminScreenView.showFailedToDeleteUserError();
            }
        };
    }
    private Command getUserInfo(){
        return (x) -> {};
    }
    boolean getAdmin(String userName){
        if (adminManager.getUserData(userName) != null){
            adminScreenView.showAdminData(adminManager.getUserData(userName));
            return true;
        } else {
            return false;
        }
    }
    Optional<PatientData> getPatient(String userName){
        patientMan
    }
    private Command changeUserPassword(){
        return (x) -> {
            patientManager.g
            if (patientManager.changeUserPassword())
        };
    }
}
