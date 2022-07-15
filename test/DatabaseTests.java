import entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import database.Database;
import database.DataMapperGateway;
import utilities.DeleteUtils;

import java.io.File;
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

        for (int i = 1; i <= 3; i++) {
            originalPatient.addLogId(i);
            originalPatient.addReportId(i + 10);
        }

        Integer patientID = originalPatientDatabase.add(originalPatient);

        assertEquals("Original patient should share the same ID from the database",
                originalPatient.getId(), patientID);

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
        assertEquals("Original patient and loaded patient should share the same reports",
                originalPatient.getReportIds(), loadedPatient.getReportIds());
        assertEquals("Original patient and loaded patient should share the same logs",
                originalPatient.getLogIds(), loadedPatient.getLogIds());
        assertEquals("Original patient and loaded patient should share the same contact information",
                originalPatient.getContactInfoId(), loadedPatient.getContactInfoId());
        assertEquals("Original patient and loaded patient should share the same health numbers",
                originalPatient.getHealthNumber(), loadedPatient.getHealthNumber());
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadDoctorDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> originalDoctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor originalDoctor = new
                Doctor("jeff", "123", 123456789);

        for (int i = 1; i <= 3; i++) {
            originalDoctor.addLogId(i);
        }

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
        assertEquals("Original doctor and loaded doctor should share the same logs",
                originalDoctor.getLogIds(), originalDoctor.getLogIds());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadSecretaryDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> originalSecretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary originalSecretary = new
                Secretary("jeff", "123", 123456789);

        for (int i = 1; i <= 3; i++) {
            originalSecretary.addLogId(i);
        }

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
        assertEquals("Original secretary and loaded secretary should share the same logs",
                originalSecretary.getLogIds(), originalSecretary.getLogIds());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadAdminDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> originalAdminDatabase = originalDatabase.getAdminDatabase();

        Admin originalAdmin = new
                Admin("jeff", "123", 123456789);

        for (int i = 1; i <= 3; i++) {
            originalAdmin.addLogId(i);
        }

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
        assertEquals("Original admin and loaded admin should share the same logs",
                originalAdmin.getLogIds(), originalAdmin.getLogIds());
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

    @Test(timeout = 1000)
    public void testSaveLoadAppointmentDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Appointment> originalAppointmentDatabase = originalDatabase.getAppointmentDatabase();

        LocalDateTime localStartTime = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localEndTime = LocalDateTime.of(2022,7,1,6,3);
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

        /* Testing if the loaded appointment and the original appointment are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original appointment and loaded appointment should start at the same time",
                originalAppointment.getTimeBlock().getStartTime().compareTo(loadedAppointment.getTimeBlock().
                        getStartTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original appointment and loaded appointment should end at the same time",
                originalAppointment.getTimeBlock().getEndTime().compareTo(loadedAppointment.getTimeBlock().
                        getEndTime()), 0);
        assertEquals("Original appointment and loaded appointment should share the same patient ID",
                originalAppointment.getPatientId(), loadedAppointment.getPatientId());
        assertEquals("Original appointment and loaded appointment should share the same doctor ID",
                originalAppointment.getDoctorId(), loadedAppointment.getDoctorId());
    }

    @Test(timeout = 1000)
    public void testSaveLoadLogDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Log> originalLogDatabase = originalDatabase.getLogDatabase();

        Log originalLog = new Log("jeff");

        Integer logID = originalLogDatabase.add(originalLog);
        originalLogDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Log> loadedLogDatabase = loadedDatabase.getLogDatabase();

        Log loadedLog = loadedLogDatabase.get(logID);

        /* Testing if the loaded log and the original log are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original log and loaded log should share the same timestamp",
                originalLog.getTime(), loadedLog.getTime());
        assertEquals("Original log and loaded log should share the same message",
                originalLog.getMessage(), loadedLog.getMessage());
    }

    @Test(timeout = 1000)
    public void testSaveLoadContactDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Contact> originalContactDatabase = originalDatabase.getContactDatabase();

        LocalDate birthday = LocalDate.of(2022, 1, 1);
        Contact originalContact = new Contact("jeff", "jeff@gmail.com", "12345678",
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

    @Test(timeout = 1000)
    public void testSaveLoadClinicDatabase() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Clinic> originalClinicDatabase = originalDatabase.getClinicDatabase();

        LocalDateTime localStartTime = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localEndTime = LocalDateTime.of(2022,7,1,6,3);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedStartTime = ZonedDateTime.of(localStartTime, torontoID);
        ZonedDateTime zonedEndTime = ZonedDateTime.of(localEndTime, torontoID);
        TimeBlock timeBlock = new TimeBlock(zonedStartTime, zonedEndTime);

        Clinic originalClinic = new Clinic("jeff clinic",  "12345678", "21 jump street",
                torontoID, timeBlock);

        Integer clinicID = originalClinicDatabase.add(originalClinic);
        originalClinicDatabase.save();

        Database loadedDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Clinic> loadedClinicDatabase = loadedDatabase.getClinicDatabase();

        Clinic loadedClinic = loadedClinicDatabase.get(clinicID);

        /* Testing if the loaded clinic and the original clinic are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original clinic and loaded clinic should share the same name",
                originalClinic.getName(), loadedClinic.getName());
        assertEquals("Original clinic and loaded clinic should share the same phone number",
                originalClinic.getPhoneNumber(), loadedClinic.getPhoneNumber());
        assertEquals("Original clinic and loaded clinic should share the same address",
                originalClinic.getAddress(), loadedClinic.getAddress());
        assertEquals("Original clinic and loaded clinic should share the same time zone",
                originalClinic.getTimeZone().toString(), loadedClinic.getTimeZone().toString());
        assertEquals("Original clinic and loaded clinic should start at the same time",
                originalClinic.getClinicHours().getStartTime().compareTo(loadedClinic.getClinicHours().
                        getStartTime()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original clinic and loaded clinic should end at the same time",
                originalClinic.getClinicHours().getEndTime().compareTo(loadedClinic.getClinicHours().
                        getEndTime()), 0);
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
