package controllers;

import dataBundles.AppointmentDataBundle;
import dataBundles.DoctorDataBundle;
import dataBundles.LogDataBundle;
import presenter.screenViews.DoctorView;
import useCases.accessClasses.DoctorAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorController extends TerminalController{
    private Integer doctorId;
    private DoctorAccess doctorAccess;
    private DoctorView view = new DoctorView();

    private DoctorDataBundle doctorData;

    public DoctorController(Context context, Integer doctorId){
        super(context);
        this.doctorId = doctorId;
        this.doctorAccess = new DoctorAccess(getDatabase());
        this.doctorData = doctorAccess.getDoctorData(doctorId).get();
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap h = super.AllCommands();
        h.put("loadpatient", new LoadPatient());
        h.put("changePassword", new ChangePassword());
        h.put("show schedule", new CheckSchedule());
        h.put("show logs", new GetLogs());
        return h;
    }

    @Override
    void WelcomeMessage() {

    }
    class LoadPatient implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String name = presenter.promptPopup("name");
            doctorAccess.getPatientId(name).map((x) -> {
                    changeCurrentController(new DoctorLoadedPatientController(getContext(), doctorId, x));
                    return x;});
            return false;
        }
    }
    class ChangePassword implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String password = presenter.promptPopup("chooseNewPassword");
            String password2 = presenter.promptPopup("validatePassword");
            if (password2 == password){
                doctorAccess.changePassword(doctorId, password );
            } else {
                presenter.errorMessage("these do not match");
            }
            return false;
        }
    }

    class CheckSchedule implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            List<AppointmentDataBundle> appointments = doctorAccess.getAllDoctorAppointments(doctorId);
            return false;
        }
    }
    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogDataBundle> logs = doctorAccess.getLogs(doctorData.getUsername());
            return false;


        }
    }
}
