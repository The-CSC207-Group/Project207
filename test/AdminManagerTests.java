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
import static org.junit.Assert.assertNull;

/**
 * A class that tests AdminManager.
 */
public class AdminManagerTests {
    Database originalDatabase;
    DataMapperGateway<Admin> adminDatabase;
    Admin admin;
    AdminManager adminManager;

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Before
    public void before(){
        originalDatabase = new Database(databaseFolder.toString());
        adminDatabase = originalDatabase.getAdminDatabase();
        admin = new Admin("jeff", "123", 123456789);
        adminManager = new AdminManager(originalDatabase);
    }

    /**
     * Tests createAdmin by creating an admin in the database, and that admin with the adminData to ensure they
     * represent the same entity.
     */
    @Test(timeout = 1000)
    public void testCreateAdminValid() {
        String username = "jeff";
        String password = "123";

        AdminData adminData = adminManager.createAdmin(username, password);

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
     * Test invalid createAdmin with a username that already exists in the database.
     */
    @Test(timeout = 1000)
    public void testCreateAdminInvalid() {
        String username = "jeff";
        String password = "123";

        adminManager.createAdmin(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", adminManager.createAdmin(username, password));
    }

    /**
     * Test deleteAdmin by ensuring an admin exists in the database, deleting it, then ensuring that the admin cannot
     * be access by using its adminID.
     */
    @Test(timeout = 1000)
    public void testDeleteAdmin() {
        Integer adminID = adminDatabase.add(admin);

        assertNotNull("An admin object should be returned before it is deleted ",
                adminDatabase.get(adminID));

        adminManager.deleteUser(admin.getUsername());

        assertNull("An admin object should not be returned after it is deleted ",
                adminDatabase.get(adminID));
    }

    /**
     * Testing getUserData by ensuring that the data entity values match up with the values stored in the database.
     */
    @Test(timeout = 1000)
    public void getUserData() {
        adminDatabase.add(admin);

        AdminData adminData = adminManager.getUserData(admin.getUsername());

        /* Testing if the adminData and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("admin and adminData should share the same Id",
                admin.getId(), adminData.getId());
        assertEquals("admin and adminData should share the same unique username",
                admin.getUsername(), adminData.getUsername());
        assertEquals("admin and adminData should share the same contact information",
                admin.getContactInfoId(), adminData.getContactInfoId());
        assertTrue("admin and adminData should share the same password",
                admin.comparePassword("123"));
    }

    /**
     * Tests an invalid use of getUserData by getting a user that does not exist.
     */
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        assertNull("trying to get user data of a user that doesn't exist should return null",
                adminManager.getUserData("jim"));
    }

    /**
     * Test deleteUserByData specifically with an Admin account, ensuring that the admin cannot be access by using
     * its adminID.
     */
    @Test(timeout = 1000)
    public void testDeleteAdminByData() {
        Integer adminID = adminDatabase.add(admin);
        AdminData userdata = adminManager.getUserData(admin.getUsername());

        assertNotNull("An admin object should be returned before it is deleted",
                adminDatabase.get(adminID));

        adminManager.deleteUserByData(userdata);

        assertNull("An admin object should not be returned after it is deleted by data",
                adminDatabase.get(adminID));
    }

    /**
     * Tests changeUserPassword for admin accounts, by comparing the password in the database before and after the
     * change.
     */
    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Integer adminID = adminDatabase.add(admin);
        AdminData adminData = new AdminData(admin);

        assertTrue("The password should remain the same before the change",
                adminDatabase.get(adminID).comparePassword("123"));

        adminManager.changeUserPassword(adminData, "456");

        assertTrue("The admin object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                adminDatabase.get(adminID).comparePassword("456"));
    }

    /**
     * Tests getAdmin by comparing the values of the entity and the values stored under the admin in the database to
     * ensure that they represent the same entity.
     */
    @Test(timeout = 1000)
    public void testGetAdmin() {
        Admin originalAdmin = admin;
        Integer adminID = adminDatabase.add(originalAdmin);

        assertEquals("Original admin should share the same ID from the database",
                originalAdmin.getId(), adminID);

        Admin loadedAdmin = adminDatabase.get(originalAdmin.getId());
        /* Testing if the loaded admin and the original admin are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original admin and loaded admin should share the same Id",
                originalAdmin.getId(), loadedAdmin.getId());
        assertEquals("Original admin and loaded admin should share the same unique username",
                originalAdmin.getUsername(), loadedAdmin.getUsername());
        assertEquals("Original admin and loaded admin should share the same contact information",
                originalAdmin.getContactInfoId(), loadedAdmin.getContactInfoId());
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword("123"));
    }

    /**
     * Tests doesUserExist by ensuring an admin exists in the database, then testing if the bool returned is true.
     */
    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Integer adminId = adminDatabase.add(admin);

        assertNotNull("An admin object should be returned when added to the database",
                adminDatabase.get(adminId));
        assertTrue("DoesUserExist should return true since the admin is stored in the database",
                adminManager.doesUserExist(admin.getUsername()));
    }

    /**
     * Tests doesUserExist by ensuring an admin exists in the database, then testing if the bool returned is false.
     */
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username", adminManager.doesUserExist("jim"));
    }

    /**
     * Tests canSignIn by ensuring that if a username and password is linked to an account in the database, a true
     * boolean should be returned.
     */
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        adminDatabase.add(admin);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", adminManager.canSignIn("jeff", "123"));
    }

    /**
     * Tests canSignIn by ensuring that if a username and password is not linked to an account in the database, a false
     * boolean should be returned.
     */
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        adminDatabase.add(admin);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", adminManager.canSignIn("jim", "password"));
    }

    /**
     * Tests a valid use of the signIn function with an existing account.
     */
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        adminDatabase.add(admin);
        AdminData adminData = adminManager.getUserData(admin.getUsername());

        assertEquals("A correct account detail sign in should return the respective adminData",
                adminManager.signIn(admin.getUsername(), "123").getId(), adminData.getId());
    }

    /**
     * Tests the signIn function when invalid account username or password is inputted, not matching with an account
     * in the database.
     */
    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        adminDatabase.add(admin);

        assertNull("an incorrect account detail sign in should return null", adminManager.signIn("jim",
                "password"));
    }
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
