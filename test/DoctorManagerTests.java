import dataBundles.DoctorData;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.DoctorManager;
import utilities.DeleteUtils;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Class of unit tests for DoctorManager use case class.
 */
public class DoctorManagerTests {

    private final String username = "mynamejeff";
    private final String password = "123456789";
    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    private DataMapperGateway<Doctor> doctorDatabase;
    private DoctorManager doctorManager;
    private DoctorData doctorData;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        Database database = new Database(databaseFolder.toString());
        doctorDatabase = database.getDoctorDatabase();
        doctorManager = new DoctorManager(database);
        doctorData = doctorManager.createDoctor(username, password);
    }

    /**
     * Tests createDoctor by creating a doctor in the database using a valid and unused username.
     */
    @Test(timeout = 1000)
    public void testCreateDoctorValidUnused() {
        /* Testing if the return doctor data is valid by testing if the fields of are equal to the parameters of
        createDoctor */
        assertEquals("The created doctor data should have the same name as the parameters of " +
                "createDoctor method", doctorData.getUsername(), username);

        Doctor loadedDoctor = doctorDatabase.get(doctorData.getId());

        /* Testing if the doctor object has been correctly added to the database by testing if the fields of the loaded
        doctor are equal to the parameters of createDoctor */
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                loadedDoctor.getUsername(), username);
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword(password));
    }

    /**
     * Tests createDoctor with a username that already exists in the database.
     */
    @Test(timeout = 1000)
    public void testCreateDoctorExistingUsername() {
        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", doctorManager.createDoctor(username, password));
    }

    /**
     * Tests createDoctor with a format that does pass the regex check.
     */
    @Test(timeout = 1000)
    public void testCreateDoctorInvalidFormat() {
        assertThrows("creating a user with a non-alphanumeric username characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> doctorManager.createDoctor("newuser!", "123456789"));
        assertThrows("creating a user with a username shorter than 6 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> doctorManager.createDoctor("newu", "123456789"));
        assertThrows("creating a user with a password shorter than 8 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> doctorManager.createDoctor("newuser1", "1234567"));
    }

    /**
     * Tests the doctor manager's method that deletes a doctor with an existing username.
     */
    @Test(timeout = 1000)
    public void testDeleteDoctorValidUsername() {
        assertNotNull("A doctor object should be returned before it is deleted ",
                doctorDatabase.get(doctorData.getId()));

        boolean deleteDoctorReturnValue = doctorManager.deleteUser(doctorData.getUsername());

        assertTrue("The method should return true when the username exists", deleteDoctorReturnValue);
        assertNull("A doctor object should not be returned after it is deleted ",
                doctorDatabase.get(doctorData.getId()));
    }

    /**
     * Tests the doctor manager's method that deletes a doctor with a non-existent username.
     */
    @Test(timeout = 1000)
    public void testDeleteDoctorInvalidUsername() {
        boolean deleteDoctorReturnValue = doctorManager.deleteUser("jimhalpert");

        assertFalse("The method should return false when the username does not exist", deleteDoctorReturnValue);
    }

    /**
     * Tests the doctor manager's getter method that returns a data bundle of a doctor user with an existing username.
     */
    @Test(timeout = 1000)
    public void getUserDataValidUsername() {
        DoctorData loadedDoctorData = doctorManager.getUserData(doctorData.getUsername());

        /* Testing if the doctorData and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("doctor and doctorData should share the same Id",
                doctorData.getId(), loadedDoctorData.getId());
        assertEquals("doctor and doctorData should share the same unique username",
                doctorData.getUsername(), loadedDoctorData.getUsername());
        assertEquals("doctor and doctorData should share the same contact information",
                doctorData.getContactInfoId(), loadedDoctorData.getContactInfoId());
    }

    /**
     * Tests the doctor manager's getter method that returns a data bundle of a doctor user with a non-existent username.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalidUsername() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                doctorManager.getUserData("jimhalpert"));

    }

    /**
     * Tests the doctor manager's method that deletes a doctor by passing in a doctor data bundle.
     */
    @Test(timeout = 1000)
    public void testDeleteDoctorByData() {
        DoctorData loadedDoctorData = doctorManager.getUserData(doctorData.getUsername());

        assertNotNull("A doctor object should be returned before it is deleted ",
                doctorDatabase.get(doctorData.getId()));

        doctorManager.deleteUserByData(loadedDoctorData);

        assertNull("A doctor object should not be returned after it is deleted by data",
                doctorDatabase.get(doctorData.getId()));
    }

    /**
     * Tests the doctor manager's method that changes a doctor's password.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        assertTrue("The password should remain the same before the change ",
                doctorDatabase.get(doctorData.getId()).comparePassword(password));

        doctorManager.changeUserPassword(doctorData, "456");

        assertTrue("The doctor object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                doctorDatabase.get(doctorData.getId()).comparePassword("456"));
    }

    /**
     * Tests the doctor manager's getter method that returns a data bundle of the doctor.
     */
    @Test(timeout = 1000)
    public void testGetDoctor() {
        Doctor loadedDoctor = doctorDatabase.get(doctorData.getId());
        /* Testing if the loaded doctor and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original doctor and loaded doctor should share the same Id",
                doctorData.getId(), loadedDoctor.getId());
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                doctorData.getUsername(), loadedDoctor.getUsername());
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                doctorData.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword(password));
    }

    /**
     * Tests the doctor manager's method that checks if a doctor with a username exists using an existing username.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist() {
        assertNotNull("A doctor object should be returned when added to the database",
                doctorDatabase.get(doctorData.getId()));
        assertTrue("DoesUserExist should return true since the doctor is stored in the database",
                doctorManager.doesUserExist(doctorData.getUsername()));
    }

    /**
     * Tests the doctor manager's method that checks if a doctor with a username exists using a non-existent username.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist() {
        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                doctorManager.doesUserExist("jimhalpert"));
    }

    /**
     * Tests the doctor manager's method that checks if a doctor can sign in using valid login details.
     */
    @Test(timeout = 1000)
    public void testCanSignInValidLoginDetails() {
        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", doctorManager.canSignIn(username, password));
    }

    /**
     * Tests the doctor manager's method that checks if a doctor can sign in using invalid login details.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalidLoginDetails() {
        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", doctorManager.canSignIn("jimhalpert", "password"));
    }

    /**
     * Tests the doctor manager's method that returns a doctor's data bundle using valid login details.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount() {
        DoctorData loadedDoctorData = doctorManager.getUserData(doctorData.getUsername());

        assertEquals("A correct account detail sign in should return the respective doctorData",
                doctorManager.signIn(doctorData.getUsername(), password).getId(), loadedDoctorData.getId());
    }

    /**
     * Tests the doctor manager's method that returns a doctor's data bundle using invalid login details.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount() {
        assertNull("an incorrect account detail sign in should return null", doctorManager.signIn(
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
