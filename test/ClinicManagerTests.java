import dataBundles.ClinicData;
import database.DataMapperGateway;
import database.Database;
import entities.Availability;
import entities.Clinic;
import entities.Doctor;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.ClinicManager;

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
    public void changePhone(){
        Database originalDatabase = new Database(databaseFolder.toString());


//        originalDatabase.setClinic(new Clinic("ABC", "123", "abc",
//                ZoneId.of("US/Eastern"),
//                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
//                        LocalTime.of(17, 0))))));

        Clinic clinic = new Clinic("ABC", "123", "abc", "address",
                ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                LocalTime.of(17, 0)))));
        ClinicManager clinicManager = new ClinicManager(originalDatabase);
        ClinicData clinicData = new ClinicData(clinic);
        originalDatabase.setClinic(clinic);
        clinicManager.changeClinicPhoneNumber("12345");

        assertEquals("The original phone number and the new phone number are the same",
                clinicData.getPhoneNumber(), "12345");
    }







//    @Test(timeout = 1000)
//    public void changeAddress(){
//        Database originalDatabase = new Database(databaseFolder.toString());
//        originalDatabase.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
//                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
//                        LocalTime.of(17, 0))))));
//        ClinicManager clinicManager = new ClinicManager(originalDatabase);
//    }
}
