package controllers;


import dataBundles.*;
import entities.Patient;
import presenter.entityViews.AppointmentView;
import presenter.response.AppointmentDayDetails;
import presenter.response.AppointmentTimeDetails;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import presenter.screenViews.DoctorScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.SecretaryAccess;


import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {


    private final SecretaryAccess secretaryAccess;
    private final SecretaryData secretaryData;
    private final SecretaryController self = this;

    private final DoctorScreenView doctorScreenView = new DoctorScreenView();
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AppointmentView appointmentView = new AppointmentView();
    private final AdminScreenView adminScreenVIew = new AdminScreenView();


    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", new ChangePassword());
        commands.put("create patient", new CreatePatientAccount());
        commands.put("create doctor", new CreateDoctorAccount());
        commands.put("get logs", new GetLogs());
        commands.put("load patient", new LoadPatient());
        commands.put("book", new BookAppointment());
        return commands;
    }

    class LoadPatient implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.enterPatientUsernamePrompt();
            secretaryAccess.getPatient(username).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new SecretaryLoadedPatientController(getContext(), self, patientData));
                    }
            );
        }
    }

    class CreatePatientAccount implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!secretaryAccess.doesPatientExist(userCredentials.username())) {
                secretaryAccess.createPatient(userCredentials.username(), userCredentials.password());
                adminScreenVIew.successCreateAccount();
            } else {
                adminScreenVIew.failedCreateAccount();
            }
        }
    }

    class CreateDoctorAccount implements Command {

        @Override
        public void execute(ArrayList<String> args) {

            UserCredentials userCredentials = secretaryScreenView.registerDoctorAccount();
            if (!secretaryAccess.doesDoctorExist(userCredentials.username())) {
                secretaryAccess.createDoctor(userCredentials.username(), userCredentials.password());
            } else {
                // need warning message
                adminScreenVIew.failedCreateAccount();
            }
        }
    }

    class ChangePassword implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                secretaryAccess.changeSecretaryPassword(secretaryData, passwordResetDetails.password());
                // need success message
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        }
    }

    class GetLogs implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<LogData> logs = secretaryAccess.getLogs(secretaryData);
            secretaryScreenView.viewUserLogs(logs);
        }
    }

    class BookAppointment implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            AppointmentDayDetails appointmentDayDetails = secretaryScreenView.bookAppointmentDayPrompt();

            int day = appointmentDayDetails.day();
            int month = appointmentDayDetails.month();
            int year = appointmentDayDetails.year();
            String doctor = appointmentDayDetails.doctorUsername();
            String patient = appointmentDayDetails.patientUsername();

            if (secretaryAccess.getPatient(patient).isPresent() && secretaryAccess.getDoctor(doctor).isPresent()) {
                PatientData patientData = secretaryAccess.getPatient(patient).get();
                DoctorData doctorData = secretaryAccess.getDoctor(doctor).get();

                ArrayList<AppointmentData> scheduleData = secretaryAccess.getScheduleData(doctorData, year, month,
                        day);

                appointmentView.viewFullFromList(scheduleData);


                AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();
                secretaryAccess.bookAppointment(patientData, doctorData, year,
                        month, day, appointmentTimeDetails.hour(),
                        appointmentTimeDetails.minute(),
                        appointmentTimeDetails.length());

            } else {
                // need error message
                presenter.errorMessage("Patient or Doctor does not exist");

            }

        }

    }

    class PatientAppointments implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.enterPatientUsernamePrompt();
            if (secretaryAccess.getPatient(username).isPresent()){
                PatientData patientData = secretaryAccess.getPatient(username).get();
                ArrayList<AppointmentData> patientAppointment =
                        secretaryAccess.getPatientAppointmentDataBundles(patientData);
                appointmentView.viewFullFromList(patientAppointment);
            }
        }

    }

    class CancelAppointment implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            String username = secretaryScreenView.enterPatientUsernamePrompt();
            if (secretaryAccess.getPatient(username).isPresent()) {
                PatientData patientData = secretaryAccess.getPatient(username).get();
                ArrayList<AppointmentData> data = secretaryAccess.getPatientAppointmentDataBundles(patientData);
                appointmentView.viewFullFromList(data);
                // need something to prompt which one to remove
                int index = Integer.parseInt(presenter.promptPopup("Enter id"));
                secretaryAccess.removeAppointment(data.get(index));
            }


        }
    }

    class RescheduleAppointment implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            new PatientAppointments();

            }

        }



}
