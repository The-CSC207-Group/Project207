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

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        assertTrue("An appointment object should not exist in the database before booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(appointment.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(appointment.getAppointmentId()).getId());

        /* testing that the appointment object and appointment in the database are equal by comparing their fields
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

    @Test(timeout = 100000)
    public void testBookInvalidAppointmentStartTimeAvailability() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        assertTrue("An appointment object should not exist in the database before booking one", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());

        /*test an invalid appointment booked at a start time before a clinic's availability.
         */
        AppointmentData invalidAppointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 7, 0, 120);
        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment1);
        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
    }

        @Test(timeout = 100000)
        public void testBookInvalidAppointmentEndTimeAvailability() {
            Database originalDatabase = new Database(databaseFolder.toString());

            originalDatabase.setClinic(new Clinic("", "", "", "",
                    new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                            LocalTime.of(17, 0))))));

            DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
            PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
            /*test an invalid appointment booked at an end time before a clinic's availability.
             */
            AppointmentData invalidAppointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                    doctorData, 2022, 12, 5, 16, 0, 120);
            assertNull("an appointment should be returning null when booking at an invalid time",
                    invalidAppointment2);
            assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
                    .getAppointmentDatabase().getAllIds().isEmpty());

        }
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeAvailability() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at an end time and start time not in a clinic's availability.
         */
        AppointmentData invalidAppointment3 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 20, 0, 120);
        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment3);
        assertTrue("An invalid appointment object should not exist in the database after booking", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
    }
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeExistingAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at the exact same time as another appointment.
         */
        AppointmentData validAppointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        assertFalse("The valid appointment should exist in the database", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(validAppointment1.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(validAppointment1.getAppointmentId()).getId());
        AppointmentData invalidAppointment4 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment4);

    }
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentSameEndTime() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at an end time that conflicts with another appointment as another
         appointment.*/
        AppointmentData validAppointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        assertFalse("The valid appointment should exist in the database", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(validAppointment2.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(validAppointment2.getAppointmentId()).getId());
        AppointmentData invalidAppointment5 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 11, 30, 60);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment5);

    }
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentsSameStartTime() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

                /*test an invalid appointment booked at a start time that conflicts with another appointment as another
         appointment.*/
        AppointmentData validAppointment3 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        assertFalse("The valid appointment should exist in the database", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(validAppointment3.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(validAppointment3.getAppointmentId()).getId());
        AppointmentData invalidAppointment6 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 1, 0, 120);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment6);
    }


    @Test(timeout = 100000)
    public void testRemoveAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        new AppointmentManager(originalDatabase).removeAppointment(appointment);
        assertTrue("An appointment object should not exist in the database after removing one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

    }

    @Test(timeout = 1000)
    public void testRescheduleAppointmentValid() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one",
                originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        assertTrue("An reschedule time within a clinic's availability should return true when rescheduling an" +
                        " appointment", new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 12, 0, 60));
    }
    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidEndTimeAvailability() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("A reschedule time that is outside a clinic's end time availability should return false",
                new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 16, 0, 120));
        assertFalse("An appointment object should exist in the database after rescheduling an invalid " +
                        "appointment time", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
    }

    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidStartTimeAvailability() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("A reschedule time that is outside a clinic's start time availability should return false",
                new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 8, 0, 60));
        assertFalse("An appointment object should exist in the database after rescheduling an invalid " +
                "appointment time", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
    }

    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidOutsideAvailability() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("An reschedule time outside a clinic's availability should return false when rescheduling an" +
                " appointment", new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 20, 0, 60));
        assertFalse("An appointment object should exist in the database after rescheduling an invalid " +
                "appointment time", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
    }

    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidConflictAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "","",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);
        AppointmentData appointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 14, 0, 120);

        assertFalse("An reschedule time conflicting with another appointment should return false",
                new AppointmentManager(originalDatabase).rescheduleAppointment(appointment1,
                2022, 12, 5, 14, 0, 60));
        assertTrue("Appointment 1 should still exist in the database",
                originalDatabase.getAppointmentDatabase().getAllIds().contains(appointment1.getAppointmentId()));
        assertTrue("Appointment 2 should still exist in the database",
                originalDatabase.getAppointmentDatabase().getAllIds().contains(appointment2.getAppointmentId()));
    }

    @Test(timeout = 1000)
    public void testGetPatientAppointments() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);
        AppointmentData appointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 14, 0, 120);
        for (AppointmentData patientAppointment : new AppointmentManager(originalDatabase).getPatientAppointments(patientData)){
            assertTrue("Each id of a patient appointment from getPatientAppointment should exist in the database",
                    originalDatabase.getAppointmentDatabase().getAllIds().contains(patientAppointment.getAppointmentId()));
        }
        for (Integer appointmentId: originalDatabase.getAppointmentDatabase().getAllIds()){
            assertTrue("Each appointment in the database relating to the doctor should exist in getDoctorAppointment",
                    new AppointmentManager(originalDatabase).getPatientAppointments(patientData).stream()
                            .map(AppointmentData::getAppointmentId)
                            .anyMatch(x-> x.equals(appointmentId)));
        }
    }

    @Test(timeout = 1000)
    public void testGetDoctorAppointments() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment1 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);
        AppointmentData appointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 14, 0, 120);
        for (AppointmentData doctorAppointment : new AppointmentManager(originalDatabase).getDoctorAppointments(doctorData)){
            assertTrue("Each id of a doctor appointment from getDoctorAppointment should exist in the database",
                    originalDatabase.getAppointmentDatabase().getAllIds().contains(doctorAppointment.getAppointmentId()));
        }
        for (Integer appointmentId: originalDatabase.getAppointmentDatabase().getAllIds()){
            assertTrue("Each appointment in the database relating to the doctor should exist in getDoctorAppointment",
                    new AppointmentManager(originalDatabase).getDoctorAppointments(doctorData).stream()
                            .map(AppointmentData::getAppointmentId)
                            .anyMatch(x-> x.equals(appointmentId)));
        }
    }
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }


}