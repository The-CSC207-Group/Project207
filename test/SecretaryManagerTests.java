import dataBundles.ContactDataBundle;
import dataBundles.SecretaryDataBundle;
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
        ContactDataBundle contactDataBundle = new ContactDataBundle("jeff", "jeff@gmail.com",
                "12345678", "jeff street", birthday, "jim",
                "jim@gmail.com", "87654321",
                "father");

        SecretaryManager secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

        SecretaryDataBundle secretaryDataBundle = secretaryManager.createSecretary(username, password,
                contactDataBundle);

        /* Testing if the return secretary data bundle is valid by testing if the fields of are equal to the parameters of
        createSecretary */
        assertEquals("The created secretary data bundle should have the same name as the parameters of " +
                "createSecretary method", secretaryDataBundle.getUsername(), username);

        Secretary loadedSecretary = secretaryDatabase.get(secretaryDataBundle.getId());

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

        secretaryManager.deleteSecretary(secretaryID);

        assertNull("A secretary object should not be returned after it is deleted ",
                secretaryDatabase.get(secretaryID));
    }


    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
