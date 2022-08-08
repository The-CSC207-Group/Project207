import database.Database;
import entities.Availability;
import entities.Clinic;
import org.junit.After;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.ClinicManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClinicManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    private ClinicManager clinicManager;
    private Clinic clinic;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        Database originalDatabase = new Database(databaseFolder.toString());
        clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);
        clinicManager = new ClinicManager(originalDatabase);
    }

    /**
     * Tests the clinic manager method that changes the clinic's phone number with an input that fails the regex check.
     */
    @Test(timeout = 1000)
    public void changeClinicPhoneNumberFailsRegex() {
        assertEquals("The clinic phone should stay same before changing it.",
               clinic.getPhoneNumber(), "4169782011");

        clinicManager.changeClinicPhoneNumber("(416) 978-2012");

        assertEquals("The clinic phone number stays the same despite being changed",
               clinic.getPhoneNumber(), "4169782011");
    }

    /**
     * Tests the clinic manager method that changes the clinic's phone number with an input that passes the regex check.
     */
    @Test(timeout = 1000)
    public void changeClinicPhoneNumberPassesRegex() {
        assertEquals("The clinic phone should stay same before changing it.",
                clinic.getPhoneNumber(), "4169782011");

        clinicManager.changeClinicPhoneNumber("4169782012");

        assertEquals("The clinic phone number is successfully changed",
                clinic.getPhoneNumber(), "4169782012");
    }

    /**
     * Tests the clinic manager method that changes the clinic's address.
     */
    @Test(timeout = 1000)
    public void changeClinicAddress() {
        assertEquals("The clinic address should stay same before changing it.",
                clinic.getAddress(), "address");

        clinicManager.changeClinicAddress("abcde");

        assertEquals("The clinic address and the new address are the same",
                clinic.getAddress(), "abcde");
    }

    /**
     * Tests the clinic manager method that changes the clinic's name.
     */
    @Test(timeout = 1000)
    public void changeClinicName() {
        assertEquals("The clinic name should stay same before changing it.",
                clinic.getName(), "ABC");

        clinicManager.changeClinicName("The Clinic");

        assertEquals("The clinic name and the new name are the same",
                clinic.getName(), "The Clinic");
    }

    /**
     * Tests the clinic manager method that changes the clinic's email with an input that fails the regex check.
     */
    @Test(timeout = 1000)
    public void changeClinicEmailAddressFailsRegex() {
        assertEquals("The clinic address should stay same before changing it.",
                clinic.getEmail(), "abc@gmail.com");

        clinicManager.changeClinicEmail("ryan m");

        assertEquals("The clinic email stays the same despite being changed",
                clinic.getEmail(), "abc@gmail.com");
    }

    /**
     * Tests the clinic manager method that changes the clinic's email with an input that passes the regex check.
     */
    @Test(timeout = 1000)
    public void changeClinicEmailAddressPassesRegex() {
        assertEquals("The clinic address should stay same before changing it.",
                clinic.getEmail(), "abc@gmail.com");

        clinicManager.changeClinicEmail("ryanm@gmail.com");

        assertEquals("The clinic email stays the same despite being changed",
                clinic.getEmail(), "ryanm@gmail.com");
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}

