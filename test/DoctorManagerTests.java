import dataBundles.ContactDataBundle;
import dataBundles.DoctorDataBundle;
import dataBundles.PatientDataBundle;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Doctor;
import entities.Patient;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.DoctorManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoctorManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

    @Test(timeout = 1000)
    public void testCreateDoctor() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactDataBundle contactDataBundle = new ContactDataBundle("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        DoctorManager doctorManager = new DoctorManager(doctorDatabase, contactDatabase);

        DoctorDataBundle doctorDataBundle = doctorManager.createDoctor(username, password, contactDataBundle);

        /* Testing if the return doctor data bundle is valid by testing if the fields of are equal to the parameters of
        createDoctor */
        assertEquals("The created doctor data bundle should have the same name as the parameters of " +
                "createDoctor method", doctorDataBundle.getUsername(), username);

        Doctor loadedDoctor = doctorDatabase.get(doctorDataBundle.getId());

        /* Testing if the doctor object has been correctly added to the database by testing if the fields of the loaded
        doctor are equal to the parameters of createDoctor */
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                loadedDoctor.getUsername(), username);
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                loadedDoctor.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword(password));
    }

}
