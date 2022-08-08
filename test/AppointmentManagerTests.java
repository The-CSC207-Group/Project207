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

/**
 * A class of unit tests for AppointmentManager
 */
public class AppointmentManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    /**
     * tests bookAppointment to ensure an appointment is created in the database, and checking if the appointment object
     * and the appointment stored in the database have the same values.
     */
    @Test(timeout = 10000)
    public void testBookAppointment() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        checkObjectExistsInDatabase(originalDatabase, false);

        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        checkObjectExistsInDatabase(originalDatabase, true);
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

    /**
     * Tests booking an invalid appointment that starts before the clinic's availability start time.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentStartTimeAvailability() {
        Database originalDatabase = createStandardDatabase();

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
        checkObjectExistsInDatabase(originalDatabase, false);
    }

    /**
     * Tests booking an invalid appointment that ends after the clinic's availability end time.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentEndTimeAvailability() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        /*test an invalid appointment booked at an end time before a clinic's availability.
         */
        AppointmentData invalidAppointment2 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 16, 0, 120);
        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment2);
        checkObjectExistsInDatabase(originalDatabase, false);
    }

    /**
     * Tests booking an invalid appointment with start and end time outside a clinic's availability.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeAvailability() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at an end time and start time not in a clinic's availability.
         */
        AppointmentData invalidAppointment3 = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 20, 0, 120);
        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment3);
        checkObjectExistsInDatabase(originalDatabase, false);
    }

    /**
     * Tests booking an invalid appointment at the exact same time as another appointment
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeExistingAppointment() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at the exact same time as another appointment.
         */
        AppointmentData validAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        checkValidAppointmentExistsInDatabase(originalDatabase, validAppointment);
        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);

    }

    /**
     * Tests booking an invalid appointment completely inside another appointment's start time and end time.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeExistingAppointmentInside() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at the exact same time as another appointment.
         */
        AppointmentData validAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 60);

        checkValidAppointmentExistsInDatabase(originalDatabase, validAppointment);

        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 11, 0, 180);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);
    }

    /**
     *Tests booking an invalid appointment that would result in a pre-existing appointment being inside it's start
     * time and end time.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentTimeExistingAppointmentOutside() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at the exact same time as another appointment.
         */
        AppointmentData validAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 11, 0, 180);

        checkValidAppointmentExistsInDatabase(originalDatabase, validAppointment);
        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 60);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);
    }

    /**
     * Tests booking an invalid appointment that starts at the same end time as an existing appointment only.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentSameEndTime() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        /*test an invalid appointment booked at an end time that conflicts with another appointment as another
         appointment.*/
        AppointmentData validAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        checkValidAppointmentExistsInDatabase(originalDatabase, validAppointment);
        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 11, 30, 60);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);

    }

    /**
     * Tests booking an invalid appointment that starts at the same start time as an existing appointment only.
     */
    @Test(timeout = 100000)
    public void testBookInvalidAppointmentsSameStartTime() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

                /*test an invalid appointment booked at a start time that conflicts with another appointment as another
         appointment.*/
        AppointmentData validAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 12, 0, 120);

        checkValidAppointmentExistsInDatabase(originalDatabase, validAppointment);
        AppointmentData invalidAppointment = new AppointmentManager(originalDatabase).bookAppointment(patientData,
                doctorData, 2022, 12, 5, 1, 0, 120);

        assertNull("an appointment should be returning null when booking at an invalid time",
                invalidAppointment);
    }

    /**
     * Tests removing an appointment from the database.
     */
    @Test(timeout = 100000)
    public void testRemoveAppointment() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        checkObjectExistsInDatabase(originalDatabase, true);
        new AppointmentManager(originalDatabase).removeAppointment(appointment);
        checkObjectExistsInDatabase(originalDatabase, false);

    }

    /**
     * Tests a valid use of rescheduling an appointment.
     */
    @Test(timeout = 1000)
    public void testRescheduleAppointmentValid() {
        Database originalDatabase = createStandardDatabase();
        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        checkObjectExistsInDatabase(originalDatabase, true);
        assertTrue("An reschedule time within a clinic's availability should return true when rescheduling an" +
                " appointment", new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 12, 0, 60));
    }

    /**
     * Tests rescheduling an appointment at an invalid end time that is after a clinic's availability end time.
     */
    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidEndTimeAvailability() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("A reschedule time that is outside a clinic's end time availability should return false",
                new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                        2022, 12, 5, 16, 0, 120));
        checkObjectExistsInDatabase(originalDatabase, true);
    }

    /**
     * Tests rescheduling an appointment at an invalid end time that is before a clinic's availability start time.
     */
    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidStartTimeAvailability() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("A reschedule time that is outside a clinic's start time availability should return false",
                new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                        2022, 12, 5, 8, 0, 60));
        checkObjectExistsInDatabase(originalDatabase, true);
    }

    /**
     * Tests rescheduling an appointment at an invalid time completely outside a clinic's availability's end time
     * and start time.
     */
    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidOutsideAvailability() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);

        assertFalse("An reschedule time outside a clinic's availability should return false when rescheduling an" +
                " appointment", new AppointmentManager(originalDatabase).rescheduleAppointment(appointment,
                2022, 12, 5, 20, 0, 60));
        checkObjectExistsInDatabase(originalDatabase, true);
    }

    /**
     * Test rescheduling an appointment at an invalid time conflicting with an existing appointment.
     */
    @Test(timeout = 1000)
    public void testRescheduleAppointmentInvalidConflictAppointment() {
        Database originalDatabase = createStandardDatabase();

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

    /**
     * Tests that getPatientAppointments returns appointments that are in the database, and vice versa.
     */
    @Test(timeout = 1000)
    public void testGetPatientAppointments() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);
        new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 14, 0, 120);

        checkGetAppointmentWithDatabase(originalDatabase, patientData);

    }
    /**
     * Tests that getDoctorAppointments returns appointments that are in the database, and vice versa.
     */
    @Test(timeout = 1000)
    public void testGetDoctorAppointments() {
        Database originalDatabase = createStandardDatabase();

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 10, 0, 120);
        new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData,
                2022, 12, 5, 14, 0, 120);

        checkGetAppointmentWithDatabase(originalDatabase, doctorData);

    }

    /**
     * Tests that getAvailabilityFromDayOfWeek has the same data as the availability stored in the clinic.
     */
    @Test(timeout = 1000)
    public void testGetAvailabilityFromDayOfWeek() {
        Database originalDatabase = createStandardDatabase();
        /*
        if start time, end time, and day of week are the same between AvailabilityData from getDayOfWeek and the Availability
        stored in the database, they are the same.
         */
        assertEquals("getAvailabilityDayOfWeek should return the same availability start time as the one stored" +
                "in the database", new AppointmentManager(originalDatabase).getAvailabilityFromDayOfWeek(
                DayOfWeek.of(1)).startTime(), originalDatabase.getClinic().getClinicHours().get(0).startTime());

        assertEquals("getAvailabilityDayOfWeek should return the same availability end time as the one stored" +
                "in the database", new AppointmentManager(originalDatabase).getAvailabilityFromDayOfWeek(
                DayOfWeek.of(1)).endTime(), originalDatabase.getClinic().getClinicHours().get(0).endTime());

        assertEquals("getAvailabilityDayOfWeek should return the same availability dayOfWeek as the one stored" +
                "in the database", new AppointmentManager(originalDatabase).getAvailabilityFromDayOfWeek(
                DayOfWeek.of(1)).getDayOfWeek(), originalDatabase.getClinic().getClinicHours().get(0).dayOfWeek());
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

    private Database createStandardDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());

        Availability availability = new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                LocalTime.of(17, 0));
        originalDatabase.setClinic(new Clinic("", "", "", "",
                new ArrayList<>(List.of(availability))));
        return originalDatabase;
    }

    private void checkValidAppointmentExistsInDatabase(Database originalDatabase, AppointmentData validAppointment) {

        assertFalse("The valid appointment should exist in the database", originalDatabase
                .getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals("The id of the valid appointment and the appointment stored in the database should be the " +
                "same", validAppointment.getAppointmentId(), originalDatabase.getAppointmentDatabase()
                .get(validAppointment.getAppointmentId()).getId());
    }

    private void checkGetAppointmentWithDatabase(Database originalDatabase, DoctorData doctorData) {
        for (AppointmentData doctorAppointment : new AppointmentManager(originalDatabase).getDoctorAppointments(doctorData)) {
            assertTrue("Each id of a doctor appointment from getDoctorAppointment should exist in the database",
                    originalDatabase.getAppointmentDatabase().getAllIds().contains(doctorAppointment.getAppointmentId()));
        }
        for (Integer appointmentId : originalDatabase.getAppointmentDatabase().getAllIds()) {
            assertTrue("Each appointment in the database relating to the doctor should exist in getDoctorAppointment",
                    new AppointmentManager(originalDatabase).getDoctorAppointments(doctorData).stream()
                            .map(AppointmentData::getAppointmentId)
                            .anyMatch(x -> x.equals(appointmentId)));
        }
    }

    private void checkGetAppointmentWithDatabase(Database originalDatabase, PatientData patientData) {
        for (AppointmentData patientAppointment : new AppointmentManager(originalDatabase).getPatientAppointments(patientData)) {
            assertTrue("Each id of a patient appointment from getPatientAppointment should exist in the database",
                    originalDatabase.getAppointmentDatabase().getAllIds().contains(patientAppointment.getAppointmentId()));
        }
        for (Integer appointmentId : originalDatabase.getAppointmentDatabase().getAllIds()) {
            assertTrue("Each appointment in the database relating to the doctor should exist in getDoctorAppointment",
                    new AppointmentManager(originalDatabase).getPatientAppointments(patientData).stream()
                            .map(AppointmentData::getAppointmentId)
                            .anyMatch(x -> x.equals(appointmentId)));
        }
    }
    private void checkObjectExistsInDatabase(Database originalDatabase, boolean desiredResult){
        if (desiredResult){
            assertFalse("An object added to to the database should result in the databases getAllIds having a " +
                    "size > 1", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        }
        else {
            assertTrue("An object added to to the database should result in the databases getAllIds having a " +
                    "size > 1", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        }
    }
}