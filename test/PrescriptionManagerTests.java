import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;
import entities.Patient;
import entities.Prescription;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;
import utilities.DeleteUtils;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PrescriptionManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testGetPatientActivePrescriptionDataUsingActivePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(), doctor.getId(), zonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), zonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionData originalPrescriptionBundle1 = new PrescriptionData(originalPrescription1);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

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
    @Test(timeout = 1000)
    public void testGetPatientActivePrescriptionDataUsingInactivePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), zonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", doctor.getId(),
                doctor.getId(), zonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllActivePrescriptions(patient);

        assertTrue("Since there are no non expired prescriptions, the ArrayList should be empty",
                loadedPrescriptionList.isEmpty());
    }
    @Test(timeout = 1000)
    public void testGetPatientAllPrescriptionData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime inactiveLocalExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime activeLocalExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime inactiveZonedExpiryDate = ZonedDateTime.of(inactiveLocalExpiryDate, torontoID);
        ZonedDateTime activeZonedExpiryDate = ZonedDateTime.of(activeLocalExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveZonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeZonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(patient);

        assertEquals("The array list should have a length of 2 even though one of " +
                        "the prescriptions is expired", 2, loadedPrescriptionList.size());
    }

    @Test(timeout = 1000)
    public void testCreatePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);
        String header = "medicine";
        String body = "healthy";

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        PrescriptionData prescriptionData = prescriptionManager.createPrescription(header, body, patient,
                doctor, zonedExpiryDate);

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
                prescriptionData.getExpiryDate().compareTo(zonedExpiryDate), 0);

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
                        "createPrescription method", prescriptionData.getExpiryDate().compareTo(zonedExpiryDate),
                0);
    }

    @Test(timeout = 1000)
    public void testRemovePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime inactiveLocalExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime activeLocalExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime inactiveZonedExpiryDate = ZonedDateTime.of(inactiveLocalExpiryDate, torontoID);
        ZonedDateTime activeZonedExpiryDate = ZonedDateTime.of(activeLocalExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveZonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeZonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

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
    @Test(timeout = 1000)
    public void testGetPatientsPrescriptionsWhenTheyHaveNone() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        PatientData patient = new PatientManager(originalDatabase).createPatient("test 4", "test4");
        PatientData patient2 = new PatientManager(originalDatabase).createPatient("test 5", "test4");
        DoctorData doctor = new DoctorManager(originalDatabase).createDoctor("test 3", "test4");

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime inactiveLocalExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime activeLocalExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime inactiveZonedExpiryDate = ZonedDateTime.of(inactiveLocalExpiryDate, torontoID);
        ZonedDateTime activeZonedExpiryDate = ZonedDateTime.of(activeLocalExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", patient.getId(),
                doctor.getId(), inactiveZonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", patient.getId(),
                doctor.getId(), activeZonedExpiryDate);

        prescriptionDatabase.add(originalPrescription1);
        prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getAllPrescriptions(patient2);

        Assert.assertTrue(loadedPrescriptionList.isEmpty());
    }


    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
