import dataBundles.ContactDataBundle;
import dataBundles.PatientDataBundle;
import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Patient;
import entities.Prescription;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

public class PatientManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreatePatient() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        String username = "jeff";
        String password = "123";
        String healthNumber = "5544";
        Date birthday = new Date(2022, Calendar.JANUARY, 1);
        ContactDataBundle contactDataBundle = new ContactDataBundle("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        PatientManager patientManager = new PatientManager(patientDatabase, contactDatabase);

        PatientDataBundle patientDataBundle = patientManager.createPatient(username, password, contactDataBundle,
                healthNumber);

        /* Testing if the return patient data bundle is valid by testing if the fields of are equal to the parameters of
        createPatient */
        assertEquals("The created patient data bundle should have the same name as the parameters of " +
                        "createPatient method", patientDataBundle.getUsername(), username);
        assertEquals("The created patient data bundle should have the same health number as the parameters " +
                "of createPatient method", patientDataBundle.getHealthNumber(), healthNumber);

        Patient loadedPatient = patientDatabase.get(patientDataBundle.getId());

        /* Testing if the patient object has been correctly added to the database by testing if the fields of the loaded
        patient are equal to the parameters of createPatient */
        assertEquals("Original patient and loaded patient should share the same unique username",
                loadedPatient.getUsername(), username);
        assertEquals("Original patient and loaded patient should share the same contact information",
                loadedPatient.getContactInfoId(), loadedPatient.getContactInfoId());
        assertEquals("Original patient and loaded patient should share the same health numbers",
                loadedPatient.getHealthNumber(), loadedPatient.getHealthNumber());
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword(password));
    }

    @Test(timeout = 1000)
    public void testDeletePatient() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789, "5544");

        PatientManager patientManager = new PatientManager(patientDatabase, contactDatabase);

        Integer patientID = patientDatabase.add(patient);

        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientID));

        patientManager.deletePatient(patientID);

        assertNull("A patient object should not be returned after it is deleted ",
                patientDatabase.get(patientID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789, "5544");

        PatientManager patientManager = new PatientManager(patientDatabase, contactDatabase);

        Integer patientID = patientDatabase.add(patient);

        assertTrue("The password should remain the same before the change ",
                patientDatabase.get(patientID).comparePassword("123"));

        patientManager.changeUserPassword(patientID, "456");

        assertTrue("The patient object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method  ",
                patientDatabase.get(patientID).comparePassword("456"));
    }
}
