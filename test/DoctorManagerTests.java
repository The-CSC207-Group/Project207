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
import useCases.managers.PatientManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

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

    @Test(timeout = 1000)
    public void testDeleteDoctor() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(doctorDatabase, contactDatabase);

        Integer doctorID = doctorDatabase.add(doctor);

        assertNotNull("A doctor object should be returned before it is deleted ",
                doctorDatabase.get(doctorID));

        doctorManager.deleteDoctor(doctorID);

        assertNull("A doctor object should not be returned after it is deleted ",
                doctorDatabase.get(doctorID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(doctorDatabase, contactDatabase);

        Integer doctorID = doctorDatabase.add(doctor);

        assertTrue("The password should remain the same before the change ",
                doctorDatabase.get(doctorID).comparePassword("123"));

        doctorManager.changeUserPassword(doctorID, "456");

        assertTrue("The doctor object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                doctorDatabase.get(doctorID).comparePassword("456"));
    }

    @Test(timeout = 1000)
    public void testGetDoctor() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Doctor originalDoctor = new
                Doctor("jeff", "123", 123456789);

        for (int i = 1; i <= 3; i++) {
            originalDoctor.addLogId(i);
        }

        DoctorManager doctorManager = new DoctorManager(doctorDatabase, contactDatabase);

        Integer doctorID = doctorDatabase.add(originalDoctor);

        assertEquals("Original doctor should share the same ID from the database",
                originalDoctor.getId(), doctorID);

        Doctor loadedDoctor = doctorManager.getDoctor(doctorID);

        /* Testing if the loaded doctor and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original doctor and loaded doctor should share the same Id",
                originalDoctor.getId(), loadedDoctor.getId());
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                originalDoctor.getUsername(), loadedDoctor.getUsername());
        assertEquals("Original doctor and loaded doctor should share the same logs",
                originalDoctor.getLogIds(), loadedDoctor.getLogIds());
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                originalDoctor.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword("123"));
    }


}
