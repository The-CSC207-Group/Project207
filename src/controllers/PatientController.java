package controllers;

import entities.Patient;
import entities.User;

public class PatientController extends TerminalController {
    private Patient patient;

    public PatientController(Context context, Patient patient) {
        super(context);
        this.patient = patient;
    }

    @Override
    void WelcomeMessage() {
    }
}
