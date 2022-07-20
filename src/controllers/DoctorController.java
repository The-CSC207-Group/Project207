package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.DoctorScreenView;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.time.LocalDate;
import java.util.HashMap;

public class DoctorController extends TerminalController{
    private DoctorScreenView doctorView = new DoctorScreenView();
    private DoctorManager doctorManager = new DoctorManager(getDatabase());
    private DoctorData doctorData;
    private DoctorController self = this;

    public DoctorController(Context context, DoctorData doctorData){
        super(context);
        this.doctorData = doctorData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("load patient", LoadPatient());
        commands.put("change password", ChangePassword());
        commands.put("show schedule", ViewSchedule());
        commands.put("show logs", GetLogs());
        commands.put("sign out", signOut());
        commands.put("show assigned appointments", ViewAllDoctorAppointments());
        commands.put("show all appointments", ViewAllAppointments());
        return commands;
    }

    private Command LoadPatient() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            String patientUsername = doctorView.loadPatientPrompt();
            PatientData loadedPatientData = patientManager.getUserData(patientUsername);
            if (loadedPatientData != null){
                changeCurrentController(new DoctorLoadedPatientController(
                        getContext(), self, doctorData, loadedPatientData));
            }
        };
    }

    private Command ChangePassword(){
        return (x) -> {
            PasswordResetDetails passwordResetDetails = doctorView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())){
                doctorManager.changeUserPassword(doctorData, passwordResetDetails.password());
                doctorView.showResetPasswordSuccessMessage();
            } else {
                doctorView.showResetPasswordMismatchError();
            }
        };
    }

    private Command ViewSchedule(){
        return (x) -> {
            LocalDate viewDate = doctorView.viewSchedulePrompt();
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getScheduleData(doctorData, viewDate));
        };
    }

    private Command ViewAllDoctorAppointments(){
        return (x) -> {
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getDoctorAppointments(doctorData));
        };
    }
    private Command GetLogs(){
        return (x) -> {
            doctorView.viewUserLogs(new LogManager(getDatabase()).getUserLogs(doctorData));
        };
    }
    private Command ViewAllAppointments(){
        return (x) -> {
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getAllAppointments());
        };
    }
}
