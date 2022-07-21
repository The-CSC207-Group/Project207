import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;

public class AppointmentManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    @Test(timeout = 1000)
    public void testBookAppointment() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DoctorData doctorData = new DoctorManager(originalDatabase).createDoctor("test1", "test1");
        PatientData patientData = new PatientManager(originalDatabase).createPatient("test2", "test2");
        new AppointmentManager(originalDatabase).bookAppointment(patientData, doctorData, 2022, 12, 1, 10, 0, 120);


    }


}
