package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import entities.Doctor;
import presenter.screenViews.DoctorScreenView;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;

import java.time.LocalDate;
import java.util.HashMap;

public class DoctorController extends UserController<Doctor> {
    private DoctorScreenView doctorView = new DoctorScreenView();
    private DoctorData doctorData;
    private DoctorController self = this;

    public DoctorController(Context context, DoctorData doctorData){
        super(context, doctorData, new DoctorManager(context.getDatabase()), new DoctorScreenView());
        this.doctorData = doctorData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("load patient", LoadPatient());
        commands.put("show schedule", ViewSchedule());
        commands.put("show assigned appointments", ViewAllDoctorAppointments());
        commands.put("show all appointments", ViewAllAppointments());
        commands.put("create new absence", newAbsence());
        commands.put("delete absence", deleteAbsence());
        commands.put("create new availability", newAvailability());
        commands.put("delete availability", deleteAvailability());
        return commands;
    }

    private Command LoadPatient() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            String patientUsername = doctorView.loadPatientPrompt();
            PatientData loadedPatientData = patientManager.getUserData(patientUsername);
            if (loadedPatientData != null){
                changeCurrentController(new DoctorLoadedPatientController(
                        getContext(), self, doctorData, loadedPatientData));
            }
        };
    }

    private Command ViewSchedule(){
        return (x) -> {
            LocalDate viewDate = doctorView.viewSchedulePrompt();
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getScheduleData(doctorData, viewDate));
        };
    }

    private Command ViewAllDoctorAppointments(){
        return (x) -> {
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getDoctorAppointments(doctorData));
        };
    }
    private Command ViewAllAppointments(){
        return (x) -> {
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getAllAppointments());
        };

    }
    private Command newAvailability() {
        return (x) -> {
            //pending presenter implementation for (get dayofweek, hour, minute)
            //doctorView.
            //new AppointmentManager(getDatabase()).newAvailability(doctorData, );
        };
    }
    private Command deleteAvailability() {
        return (x) -> {
            //pending presenter implementation for (
            //new AppointmentManager(getDatabase()).removeAvailability(doctorData);
        };
    }
    private Command rescheduleAvailability() {
        return (x) -> {
            //pending presenter implementation for (
            //new AppointmentManager(getDatabase()).removeAvailability(doctorData);
        };
    }
    private Command deleteAbsence() {
        return (x) -> {
            //pending presenter implementation for absence TimeBlock
            //doctorView.
            //new AppointmentManager(getDatabase()).deleteAbsence(doctorData, );
        };
    }
    private Command newAbsence() {
        return (x) -> {
            //pending presenter implementation for absence TimeBlock
            //doctorView.
            //new AppointmentManager(getDatabase()).addAbsence(doctorData, );
        };
    }
}
