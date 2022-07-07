package controllers;

import entities.Doctor;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorController extends TerminalController{
    private Doctor doctor;
    public DoctorController(Context context, Doctor doctor){
        super(context);
        this.doctor = doctor;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap h = super.AllCommands();
        return h;
    }

    @Override
    void WelcomeMessage() {

    }
}
