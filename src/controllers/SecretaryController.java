package controllers;

import dataBundles.AvailabilityData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import entities.Secretary;
import presenter.response.UserCredentials;
import presenter.screenViews.SecretaryScreenView;
import useCases.managers.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class that processes the commands that a secretary passes in.
 */
public class SecretaryController extends UserController<Secretary> {

    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final PatientManager patientManager;
    private final AdminManager adminManager;
    private final DoctorManager doctorManager;
    private final SecretaryManager secretaryManager;
    private final AppointmentManager appointmentManager;

    /**
     * Creates a new controller for handling the state of the program when a secretary is signed in.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param secretaryData SecretaryData - a data bundle containing the ID and attributes of the current secretary
     *                      user.
     */
    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context, secretaryData, new SecretaryManager(context.getDatabase()), new SecretaryScreenView());
        this.appointmentManager = new AppointmentManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.adminManager = new AdminManager(getDatabase());
        this.patientManager = new PatientManager(getDatabase());
        this.secretaryManager = new SecretaryManager(getDatabase());
    }

    /**
     * Creates a hashmap of all string representations of secretary commands mapped to the method that each
     * command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective secretary commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create patient", createPatientAccount());
        commands.put("load patient", LoadPatient());
        commands.put("delete patient", deletePatient());
        //pending implementation for phase 2
//        commands.put("add availability", addDoctorAvailability());
//        commands.put("delete availability", removeDoctorAvailability());
//        commands.put("add absence", addDoctorAbsence());

        return commands;
    }

    private Command LoadPatient() {
        return (x) -> {
            String username = secretaryScreenView.loadPatientPrompt();
            PatientData patientData = patientManager.getUserData(username);
            SecretaryController currentController = this;
            if (patientData != null) {
                secretaryScreenView.showSuccessLoadingPatient(patientData);
                changeCurrentController(new SecretaryLoadedPatientController(getContext(), currentController,
                        patientData));
            } else {
                secretaryScreenView.showErrorLoadingPatient();
            }
        };
    }

    private Command createPatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!patientManager.doesUserExist(userCredentials.username()) &&
            !doctorManager.doesUserExist(userCredentials.username()) &&
            !adminManager.doesUserExist(userCredentials.username()) &&
            !secretaryManager.doesUserExist(userCredentials.username())){
                patientManager.createPatient(userCredentials.username(), userCredentials.password());
                secretaryScreenView.showRegisterPatientSuccess();
            } else {
                secretaryScreenView.showRegisterPatientError();
            }

        };
    }

    private Command deletePatient() {
        return (x) -> {
            String patient = secretaryScreenView.showDeletePatientPrompt();
            if (patientManager.deleteUser(patient)) {
                secretaryScreenView.showDeletePatientSuccess();
            } else {
                secretaryScreenView.showFailedToDeletePatientError();
            }

        };
    }
//pending implementation for phase 2
//    private Command addDoctorAvailability() {
//        return (x) -> {
//            String doctor = secretaryScreenView.getTargetDoctor();
//            DoctorData doctorData = doctorManager.getUserData(doctor);
//            ArrayList<Integer> days = secretaryScreenView.addAvailabilityPrompt();
//            appointmentManager.newAvailability(doctorData,
//                    DayOfWeek.of(days.get(0)),
//                    days.get(1),
//                    days.get(2),
//                    days.get(3));
//        };
//
//    }
//
//    private Command removeDoctorAvailability() {
//        return (x) -> {
//            String doctor = secretaryScreenView.getTargetDoctor();
//            DoctorData doctorData = doctorManager.getUserData(doctor);
//            Integer deleteInteger = secretaryScreenView.deleteAvailabilityPrompt(new ContactManager(getDatabase())
//                    .getContactData(doctorData), new AppointmentManager(getDatabase())
//                    .getAvailabilityData(doctorData));
//            ArrayList<AvailabilityData> availability = doctorData.getAvailability();
//            if (deleteInteger >= 0 & deleteInteger < availability.size()){
//                new AppointmentManager(getDatabase()).removeAvailability(doctorData,
//                        doctorData.getAvailability().get(deleteInteger));
//            } else {
//                secretaryScreenView.showDeleteOutOfRangeError();
//            }
//        };
//    }
//
//    private Command addDoctorAbsence() {
//        return (x) -> {
//            String doctor = secretaryScreenView.getTargetDoctor();
//            DoctorData doctorData = doctorManager.getUserData(doctor);
//
//            appointmentManager.addAbsence(doctorData, secretaryScreenView.addZoneDateTimeStart(),
//                    secretaryScreenView.addZoneDateTimeEnd());
//        };
//    }

}
