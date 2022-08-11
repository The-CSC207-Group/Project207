import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.DataMapperGateway;
import database.Database;
import entities.Prescription;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import useCases.DoctorManager;
import useCases.PatientManager;
import useCases.PrescriptionManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the PrescriptionManager class.
 */
public class PrescriptionManagerTests {
    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public final TemporaryFolder databaseFolder = new TemporaryFolder();
    private Database originalDatabase;
    private DataMapperGateway<Prescription> prescriptionDatabase;
    private PatientData patientData;
    private DoctorData doctorData;
    private PrescriptionManager prescriptionManager;
    private PrescriptionData inactivePrescriptionData;
    private PrescriptionData activePrescriptionData;


    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        originalDatabase = new Database(databaseFolder.toString());
        prescriptionDatabase = originalDatabase.getPrescriptionDatabase();
        patientData = new PatientManager(originalDatabase).createPatient("mynamejeff", "123456789");
        doctorData = new DoctorManager(originalDatabase).createDoctor("mynamejim", "123456789");
        prescriptionManager = new PrescriptionManager(originalDatabase);
        LocalDate inactiveLocalExpiryDate = LocalDate.of(2020, 2, 2);
        LocalDate activeLocalExpiryDate = LocalDate.of(2099, 1, 1);
        inactivePrescriptionData = prescriptionManager.createPrescription("medicine", "healthy",
                patientData, doctorData, inactiveLocalExpiryDate);
        activePrescriptionData = prescriptionManager.createPrescription("bad", "very unhealthy",
                patientData, doctorData, activeLocalExpiryDate);
    }

    /**
     * Tests getAllActivePrescription with a patient that has an active prescription, and ensuring the prescription
     * in database is the same as the one returned.
     */
    @Test(timeout = 1000)
    public void testGetPatientActivePrescriptionData() {
        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllActivePrescriptions(patientData);

        assertEquals("Since there is only one non-expired prescriptions, the ArrayList have a length of 1",
                loadedPrescriptionList.size(), 1);

        PrescriptionData loadedPrescriptionData = loadedPrescriptionList.get(0);

        /* testing if the loaded prescription and the original prescription are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original prescription and loaded prescription should share the same ID",
                activePrescriptionData.getId(), loadedPrescriptionData.getId());
        assertEquals("Original prescription and loaded prescription should be have been noted " +
                "on the same date", activePrescriptionData.getDateNoted().compareTo(loadedPrescriptionData.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original prescription and loaded prescription should share the same header",
                activePrescriptionData.getHeader(), loadedPrescriptionData.getHeader());
        assertEquals("Original prescription and loaded prescription should share the same body",
                activePrescriptionData.getBody(), loadedPrescriptionData.getBody());
        assertEquals("Original prescription and loaded prescription should share the same patient ID",
                activePrescriptionData.getPatientId(), loadedPrescriptionData.getPatientId());
        assertEquals("Original prescription and loaded prescription should share the same doctor ID",
                activePrescriptionData.getDoctorId(), loadedPrescriptionData.getDoctorId());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                activePrescriptionData.getExpiryDate().compareTo(loadedPrescriptionData.
                        getExpiryDate()), 0);
    }

    /**
     * Tests getAllPrescriptions related to a patient, and ensuring that even expired prescriptions are returned.
     */
    @Test(timeout = 1000)
    public void testGetPatientAllPrescriptionData() {
        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(patientData);

        assertEquals("The array list should have a length of 2 even though one of " +
                "the prescriptions is expired", 2, loadedPrescriptionList.size());
    }

    /**
     * Tests create prescription and ensuring that the prescription created in the database is the same as the
     * prescriptionData returned.
     */
    @Test(timeout = 1000)
    public void testCreatePrescription() {
        LocalDate activeLocalExpiryDate = LocalDate.of(2099, 1, 1);
        /* testing if the created prescription data is valid by testing if its fields match with the parameters
         * of the createPrescription method */
        assertEquals("The created prescription data should have the same header as the " +
                "parameters of createPrescription method", activePrescriptionData.getHeader(), "bad");
        assertEquals("The created prescription data should have the same body as the " +
                        "parameters of createPrescription method", activePrescriptionData.getBody(),
                "very unhealthy");
        assertEquals("The created prescription data should have the same patient ID noted as the " +
                        "parameters of createPrescription method",
                activePrescriptionData.getPatientId(), patientData.getId());
        assertEquals("The created prescription data should have the same patient ID noted as the " +
                "parameters of createPrescription method", activePrescriptionData.getDoctorId(), doctorData.getId());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                activePrescriptionData.getExpiryDate().compareTo(activeLocalExpiryDate), 0);

        Prescription loadedPrescription = prescriptionDatabase.get(activePrescriptionData.getId());

        /* Testing if the prescription object has been correctly added to the database by testing if the fields of the
        loaded patient are equal to the parameters of createPatient */
        assertEquals("The loaded prescription object should have the same header as the " +
                "parameters of createPrescription method", loadedPrescription.getHeader(), "bad");
        assertEquals("The loaded prescription object should have the same body as the " +
                "parameters of createPrescription method", loadedPrescription.getBody(), "very unhealthy");
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", loadedPrescription.getPatientId(), patientData.getId());
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", activePrescriptionData.getDoctorId(), doctorData.getId());
        assertEquals("The loaded prescription object should have the same expiry as the parameters of " +
                        "createPrescription method",
                activePrescriptionData.getExpiryDate().compareTo(activeLocalExpiryDate), 0);
    }

    /**
     * Tests remove prescription and ensures that no prescriptions exist after removed.
     */
    @Test(timeout = 1000)
    public void testRemovePrescription() {
        ArrayList<PrescriptionData> loadedPrescriptionList1 =
                prescriptionManager.getAllPrescriptions(patientData);

        assertEquals("The array list should have a length of 2 before a prescription is removed ",
                2, loadedPrescriptionList1.size());

        prescriptionManager.removePrescription(activePrescriptionData);

        ArrayList<PrescriptionData> loadedPrescriptionList2 =
                prescriptionManager.getAllPrescriptions(patientData);

        assertEquals("The array list should have a length of 1 after a prescription is removed ",
                1, loadedPrescriptionList2.size());

        prescriptionManager.removePrescription(inactivePrescriptionData);

        ArrayList<PrescriptionData> loadedPrescriptionList3 =
                prescriptionManager.getAllPrescriptions(patientData);

        assertTrue("The array list should be empty after all prescriptions are removed",
                loadedPrescriptionList3.isEmpty());
    }

    /**
     * Tests an invalid use of getAllPrescriptions related to a patient when no prescriptions are stored in the database.
     */
    @Test(timeout = 1000)
    public void testGetPatientsPrescriptionsWhenTheyHaveNone() {
        PatientData newPatientData = new PatientManager(originalDatabase).createPatient(
                "newpatient", "123456789");
        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(newPatientData);

        Assert.assertTrue("The list of this patient's prescriptions should be empty",
                loadedPrescriptionList.isEmpty());
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
