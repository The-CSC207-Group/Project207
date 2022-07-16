package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.screenViews.DoctorScreenView;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;

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
        c.put("prescription", persciption());
        c.put("appointments", appointments());
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorData doctorData, PatientData patientData) {
        super(parent);
        this.patientData = patientData;
        this.doctorData = doctorData;
        doctorAccess = new DoctorAccess(getDatabase());
        this.prev = prev;
    }
    private Command persciption(){
        return (x) -> {
           return false;
        };
    }
    private Command appointments(){
        return (x) -> {
//            doctorView.viewAppointments(doctorAccess.getAllPatientAppointments(patientData.getId()));
            return false;
        };
    }

    private Command createAppointent(){
        return (x) -> {
            return false;
        };
    }
}
