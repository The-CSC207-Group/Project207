import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Appointment;
import entities.Availability;
import entities.Clinic;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppointmentManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    @Test(timeout = 1000)
    public void testBookAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());

        originalDatabase.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30), LocalTime.of(17, 0))))));

        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");

        assertTrue("An appointment object should not exist in the database before booking one", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());

        AppointmentData appointment = new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData, 2022, 12, 1, 10, 0, 120);

        assertFalse("An appointment object should exist in the database after booking one", originalDatabase.getAppointmentDatabase().getAllIds().isEmpty());
        assertEquals(appointment.getAppointmentId(), originalDatabase.getAppointmentDatabase().get(appointment.getAppointmentId()).getId());

    }


}
