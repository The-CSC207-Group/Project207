import dataBundles.AdminData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.AdminManager;
import utilities.DeleteUtils;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Class of unit tests for AdminManager use case class.
 */
public class AdminManagerTests {

    private final String username = "mynamejeff";
    private final String password = "123456789";
    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public final TemporaryFolder databaseFolder = new TemporaryFolder();
    private DataMapperGateway<Admin> adminDatabase;
    private AdminData adminData;
    private AdminManager adminManager;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        Database originalDatabase = new Database(databaseFolder.toString());
        adminDatabase = originalDatabase.getAdminDatabase();
        adminManager = new AdminManager(originalDatabase);
        adminData = adminManager.createAdmin(username, password);
    }

    /**
     * Tests createAdmin by creating an admin in the database using a valid and unused username.
     */
    @Test(timeout = 1000)
    public void testCreateAdminValidNonExistent() {
        /* Testing if the return admin data is valid by testing if the fields of are equal to the parameters of
        createAdmin */
        assertEquals("The created admin data should have the same name as the parameters of " +
                "createAdmin method", adminData.getUsername(), username);

        Admin loadedAdmin = adminDatabase.get(adminData.getId());

        /* Testing if the admin object has been correctly added to the database by testing if the fields of the loaded
        admin are equal to the parameters of createAdmin */
        assertEquals("Original admin and loaded admin should share the same unique username",
                loadedAdmin.getUsername(), username);
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword(password));
    }

    /**
     * Tests createAdmin with a username that already exists in the database.
     */
    @Test(timeout = 1000)
    public void testCreateAdminExistingUsername() {
        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", adminManager.createAdmin(username, password));
    }

    /**
     * Tests createAdmin with a format that does pass the regex check.
     */
    @Test(timeout = 1000)
    public void testCreateAdminInvalidFormat() {
        assertThrows("creating a user with a non-alphanumeric username characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> adminManager.createAdmin("newuser!", "123456789"));
        assertThrows("creating a user with a username shorter than 6 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> adminManager.createAdmin("newu", "123456789"));
        assertThrows("creating a user with a password shorter than 8 characters will return an illegal " +
                        "argument exception", IllegalArgumentException.class,
                () -> adminManager.createAdmin("newuser1", "1234567"));
    }

    /**
     * Test deleteAdmin by ensuring an admin exists in the database, deleting it, then ensuring that the admin cannot
     * be access by using its adminID.
     */
    @Test(timeout = 1000)
    public void testDeleteAdmin() {
        assertNotNull("An admin object should be returned before it is deleted ",
                adminDatabase.get(adminData.getId()));

        adminManager.deleteUser(adminData.getUsername());

        assertNull("An admin object should not be returned after it is deleted ",
                adminDatabase.get(adminData.getId()));
    }

    /**
     * Testing getUserData by ensuring that the data entity values match up with the values stored in the database.
     */
    @Test(timeout = 1000)
    public void getUserData() {
        AdminData loadedAdminData = adminManager.getUserData(adminData.getUsername());

        /* Testing if the adminData and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("admin and adminData should share the same Id",
                adminData.getId(), loadedAdminData.getId());
        assertEquals("admin and adminData should share the same unique username",
                adminData.getUsername(), loadedAdminData.getUsername());
        assertEquals("admin and adminData should share the same contact information",
                adminData.getContactInfoId(), loadedAdminData.getContactInfoId());
    }

    /**
     * Tests an invalid use of getUserData by getting a user that does not exist.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                adminManager.getUserData("jimhalpert"));
    }

    /**
     * Test deleteUserByData specifically with an Admin account, ensuring that the admin cannot be access by using
     * its adminID.
     */
    @Test(timeout = 1000)
    public void testDeleteAdminByData() {
        assertNotNull("An admin object should be returned before it is deleted",
                adminDatabase.get(adminData.getId()));

        adminManager.deleteUserByData(adminData);

        assertNull("An admin object should not be returned after it is deleted by data",
                adminDatabase.get(adminData.getId()));
    }

    /**
     * Tests changeUserPassword for admin accounts, by comparing the password in the database before and after the
     * change.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        assertTrue("The password should remain the same before the change",
                adminDatabase.get(adminData.getId()).comparePassword(password));

        adminManager.changeUserPassword(adminData, "456");

        assertTrue("The admin object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                adminDatabase.get(adminData.getId()).comparePassword("456"));
    }

    /**
     * Tests getAdmin by comparing the values of the entity and the values stored under the admin in the database to
     * ensure that they represent the same entity.
     */
    @Test(timeout = 1000)
    public void testGetAdmin() {
        Admin loadedAdmin = adminDatabase.get(adminData.getId());
        /* Testing if the loaded admin and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original admin and loaded admin should share the same Id",
                adminData.getId(), loadedAdmin.getId());
        assertEquals("Original admin and loaded admin should share the same unique username",
                adminData.getUsername(), loadedAdmin.getUsername());
        assertEquals("Original admin and loaded admin should share the same contact information",
                adminData.getContactInfoId(), loadedAdmin.getContactInfoId());
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword(password));
    }

    /**
     * Tests doesUserExist by ensuring an admin exists in the database, then testing if the bool returned is true.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist() {
        assertNotNull("An admin object should be returned when added to the database",
                adminDatabase.get(adminData.getId()));
        assertTrue("DoesUserExist should return true since the admin is stored in the database",
                adminManager.doesUserExist(adminData.getUsername()));
    }

    /**
     * Tests doesUserExist by ensuring an admin exists in the database, then testing if the bool returned is false.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist() {
        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                "inputted username", adminManager.doesUserExist("jimhalpert"));
    }

    /**
     * Tests canSignIn by ensuring that if a username and password is linked to an account in the database, a true
     * boolean should be returned.
     */
    @Test(timeout = 1000)
    public void testCanSignInValid() {
        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", adminManager.canSignIn(username, password));
    }

    /**
     * Tests canSignIn by ensuring that if a username and password is not linked to an account in the database, a false
     * boolean should be returned.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalid() {
        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", adminManager.canSignIn("jimhalpert", "password"));
    }

    /**
     * Tests a valid use of the signIn function with an existing account.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount() {
        assertEquals("A correct account detail sign in should return the respective adminData",
                adminManager.signIn(adminData.getUsername(), password).getId(), adminData.getId());
    }

    /**
     * Tests the signIn function when invalid account username or password is inputted, not matching with an account
     * in the database.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount() {
        assertNull("an incorrect account detail sign in should return null", adminManager.signIn(
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
