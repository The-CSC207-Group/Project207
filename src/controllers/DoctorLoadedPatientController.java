package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.TimeManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController{
    PatientData patientData;
    DoctorData doctorData;
    DoctorAccess doctorAccess;

    DoctorScreenView doctorView = new DoctorScreenView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> c  = super.AllCommands();
        c.put("unload patient", back(prev));
        c.put("view patient appointments", ViewPatientAppointments());
        c.put("get reports", getReport());
        c.put("create report", createReport());
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorData doctorData, PatientData patientData) {
        super(parent);
        this.patientData = patientData;
        this.doctorData = doctorData;
        doctorAccess = new DoctorAccess(getDatabase());
        this.prev = prev;
    }
    private Command ViewPatientAppointments(){
        return (x) -> {
            doctorView.viewAppointments(doctorAccess.getAllPatientAppointments(patientData));
        };
    }
    private Command getReport(){
        return (x) -> {
            doctorAccess.getPatientReports(patientData);
        };
    }
    private Command createReport(){
        return (x) -> {
            doctorAccess.addPatientReport(patientData, doctorData, new TimeManager().getCurrentZonedDateTime(),
                    doctorView.reportDetailsPrompt().header(), doctorView.reportDetailsPrompt().body());
        };
    }

}
