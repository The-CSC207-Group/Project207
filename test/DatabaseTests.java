import entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import database.Database;
import database.DataMapperGateway;
import Utilities.DeleteUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DatabaseTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testSaveLoadPatientDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> originalPatientDatabase = originalDatabase.getPatientDatabase();

        Patient originalPatient = new
                Patient("jeff", "123", 123456789, "5544");

        Integer patientID = originalPatientDatabase.add(originalPatient);
        originalPatientDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> loadedPatientDatabase = loadedDatabase.getPatientDatabase();

        Patient loadedPatient = loadedPatientDatabase.get(patientID);

        /*Testing if the loaded patient and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original patient and loaded patient should share the same unique username",
                originalPatient.getUsername(), loadedPatient.getUsername());
        assertEquals("Original patient and loaded patient should share the same contact information",
                originalPatient.getContactInfoId(), loadedPatient.getContactInfoId());
        assertEquals("Original patient and loaded patient should share the same health numbers",
                originalPatient.getHealthNumber(), loadedPatient.getHealthNumber());
        assertEquals("Original patient and loaded patient should share the same password",
                originalPatient.comparePassword("123"),
                loadedPatient.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadDoctorDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> originalDoctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor originalDoctor = new
                Doctor("jeff", "123", 123456789);

        Integer doctorID = originalDoctorDatabase.add(originalDoctor);
        originalDoctorDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> loadedDoctorDatabase = loadedDatabase.getDoctorDatabase();

        Doctor loadedDoctor = loadedDoctorDatabase.get(doctorID);

        /*Testing if the loaded doctor and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                originalDoctor.getUsername(), loadedDoctor.getUsername());
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                originalDoctor.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadSecretaryDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> originalSecretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary originalSecretary = new
                Secretary("jeff", "123", 123456789);

        Integer secretaryID = originalSecretaryDatabase.add(originalSecretary);
        originalSecretaryDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> loadedSecretaryDatabase = loadedDatabase.getSecretaryDatabase();

        Secretary loadedSecretary = loadedSecretaryDatabase.get(secretaryID);

        /*Testing if the loaded secretary and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                originalSecretary.getUsername(), loadedSecretary.getUsername());
        assertEquals("Original secretary and loaded secretary should share the same contact information",
                originalSecretary.getContactInfoId(), loadedSecretary.getContactInfoId());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadAdminDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> originalAdminDatabase = originalDatabase.getAdminDatabase();

        Admin originalAdmin = new
                Admin("jeff", "123", 123456789);

        Integer adminID = originalAdminDatabase.add(originalAdmin);
        originalAdminDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> loadedAdminDatabase = loadedDatabase.getAdminDatabase();

        Admin loadedAdmin = loadedAdminDatabase.get(adminID);

        /*Testing if the loaded admin and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original admin and loaded admin should share the same unique username",
                originalAdmin.getUsername(), loadedAdmin.getUsername());
        assertEquals("Original admin and loaded admin should share the same contact information",
                originalAdmin.getContactInfoId(), loadedAdmin.getContactInfoId());
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadPrescriptionDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> originalPrescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2024, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        Prescription originalPrescription = new
                Prescription(zonedDateNoted, "medicine", "healthy", 123,
                456, zonedExpiryDate);

        Integer prescriptionID = originalPrescriptionDatabase.add(originalPrescription);
        originalPrescriptionDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> loadedPrescriptionDatabase = loadedDatabase.getPrescriptionDatabase();

        Prescription loadedPrescription = loadedPrescriptionDatabase.get(prescriptionID);

        /* testing if the loaded prescription and the original prescription are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original prescription and loaded prescription should be have been noted " +
                        "on the same date", originalPrescription.getDateNoted().compareTo(loadedPrescription.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original prescription and loaded prescription should share the same header",
                originalPrescription.getHeader(), loadedPrescription.getHeader());
        assertEquals("Original prescription and loaded prescription should share the same body",
                originalPrescription.getBody(), loadedPrescription.getBody());
        assertEquals("Original prescription and loaded prescription should share the same patient ID",
                originalPrescription.getPatientID(), loadedPrescription.getPatientID());
        assertEquals("Original prescription and loaded prescription should share the same doctor ID",
                originalPrescription.getDoctorID(), loadedPrescription.getDoctorID());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                originalPrescription.getExpiryDate().compareTo(loadedPrescription.
                getExpiryDate()), 0);
    }

    @Test(timeout = 1000)
    public void testSaveLoadReportDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Report> originalReportDatabase = originalDatabase.getReportDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);

        Report originalReport = new
                Report(zonedDateNoted, "medicine", "healthy", 123,
                456);

        Integer reportID = originalReportDatabase.add(originalReport);
        originalReportDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Report> loadedReportDatabase = loadedDatabase.getReportDatabase();

        Report loadedReport = loadedReportDatabase.get(reportID);

        /* testing if the loaded report and the original report are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original report and loaded report should be have been noted " +
                "on the same date", originalReport.getDateNoted().compareTo(loadedReport.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original report and loaded report should share the same header",
                originalReport.getHeader(), loadedReport.getHeader());
        assertEquals("Original report and loaded report should share the same body",
                originalReport.getBody(), loadedReport.getBody());
        assertEquals("Original report and loaded report should share the same patient ID",
                originalReport.getPatientID(), loadedReport.getPatientID());
        assertEquals("Original report and loaded report should share the same doctor ID",
                originalReport.getDoctorID(), loadedReport.getDoctorID());
    }

    @Test(timeout = 1000)
    public void testSaveLoadAppointmentDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Appointment> originalAppointmentDatabase = originalDatabase.getAppointmentDatabase();

        LocalDateTime localStartTime = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localEndTime = LocalDateTime.of(2024,7,1,4,3);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedStartTime = ZonedDateTime.of(localStartTime, torontoID);
        ZonedDateTime zonedEndTime = ZonedDateTime.of(localEndTime, torontoID);
        TimeBlock timeBlock = new TimeBlock(zonedStartTime, zonedEndTime);

        Appointment originalAppointment = new Appointment(timeBlock,  123, 456);

        Integer appointmentID = originalAppointmentDatabase.add(originalAppointment);
        originalAppointmentDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Appointment> loadedAppointmentDatabase = loadedDatabase.getAppointmentDatabase();

        Appointment loadedAppointment = loadedAppointmentDatabase.get(appointmentID);

        /* testing if the loaded appointment and the original appointment are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original appointment and loaded appointment should start at the same time",
                originalAppointment.getTimeBlock().getStartTime().compareTo(loadedAppointment.getTimeBlock().
                        getStartTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original appointment and loaded appointment should end at the same time",
                originalAppointment.getTimeBlock().getEndTime().compareTo(loadedAppointment.getTimeBlock().
                        getEndTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original appointment and loaded appointment should share the same patient ID",
                originalAppointment.getPatientID(), loadedAppointment.getPatientID());
        assertEquals("Original appointment and loaded appointment should share the same doctor ID",
                originalAppointment.getDoctorID(), loadedAppointment.getDoctorID());
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
