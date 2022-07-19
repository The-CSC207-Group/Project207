package controllers;

import dataBundles.*;
import presenter.entityViews.AppointmentView;
import presenter.response.AppointmentTimeDetails;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.SecretaryScreenView;
import presenter.entityViews.PrescriptionView;
import useCases.managers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController {
    PatientData patientData;

    SecretaryController secretaryController;
    PrescriptionView prescriptionView;
    AppointmentManager appointmentManager;
    PrescriptionManager prescriptionManager;
    DoctorManager doctorManager;
    PatientManager patientManager;

    ContactManager contactManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AppointmentView appointmentView = new AppointmentView();

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
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
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllActivePrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }

        };
    }

    private Command ViewPrescriptionHistory() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllPrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }
        };
    }

    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            appointmentView.viewFullFromList(appointments);

        };
    }

    private Command ChangePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            patientManager.changeUserPassword(patientData, passwordResetDetails.password());
        };
    }

    private Command BookAppointment() {
        return (x) -> {
            LocalDate appointmentDayDetails = secretaryScreenView.bookAppointmentDayPrompt();

            int day = appointmentDayDetails.getDayOfMonth();
            int month = appointmentDayDetails.getMonthValue();
            int year = appointmentDayDetails.getYear();
            String doctor = secretaryScreenView.bookAppointmentPatientDoctorPrompt().doctorUsername();


            if (doctorManager.getDoctor(doctor).isPresent()) {
                DoctorData doctorData = doctorManager.getDoctor(doctor).get();
                appointmentView.viewFullFromList(appointmentManager.getScheduleData(doctorData,
                        LocalDate.of(year, month, day)));

                AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();
                appointmentManager.bookAppointment(
                        patientData, doctorData, year, month, day,
                        appointmentTimeDetails.time().getHour(),
                        appointmentTimeDetails.time().getMinute(),
                        appointmentTimeDetails.length());
            } else {

                secretaryScreenView.showDoctorDoesNotExistError();
            }
        };
    }

    private Command PatientAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> patientAppointment =
                    appointmentManager.getPatientAppointments(patientData);
            appointmentView.viewFullFromList(patientAppointment);
        };
    }

    private Command CancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            int index = secretaryScreenView.deleteAppointmentPrompt(contactData, data);
            appointmentManager.removeAppointment(data.get(index));
        };
    }

    private Command RescheduleAppointment() {

        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            int index = secretaryScreenView.rescheduleAppointmentPrompt(contactData, appointments);
            LocalDate day = secretaryScreenView.bookAppointmentDayPrompt();
            AppointmentTimeDetails time = secretaryScreenView.bookAppointmentTimePrompt();
            appointmentManager.rescheduleAppointment(appointments.get(index), day.getYear(), day.getMonthValue(),
                    day.getDayOfMonth(),
                    time.time().getHour(), time.time().getMinute(), time.length());

        };

    }


}
