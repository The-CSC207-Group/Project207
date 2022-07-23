import dataBundles.DoctorData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.DoctorManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class DoctorManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateDoctorValid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        DoctorData doctorData = doctorManager.createDoctor(username, password);

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
    @Test(timeout = 1000)
    public void testCreateDoctorInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        DoctorData doctorData = doctorManager.createDoctor(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", doctorManager.createDoctor(username, password));
    }

    @Test(timeout = 1000)
    public void testDeleteDoctor() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        Integer doctorID = doctorDatabase.add(doctor);

        assertNotNull("A doctor object should be returned before it is deleted ",
                doctorDatabase.get(doctorID));

        doctorManager.deleteUser(doctor.getUsername());

        assertNull("A doctor object should not be returned after it is deleted ",
                doctorDatabase.get(doctorID));
    }
    @Test(timeout = 1000)
    public void getUserData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        Integer doctorID = doctorDatabase.add(doctor);

        DoctorData doctorData = doctorManager.getUserData(doctor.getUsername());

        /* Testing if the doctorData and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("doctor and doctorData should share the same Id",
                doctor.getId(), doctorData.getId());
        assertEquals("doctor and doctorData should share the same unique username",
                doctor.getUsername(), doctorData.getUsername());
        assertEquals("doctor and doctorData should share the same contact information",
                doctor.getContactInfoId(), doctorData.getContactInfoId());
        assertTrue("doctor and doctorData should share the same password",
                doctor.comparePassword("123"));
    }
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        assertNull("trying to get user data of a user that doesn't exist should return null",
                doctorManager.getUserData("jim"));

    }
    @Test(timeout = 1000)
    public void testDeleteDoctorByData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        Integer doctorID = doctorDatabase.add(doctor);
        DoctorData userdata = doctorManager.getUserData(doctor.getUsername());

        assertNotNull("A doctor object should be returned before it is deleted ",
                doctorDatabase.get(doctorID));

        doctorManager.deleteUserByData(userdata);

        assertNull("A doctor object should not be returned after it is deleted by data",
                doctorDatabase.get(doctorID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        Integer doctorID = doctorDatabase.add(doctor);
        DoctorData doctorData = new DoctorData(doctor);

        assertTrue("The password should remain the same before the change ",
                doctorDatabase.get(doctorID).comparePassword("123"));

        doctorManager.changeUserPassword(doctorData, "456");

        assertTrue("The doctor object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                doctorDatabase.get(doctorID).comparePassword("456"));
    }

    @Test(timeout = 1000)
    public void testGetDoctor() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor originalDoctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);

        Integer doctorID = doctorDatabase.add(originalDoctor);

        assertEquals("Original doctor should share the same ID from the database",
                originalDoctor.getId(), doctorID);

        Doctor loadedDoctor = doctorDatabase.get(originalDoctor.getId());
        /* Testing if the loaded doctor and the original doctor are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original doctor and loaded doctor should share the same Id",
                originalDoctor.getId(), loadedDoctor.getId());
        assertEquals("Original doctor and loaded doctor should share the same unique username",
                originalDoctor.getUsername(), loadedDoctor.getUsername());
        assertEquals("Original doctor and loaded doctor should share the same contact information",
                originalDoctor.getContactInfoId(), loadedDoctor.getContactInfoId());
        assertTrue("Original doctor and loaded doctor should share the same password",
                loadedDoctor.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);

        assertNotNull("A doctor object should be returned when added to the database",
                doctorDatabase.get(doctorId));
        assertTrue("DoesUserExist should return true since the doctor is stored in the database",
                doctorManager.doesUserExist(doctor.getUsername()));
    }
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);

        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                doctorManager.doesUserExist("jim"));
    }
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", doctorManager.canSignIn("jeff", "123"));
    }
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", doctorManager.canSignIn("jim", "password"));
    }
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);
        DoctorData doctorData = doctorManager.getUserData(doctor.getUsername());

        assertEquals("A correct account detail sign in should return the respective doctorData",
                doctorManager.signIn(doctor.getUsername(), "123").getId(), doctorData.getId());
    }

    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Doctor> doctorDatabase = originalDatabase.getDoctorDatabase();

        Doctor doctor = new
                Doctor("jeff", "123", 123456789);

        DoctorManager doctorManager = new DoctorManager(originalDatabase);
        Integer doctorId = doctorDatabase.add(doctor);
        DoctorData doctorData = doctorManager.getUserData(doctor.getUsername());

        assertNull("an incorrect account detail sign in should return null", doctorManager.signIn("jim",
                "password"));
    }
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
