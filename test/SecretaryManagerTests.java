import dataBundles.ContactData;
import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.Secretary;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.SecretaryManager;
import utilities.DeleteUtils;
import java.io.File;
import java.time.LocalDate;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class SecretaryManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateSecretaryValid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        SecretaryData secretaryData = secretaryManager.createSecretary(username, password);

        /* Testing if the return secretary data is valid by testing if the fields of are equal to the parameters of
        createSecretary */
        assertEquals("The created secretary data should have the same name as the parameters of " +
                "createSecretary method", secretaryData.getUsername(), username);

        Secretary loadedSecretary = secretaryDatabase.get(secretaryData.getId());

        /* Testing if the secretary object has been correctly added to the database by testing if the fields of the loaded
        secretary are equal to the parameters of createSecretary */
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                loadedSecretary.getUsername(), username);
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword(password));
    }
    @Test(timeout = 1000)
    public void testCreateSecretaryInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        SecretaryData secretaryData = secretaryManager.createSecretary(username, password);

        assertNull("creating a user with the same name and password already existing in the database " +
                "should return null", secretaryManager.createSecretary(username, password));
    }

    @Test(timeout = 1000)
    public void testDeleteSecretary() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        Integer secretaryID = secretaryDatabase.add(secretary);

        assertNotNull("A secretary object should be returned before it is deleted ",
                secretaryDatabase.get(secretaryID));

        secretaryManager.deleteUser(secretary.getUsername());

        assertNull("A secretary object should not be returned after it is deleted ",
                secretaryDatabase.get(secretaryID));
    }
    @Test(timeout = 1000)
    public void getUserData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        Integer secretaryID = secretaryDatabase.add(secretary);

        SecretaryData secretaryData = secretaryManager.getUserData(secretary.getUsername());

        /* Testing if the secretaryData and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("secretary and secretaryData should share the same Id",
                secretary.getId(), secretaryData.getId());
        assertEquals("secretary and secretaryData should share the same unique username",
                secretary.getUsername(), secretaryData.getUsername());
        assertEquals("secretary and secretaryData should share the same contact information",
                secretary.getContactInfoId(), secretaryData.getContactInfoId());
        assertTrue("secretary and secretaryData should share the same password",
                secretary.comparePassword("123"));
    }
    @Test(timeout = 1000)
    public void getUserDataInvalid() {
        Database originalDatabase = new Database(databaseFolder.toString());
        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        assertNull("trying to get user data of a user that doesn't exist should return null",
                secretaryManager.getUserData("jim"));

    }
    @Test(timeout = 1000)
    public void testDeleteSecretaryByData() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        Integer secretaryID = secretaryDatabase.add(secretary);
        SecretaryData userdata = secretaryManager.getUserData(secretary.getUsername());

        assertNotNull("A secretary object should be returned before it is deleted ",
                secretaryDatabase.get(secretaryID));

        secretaryManager.deleteUserByData(userdata);

        assertNull("A secretary object should not be returned after it is deleted by data",
                secretaryDatabase.get(secretaryID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        Integer secretaryID = secretaryDatabase.add(secretary);
        SecretaryData secretaryData = new SecretaryData(secretary);

        assertTrue("The password should remain the same before the change ",
                secretaryDatabase.get(secretaryID).comparePassword("123"));

        secretaryManager.changeUserPassword(secretaryData, "456");

        assertTrue("The secretary object should have the same password as we inputted into the parameters " +
                        "of the changeUserPassword method ",
                secretaryDatabase.get(secretaryID).comparePassword("456"));
    }

    @Test(timeout = 1000)
    public void testGetSecretary() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary originalSecretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);

        Integer secretaryID = secretaryDatabase.add(originalSecretary);

        assertEquals("Original secretary should share the same ID from the database",
                originalSecretary.getId(), secretaryID);

        Secretary loadedSecretary = secretaryDatabase.get(originalSecretary.getId());
        /* Testing if the loaded secretary and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original secretary and loaded secretary should share the same Id",
                originalSecretary.getId(), loadedSecretary.getId());
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                originalSecretary.getUsername(), loadedSecretary.getUsername());
        assertEquals("Original secretary and loaded secretary should share the same contact information",
                originalSecretary.getContactInfoId(), loadedSecretary.getContactInfoId());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword("123"));
    }

    @Test(timeout = 1000)
    public void testDoesUserExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);

        assertNotNull("A secretary object should be returned when added to the database",
                secretaryDatabase.get(secretaryId));
        assertTrue("DoesUserExist should return true since the secretary is stored in the database",
                secretaryManager.doesUserExist(secretary.getUsername()));
    }
    @Test(timeout = 1000)
    public void testUserDoesNotExist(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);

        assertFalse("DoesUserExist should return false if there is no account stored in the database with the" +
                        "inputted username",
                secretaryManager.doesUserExist("jim"));
    }
    @Test(timeout = 1000)
    public void testCanSignInValid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);

        assertTrue("canSignIn should return true if given a username and password to an account in the " +
                "database", secretaryManager.canSignIn("jeff", "123"));
    }
    @Test(timeout = 1000)
    public void testCanSignInInvalid(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);

        assertFalse("canSignIn should return false if given a username and password not linked to an account" +
                "in the database", secretaryManager.canSignIn("jim", "password"));
    }
    @Test(timeout = 1000)
    public void testSignInExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);
        SecretaryData secretaryData = secretaryManager.getUserData(secretary.getUsername());

        assertEquals("A correct account detail sign in should return the respective secretaryData",
                secretaryManager.signIn(secretary.getUsername(), "123").getId(), secretaryData.getId());
    }

    @Test(timeout = 1000)
    public void testSignInNonExistingAccount(){
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(originalDatabase);
        Integer secretaryId = secretaryDatabase.add(secretary);
        SecretaryData secretaryData = secretaryManager.getUserData(secretary.getUsername());

        assertNull("an incorrect account detail sign in should return null", secretaryManager.signIn("jim",
                "password"));
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
