package controllers;

import dataBundles.DoctorDataBundle;
import dataBundles.PatientData;
import presenter.screenViews.DoctorView;
import useCases.accessClasses.DoctorAccess;

import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController{
    PatientData patientData;
    DoctorDataBundle doctorData;
    DoctorAccess doctorAccess;

    DoctorView doctorView = new DoctorView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> c  = super.AllCommands();
        c.put("back", back(prev));
        c.put("pescription", persciption());
        c.put("appointments", appointments());
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorDataBundle doctorData, PatientData patientData) {
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


    @Override
    void WelcomeMessage() {

    }
}
