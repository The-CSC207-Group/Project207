import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;
import entities.Patient;
import entities.Prescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.PrescriptionManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PrescriptionManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 50)
    public void testGetPatientActivePrescriptionDataByUserIdUsingActivePrescription() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Prescription> prescriptionDatabase = originalDatabase.getPrescriptionDatabase();

        LocalDateTime localDateNoted = LocalDateTime.of(2022,7,1,4,3);
        LocalDateTime localExpiryDate = LocalDateTime.of(2050, 7, 1, 0, 0);
        ZoneId torontoID = ZoneId.of("Canada/Eastern");
        ZonedDateTime zonedDateNoted = ZonedDateTime.of(localDateNoted, torontoID);
        ZonedDateTime zonedExpiryDate = ZonedDateTime.of(localExpiryDate, torontoID);

        Prescription originalPrescription1 = new
                Prescription(zonedDateNoted, "medicine", "healthy", 123,
                456, zonedExpiryDate);
        Prescription originalPrescription2 = new
                Prescription(zonedDateNoted, "bad", "very unhealthy", 789,
                1011, zonedExpiryDate);

        Integer prescriptionID1 = prescriptionDatabase.add(originalPrescription1);
        Integer prescriptionID2 = prescriptionDatabase.add(originalPrescription2);

        PrescriptionDataBundle originalPrescriptionBundle1 = new PrescriptionDataBundle(prescriptionID1,
                originalPrescription1);

        PrescriptionManager prescriptionManager = new PrescriptionManager(prescriptionDatabase);

        ArrayList<PrescriptionDataBundle> loadedPrescriptionList =
                prescriptionManager.getPatientActivePrescriptionDataByUserId(123);

        PrescriptionDataBundle loadedPrescriptionDataBundle1 = loadedPrescriptionList.get(0);

        /* testing if the loaded prescription and the original prescription are equal by testing whether all
        the fields of both objects are equal */
        assertEquals("Original prescription and loaded prescription should share the same ID",
                originalPrescriptionBundle1.getId(), loadedPrescriptionDataBundle1.getId());
        assertEquals("Original prescription and loaded prescription should be have been noted " +
                "on the same date", originalPrescriptionBundle1.getDateNoted().compareTo(loadedPrescriptionDataBundle1.
                getDateNoted()), 0); // the compareTo function returns 0 when both dates are equal
        assertEquals("Original prescription and loaded prescription should share the same header",
                originalPrescriptionBundle1.getHeader(), loadedPrescriptionDataBundle1.getHeader());
        assertEquals("Original prescription and loaded prescription should share the same body",
                originalPrescriptionBundle1.getBody(), loadedPrescriptionDataBundle1.getBody());
        assertEquals("Original prescription and loaded prescription should share the same patient ID",
                originalPrescriptionBundle1.getPatient(), loadedPrescriptionDataBundle1.getPatient());
        assertEquals("Original prescription and loaded prescription should share the same doctor ID",
                originalPrescriptionBundle1.getDoctor(), loadedPrescriptionDataBundle1.getDoctor());
        assertEquals("Original prescription and loaded prescription have the same expiry date",
                originalPrescriptionBundle1.getExpiryDate().compareTo(loadedPrescriptionDataBundle1.
                        getExpiryDate()), 0);
    }
}
