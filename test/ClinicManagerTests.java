import dataBundles.ClinicData;
import database.DataMapperGateway;
import database.Database;
import entities.Availability;
import entities.Clinic;
import entities.Doctor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.ClinicManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClinicManagerTests {
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void changeClinicPhoneNumber(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "123", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The original phone should stay same before changing it.",
               clinic.getPhoneNumber(), "123");

        clinicManager.changeClinicPhoneNumber("12345");
        assertEquals("The original phone number and the new phone number are the same",
               clinic.getPhoneNumber(), "12345");

        assertNotEquals("The phone number in the database doesnt match the inputted phone number.",
                clinic.getPhoneNumber(), "123");
    }
    @Test(timeout = 1000)
    public void changeClinicAddress(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "123", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The original address should stay same before changing it.",
                clinic.getAddress(), "address");

        clinicManager.changeClinicAddress("abcde");

        assertEquals("The original address and the new address are the same",
                clinic.getAddress(), "abcde");

        assertNotEquals("The address in database and the inputted address arent the same",
                clinic.getAddress(), "abcdefgh");
    }
    @Test(timeout = 1000)
    public void changeClinicName(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "123", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The original name should stay same before changing it.",
                clinic.getName(), "ABC");

        clinicManager.changeClinicName("The Clinic");

        assertEquals("The original name and the new name are the same",
                clinic.getName(), "The Clinic");

        assertNotEquals("The clinic's name in database and input name aren't the same",
                clinic.getName(), "A Clinic");
    }
    @Test(timeout = 1000)
    public void changeClinicEmailAddress(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "123", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));

        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The original address should stay same before changing it.",
                clinic.getEmail(), "abc@gmail.com");

        clinicManager.changeClinicEmail("clinic@gmail.com");

        assertEquals("The original address and the new address are the same",
                clinic.getEmail(), "clinic@gmail.com");

        assertNotEquals("The address in the database and the inputted address arent the same",
                clinic.getEmail(), "clinic@yahoo.com");
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}

