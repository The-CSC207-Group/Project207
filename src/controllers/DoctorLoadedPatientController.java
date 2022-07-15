package controllers;

public class DoctorLoadedPatientController extends TerminalController{
    Integer doctorId;
    Integer patientId;
    public DoctorLoadedPatientController(Context parent, Integer doctorId, Integer PatientId) {
        super(parent);
        this.doctorId = doctorId;
        this.patientId = PatientId;
    }

    @Override
    void WelcomeMessage() {

    }

}
