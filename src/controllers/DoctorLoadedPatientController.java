package controllers;

import dataBundles.DoctorDataBundle;
import dataBundles.PatientDataBundle;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;

import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController{
    PatientDataBundle patientData;
    DoctorDataBundle doctorData;
    DoctorAccess doctorAccess;

    DoctorScreenView doctorView = new DoctorScreenView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> c  = super.AllCommands();
        c.put("back", back(prev));
        c.put("pescription", persciption());
        c.put("appointments", appointments());
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorDataBundle doctorData, PatientDataBundle patientData) {
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
