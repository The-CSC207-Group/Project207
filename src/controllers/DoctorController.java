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
        HashMap<String, Command> h = super.AllCommands();
        h.put("load patient", LoadPatient());
        h.put("change password", ChangePassword());
        h.put("show schedule", ViewSchedule());
        h.put("show logs", GetLogs());
        h.put("sign out", signOut());
        h.put("show assigned appointments", ViewAllDoctorAppointments());
        h.put("show all appointments", ViewAllAppointments());
        return h;
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
            } else {
                doctorView.showResetPasswordMismatchError();;
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
