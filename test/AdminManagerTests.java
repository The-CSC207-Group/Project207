import dataBundles.AdminDataBundle;
import dataBundles.ContactDataBundle;
import dataBundles.DoctorDataBundle;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Contact;
import entities.Doctor;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AdminManager;
import useCases.managers.DoctorManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdminManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateAdmin() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Admin> adminDatabase = originalDatabase.getAdminDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactDataBundle contactDataBundle = new ContactDataBundle("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        AdminManager adminManager = new AdminManager(adminDatabase, contactDatabase);

        AdminDataBundle adminDataBundle = adminManager.createAdmin(username, password, contactDataBundle);

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

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
