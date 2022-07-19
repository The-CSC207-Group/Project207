package controllers;

import dataBundles.AdminData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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
            String patientUsername = doctorView.enterPatientUsernamePrompt();
            Optional <PatientData> loadedPatientData = Optional.ofNullable(patientManager.getUser(patientUsername))
                    .map(PatientData::new);
            loadedPatientData.ifPresent(
                    (patientData) -> {
                        changeCurrentController(new DoctorLoadedPatientController(
                                getContext(), self, doctorData, patientData));
                    });
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
            String year = doctorView.enterYearPrompt();
            String month = doctorView.enterMonthPrompt();
            String day = doctorView.enterDayPrompt();
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getScheduleData(doctorData,
                    new TimeManager().createLocalDate(Integer.parseInt(year),
                            Integer.parseInt(month), Integer.parseInt(day))));
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
