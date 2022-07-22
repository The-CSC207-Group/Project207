import dataBundles.AdminData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Contact;
import entities.Patient;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AdminManager;
import useCases.managers.PatientManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class AdminManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateAdminValid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        AdminManager adminManager = new AdminManager(originalDatabase);

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
    @Test(timeout = 1000)
    public void testCreateAdminInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        AdminManager adminManager = new AdminManager(originalDatabase);

        AdminData adminData = adminManager.createAdmin(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", adminManager.createAdmin(username, password));
    }

    @Test(timeout = 1000)
    public void testDeleteAdmin() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);

        Integer adminID = adminDatabase.add(admin);

        assertNotNull("An admin object should be returned before it is deleted ",
                adminDatabase.get(adminID));

        adminManager.deleteUser(admin.getUsername());

        assertNull("An admin object should not be returned after it is deleted ",
                adminDatabase.get(adminID));
    }
    @Test(timeout = 1000)
    public void getUserData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);

        Integer adminID = adminDatabase.add(admin);

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
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        AdminManager adminManager = new AdminManager(originalDatabase);

        assertNull("trying to get user data of a user that doesn't exist should return null",
                adminManager.getUserData("jim"));

    }
    @Test(timeout = 1000)
    public void testDeleteAdminByData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);

        Integer adminID = adminDatabase.add(admin);
        AdminData userdata = adminManager.getUserData(admin.getUsername());

        assertNotNull("An admin object should be returned before it is deleted ",
                adminDatabase.get(adminID));

        adminManager.deleteUserByData(userdata);

        assertNull("An admin object should not be returned after it is deleted by data",
                adminDatabase.get(adminID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);

        Integer adminID = adminDatabase.add(admin);
        AdminData adminData = new AdminData(admin);

        assertTrue("The password should remain the same before the change ",
                adminDatabase.get(adminID).comparePassword("123"));

        adminManager.changeUserPassword(adminData, "456");

        assertTrue("The admin object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                adminDatabase.get(adminID).comparePassword("456"));
    }

    @Test(timeout = 1000)
    public void testGetAdmin() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin originalAdmin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);

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

    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);

        assertNotNull("An admin object should be returned when added to the database",
                adminDatabase.get(adminId));
        assertTrue("DoesUserExist should return true since the admin is stored in the database",
                adminManager.doesUserExist(admin.getUsername()));
    }
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);

        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                adminManager.doesUserExist("jim"));
    }
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", adminManager.canSignIn("jeff", "123"));
    }
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", adminManager.canSignIn("jim", "password"));
    }
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);
        AdminData adminData = adminManager.getUserData(admin.getUsername());

        assertEquals("A correct account detail sign in should return the respective adminData",
                adminManager.signIn(admin.getUsername(), "123").getId(), adminData.getId());
    }

    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();

        Admin admin = new
                Admin("jeff", "123", 123456789);

        AdminManager adminManager = new AdminManager(originalDatabase);
        Integer adminId = adminDatabase.add(admin);
        AdminData adminData = adminManager.getUserData(admin.getUsername());

        assertNull("an incorrect account detail sign in should return null", adminManager.signIn("jim",
                "password"));
    }
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
