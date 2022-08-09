import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.Database;
import entities.Availability;
import entities.Clinic;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.AppointmentManager;
import useCases.DoctorManager;
import useCases.PatientManager;
import utilities.DeleteUtils;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppointmentManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 10000)
    public void testBookAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("testdoctor",
                "123456789");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("testpatient",
                "123456789");

        assertTrue("An appointment object should not exist in the database before booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 1, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(appointment.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(appointment.getAppointmentId()).getId());

        /* testing that the appointment object and apppointment in the database are equal by comparing their fields
         and whether they are equal.*/
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

 //   @Test(timeout = 100000)
//    public void testBookInvalidAppointments() {
//        Database originalDatabase = new Database(databaseFolder.toString());
//
//        originalDatabase.setClinic(new Clinic("", "", "","",
//                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
//                        LocalTime.of(17, 0))))));
//
//        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
//        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
//
//        assertTrue("An appointment object should not exist in the database before booking one", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//
//        /*test an invalid appointment booked at a start time before a doctors availability.
//         */
//        AppointmentData invalidAppointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 7, 0, 120);
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment1);
//        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//
//        /*test an invalid appointment booked at an end time before a doctors availability.
//         */
//        AppointmentData invalidAppointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 16, 0, 120);
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment2);
//        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//
//        /*test an invalid appointment booked at an end time and start time not in a doctors availability.
//         */
//        AppointmentData invalidAppointment3 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 20, 0, 120);
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment3);
//        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//
//        /*test an invalid appointment booked at the exact same time as another appointment.
//         */
//        AppointmentData validAppointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 12, 0, 120);
//
//        assertFalse("The valid appointment should exist in the database", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//        assertEquals(validAppointment1.getAppointmentId(), originalDatabase.getAppointmentDatabase()
//                .get(validAppointment1.getAppointmentId()).getId());
//        AppointmentData invalidAppointment4 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 12, 0, 120);
//
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment4);
//
//        /*test an invalid appointment booked at an end time that conflicts with another appointment as another
//         appointment.*/
//        AppointmentData validAppointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 12, 0, 120);
//
//        assertFalse("The valid appointment should exist in the database", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//        assertEquals(validAppointment2.getAppointmentId(), originalDatabase.getAppointmentDatabase()
//                .get(validAppointment2.getAppointmentId()).getId());
//        AppointmentData invalidAppointment5 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 11, 30, 60);
//
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment5);
//
//                /*test an invalid appointment booked at a start time that conflicts with another appointment as another
//         appointment.*/
//        AppointmentData validAppointment3 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 12, 0, 120);
//
//        assertFalse("The valid appointment should exist in the database", originalDatabase
//                .getAppointmentDatabase().getAllIds().isEmpty());
//        assertEquals(validAppointment3.getAppointmentId(), originalDatabase.getAppointmentDatabase()
//                .get(validAppointment3.getAppointmentId()).getId());
//        AppointmentData invalidAppointment6 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
//                doctorData, 2022, 12, 5, 1, 0, 120);
//
//        assertNull("an appointment should be returning null when booking at an invalid time",
//                invalidAppointment6);
//    }


    @Test(timeout = 1000)
    public void testRemoveAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("testdoctor",
                "123456789");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("testpatient",
                "123456789");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 1, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        new AppointmentManager(originalDatabase).removeAppointment(appointment);
        assertTrue("An appointment object should not exist in the database after removing one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

    @Test(timeout = 1000)
    public void testRescheduleAppointment() {

    }
}