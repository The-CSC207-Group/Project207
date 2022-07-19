import dataBundles.PrescriptionData;
import database.DataMapperGateway;
import database.Database;
import entities.Prescription;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
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
    public void testGetPatientActivePrescriptionDataByUserIdUsingActivePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", 123,
                456, zonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", 789,
                1011, zonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionData originalPrescriptionBundle1 = new PrescriptionData(originalPrescription1);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getPatientActivePrescriptionDataByUserId(123);

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
    public void testGetPatientActivePrescriptionDataByUserIdUsingInactivePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", 123,
                456, zonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", 789,
                1011, zonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getPatientActivePrescriptionDataByUserId(123);

        assertTrue("Since there are no non expired prescriptions, the ArrayList should be empty",
                loadedPrescriptionList.isEmpty());
    }
    @Test(timeout = 1000)
    public void testgetPatientAllPrescriptionDataByUserId() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime inactiveLocalExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime activeLocalExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime inactiveZonedExpiryDate = ZonedDateTime.of(inactiveLocalExpiryDate, torontoID);
        ZonedDateTime activeZonedExpiryDate = ZonedDateTime.of(activeLocalExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", 123,
                456, inactiveZonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", 123,
                1011, activeZonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList =
                prescriptionManager.getPatientAllPrescriptionDataByUserId(123);

        assertEquals("The array list should have a length of 2 even though one of " +
                        "the prescriptions is expired", 2, loadedPrescriptionList.size());
    }

    @Test(timeout = 1000)
    public void testCreatePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);
        String header = "medicine";
        String body = "healthy";
        Integer patientID = 123;
        Integer doctorID = 456;

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        PrescriptionData prescriptionData = prescriptionManager.createPrescription(header, body, patientID,
                doctorID, zonedExpiryDate);

        /* testing if the created prescription data bundle is valid by testing if its fields match with the parameters
        * of the createPrescription method */
        assertEquals("The created prescription data bundle should have the same date noted as the parameters of " +
                "createPrescription method", prescriptionData.getDateNoted().compareTo(zonedDateNoted),
                0); // the compareTo function returns 0 when both dates are equal
        assertEquals("The created prescription data bundle should have the same header as the " +
                        "parameters of createPrescription method", prescriptionData.getHeader(), header);
        assertEquals("The created prescription data bundle should have the same body as the " +
                        "parameters of createPrescription method", prescriptionData.getBody(), body);
        assertEquals("The created prescription data bundle should have the same patient ID noted as the " +
                        "parameters of createPrescription method", prescriptionData.getPatientId(), patientID);
        assertEquals("The created prescription data bundle should have the same patient ID noted as the " +
                "parameters of createPrescription method", prescriptionData.getDoctorId(), doctorID);
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                prescriptionData.getExpiryDate().compareTo(zonedExpiryDate), 0);

        Prescription loadedPrescription = prescriptionDatabase.get(prescriptionData.getPrescriptionId());

        /* Testing if the prescription object has been correctly added to the database by testing if the fields of the
        loaded patient are equal to the parameters of createPatient */
        assertEquals("The loaded prescription object should have the same date noted as the parameters of " +
                        "createPrescription method", loadedPrescription.getDateNoted().compareTo(zonedDateNoted),
                0); // the compareTo function returns 0 when both dates are equal
        assertEquals("The loaded prescription object should have the same header as the " +
                "parameters of createPrescription method", loadedPrescription.getHeader(), header);
        assertEquals("The loaded prescription object should have the same body as the " +
                "parameters of createPrescription method", loadedPrescription.getBody(), body);
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", loadedPrescription.getPatientId(), patientID);
        assertEquals("The loaded prescription object should have the same patient ID noted as the " +
                "parameters of createPrescription method", prescriptionData.getDoctorId(), doctorID);
        assertEquals("The loaded prescription object should have the same expiry as the parameters of " +
                        "createPrescription method", prescriptionData.getExpiryDate().compareTo(zonedExpiryDate),
                0);
    }

    @Test(timeout = 1000)
    public void testRemovePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2020,7,1,4,3);
        LocalDateTime inactiveLocalExpiryDate = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime activeLocalExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime inactiveZonedExpiryDate = ZonedDateTime.of(inactiveLocalExpiryDate, torontoID);
        ZonedDateTime activeZonedExpiryDate = ZonedDateTime.of(activeLocalExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription("medicine", "healthy", 123,
                456, inactiveZonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription("bad", "very unhealthy", 123,
                1011, activeZonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionManager prescriptionManager = new PrescriptionManager(originalDatabase);

        ArrayList<PrescriptionData> loadedPrescriptionList1 =
                prescriptionManager.getPatientAllPrescriptionDataByUserId(123);

        assertEquals("The array list should have a length of 2 before a prescription is removed ",
                2, loadedPrescriptionList1.size());

        prescriptionManager.removePrescription(prescriptionID2);

        ArrayList<PrescriptionData> loadedPrescriptionList2 =
                prescriptionManager.getPatientAllPrescriptionDataByUserId(123);

        assertEquals("The array list should have a length of 1 after a prescription is removed ",
                1, loadedPrescriptionList2.size());

        prescriptionManager.removePrescription(prescriptionID1);

        ArrayList<PrescriptionData> loadedPrescriptionList3 =
                prescriptionManager.getPatientAllPrescriptionDataByUserId(123);

        assertTrue("The array list should be empty after all prescriptions are removed",
                loadedPrescriptionList3.isEmpty());
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
