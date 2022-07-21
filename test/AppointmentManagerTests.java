import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Appointment;
import entities.Availability;
import entities.Clinic;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppointmentManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 10000)
    public void testBookAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        assertTrue("An appointment object should not exist in the database before booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 1, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(appointment.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(appointment.getAppointmentId()).getId());

        /* testing that the appointment object and apppointment in the database are equal by comparing their fields
         and whether they are equal*/
        assertEquals("appointment should share the same Id as the appointment in the database",
                appointment.getAppointmentId(), originalDatabase.getAppointmentDatabase().get(appointment
                        .getAppointmentId()).getId());
        assertEquals("appointment should share the same patientId as the appointment in the database",
                appointment.getPatientId(), originalDatabase.getAppointmentDatabase().get(appointment
                        .getAppointmentId()).getPatientId());
        assertEquals("appointment should share the same doctorId as the appointment in the database",
                appointment.getDoctorId(), originalDatabase.getAppointmentDatabase().get(appointment
                        .getAppointmentId()).getDoctorId());
        assertEquals("appointment should share the same start time as the appointment in the database",
                appointment.getTimeBlock().getStartTime(), originalDatabase.getAppointmentDatabase().get(appointment
                        .getAppointmentId()).getTimeBlock().getStartTime());
        assertEquals("appointment should share the same end time as the appointment in the database",
                appointment.getTimeBlock().getEndTime(), originalDatabase.getAppointmentDatabase().get(appointment
                        .getAppointmentId()).getTimeBlock().getEndTime());

    }

    @Test(timeout = 1000)
    public void testBookInvalidAppointments() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        assertTrue("An appointment object should not exist in the database before booking one", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());

        /*test an invalid appointment booked at a start time before a doctors availability
         */
        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 1, 7, 0, 120);
        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);
        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());

    }

    @Test(timeout = 1000)
    public void testRemoveAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 1, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        new AppointmentManager(originalDatabase).removeAppointment(appointment);
        assertTrue("An appointment object should not exist in the database after removing one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

    }

    @Test(timeout = 1000)
    public void testRescheduleAppointment() {

    }
}