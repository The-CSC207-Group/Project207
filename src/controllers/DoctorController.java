package controllers;

import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.LogData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.TimeManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        h.put("change password", new ChangePassword());
        h.put("show schedule", new CheckSchedule());
        h.put("show logs", new GetLogs());
        h.put("sign out", signOut());
        h.put("show assigned appointments", new ViewAllDoctorAppointments());
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
    class ChangePassword implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            String newPassword1 = presenter.promptPopup("Enter a new password");
            String newPassword2 = presenter.promptPopup("Re-enter the new password");
            if (newPassword1.equals(newPassword2)){
                doctorAccess.changePassword(doctorData, newPassword1);
            } else {
                presenter.errorMessage("These do not match");
            }
        }
    }

    class CheckSchedule implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            String year = presenter.promptPopup("Enter the year:");
            String month = presenter.promptPopup("Enter the month:");
            String day = presenter.promptPopup("Enter the day of month:");
            List<AppointmentData> appointments = doctorAccess.getScheduleData(doctorData, Integer.parseInt(year),
                    Integer.parseInt(month), Integer.parseInt(day));
        }
    }
    class ViewAllDoctorAppointments implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            doctorView.viewAppointments(doctorAccess.getAllDoctorAppointments(doctorData));
        }
    }
    class GetLogs implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<LogData> logs = doctorAccess.getLogs(doctorData);
        }
    }
}
