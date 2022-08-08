import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.PatientManager;
import utilities.DeleteUtils;
import java.io.File;
import static org.junit.Assert.*;

/**
 *Tests for Patient Manager Class.
 */
public class PatientManagerTests {
    Database originalDatabase;
    DataMapperGateway<Patient> patientDatabase;
    PatientManager patientManager;
    Patient patient;

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before(){
        originalDatabase = new Database(databaseFolder.toString());
        patientDatabase = originalDatabase.getPatientDatabase();
        patientManager = new PatientManager(originalDatabase);
        patient = new Patient("jeff", "123", 123456789);
    }

    /**
     * Tests createPatient by ensuring that the values of the patient stored in the database are the same as the
     * PatientData returned from the function.
     */
    @Test(timeout = 1000)
    public void testCreatePatientValid() {
        String username = "jeff";
        String password = "123";

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

    /**
     * Tests create patient with an invalid username and password that already exist in the database.
     */
    @Test(timeout = 1000)
    public void testCreatePatientInvalid() {
        String username = "jeff";
        String password = "123";

        patientManager.createPatient(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", patientManager.createPatient(username, password));
    }

    /**
     * Tests a valid use of deletePatient.
     */
    @Test(timeout = 1000)
    public void testDeletePatient() {
        Integer patientID = patientDatabase.add(patient);

        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientID));

        patientManager.deleteUser(patient.getUsername());

        assertNull("A patient object should not be returned after it is deleted ",
                patientDatabase.get(patientID));
    }

    /**
     * Tests getUserData by ensuring that the values from the patient stored in the database are the same as the values
     * stored in the patientData returned from the function.
     */
    @Test(timeout = 1000)
    public void getUserData() {
        patientDatabase.add(patient);

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

    /**
     * Tests an invalid use of getUserData when a username that does not exist in the database is inputted.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                patientManager.getUserData("jim"));

    }

    /**
     * Tests deleteUserByData but specifically with patient data.
     */
    @Test(timeout = 1000)
    public void testDeletePatientByData() {
        Integer patientID = patientDatabase.add(patient);
        PatientData userdata = patientManager.getUserData(patient.getUsername());

        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientID));

        patientManager.deleteUserByData(userdata);

        assertNull("A patient object should not be returned after it is deleted by data",
                patientDatabase.get(patientID));
    }

    /**
     * Tests changeUserPassword specifically with patient data.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Integer patientID = patientDatabase.add(patient);
        PatientData patientData = new PatientData(patient);

        assertTrue("The password should remain the same before the change ",
                patientDatabase.get(patientID).comparePassword("123"));

        patientManager.changeUserPassword(patientData, "456");

        assertTrue("The patient object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                patientDatabase.get(patientID).comparePassword("456"));
    }

    /**
     * Tests getUser but specifically with patient data, and ensure the patient stored in the database and the returned
     * loadedPatient.
     */
    @Test(timeout = 1000)
    public void testGetPatient() {
        Patient originalPatient = patient;

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

    /**
     * Tests doesUserExist with the patient username inputted.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Integer patientId = patientDatabase.add(patient);

        assertNotNull("A patient object should be returned when added to the database",
                patientDatabase.get(patientId));
        assertTrue("DoesUserExist should return true since the patient is stored in the database",
                patientManager.doesUserExist(patient.getUsername()));
    }

    /**
     * Tests does user exist with an invalid username inputted.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        patientDatabase.add(patient);

        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                patientManager.doesUserExist("jim"));
    }

    /**
     * Tests a valid use of canSignIn with a patient's username and password.
     */
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        patientDatabase.add(patient);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", patientManager.canSignIn("jeff", "123"));
    }

    /**
     * Tests an invalid use of canSignIn with a patient's username and password that does not exist in the database.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        patientDatabase.add(patient);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", patientManager.canSignIn("jim", "password"));
    }

    /**
     * Tests signIn with an existing patient account.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        patientDatabase.add(patient);
        PatientData patientData = patientManager.getUserData(patient.getUsername());

        assertEquals("A correct account detail sign in should return the respective patientData",
                patientManager.signIn(patient.getUsername(), "123").getId(), patientData.getId());
    }

    /**
     * Tests an invalid use of signIn with a non-existing account.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        patientDatabase.add(patient);
        patientManager.getUserData(patient.getUsername());

        assertNull("an incorrect account detail sign in should return null", patientManager.signIn("jim",
                "password"));
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
