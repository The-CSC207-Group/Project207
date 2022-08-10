import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.Secretary;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.SecretaryManager;
import utilities.DeleteUtils;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Class of unit tests for SecretaryManager use case class.
 */
public class SecretaryManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public final TemporaryFolder databaseFolder = new TemporaryFolder();
    private final String username = "mynamejeff";
    private final String password = "123456789";
    private DataMapperGateway<Secretary> secretaryDatabase;
    private SecretaryManager secretaryManager;
    private SecretaryData secretaryData;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        Database originalDatabase = new Database(databaseFolder.toString());
        secretaryDatabase = originalDatabase.getSecretaryDatabase();
        secretaryManager = new SecretaryManager(originalDatabase);
        secretaryData = secretaryManager.createSecretary(username, password);
    }

    /**
     * Tests createSecretary by creating a secretary in the database using a valid and unused username.
     */
    @Test(timeout = 1000)
    public void testCreateSecretaryValidUnused() {
        /* Testing if the return secretary data is valid by testing if the fields of are equal to the parameters of
        createSecretary */
        assertEquals("The created secretary data should have the same name as the parameters of " +
                "createSecretary method", secretaryData.getUsername(), username);

        Secretary loadedSecretary = secretaryDatabase.get(secretaryData.getId());

        /* Testing if the secretary object has been correctly added to the database by testing if the fields of the
        loaded secretary are equal to the parameters of createSecretary */
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                loadedSecretary.getUsername(), username);
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword(password));
    }

    /**
     * Tests create secretary with a username that already exists in the database.
     */
    @Test(timeout = 1000)
    public void testCreateSecretaryExistingUsername() {
        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", secretaryManager.createSecretary(username, password));
    }

    /**
     * Tests create secretary with a format that does pass the regex check.
     */
    @Test(timeout = 1000)
    public void testCreateSecretaryInvalidFormat() {
        assertThrows("creating a user with a non-alphanumeric username characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> secretaryManager.createSecretary("newuser!", "123456789"));
        assertThrows("creating a user with a username shorter than 6 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> secretaryManager.createSecretary("newu", "123456789"));
        assertThrows("creating a user with a password shorter than 8 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> secretaryManager.createSecretary("newuser1", "1234567"));
    }

    /**
     * Tests a valid use of deleteSecretary.
     */
    @Test(timeout = 1000)
    public void testDeleteSecretary() {
        assertNotNull("A secretary object should be returned before it is deleted ",
                secretaryDatabase.get(secretaryData.getId()));

        secretaryManager.deleteUser(secretaryData.getUsername());

        assertNull("A secretary object should not be returned after it is deleted ",
                secretaryDatabase.get(secretaryData.getId()));
    }

    /**
     * Tests getUserData by ensuring that the values from the secretary stored in the database are the same as the values
     * stored in the secretaryData returned from the function.
     */
    @Test(timeout = 1000)
    public void getUserData() {
        SecretaryData secretaryData1 = secretaryManager.getUserData(secretaryData.getUsername());

        /* Testing if the secretaryData and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("secretary and secretaryData should share the same Id",
                secretaryData1.getId(), secretaryData.getId());
        assertEquals("secretary and secretaryData should share the same unique username",
                secretaryData1.getUsername(), secretaryData.getUsername());
        assertEquals("secretary and secretaryData should share the same contact information",
                secretaryData1.getContactInfoId(), secretaryData.getContactInfoId());
    }

    /**
     * Tests an invalid use of getUserData when a username that does not exist in the database is inputted.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                secretaryManager.getUserData("jimhalpert"));

    }

    /**
     * Tests deleteUserByData but specifically with secretary data.
     */
    @Test(timeout = 1000)
    public void testDeleteSecretaryByData() {
        assertNotNull("A secretary object should be returned before it is deleted ",
                secretaryDatabase.get(secretaryData.getId()));

        secretaryManager.deleteUserByData(secretaryData);

        assertNull("A secretary object should not be returned after it is deleted by data",
                secretaryDatabase.get(secretaryData.getId()));
    }

    /**
     * Tests changeUserPassword specifically with secretary data.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        assertTrue("The password should remain the same before the change ",
                secretaryDatabase.get(secretaryData.getId()).comparePassword(password));

        secretaryManager.changeUserPassword(secretaryData, "456");

        assertTrue("The secretary object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                secretaryDatabase.get(secretaryData.getId()).comparePassword("456"));
    }

    /**
     * Tests getUser but specifically with secretary data, and ensure the secretary stored in the database and the returned
     * loadedSecretary.
     */
    @Test(timeout = 1000)
    public void testGetSecretary() {
        Secretary loadedSecretary = secretaryDatabase.get(secretaryData.getId());
        /* Testing if the loaded secretary and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original secretary and loaded secretary should share the same Id",
                secretaryData.getId(), loadedSecretary.getId());
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                secretaryData.getUsername(), loadedSecretary.getUsername());
        assertEquals("Original secretary and loaded secretary should share the same contact information",
                secretaryData.getContactInfoId(), loadedSecretary.getContactInfoId());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword(password));
    }

    /**
     * Tests doesUserExist with the secretary username inputted.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist() {
        assertNotNull("A secretary object should be returned when added to the database",
                secretaryDatabase.get(secretaryData.getId()));
        assertTrue("DoesUserExist should return true since the secretary is stored in the database",
                secretaryManager.doesUserExist(secretaryData.getUsername()));
    }

    /**
     * Tests does user exist with an invalid username inputted.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist() {
        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                "inputted username", secretaryManager.doesUserExist("jimhalpert"));
    }

    /**
     * Tests a valid use of canSignIn with a secretary's username and password.
     */
    @Test(timeout = 1000)
    public void testCanSignInValid() {
        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", secretaryManager.canSignIn(username, password));
    }

    /**
     * Tests an invalid use of canSignIn with a secretary's username and password that does not exist in the database.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalid() {
        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", secretaryManager.canSignIn("jimhalpert", "password"));
    }

    /**
     * Tests signIn with an existing patient account.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount() {
        assertEquals("A correct account detail sign in should return the respective secretaryData",
                secretaryManager.signIn(secretaryData.getUsername(), password).getId(), secretaryData.getId());
    }

    /**
     * Tests an invalid use of signIn with a non-existing account.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount() {
        assertNull("an incorrect account detail sign in should return null", secretaryManager.signIn(
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
