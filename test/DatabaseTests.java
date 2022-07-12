import entities.Doctor;
import entities.Patient;
import entities.Secretary;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import database.Database;
import database.DataMapperGateway;
import Utilities.DeleteUtils;

import java.io.File;
import java.io.IOException;

public class DatabaseTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testSaveLoadPatientDatabase() throws IOException {
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
    public void testSaveLoadDoctorDatabase() throws IOException {
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
        assertEquals("Original doctor and loaded doctor should share the same password",
                originalDoctor.comparePassword("123"),
                loadedDoctor.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testSaveLoadSecretaryDatabase() throws IOException {
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
        assertEquals("Original secretary and loaded secretary should share the same password",
                originalSecretary.comparePassword("123"),
                loadedSecretary.comparePassword("123"));
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
