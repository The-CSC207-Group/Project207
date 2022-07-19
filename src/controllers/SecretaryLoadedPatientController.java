package controllers;

import dataBundles.*;
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

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
    }

    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("view active prescription", viewActivePrescription());
        commands.put("view all prescriptions", viewPrescriptionHistory());
        commands.put("view appointments", viewAppointments());
        commands.put("change patient password", changePatientPassword());
        commands.put("unload patient", back(secretaryController));
        commands.put("reschedule", rescheduleAppointment());
        commands.put("book", bookAppointment());
        commands.put("cancel", cancelAppointment());
        commands.put("get appointments", patientAppointments());
        return commands;
    }

    private Command viewActivePrescription() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllActivePrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }

        };
    }

    private Command viewPrescriptionHistory() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllPrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }
        };
    }

    private Command viewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            secretaryScreenView.viewAppointments(contactManager.getContactData(patientData), appointments);

        };
    }

    private Command changePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (patientManager.changeUserPassword(patientData, passwordResetDetails.password())) {
                secretaryScreenView.showResetPasswordSuccessMessage();
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
                ;
            }
        };
    }

    private Command bookAppointment() {
        return (x) -> {
            LocalDate date = secretaryScreenView.bookAppointmentDayPrompt();
            if (date == null) {
                secretaryScreenView.showInvalidDateError();
            } else {
                String doctor = secretaryScreenView.bookAppointmentDoctorPrompt();
                DoctorData doctorData = doctorManager.getUserData(doctor);
                if (doctorData == null) {
                    secretaryScreenView.showDoctorDoesNotExistError();
                } else {
                    ArrayList<AppointmentData> appointments = appointmentManager.getScheduleData(doctorData,
                            LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    if (appointments == null) {
                        secretaryScreenView.showNoAvailableAppointmentDayError();
                    } else {
                        viewDoctorSchedule(doctorData, date);
                        AppointmentData appointment = bookAppointmentTime(doctorData, date);
                        if (appointment == null) {
                            secretaryScreenView.showAppointmentConflictError();
                        } else {
                            secretaryScreenView.showBookAppointmentSuccess(contactManager.getContactData(patientData),
                                    contactManager.getContactData(doctorData));
                        }
                    }
                }
            }
        };
    }

    private Command patientAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> patientAppointment =
                    appointmentManager.getPatientAppointments(patientData);
            secretaryScreenView.viewAppointments(contactManager.getContactData(patientData), patientAppointment);

        };
    }

    private Command cancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            int index = secretaryScreenView.deleteAppointmentPrompt(contactData, data);
            appointmentManager.removeAppointment(data.get(index));
        };
    }

    private Command rescheduleAppointment() {

        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            int index = secretaryScreenView.rescheduleAppointmentPrompt(contactData, appointments);
            LocalDate day = secretaryScreenView.bookAppointmentDayPrompt();
            AppointmentTimeDetails time = secretaryScreenView.bookAppointmentTimePrompt();
            appointmentManager.rescheduleAppointment(
                    appointments.get(index), day.getYear(), day.getMonthValue(),
                    day.getDayOfMonth(), time.time().getHour(), time.time().getMinute(), time.length());
        };

    }

    private void viewDoctorSchedule(DoctorData doctorData, LocalDate date) {
        secretaryScreenView.viewAppointments(
                contactManager.getContactData(doctorData),
                appointmentManager.getScheduleData(
                        doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth())));
    }

    public AppointmentData bookAppointmentTime(DoctorData doctorData, LocalDate date) {
        AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();

        return appointmentManager.bookAppointment(
                patientData, doctorData, date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                appointmentTimeDetails.time().getHour(),
                appointmentTimeDetails.time().getMinute(),
                appointmentTimeDetails.length());
    }


}
