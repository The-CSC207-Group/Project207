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

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the PrescriptionManager class.
 */
public class PrescriptionManagerTests {
    Database originalDatabase;
    DataMapperGateway<Prescription> prescriptionDatabase;
    PatientData patient;
    DoctorData doctor;
    PrescriptionManager prescriptionManager;

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before(){
        originalDatabase = new Database(databaseFolder.toString());
        prescriptionDatabase = originalDatabase.getPrescriptionDatabase();
        patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");
        prescriptionManager = new PrescriptionManager(originalDatabase);
    }

    /**
     * Tests getAllActivePrescription by inputting a valid active prescription, and ensuring the prescription in database
     * is the same as the one returned.
     */
    @Test(timeout = 1000)
    public void testGetPatientActivePrescriptionDataUsingActivePrescription() {
        LocalDate localExpiryDate = LocalDate.of(2050, 7, 1);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(), doctor.getId(), localExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), localExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        PrescriptionData originalPrescriptionBundle1 = new PrescriptionData(originalPrescription1);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllActivePrescriptions(patient);

        PrescriptionData loadedPrescriptionData1 = loadedPrescriptionList.get(0);

        /* testing if the loaded prescription and the original prescription are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original prescription and loaded prescription should share the same ID",
                originalPrescriptionBundle1.getPrescriptionId(), loadedPrescriptionData1.getPrescriptionId());
        assertEquals("Original prescription and loaded prescription should be have been noted " +
                "on the same date", originalPrescriptionBundle1.getDateNoted().compareTo(loadedPrescriptionData1.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original prescription and loaded prescription should share the same header",
                originalPrescriptionBundle1.getHeader(), loadedPrescriptionData1.getHeader());
        assertEquals("Original prescription and loaded prescription should share the same body",
                originalPrescriptionBundle1.getBody(), loadedPrescriptionData1.getBody());
        assertEquals("Original prescription and loaded prescription should share the same patient ID",
                originalPrescriptionBundle1.getPatientId(), loadedPrescriptionData1.getPatientId());
        assertEquals("Original prescription and loaded prescription should share the same doctor ID",
                originalPrescriptionBundle1.getDoctorId(), loadedPrescriptionData1.getDoctorId());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                originalPrescriptionBundle1.getExpiryDate().compareTo(loadedPrescriptionData1.
                        getExpiryDate()), 0);
    }
    /**
     * Tests getAllActivePrescription by inputting an inactive prescription, and ensuring the is no prescription in the
     * database.
     */
    @Test(timeout = 1000)
    public void testGetPatientActivePrescriptionDataUsingInactivePrescription() {
        LocalDate localExpiryDate = LocalDate.of(2021, 7, 1);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), localExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", doctor.getId(),
                doctor.getId(), localExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllActivePrescriptions(patient);

        assertTrue("Since there are no non expired prescriptions, the ArrayList should be empty",
                loadedPrescriptionList.isEmpty());
    }

    /**
     * Tests getAllPrescriptions related to a patient, and ensuring that even expired prescriptions are returned.
     */
    @Test(timeout = 1000)
    public void testGetPatientAllPrescriptionData() {
        LocalDate inactiveLocalExpiryDate = LocalDate.of(2021, 7, 1);
        LocalDate activeLocalExpiryDate = LocalDate.of(2050, 7, 1);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveLocalExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeLocalExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(patient);

        assertEquals("The array list should have a length of 2 even though one of " +
                        "the prescriptions is expired", 2, loadedPrescriptionList.size());
    }

    /**
     * Tests create prescription and ensuring that the prescription created in the database is the same as the
     * prescriptionData returned.
     */
    @Test(timeout = 1000)
    public void testCreatePrescription() {
        LocalDate localExpiryDate = LocalDate.of(2050, 7, 1);
        String header = "medicine";
        String body = "healthy";

        PrescriptionData prescriptionData = prescriptionManager.createPrescription(header, body, patient,
                doctor, localExpiryDate);

        /* testing if the created prescription data is valid by testing if its fields match with the parameters
        * of the createPrescription method */
        assertEquals("The created prescription data should have the same header as the " +
                        "parameters of createPrescription method", prescriptionData.getHeader(), header);
        assertEquals("The created prescription data should have the same body as the " +
                        "parameters of createPrescription method", prescriptionData.getBody(), body);
        assertEquals("The created prescription data should have the same patient ID noted as the " +
                        "parameters of createPrescription method", prescriptionData.getPatientId(), patient.getId());
        assertEquals("The created prescription data should have the same patient ID noted as the " +
                "parameters of createPrescription method", prescriptionData.getDoctorId(), doctor.getId());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                prescriptionData.getExpiryDate().compareTo(localExpiryDate), 0);

        Prescription loadedPrescription = prescriptionDatabase.get(prescriptionData.getPrescriptionId());

        /* Testing if the prescription object has been correctly added to the database by testing if the fields of the
        loaded patient are equal to the parameters of createPatient */
        assertEquals("The loaded prescription object should have the same header as the " +
                "parameters of createPrescription method", loadedPrescription.getHeader(), header);
        assertEquals("The loaded prescription object should have the same body as the " +
                "parameters of createPrescription method", loadedPrescription.getBody(), body);
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", loadedPrescription.getPatientId(), patient.getId());
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", prescriptionData.getDoctorId(), doctor.getId());
        assertEquals("The loaded prescription object should have the same expiry as the parameters of " +
                        "createPrescription method", prescriptionData.getExpiryDate().compareTo(localExpiryDate),
                0);
    }

    /**
     * Tests remove prescription and ensures that no prescriptions exist after removed.
     */
    @Test(timeout = 1000)
    public void testRemovePrescription() {
        LocalDate inactiveLocalExpiryDate = LocalDate.of(2021, 7, 1);
        LocalDate activeLocalExpiryDate = LocalDate.of(2050, 7, 1);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveLocalExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeLocalExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        ArrayList<PrescriptionData> loadedPrescriptionList1 =
                prescriptionManager.getAllPrescriptions(patient);

        assertEquals("The array list should have a length of 2 before a prescription is removed ",
                2, loadedPrescriptionList1.size());

        prescriptionManager.removePrescription(new PrescriptionData(originalPrescription2));

        ArrayList<PrescriptionData> loadedPrescriptionList2 =
                prescriptionManager.getAllPrescriptions(patient);

        assertEquals("The array list should have a length of 1 after a prescription is removed ",
                1, loadedPrescriptionList2.size());

        prescriptionManager.removePrescription(new PrescriptionData(originalPrescription1));

        ArrayList<PrescriptionData> loadedPrescriptionList3 =
                prescriptionManager.getAllPrescriptions(patient);

        assertTrue("The array list should be empty after all prescriptions are removed",
                loadedPrescriptionList3.isEmpty());
    }

    /**
     * Tests an invalid use of getAllPrescriptions related to a patient when no prescriptions are stored in the database.
     */
    @Test(timeout = 1000)
    public void testGetPatientsPrescriptionsWhenTheyHaveNone() {
        PatientData patient2 = new PatientManager(originalDatabase).createPatient("test 5", "test4");

        LocalDate inactiveLocalExpiryDate = LocalDate.of(2021, 7, 1);
        LocalDate activeLocalExpiryDate = LocalDate.of(2050, 7, 1);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveLocalExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeLocalExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(patient2);

        Assert.assertTrue("The list of this patient's prescriptions should be empty", loadedPrescriptionList.isEmpty());
    }
    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
