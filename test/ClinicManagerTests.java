import database.Database;
import entities.Availability;
import entities.Clinic;
import org.junit.After;

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

public class ClinicManagerTests {
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void changeClinicPhoneNumberFailsRegex(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic phone should stay same before changing it.",
               clinic.getPhoneNumber(), "4169782011");

        clinicManager.changeClinicPhoneNumber("(416) 978-2012");

        assertEquals("The clinic phone number stays the same despite being changed",
               clinic.getPhoneNumber(), "4169782011");
    }

    @Test(timeout = 1000)
    public void changeClinicPhoneNumberPassesRegex(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic phone should stay same before changing it.",
                clinic.getPhoneNumber(), "4169782011");

        clinicManager.changeClinicPhoneNumber("4169782012");

        assertEquals("The clinic phone number is successfully changed",
                clinic.getPhoneNumber(), "4169782012");
    }

    @Test(timeout = 1000)
    public void changeClinicAddress(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic address should stay same before changing it.",
                clinic.getAddress(), "address");

        clinicManager.changeClinicAddress("abcde");

        assertEquals("The clinic address and the new address are the same",
                clinic.getAddress(), "abcde");
    }

    @Test(timeout = 1000)
    public void changeClinicName(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));
        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic name should stay same before changing it.",
                clinic.getName(), "ABC");

        clinicManager.changeClinicName("The Clinic");

        assertEquals("The clinic name and the new name are the same",
                clinic.getName(), "The Clinic");
    }

    @Test(timeout = 1000)
    public void changeClinicEmailAddressFailsRegex(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));

        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic address should stay same before changing it.",
                clinic.getEmail(), "abc@gmail.com");

        clinicManager.changeClinicEmail("ryan m");

        assertEquals("The clinic email stays the same despite being changed",
                clinic.getEmail(), "abc@gmail.com");
    }

    @Test(timeout = 1000)
    public void changeClinicEmailAddressPassesRegex(){
        Database originalDatabase = new Database(databaseFolder.toString());

        Clinic clinic = new Clinic("ABC", "4169782011", "abc@gmail.com", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                        LocalTime.of(17, 0)))));

        originalDatabase.setClinic(clinic);

        ClinicManager clinicManager = new ClinicManager(originalDatabase);

        assertEquals("The clinic address should stay same before changing it.",
                clinic.getEmail(), "abc@gmail.com");

        clinicManager.changeClinicEmail("ryanm@gmail.com");

        assertEquals("The clinic email stays the same despite being changed",
                clinic.getEmail(), "ryanm@gmail.com");
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}

