package controllers;


import dataBundles.*;
import entities.Appointment;
import entities.AvailabilityData;
import entities.TimeBlock;
import presenter.entityViews.AppointmentView;
import presenter.response.UserCredentials;
import presenter.screenViews.DoctorScreenView;
import presenter.screenViews.PatientScreenView;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.SecretaryAccess;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class SecretaryController extends TerminalController {


    private SecretaryAccess secretaryAccess;
    private SecretaryData secretaryData;
    private SecretaryController self = this;

    private DoctorScreenView doctorScreenView =  new DoctorScreenView();
    private SecretaryScreenView secretaryScreenView = new SecretaryScreenView();


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
        public boolean execute(ArrayList<String> args) {
            String name = presenter.promptPopup("username of patient");
            secretaryAccess.getPatient(name).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new SecretaryLoadedPatientController(getContext(), self,  patientData));

                    }
            );
            return false;
        }
    }

    class CreatePatientAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (secretaryAccess.doesPatientExist(userCredentials.username())){
                secretaryAccess.createPatient(userCredentials.username(), userCredentials.password());
                presenter.successMessage("Successfully created new Patient");}
            else {
                presenter.warningMessage("This username already exists. No new patient account created");}
            return false;
        }
    }
    class CreateDoctorAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {

            UserCredentials userCredentials = secretaryScreenView.registerDoctorAccount();
            if (secretaryAccess.doesDoctorExist(userCredentials.username())){
                secretaryAccess.createDoctor(userCredentials.username(), userCredentials.password());
                presenter.successMessage("Successfully created new doctor");}
            else {
                presenter.warningMessage("This username already exists. No new doctor account created");}
            return false;
        }
    }
    class ChangePassword implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changeSecretaryPassword(secretaryData, p1 );
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = secretaryAccess.getLogs(secretaryData);
            return false;
        }
    }

    class BookAppointment implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String patient = presenter.promptPopup("Enter Patient Username ");
            String doctor = presenter.promptPopup("Enter Doctor Username ");
            if (secretaryAccess.getPatient(patient).isPresent() && secretaryAccess.getDoctor(doctor).isPresent()){
                PatientData patientData = secretaryAccess.getPatient(patient).get();
                DoctorData doctorData = secretaryAccess.getDoctor(doctor).get();
                int year = Integer.parseInt(presenter.promptPopup("Enter Year "));
                int month = Integer.parseInt(presenter.promptPopup("Enter Month "));
                int day = Integer.parseInt(presenter.promptPopup("Enter day "));

                ArrayList<AppointmentData> scheduleData =  secretaryAccess.getScheduleData(doctorData, year, month,
                        day);
                doctorScreenView.viewAppointments(scheduleData);


                Integer hour = Integer.valueOf(presenter.promptPopup("Enter hour "));
                Integer minute = Integer.valueOf(presenter.promptPopup("Enter Minute "));
                Integer len = Integer.valueOf(presenter.promptPopup("Enter length of appointment "));
                return secretaryAccess.bookAppointment(patientData, doctorData, year,
                        month, day, hour, minute, len) != null;

            } else {
                presenter.errorMessage("Patient or Doctor does not exist");

            }
            return false;
        }

    }

    class PatientAppointments implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter username ");
            if (secretaryAccess.getPatient(username).isPresent()){
                PatientData patientData = secretaryAccess.getPatient(username).get();
                ArrayList<AppointmentData> patientAppointment =
                        secretaryAccess.getPatientAppointmentDataBundles(patientData);
                for (AppointmentData data: patientAppointment){
                    presenter.infoMessage(data.getTimeBlock().toString());
                }

            }
            return false;
        }
    }






}
