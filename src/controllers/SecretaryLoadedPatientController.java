package controllers;

import dataBundles.*;
import useCases.accessClasses.SecretaryAccess;
import presenter.entityViews.AppointmentView;
import presenter.response.AppointmentDayDetails;
import presenter.response.AppointmentTimeDetails;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.AdminScreenView;
import presenter.screenViews.SecretaryScreenView;
import presenter.entityViews.PrescriptionView;
import useCases.managers.AppointmentManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController {
    PatientData patientData;

    SecretaryData secretaryData;
    SecretaryAccess secretaryAccess;
    SecretaryController secretaryController;
    PrescriptionView prescriptionView;
    AppointmentManager appointmentManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AppointmentView appointmentView = new AppointmentView();
    private final AdminScreenView adminScreenVIew = new AdminScreenView();

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
        this.secretaryAccess = new SecretaryAccess(getDatabase());
    }


    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("view active prescription", ViewActivePrescription());
        commands.put("view all prescriptions", ViewPrescriptionHistory());
        commands.put("view appointments", ViewAppointments());
        commands.put("change patient password", ChangePatientPassword());
        commands.put("unload patient", back(secretaryController));
        commands.put("reschedule", RescheduleAppointment());
        commands.put("book", BookAppointment());
        commands.put("cancel", CancelAppointment());
        commands.put("get appointments", PatientAppointments());
        return commands;
    }

    private Command ViewActivePrescription() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.
                    getActivePrescriptions(patientData.getUsername());
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }

        };
    }

    private Command ViewPrescriptionHistory() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.getAllPrescriptions(patientData.getUsername());
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }
        };
    }

    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = secretaryAccess.getPatientAppointmentDataBundles(patientData);
            appointmentView.viewFullFromList(appointments);

        };
    }

    private Command ChangePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            secretaryAccess.changePatientPassword(patientData, passwordResetDetails.password());
        };
    }

    private Command BookAppointment() {
        return (x) -> {
            AppointmentDayDetails appointmentDayDetails = secretaryScreenView.bookAppointmentDayPrompt();

            int day = appointmentDayDetails.day();
            int month = appointmentDayDetails.month();
            int year = appointmentDayDetails.year();
            String doctor = appointmentDayDetails.doctorUsername();

            if (secretaryAccess.getDoctor(doctor).isPresent()) {
                DoctorData doctorData = secretaryAccess.getDoctor(doctor).get();
                appointmentView.viewFullFromList(secretaryAccess.getScheduleData(doctorData, year, month, day));

                AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();
                secretaryAccess.bookAppointment(
                        patientData, doctorData, year, month, day,
                        appointmentTimeDetails.hour(),
                        appointmentTimeDetails.minute(),
                        appointmentTimeDetails.length());
            } else {
                // need error message
                presenter.errorMessage("Doctor does not exist");
            }
        };


    }

    private Command PatientAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> patientAppointment =
                    secretaryAccess.getPatientAppointmentDataBundles(patientData);
            appointmentView.viewFullFromList(patientAppointment);
        };
    }

    private Command CancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = secretaryAccess.getPatientAppointmentDataBundles(patientData);
            appointmentView.viewFullFromList(data);
            // need something to prompt which one to remove
            int index = Integer.parseInt(presenter.promptPopup("Enter id"));
            secretaryAccess.removeAppointment(data.get(index));
        };
    }

    private Command RescheduleAppointment() {

        return (x) -> {
            ArrayList<AppointmentData> appointments = secretaryAccess.getPatientAppointmentDataBundles(patientData);
            appointmentView.viewFullFromList(appointments);
            // need something to prompt which one to change
            int index = Integer.parseInt(presenter.promptPopup("Enter id"));
            AppointmentDayDetails day = secretaryScreenView.bookAppointmentDayPrompt();
            AppointmentTimeDetails time = secretaryScreenView.bookAppointmentTimePrompt();
            appointmentManager.rescheduleAppointment(appointments.get(index), day.year(), day.month(), day.day(),
                    time.hour(), time.minute(), time.length());

        };

    }


}
