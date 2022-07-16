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
    private DoctorAccess doctorAccess;
    private DoctorView view = new DoctorView();

    private DoctorDataBundle doctorData;
    private DoctorController self = this;

    public DoctorController(Context context, DoctorDataBundle doctorData){
        super(context);
        this.doctorAccess = new DoctorAccess(getDatabase());
        this.doctorData = doctorData;
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
            doctorAccess.getPatient(name).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new DoctorLoadedPatientController(getContext(), self, doctorData, patientData));
                    }
            );
            return false;
        }
    }
    class ChangePassword implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String password = presenter.promptPopup("chooseNewPassword");
            String password2 = presenter.promptPopup("validatePassword");
            if (password2 == password){
                doctorAccess.changePassword(doctorData.getId(), password );
            } else {
                presenter.errorMessage("these do not match");
            }
            return false;
        }
    }

    class CheckSchedule implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            List<AppointmentDataBundle> appointments = doctorAccess.getAllDoctorAppointments(doctorData.getId());
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
