import database.DataMapperGateway;
import database.UserJsonDatabase;
import entities.User;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class UserJsonDatabaseTest {
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    // test UserJsonDatabaseTest add() and get() methods
    @Test(timeout = 50)
    public void testAddGet() throws IOException {
        File testUserDatabaseFile = databaseFolder.newFile("test_user_database.json");
        DataMapperGateway<User> testUserDatabase = new UserJsonDatabase(testUserDatabaseFile.toString());

        User user1 = new User("user1", "123", true);
        User user2 = new User("user2", "456", true);

        testUserDatabase.add(user1);
        testUserDatabase.add(user2);
        User jsonUser1 = testUserDatabase.get("user1");
        User jsonUser2 = testUserDatabase.get("user2");
        User jsonUserNonexistent = testUserDatabase.get("user3");
        assertEquals("user object and object pulled from the json database with the same id " +
                "should be the same", user1, jsonUser1);
        assertNotEquals("user objects pulled from the json database with different ids " +
                "should not be the same", jsonUser1, jsonUser2);
        assertNull("using get() with a non-existent id should return null", jsonUserNonexistent);
    }

    // test UserJsonDatabaseTest remove() method
    @Test(timeout = 50)
    public void testRemove() throws IOException {
        File testUserDatabaseFile = databaseFolder.newFile("test_user_database.json");
        DataMapperGateway<User> testUserDatabase = new UserJsonDatabase(testUserDatabaseFile.toString());

        User user = new User("user", "123", true);
        testUserDatabase.add(user);
        User jsonUser = testUserDatabase.get("user");
        assertEquals("the user exists within the database, so the get method should return the correct object",
                user, jsonUser);
        testUserDatabase.remove("user");
        User jsonRemovedUser = testUserDatabase.get("user");
        assertNull("the user was removed, so the get method should return null", jsonRemovedUser);
    }

    // test UserJsonDatabaseTest save() and load() methods
    @Test(timeout = 50)
    public void testSaveLoad() throws IOException {
        File initialUserDatabaseFile = databaseFolder.newFile("test_user_database.json");
        DataMapperGateway<User> initialUserDatabase = new UserJsonDatabase(initialUserDatabaseFile.toString());

        User user1 = new User("user1", "123", true);
        User user2 = new User("user2", "456", true);

        initialUserDatabase.add(user1);
        initialUserDatabase.add(user2);

        initialUserDatabase.save();
        DataMapperGateway<User> loadedUserDatabase = new UserJsonDatabase(initialUserDatabaseFile.toString());

        HashSet<String> initialIds = initialUserDatabase.getAllIds();
        HashSet<String> loadedIds = loadedUserDatabase.getAllIds();

        assertEquals("The initial database object and the object loaded from the test_user_database.json " +
                "should have the same users", initialIds, loadedIds);

        File alternateUserDatabaseFile = databaseFolder.newFile("alternate_test_user_database.json");
        DataMapperGateway<User> alternateUserDatabase = new
                UserJsonDatabase(alternateUserDatabaseFile.toString());

        User user3 = new User("user3", "789", true);

        alternateUserDatabase.add(user3);
        HashSet<String> alternateIds = alternateUserDatabase.getAllIds();

        assertNotEquals("The object loaded from the test_user_database.json not should have the same ids as " +
                "object loaded from the alternate_test_user_database.json file",
                loadedIds, alternateIds);
    }
}
