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
    public void getContactInfoTest(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertEquals("Test to see if the contact id stored in the patient is the same as the one" +
                        "retrieved when getting contact info by username through contact manager.",
                contactManager.getContactData(patientData).getContactId(), patientData.getContactInfoId());
    }
    @Test
    public void testChangeName(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("The change name method should return true",
                contactManager.changeName(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                        "reflects this",
                database.getContactDatabase().get(patientData.getContactInfoId()).getName(), "Daniel Dervishi");
    }
    @Test
    public void testChangeEmailFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse("Testing method's return value",
                contactManager.changeEmail(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmail());
    }

    @Test
    public void testChangeEmailPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeEmail(contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmail(), "d.dervishi@mail.utoronto.ca");
    }

    @Test
    public void testChangePhoneNumberFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse("Testing method's return value",
                contactManager.changePhoneNumber(contactManager.getContactData(patientData), "(416) 978-2011"));
        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getPhoneNumber());
    }

    @Test
    public void testChangePhoneNumberPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changePhoneNumber(contactManager.getContactData(patientData), "4169782011"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                        "reflects this"
                , database.getContactDatabase().get(patientData.getContactInfoId()).getPhoneNumber(), "4169782011");
    }

    @Test
    public void testChangeAddress(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeAddress(contactManager.getContactData(patientData), "59 clown street crescent blvd"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getAddress(), "59 clown street crescent blvd");
    }

    @Test
    public void testChangeBirthday(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);
        LocalDate localDate = LocalDate.of(2007, 10, 12);

        PatientData patientData = patientManager.createPatient("dan", "iel");


        Assert.assertTrue("Testing method's return value",
                contactManager.changeBirthday(contactManager.getContactData(patientData),localDate));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this",database.getContactDatabase().get(patientData.getContactInfoId()).getBirthday(), localDate);
    }

    @Test
    public void testChangeEmergencyEmailFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse("Testing method's return value",
                contactManager.changeEmergencyContactEmail(contactManager.getContactData(patientData),
                        "Daniel Dervishi"));
        Assert.assertNull("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactEmail());
    }

    @Test
    public void testChangeEmergencyEmailPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeEmergencyContactEmail(contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactEmail(), "d.dervishi@mail.utoronto.ca");
    }

    @Test
    public void testChangeEmergencyContactName(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeEmergencyContactName(contactManager.getContactData(patientData), "Daniel Dervishi"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactName(), "Daniel Dervishi");
    }

    @Test
    public void testChangeEmergencyPhoneNumberFailsRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertFalse("Testing method's return value",
                contactManager.changeEmergencyContactPhoneNumber(contactManager.getContactData(patientData), "(416) 978-2011"));
        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactPhoneNumber());
    }

    @Test
    public void testChangeEmergencyPhoneNumberPassesRegex(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeEmergencyContactPhoneNumber(contactManager.getContactData(patientData), "4169782011"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyContactPhoneNumber(), "4169782011");
    }

    @Test
    public void testChangeEmergencyRelationship(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        ContactManager contactManager = new ContactManager(database);

        PatientData patientData = patientManager.createPatient("dan", "iel");

        Assert.assertTrue("Testing method's return value",
                contactManager.changeEmergencyRelationship(contactManager.getContactData(patientData), "Father"));
        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", database.getContactDatabase().get(patientData.getContactInfoId()).getEmergencyRelationship(), "Father");
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
