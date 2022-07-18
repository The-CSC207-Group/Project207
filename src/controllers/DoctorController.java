package controllers;

import dataBundles.AdminData;
import dataBundles.DoctorData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorController extends TerminalController{
    private DoctorAccess doctorAccess;
    private DoctorScreenView doctorView = new DoctorScreenView();

    private DoctorData doctorData;
    private DoctorController self = this;

    public DoctorController(Context context, DoctorData doctorData){
        super(context);
        this.doctorAccess = new DoctorAccess(getDatabase());
        this.doctorData = doctorData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> h = super.AllCommands();
        h.put("load patient", new LoadPatient());
        h.put("change password", ChangePassword());
        h.put("show schedule", ViewSchedule());
        h.put("show logs", GetLogs());
        h.put("sign out", signOut());
        h.put("show assigned appointments", ViewAllDoctorAppointments());
        h.put("show all appointments", ViewAllAppointments());
        return h;
    }

    class LoadPatient implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            String name = presenter.promptPopup("name");
            doctorAccess.getPatient(name).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new DoctorLoadedPatientController(getContext(), self, doctorData, patientData));
                    }
            );
        }
    }
    private Command ChangePassword(){
        return (x) -> {
            String newPassword1 = presenter.promptPopup("Enter a new password");
            String newPassword2 = presenter.promptPopup("Re-enter the new password");
            if (newPassword1.equals(newPassword2)){
                new DoctorManager(getDatabase()).changeUserPassword(doctorData, newPassword1);
            } else {
                presenter.errorMessage("These do not match");
            }
        };
    }
    private Command ViewSchedule(){
        return (x) -> {
            String year = presenter.promptPopup("Enter the year:");
            String month = presenter.promptPopup("Enter the month:");
            String day = presenter.promptPopup("Enter the day of month:");
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
