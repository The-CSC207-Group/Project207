import dataBundles.ContactData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Patient;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.PatientManager;
import utilities.DeleteUtils;
import java.io.File;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class PatientManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreatePatientValid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        PatientManager patientManager = new PatientManager(originalDatabase);

        PatientData patientData = patientManager.createPatient(username, password);

        /* Testing if the return patient data is valid by testing if the fields of are equal to the parameters of
        createPatient */
        assertEquals("The created patient data should have the same name as the parameters of " +
                "createPatient method", patientData.getUsername(), username);

        Patient loadedPatient = patientDatabase.get(patientData.getId());

        /* Testing if the patient object has been correctly added to the database by testing if the fields of the loaded
        patient are equal to the parameters of createPatient */
        assertEquals("Original patient and loaded patient should share the same unique username",
                loadedPatient.getUsername(), username);
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword(password));
    }
    @Test(timeout = 1000)
    public void testCreatePatientInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        PatientManager patientManager = new PatientManager(originalDatabase);

        PatientData patientData = patientManager.createPatient(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", patientManager.createPatient(username, password));
    }

    @Test(timeout = 1000)
    public void testDeletePatient() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);

        Integer patientID = patientDatabase.add(patient);

        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientID));

        patientManager.deleteUser(patient.getUsername());

        assertNull("A patient object should not be returned after it is deleted ",
                patientDatabase.get(patientID));
    }
    @Test(timeout = 1000)
    public void getUserData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);

        Integer patientID = patientDatabase.add(patient);

        PatientData patientData = patientManager.getUserData(patient.getUsername());

        /* Testing if the patientData and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("patient and patientData should share the same Id",
                patient.getId(), patientData.getId());
        assertEquals("patient and patientData should share the same unique username",
                patient.getUsername(), patientData.getUsername());
        assertEquals("patient and patientData should share the same contact information",
                patient.getContactInfoId(), patientData.getContactInfoId());
        assertTrue("patient and patientData should share the same password",
                patient.comparePassword("123"));
    }
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(originalDatabase);

        assertNull("trying to get user data of a user that doesn't exist should return null",
                patientManager.getUserData("jim"));

    }
    @Test(timeout = 1000)
    public void testDeletePatientByData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);

        Integer patientID = patientDatabase.add(patient);
        PatientData userdata = patientManager.getUserData(patient.getUsername());

        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientID));

        patientManager.deleteUserByData(userdata);

        assertNull("A patient object should not be returned after it is deleted by data",
                patientDatabase.get(patientID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);

        Integer patientID = patientDatabase.add(patient);
        PatientData patientData = new PatientData(patient);

        assertTrue("The password should remain the same before the change ",
                patientDatabase.get(patientID).comparePassword("123"));

        patientManager.changeUserPassword(patientData, "456");

        assertTrue("The patient object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                patientDatabase.get(patientID).comparePassword("456"));
    }

    @Test(timeout = 1000)
    public void testGetPatient() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient originalPatient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);

        Integer patientID = patientDatabase.add(originalPatient);

        assertEquals("Original patient should share the same ID from the database",
                originalPatient.getId(), patientID);

        Patient loadedPatient = patientDatabase.get(originalPatient.getId());
        /* Testing if the loaded patient and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original patient and loaded patient should share the same Id",
                originalPatient.getId(), loadedPatient.getId());
        assertEquals("Original patient and loaded patient should share the same unique username",
                originalPatient.getUsername(), loadedPatient.getUsername());
        assertEquals("Original patient and loaded patient should share the same contact information",
                originalPatient.getContactInfoId(), loadedPatient.getContactInfoId());
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);

        assertNotNull("A patient object should be returned when added to the database",
                patientDatabase.get(patientId));
        assertTrue("DoesUserExist should return true since the patient is stored in the database",
                patientManager.doesUserExist(patient.getUsername()));
    }
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);

        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                patientManager.doesUserExist("jim"));
    }
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", patientManager.canSignIn("jeff", "123"));
    }
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", patientManager.canSignIn("jim", "password"));
    }
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);
        PatientData patientData = patientManager.getUserData(patient.getUsername());

        assertEquals("A correct account detail sign in should return the respective patientData",
                patientManager.signIn(patient.getUsername(), "123").getId(), patientData.getId());
    }

    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Patient> patientDatabase = originalDatabase.getPatientDatabase();

        Patient patient = new
                Patient("jeff", "123", 123456789);

        PatientManager patientManager = new PatientManager(originalDatabase);
        Integer patientId = patientDatabase.add(patient);
        PatientData patientData = patientManager.getUserData(patient.getUsername());

        assertNull("an incorrect account detail sign in should return null", patientManager.signIn("jim",
                "password"));
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
