import dataBundles.PatientData;
import database.Database;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import useCases.ContactManager;
import useCases.PatientManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

/**
 * Class of unit tests for ContactManager use case class.
 */
public class ContactManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    private Database database;
    private ContactManager contactManager;
    private PatientData patientData;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        contactManager = new ContactManager(database);
        patientData = patientManager.createPatient("danieldervy", "123456789");

    }

    /**
     * Tests the contact manager method that changes a user's name.
     */
    @Test
    public void testChangeName() {
        boolean changeNameReturnValue = contactManager.changeName(
                contactManager.getContactData(patientData), "Daniel Dervishi");

        Assert.assertTrue("The change name method should return true", changeNameReturnValue);

        String databaseNameField = database.getContactDatabase().get(patientData.getContactInfoId()).getName();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseNameField, "Daniel Dervishi");
    }

    /**
     * Tests the contact manager method that changes a user's email with an input that fails the regex check.
     */
    @Test
    public void testChangeEmailFailsRegex() {
        boolean changeEmailReturnValue = contactManager.changeEmail(
                contactManager.getContactData(patientData), "Daniel Dervishi");

        Assert.assertFalse("Testing method's return value", changeEmailReturnValue);

        String databaseEmailField = database.getContactDatabase().get(patientData.getContactInfoId()).getEmail();

        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", databaseEmailField);
    }

    /**
     * Tests the contact manager method that changes a user's email with an input that passes the regex check.
     */
    @Test
    public void testChangeEmailPassesRegex() {
        boolean changeEmailReturnValue = contactManager.changeEmail(
                contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca");

        Assert.assertTrue("Testing method's return value", changeEmailReturnValue);

        String databaseEmailField = database.getContactDatabase().get(patientData.getContactInfoId()).getEmail();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmailField, "d.dervishi@mail.utoronto.ca");
    }

    /**
     * Tests the contact manager method that changes a user's phone number with an input that fails the regex check.
     */
    @Test
    public void testChangePhoneNumberFailsRegex() {
        boolean changePhoneNumberReturnValue = contactManager.changePhoneNumber(
                contactManager.getContactData(patientData), "(416) 978-2011");

        Assert.assertFalse("Testing method's return value", changePhoneNumberReturnValue);

        String databasePhoneNumberField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getPhoneNumber();

        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", databasePhoneNumberField);
    }

    /**
     * Tests the contact manager method that changes a user's phone number with an input that passes the regex check.
     */
    @Test
    public void testChangePhoneNumberPassesRegex() {
        boolean changePhoneNumberReturnValue = contactManager.changePhoneNumber(
                contactManager.getContactData(patientData), "4169782011");

        Assert.assertTrue("Testing method's return value", changePhoneNumberReturnValue);

        String databasePhoneNumberField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getPhoneNumber();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databasePhoneNumberField, "4169782011");
    }

    /**
     * Tests the contact manager method that changes a user's address.
     */
    @Test
    public void testChangeAddress() {
        boolean changeAddressReturnValue = contactManager.changeAddress(
                contactManager.getContactData(patientData), "59 clown street crescent blvd");

        Assert.assertTrue("Testing method's return value", changeAddressReturnValue);

        String databaseAddressField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getAddress();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseAddressField, "59 clown street crescent blvd");
    }

    /**
     * Tests the contact manager method that changes a user's birthday.
     */
    @Test
    public void testChangeBirthday() {
        LocalDate localDate = LocalDate.of(2007, 10, 12);
        boolean changeBirthdayReturnValue = contactManager.changeBirthday(
                contactManager.getContactData(patientData), localDate);

        Assert.assertTrue("Testing method's return value", changeBirthdayReturnValue);

        LocalDate databaseBirthdayField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getBirthday();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseBirthdayField, localDate);
    }

    /**
     * Tests the contact manager method that changes a user's emergency contact email with an input that fails the
     * regex check.
     */
    @Test
    public void testChangeEmergencyEmailFailsRegex() {
        boolean changeEmergencyEmailReturnValue = contactManager.changeEmergencyContactEmail(
                contactManager.getContactData(patientData), "Daniel Dervishi");

        Assert.assertFalse("Testing method's return value", changeEmergencyEmailReturnValue);

        String databaseEmergencyEmailField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyContactEmail();

        Assert.assertNull("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyEmailField);
    }

    /**
     * Tests the contact manager method that changes a user's emergency contact email with an input that passes the
     * regex check.
     */
    @Test
    public void testChangeEmergencyEmailPassesRegex() {
        boolean changeEmergencyEmailReturnValue = contactManager.changeEmergencyContactEmail(
                contactManager.getContactData(patientData), "d.dervishi@mail.utoronto.ca");

        Assert.assertTrue("Testing method's return value", changeEmergencyEmailReturnValue);

        String databaseEmergencyEmailField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyContactEmail();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyEmailField, "d.dervishi@mail.utoronto.ca");
    }

    /**
     * Tests the contact manager method that changes a user's emergency contact name.
     */
    @Test
    public void testChangeEmergencyContactName() {
        boolean changeEmergencyContactNameReturnValue = contactManager.changeEmergencyContactName(
                contactManager.getContactData(patientData), "Daniel Dervishi");

        Assert.assertTrue("Testing method's return value", changeEmergencyContactNameReturnValue);

        String databaseEmergencyContactNameField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyContactName();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyContactNameField, "Daniel Dervishi");
    }

    /**
     * Tests the contact manager method that changes a user's emergency contact phone number with an input that fails
     * the regex check.
     */
    @Test
    public void testChangeEmergencyPhoneNumberFailsRegex() {
        boolean changeEmergencyPhoneNumberReturnValue = contactManager.changeEmergencyContactPhoneNumber(
                contactManager.getContactData(patientData), "(416) 978-2011");

        Assert.assertFalse("Testing method's return value", changeEmergencyPhoneNumberReturnValue);

        String databaseEmergencyPhoneNumberField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyContactPhoneNumber();

        Assert.assertNull("Make sure the field was not updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyPhoneNumberField);
    }

    /**
     * Tests the contact manager method that changes a user's emergency contact phone number with an input that passes
     * the regex check.
     */
    @Test
    public void testChangeEmergencyPhoneNumberPassesRegex() {
        boolean changeEmergencyPhoneNumberReturnValue = contactManager.changeEmergencyContactPhoneNumber(
                contactManager.getContactData(patientData), "4169782011");

        Assert.assertTrue("Testing method's return value", changeEmergencyPhoneNumberReturnValue);

        String databaseEmergencyPhoneNumberField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyContactPhoneNumber();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyPhoneNumberField, "4169782011");
    }

    /**
     * Tests the contact manager method that changes a user's emergency relationship.
     */
    @Test
    public void testChangeEmergencyRelationship() {
        boolean changeEmergencyPhoneNumberReturnRelationship = contactManager.changeEmergencyRelationship(
                contactManager.getContactData(patientData), "Father");

        Assert.assertTrue("Testing method's return value", changeEmergencyPhoneNumberReturnRelationship);

        String databaseEmergencyRelationshipField = database.getContactDatabase().get(
                patientData.getContactInfoId()).getEmergencyRelationship();

        Assert.assertEquals("Make sure the field was updated in the contact and that the contact database" +
                "reflects this", databaseEmergencyRelationshipField, "Father");
    }

    /**
     * Tests the contact manager getter method that returns a user's contact info.
     */
    @Test
    public void getContactInfoTest() {
        Assert.assertEquals("Test to see if the contact id stored in the patient is the same as the one" +
                        "retrieved when getting contact info by username through contact manager.",
                contactManager.getContactData(patientData).getContactId(), patientData.getContactInfoId());
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
