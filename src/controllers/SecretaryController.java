package controllers;


import dataBundles.*;
import entities.Availability;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.SecretaryScreenView;
import useCases.managers.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {
    private final SecretaryData secretaryData;
    private final SecretaryController self = this;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    PatientManager patientManager;
    SecretaryManager secretaryManager;
    LogManager logManager;
    ContactManager contactManager;
    DoctorManager doctorManager;
    AppointmentManager appointmentManager;

    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", changePassword());
        commands.put("create patient", createPatientAccount());
        commands.put("view logs", getLogs());
        commands.put("load patient", new LoadPatient());
        commands.put("delete patient", deletePatient());
        commands.put("add availability", addDoctorAvailability());
        commands.put("delete availability", removeDoctorAvailability());
        commands.put("add absence", addDoctorAbsence());

        return commands;
    }

    class LoadPatient implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.loadPatientPrompt();
            PatientData patientData = patientManager.getUserData(username);
            if (patientData != null) {
                changeCurrentController(new SecretaryLoadedPatientController(getContext(), self, patientData));
                secretaryScreenView.showSuccessLoadingPatient(contactManager.getContactData(patientData));
            }
            secretaryScreenView.showErrorLoadingPatient();
        }
    }

    private Command createPatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!patientManager.doesUserExist(userCredentials.username())) {
                patientManager.createPatient(userCredentials.username(), userCredentials.password());
                secretaryScreenView.showRegisterPatientSuccess();
            } else {
                secretaryScreenView.showRegisterPatientError();
            }

        };
    }

    private Command changePassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                secretaryManager.changeUserPassword(secretaryData, passwordResetDetails.password());
                secretaryScreenView.showResetPasswordSuccessMessage();
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command getLogs() {
        return (x) -> {
            ArrayList<LogData> logs = logManager.getUserLogs(secretaryData);
            secretaryScreenView.viewUserLogs(logs);
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

    private Command addDoctorAvailability() {
        return (x) -> {
            String doctor = secretaryScreenView.getTargetDoctor();
            DoctorData doctorData = doctorManager.getUserData(doctor);
            appointmentManager.newAvailability(doctorData, secretaryScreenView.getDay(), secretaryScreenView.addDoctorAvailabilityTime().getHour(), secretaryScreenView.addDoctorAvailabilityTime().getMinute(), secretaryScreenView.addDoctorAvailableLength());
        };

    }

    private Command removeDoctorAvailability() {
        return (x) -> {
            String doctor = secretaryScreenView.getTargetDoctor();
            DoctorData doctorData = doctorManager.getUserData(doctor);

            ArrayList<Availability> availableTimes = appointmentManager.getAvailabilityFromDayOfWeek(doctorData.getId(), secretaryScreenView.getDay());
            secretaryScreenView.viewAvailabilityFull(availableTimes);

            appointmentManager.removeAvailability(doctorData, availableTimes.get(secretaryScreenView.getIndexToRemove()));
        };
    }

    private Command addDoctorAbsence() {
        return (x) -> {
            String doctor = secretaryScreenView.getTargetDoctor();
            DoctorData doctorData = doctorManager.getUserData(doctor);

            appointmentManager.addAbsence(doctorData, secretaryScreenView.addZoneDateTimeStart(), secretaryScreenView.addZoneDateTimeEnd());
        };
    }

}
