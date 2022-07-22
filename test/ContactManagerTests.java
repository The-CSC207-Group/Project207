import dataBundles.PatientData;
import database.Database;
import entities.Patient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.ContactManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

public class ContactManagerTests {
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test
    public void testChangeName(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeName(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getName(), "Daniel Dervishi");
        Assert.assertEquals(contactManager.getContactData(patientData).getName(), "Daniel Dervishi");
    }
    @Test
    public void testChangeEmailFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse(contactManager.changeEmail(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmail());
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmail());

    }

    @Test
    public void testChangeEmailPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeEmail(contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getEmail(), "d.dervishi@mail.utoronto.ca");
        Assert.assertEquals(contactManager.getContactData(patientData).getEmail(), "d.dervishi@mail.utoronto.ca");
    }

    @Test
    public void testChangePhoneNumberFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse(contactManager.changePhoneNumber(contactManager.getContactData(patientData), "(416) 978-2011"));
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getPhoneNumber());
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getPhoneNumber());

    }

    @Test
    public void testChangePhoneNumberPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changePhoneNumber(contactManager.getContactData(patientData), "4169782011"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getPhoneNumber(), "4169782011");
        Assert.assertEquals(contactManager.getContactData(patientData).getPhoneNumber(), "4169782011");
    }

    @Test
    public void testChangeAddress(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeAddress(contactManager.getContactData(patientData), "59 clown street crescent blvd"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getAddress(), "59 clown street crescent blvd");
        Assert.assertEquals(contactManager.getContactData(patientData).getAddress(), "59 clown street crescent blvd");
    }

    @Test
    public void testChangeBirthday(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);
        LocalDate localDate = LocalDate.of(2007, 10, 12);

        PatientData patientData = patientManager.createPatient("dan", "iel");


        Assert.assertTrue(contactManager.changeBirthday(contactManager.getContactData(patientData),localDate));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getBirthday(), localDate);
        Assert.assertEquals(contactManager.getContactData(patientData).getBirthday(), localDate);
    }

    @Test
    public void testChangeEmergencyEmailFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse(contactManager.changeEmergencyContactEmail(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactEmail());
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactEmail());

    }

    @Test
    public void testChangeEmergencyEmailPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeEmergencyContactEmail(contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactEmail(), "d.dervishi@mail.utoronto.ca");
        Assert.assertEquals(contactManager.getContactData(patientData).getEmergencyContactEmail(), "d.dervishi@mail.utoronto.ca");
    }

    @Test
    public void testChangeEmergencyContactName(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeEmergencyContactName(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactName(), "Daniel Dervishi");
        Assert.assertEquals(contactManager.getContactData(patientData).getEmergencyContactName(), "Daniel Dervishi");
    }

    @Test
    public void testChangeEmergencyPhoneNumberFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse(contactManager.changeEmergencyContactPhoneNumber(contactManager.getContactData(patientData), "(416) 978-2011"));
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactPhoneNumber());
        Assert.assertNull(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactPhoneNumber());

    }

    @Test
    public void testChangeEmergencyPhoneNumberPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeEmergencyContactPhoneNumber(contactManager.getContactData(patientData), "4169782011"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactPhoneNumber(), "4169782011");
        Assert.assertEquals(contactManager.getContactData(patientData).getEmergencyContactPhoneNumber(), "4169782011");
    }

    @Test
    public void testChangeEmergencyRelationship(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue(contactManager.changeEmergencyRelationship(contactManager.getContactData(patientData), "Father"));
        Assert.assertEquals(database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyRelationship(), "Father");
        Assert.assertEquals(contactManager.getContactData(patientData).getEmergencyRelationship(), "Father");
    }







    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
