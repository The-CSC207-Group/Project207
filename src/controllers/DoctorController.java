package controllers;

import dataBundles.AvailabilityData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.TimeBlockData;
import entities.Doctor;
import presenter.screenViews.DoctorScreenView;
import useCases.managers.*;
import utilities.TimeUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Controller class that processes the commands that a patient passes in.
 */
public class DoctorController extends UserController<Doctor> {

    private final DoctorScreenView doctorView = new DoctorScreenView();
    private final DoctorData doctorData;
    private final DoctorController currentController = this;

    /**
     * Creates a new controller for handling the state of the program when a doctor is signed in.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     * @param doctorData a data bundle containing the ID and attributes of the current doctor user.
     */
    public DoctorController(Context context, DoctorData doctorData){
        super(context, doctorData, new DoctorManager(context.getDatabase()), new DoctorScreenView());
        this.doctorData = doctorData;
    }

    /**
     * Creates a hashmap of all string representations of doctor commands mapped to the method that each
     * command calls.
     * @return HashMap of strings mapped to their respective doctor commands.
     */
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
                        getContext(), currentController, doctorData, loadedPatientData));
                doctorView.showSuccessLoadingPatient(new ContactManager(getDatabase()).getContactData(loadedPatientData));
            } else {
                doctorView.showErrorLoadingPatient();
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
        return (x) -> doctorView.viewAppointments(new AppointmentManager(getDatabase())
                .getDoctorAppointments(doctorData));
    }

    private Command ViewAllAppointments(){
        return (x) -> doctorView.viewAppointments(new AppointmentManager(getDatabase()).getAllAppointments());

    }

    private Command newAvailability() {
        return (x) -> {
            ArrayList<Integer> availabilityInfo = doctorView.addAvailabilityPrompt();
            new AppointmentManager(getDatabase()).newAvailability(doctorData, DayOfWeek.of(availabilityInfo.get(0)),
                    availabilityInfo.get(1), availabilityInfo.get(2), availabilityInfo.get(3));
        };
    }

    private Command deleteAvailability() {
        return (x) -> {
            Integer deleteInteger = doctorView.deleteAvailabilityPrompt(new ContactManager(getDatabase())
                    .getContactData(doctorData), new AppointmentManager(getDatabase())
                    .getAvailabilityData(doctorData));
            ArrayList<AvailabilityData> availabiltiy = doctorData.getAvailability();
            if (deleteInteger >= 0 & deleteInteger < availabiltiy.size()) {
                new AppointmentManager(getDatabase()).removeAvailability(doctorData,
                        doctorData.getAvailability().get(deleteInteger));
            }
        };
    }

    private Command deleteAbsence() {
        return (x) -> {
            Integer deleteInteger = doctorView.deleteAbsencePrompt(new ContactManager(getDatabase())
                    .getContactData(doctorData), doctorData.getAbsence().stream()
                    .map(TimeBlockData::new)
                    .collect(Collectors.toCollection(ArrayList::new)));
            new AppointmentManager(getDatabase()).deleteAbsence(doctorData, doctorData.getAbsence().get(deleteInteger));
        };
    }

    private Command newAbsence() {
        return (x) -> {
            ArrayList<Integer> absenceData = doctorView.addAbsencePrompt();
            new AppointmentManager(getDatabase()).addAbsence(doctorData, new TimeUtils()
                    .createZonedDataTime(absenceData.get(0), absenceData.get(1),
                    absenceData.get(2), 0, 0), new TimeUtils().createZonedDataTime(absenceData.get(0),
                    absenceData.get(1), absenceData.get(2) + absenceData.get(3), 0, 0));
        };
    }

}
