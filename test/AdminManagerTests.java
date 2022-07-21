import dataBundles.AdminData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Contact;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AdminManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class AdminManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateAdmin() {
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

        AdminData adminDataBundle = adminManager.createAdmin(username, password);

        /* Testing if the return admin data bundle is valid by testing if the fields of are equal to the parameters of
        createAdmin */
        assertEquals("The created admin data bundle should have the same name as the parameters of " +
                "createAdmin method", adminDataBundle.getUsername(), username);

        Admin loadedAdmin = adminDatabase.get(adminDataBundle.getId());

        /* Testing if the admin object has been correctly added to the database by testing if the fields of the loaded
        admin are equal to the parameters of createAdmin */
        assertEquals("Original admin and loaded admin should share the same unique username",
                loadedAdmin.getUsername(), username);
        assertTrue("Original admin and loaded admin should share the same password",
                loadedAdmin.comparePassword(password));
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


    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
