import database.DataMapperGateway;
import database.Database;
import entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import utilities.DeleteUtils;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class of unit tests for the program's databases.
 */
public class DatabaseTests {

    private final String username = "mynamejeff";
    private final String password = "123456789";
    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public final TemporaryFolder databaseFolder = new TemporaryFolder();
    private Database originalDatabase;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        originalDatabase = new Database(databaseFolder.toString());
    }

    /**
     * Tests the saving and loading functionality of patient databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadPatientDatabase() {
        DataMapperGateway<Patient> originalPatientDatabase = originalDatabase.getPatientDatabase();

        Patient originalPatient = new Patient(username, password, 123456789, "5544");

        Integer patientID = originalPatientDatabase.add(originalPatient);
        originalPatientDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> loadedPatientDatabase = loadedDatabase.getPatientDatabase();

        Patient loadedPatient = loadedPatientDatabase.get(patientID);

        /* Testing if the loaded patient and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original patient and loaded patient should share the same Id",
                originalPatient.getId(), loadedPatient.getId());
        assertEquals("Original patient and loaded patient should share the same unique username",
                originalPatient.getUsername(), loadedPatient.getUsername());
        assertEquals("Original patient and loaded patient should share the same contact information",
                originalPatient.getContactInfoId(), loadedPatient.getContactInfoId());
        assertEquals("Original patient and loaded patient should share the same health numbers",
                originalPatient.getHealthNumber(), loadedPatient.getHealthNumber());
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword(password));
    }

    /**
     * Tests the saving and loading functionality of doctor databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadDoctorDatabase() {
        DataMapperGateway<Doctor> originalDoctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor originalDoctor = new
                Doctor(username, password, 123456789);

        Integer doctorID = originalDoctorDatabase.add(originalDoctor);
        originalDoctorDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> loadedDoctorDatabase = loadedDatabase.getDoctorDatabase();

        Doctor loadedDoctor = loadedDoctorDatabase.get(doctorID);

        /* Testing if the loaded doctor and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                originalDoctor.getUsername(), loadedDoctor.getUsername());
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                originalDoctor.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword(password));
    }

    /**
     * Tests the saving and loading functionality of secretary databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadSecretaryDatabase() {
        DataMapperGateway<Secretary> originalSecretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary originalSecretary = new
                Secretary(username, password, 123456789);

        Integer secretaryID = originalSecretaryDatabase.add(originalSecretary);
        originalSecretaryDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> loadedSecretaryDatabase = loadedDatabase.getSecretaryDatabase();

        Secretary loadedSecretary = loadedSecretaryDatabase.get(secretaryID);

        /* Testing if the loaded secretary and the original secretary are equal by testing whether all the fields of
        both objects are equal */
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                originalSecretary.getUsername(), loadedSecretary.getUsername());
        assertEquals("Original secretary and loaded secretary should share the same contact information",
                originalSecretary.getContactInfoId(), loadedSecretary.getContactInfoId());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword(password));
    }

    /**
     * Tests the saving and loading functionality of admin databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadAdminDatabase() {
        DataMapperGateway<Admin> originalAdminDatabase = originalDatabase.getAdminDatabase();

        Admin originalAdmin = new
                Admin(username, password, 123456789);

        Integer adminID = originalAdminDatabase.add(originalAdmin);
        originalAdminDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> loadedAdminDatabase = loadedDatabase.getAdminDatabase();

        Admin loadedAdmin = loadedAdminDatabase.get(adminID);

        /* Testing if the loaded admin and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original admin and loaded admin should share the same unique username",
                originalAdmin.getUsername(), loadedAdmin.getUsername());
        assertEquals("Original admin and loaded admin should share the same contact information",
                originalAdmin.getContactInfoId(), loadedAdmin.getContactInfoId());
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword(password));
    }

    /**
     * Tests the saving and loading functionality of prescription databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadPrescriptionDatabase() {
        DataMapperGateway<Prescription> originalPrescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDate localExpiryDate = LocalDate.of(2024, 7, 1);

        Prescription originalPrescription = new
                Prescription("medicine", "healthy", 123,
                456, localExpiryDate);

        Integer prescriptionID = originalPrescriptionDatabase.add(originalPrescription);
        originalPrescriptionDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> loadedPrescriptionDatabase = loadedDatabase.getPrescriptionDatabase();

        Prescription loadedPrescription = loadedPrescriptionDatabase.get(prescriptionID);

        /* Testing if the loaded prescription and the original prescription are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original prescription and loaded prescription should be have been noted " +
                "on the same date", originalPrescription.getDateNoted().compareTo(loadedPrescription.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original prescription and loaded prescription should share the same header",
                originalPrescription.getHeader(), loadedPrescription.getHeader());
        assertEquals("Original prescription and loaded prescription should share the same body",
                originalPrescription.getBody(), loadedPrescription.getBody());
        assertEquals("Original prescription and loaded prescription should share the same patient ID",
                originalPrescription.getPatientId(), loadedPrescription.getPatientId());
        assertEquals("Original prescription and loaded prescription should share the same doctor ID",
                originalPrescription.getDoctorId(), loadedPrescription.getDoctorId());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                originalPrescription.getExpiryDate().compareTo(loadedPrescription.
                        getExpiryDate()), 0);
    }

    /**
     * Tests the saving and loading functionality of report databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadReportDatabase() {
        DataMapperGateway<Report> originalReportDatabase = originalDatabase.getReportDatabase();

        Report originalReport = new
                Report("medicine", "healthy", 123,
                456);

        Integer reportID = originalReportDatabase.add(originalReport);
        originalReportDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Report> loadedReportDatabase = loadedDatabase.getReportDatabase();

        Report loadedReport = loadedReportDatabase.get(reportID);

        /* Testing if the loaded report and the original report are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original report and loaded report should be have been noted " +
                "on the same date", originalReport.getDateNoted().compareTo(loadedReport.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original report and loaded report should share the same header",
                originalReport.getHeader(), loadedReport.getHeader());
        assertEquals("Original report and loaded report should share the same body",
                originalReport.getBody(), loadedReport.getBody());
        assertEquals("Original report and loaded report should share the same patient ID",
                originalReport.getPatientId(), loadedReport.getPatientId());
        assertEquals("Original report and loaded report should share the same doctor ID",
                originalReport.getDoctorId(), loadedReport.getDoctorId());
    }

    /**
     * Tests the saving and loading functionality of appointment databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadAppointmentDatabase() {
        DataMapperGateway<Appointment> originalAppointmentDatabase = originalDatabase.getAppointmentDatabase();

        LocalDateTime localStartTime = LocalDateTime.of(2022, 7, 1, 4, 3);
        LocalDateTime localEndTime = LocalDateTime.of(2022, 7, 1, 6, 3);
        TimeBlock timeBlock = new TimeBlock(localStartTime, localEndTime);

        Appointment originalAppointment = new Appointment(timeBlock, 123, 456);

        Integer appointmentID = originalAppointmentDatabase.add(originalAppointment);
        originalAppointmentDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Appointment> loadedAppointmentDatabase = loadedDatabase.getAppointmentDatabase();

        Appointment loadedAppointment = loadedAppointmentDatabase.get(appointmentID);

        /* Testing if the loaded appointment and the original appointment are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original appointment and loaded appointment should start at the same time",
                originalAppointment.getTimeBlock().getStartDateTime().compareTo(loadedAppointment.getTimeBlock().
                        getStartDateTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original appointment and loaded appointment should end at the same time",
                originalAppointment.getTimeBlock().getEndDateTime().compareTo(loadedAppointment.getTimeBlock().
                        getEndDateTime()), 0);
        assertEquals("Original appointment and loaded appointment should share the same patient ID",
                originalAppointment.getPatientId(), loadedAppointment.getPatientId());
        assertEquals("Original appointment and loaded appointment should share the same doctor ID",
                originalAppointment.getDoctorId(), loadedAppointment.getDoctorId());
    }

    /**
     * Tests the saving and loading functionality of log databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadLogDatabase() {
        DataMapperGateway<Log> originalLogDatabase = originalDatabase.getLogDatabase();

        Log originalLog = new Log(21, username);

        Integer logID = originalLogDatabase.add(originalLog);
        originalLogDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Log> loadedLogDatabase = loadedDatabase.getLogDatabase();

        Log loadedLog = loadedLogDatabase.get(logID);

        /* Testing if the loaded log and the original log are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original log and loaded log should share the same UserID",
                originalLog.getUserId(), loadedLog.getUserId());
        assertEquals("Original log and loaded log should share the same timestamp",
                originalLog.getTime(), loadedLog.getTime());
        assertEquals("Original log and loaded log should share the same message",
                originalLog.getMessage(), loadedLog.getMessage());
    }

    /**
     * Tests the saving and loading functionality of contact databases.
     */
    @Test(timeout = 1000)
    public void testSaveLoadContactDatabase() {
        DataMapperGateway<Contact> originalContactDatabase = originalDatabase.getContactDatabase();

        LocalDate birthday = LocalDate.of(2022, 1, 1);
        Contact originalContact = new Contact(username, "jeff@gmail.com", "12345678",
                "jeff street", birthday, "jim", "jim@gmail.com",
                "87654321", "father");

        Integer contactID = originalContactDatabase.add(originalContact);
        originalContactDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Contact> loadedContactDatabase = loadedDatabase.getContactDatabase();

        Contact loadedContact = loadedContactDatabase.get(contactID);

        /* Testing if the loaded contact and the original contact are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original contact and loaded contact should share the same name",
                originalContact.getName(), loadedContact.getName());
        assertEquals("Original contact and loaded contact should share the same email",
                originalContact.getEmail(), loadedContact.getEmail());
        assertEquals("Original contact and loaded contact should share the same phone number",
                originalContact.getPhoneNumber(), loadedContact.getPhoneNumber());
        assertEquals("Original contact and loaded contact should share the same address",
                originalContact.getAddress(), loadedContact.getAddress());
        assertEquals("Original contact and loaded contact should share the same birthday",
                originalContact.getBirthday(), loadedContact.getBirthday());
        assertEquals("Original contact and loaded contact should share the same emergency contact name",
                originalContact.getEmergencyContactName(), loadedContact.getEmergencyContactName());
        assertEquals("Original contact and loaded contact should share the same emergency contact email",
                originalContact.getEmergencyContactEmail(), loadedContact.getEmergencyContactEmail());
        assertEquals("Original contact and loaded contact should share the same emergency contact phone number",
                originalContact.getEmergencyContactPhoneNumber(), loadedContact.getEmergencyContactPhoneNumber());
        assertEquals("Original contact and loaded contact should share the same emergency relationship",
                originalContact.getEmergencyRelationship(), loadedContact.getEmergencyRelationship());
    }

    /**
     * Tests the getting and setting clinic entity functionality of main database of the program.
     */
    @Test(timeout = 1000)
    public void testSetGetClinic() {
        ArrayList<Availability> clinicHours = new ArrayList<>();
        clinicHours.add(new Availability(DayOfWeek.of(1), LocalTime.of(8, 0), LocalTime.of(20, 0)));
        Clinic originalClinic = new Clinic("jeff clinic", "12345678", "abc@gmail.com",
                "21 jump street", clinicHours);

        originalDatabase.setClinic(originalClinic);

        originalDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        Clinic loadedClinic = loadedDatabase.getClinic();

        /* Testing if the loaded clinic and the original clinic are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original clinic and loaded clinic should share the same name",
                originalClinic.getName(), loadedClinic.getName());
        assertEquals("Original clinic and loaded clinic should share the same phone number",
                originalClinic.getPhoneNumber(), loadedClinic.getPhoneNumber());
        assertEquals("Original clinic and loaded clinic should share the same address",
                originalClinic.getAddress(), loadedClinic.getAddress());
        assertEquals("Original clinic and loaded clinic should start at the same time",
                originalClinic.getClinicHours().get(0).getStartTime().compareTo(loadedClinic.getClinicHours().get(0).
                        getStartTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original clinic and loaded clinic should end at the same time",
                originalClinic.getClinicHours().get(0).getEndTime().compareTo(loadedClinic.getClinicHours().get(0).
                        getEndTime()), 0);
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
