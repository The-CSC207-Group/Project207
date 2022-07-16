import dataBundles.ContactData;
import dataBundles.SecretaryData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Secretary;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.SecretaryManager;
import utilities.DeleteUtils;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class SecretaryManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @Test(timeout = 1000)
    public void testCreateSecretary() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        String username = "jeff";
        String password = "123";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        ContactData contactData = new ContactData("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        SecretaryManager secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

        SecretaryData secretaryData = secretaryManager.createSecretary(username, password);

        /* Testing if the return secretary data bundle is valid by testing if the fields of are equal to the parameters of
        createSecretary */
        assertEquals("The created secretary data bundle should have the same name as the parameters of " +
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
    public void testDeleteSecretary() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

        Integer secretaryID = secretaryDatabase.add(secretary);

        assertNotNull("A secretary object should be returned before it is deleted ",
                secretaryDatabase.get(secretaryID));

        secretaryManager.deleteUser(secretary.getUsername());

        assertNull("A secretary object should not be returned after it is deleted ",
                secretaryDatabase.get(secretaryID));
    }

    @Test(timeout = 1000)
    public void testChangeUserPassword() {
        Database originalDatabase = new Database(databaseFolder.toString());
        DataMapperGateway<Secretary> secretaryDatabase = originalDatabase.getSecretaryDatabase();
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Secretary secretary = new
                Secretary("jeff", "123", 123456789);

        SecretaryManager secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

        SecretaryData secretaryData = new SecretaryData(secretary);

        Integer secretaryID = secretaryDatabase.add(secretary);

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
        DataMapperGateway<Contact> contactDatabase = originalDatabase.getContactDatabase();

        Secretary originalSecretary = new
                Secretary("jeff", "123", 123456789);

        for (int i = 1; i <= 3; i++) {
            originalSecretary.addLogId(i);
        }

        SecretaryManager secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

        Integer secretaryID = secretaryDatabase.add(originalSecretary);

        assertEquals("Original secretary should share the same ID from the database",
                originalSecretary.getId(), secretaryID);

        Secretary loadedSecretary = secretaryManager.getUser(originalSecretary.getUsername());

        /* Testing if the loaded secretary and the original secretary are equal by testing whether all the fields of both
        objects are equal */
        assertEquals("Original secretary and loaded secretary should share the same Id",
                originalSecretary.getId(), loadedSecretary.getId());
        assertEquals("Original secretary and loaded secretary should share the same unique username",
                originalSecretary.getUsername(), loadedSecretary.getUsername());
        assertEquals("Original secretary and loaded secretary should share the same logs",
                originalSecretary.getLogIds(), loadedSecretary.getLogIds());
        assertEquals("Original secretary and loaded secretary should share the same contact information",
                originalSecretary.getContactInfoId(), loadedSecretary.getContactInfoId());
        assertTrue("Original secretary and loaded secretary should share the same password",
                loadedSecretary.comparePassword("123"));
    }

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
