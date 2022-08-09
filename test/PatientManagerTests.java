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
 * Class of unit tests for PatientManager use case class.
 */
public class PatientManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    private DataMapperGateway<Patient> patientDatabase;
    private PatientManager patientManager;
    private PatientData patientData;
    private final String username = "mynamejeff";
    private final String password = "123456789";

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before(){
        Database originalDatabase = new Database(databaseFolder.toString());
        patientDatabase = originalDatabase.getPatientDatabase();
        patientManager = new PatientManager(originalDatabase);
        patientData = patientManager.createPatient(username, password);
    }

    /**
     * Tests createPatient by creating an patient in the database using a valid and unused username.
     */
    @Test(timeout = 1000)
    public void testCreatePatientValidUnused() {
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
     * Tests createPatient with a username that already exists in the database.
     */
    @Test(timeout = 1000)
    public void testCreatePatientExistingUsername() {
        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", patientManager.createPatient(username, password));
    }

    /**
     * Tests createPatient with a format that does pass the regex check.
     */
    @Test(timeout = 1000)
    public void testCreatePatientInvalidFormat() {
        assertThrows("creating a user with a non-alphanumeric username and a password shorter than 8 " +
                        "characters will return an illegal argument exception", IllegalArgumentException.class,
                () -> patientManager.createPatient("!!!", "123"));
    }

    /**
     * Tests a valid use of deletePatient.
     */
    @Test(timeout = 1000)
    public void testDeletePatient() {
        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientData.getId()));

        patientManager.deleteUser(patientData.getUsername());

        assertNull("A patient object should not be returned after it is deleted ",
                patientDatabase.get(patientData.getId()));
    }

    /**
     * Tests getUserData by ensuring that the values from the patient stored in the database are the same as the values
     * stored in the patientData returned from the function.
     */
    @Test(timeout = 1000)
    public void getUserData() {
        PatientData loadedPatientData = patientManager.getUserData(patientData.getUsername());

        /* Testing if the patientData and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("patient and patientData should share the same Id",
                patientData.getId(), loadedPatientData.getId());
        assertEquals("patient and patientData should share the same unique username",
                patientData.getUsername(), loadedPatientData.getUsername());
        assertEquals("patient and patientData should share the same contact information",
                patientData.getContactInfoId(), loadedPatientData.getContactInfoId());
    }

    /**
     * Tests an invalid use of getUserData when a username that does not exist in the database is inputted.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                patientManager.getUserData("jimhalpert"));

    }

    /**
     * Tests deleteUserByData but specifically with patient data.
     */
    @Test(timeout = 1000)
    public void testDeletePatientByData() {
        assertNotNull("A patient object should be returned before it is deleted ",
                patientDatabase.get(patientData.getId()));

        patientManager.deleteUserByData(patientData);

        assertNull("A patient object should not be returned after it is deleted by data",
                patientDatabase.get(patientData.getId()));
    }

    /**
     * Tests changeUserPassword specifically with patient data.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        assertTrue("The password should remain the same before the change ",
                patientDatabase.get(patientData.getId()).comparePassword(password));

        patientManager.changeUserPassword(patientData, "456");

        assertTrue("The patient object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                patientDatabase.get(patientData.getId()).comparePassword("456"));
    }

    /**
     * Tests getUser but specifically with patient data, and ensure the patient stored in the database and the returned
     * loadedPatient.
     */
    @Test(timeout = 1000)
    public void testGetPatient() {
        Patient loadedPatient = patientDatabase.get(patientData.getId());
        /* Testing if the loaded patient and the original patient are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original patient and loaded patient should share the same Id",
                patientData.getId(), loadedPatient.getId());
        assertEquals("Original patient and loaded patient should share the same unique username",
                patientData.getUsername(), loadedPatient.getUsername());
        assertEquals("Original patient and loaded patient should share the same contact information",
                patientData.getContactInfoId(), loadedPatient.getContactInfoId());
        assertTrue("Original patient and loaded patient should share the same password",
                loadedPatient.comparePassword(password));
    }

    /**
     * Tests doesUserExist with the patient username inputted.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist(){
        assertNotNull("A patient object should be returned when added to the database",
                patientDatabase.get(patientData.getId()));
        assertTrue("DoesUserExist should return true since the patient is stored in the database",
                patientManager.doesUserExist(patientData.getUsername()));
    }

    /**
     * Tests does user exist with an invalid username inputted.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username", patientManager.doesUserExist("jimhalpert"));
    }

    /**
     * Tests a valid use of canSignIn with a patient's username and password.
     */
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", patientManager.canSignIn(username, password));
    }

    /**
     * Tests an invalid use of canSignIn with a patient's username and password that does not exist in the database.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", patientManager.canSignIn("jimhalpert", "password"));
    }

    /**
     * Tests signIn with an existing patient account.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        PatientData loadedPatientData = patientManager.getUserData(patientData.getUsername());

        assertEquals("A correct account detail sign in should return the respective patientData",
                patientManager.signIn(loadedPatientData.getUsername(), password).getId(),
                patientData.getId());
    }

    /**
     * Tests an invalid use of signIn with a non-existing account.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        assertNull("an incorrect account detail sign in should return null", patientManager.signIn(
                "jimhalpert", "password"));
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
