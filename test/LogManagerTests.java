import dataBundles.LogData;
import dataBundles.PatientData;
import database.Database;
import entities.Patient;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import useCases.LogManager;
import utilities.DeleteUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    private Database database;
    private Patient patient;
    private Integer id;
    private LogManager logManager;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        database = new Database(databaseFolder.toString());
        patient = new Patient("dan","dervi", 1);
        id = database.getPatientDatabase().add(patient);
        logManager = new LogManager(database);
    }

    /**
     * Tests the log manager method that adds a log to a user's logs.
     */
    @Test
    public void testAddingNewLog() {
        LogData logData = logManager.addLog("testing123", id);
        Assert.assertEquals("Make sure that the right log is returned to the user", logData.getId(), id);
        Assert.assertNotNull("Make sure that log actually exists in the database",
                database.getLogDatabase().get(id));
    }

    /**
     * Tests the log manager getter method that returns a user's logs with an existing user.
     */
    @Test
    public void testGettingExistingUserLogs() {
        LogData logA = logManager.addLog("testing1", id);
        LogData logB = logManager.addLog("testing2", id);
        LogData logC = logManager.addLog("testing3", id);
        System.out.println(database.getLogDatabase());

        ArrayList<LogData> logArrayList = logManager.getUserLogs(new PatientData(patient));
        Assert.assertFalse("Check if logA is returned to the user as part of" +
                "the arraylist returned when getting a user's logs", logArrayList.stream().
                map(LogData::getUserId).
                filter(x -> x.equals(logA.getUserId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertFalse("Check if logB is returned to the user as part of" +
                "the arraylist returned when getting a user's logs", logArrayList.stream().
                map(LogData::getId).
                filter(x -> x.equals(logB.getId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertFalse("Check if logC is returned to the user as part of" +
                "the arraylist returned when getting a user's logs", logArrayList.stream().
                map(LogData::getId).
                filter(x -> x.equals(logC.getId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertEquals("Make sure there are only 3 logs in the arraylist. When paired with the other" +
                "assert statements, the arraylist only consists of logA, logB, logC", 3, logArrayList.size());
    }

    /**
     * Tests the log manager getter method that returns a user's logs with a non-existent user.
     */
    @Test
    public void testGettingNonExistentUserLogs() {
        logManager.addLog("testing1", 1);
        logManager.addLog("testing2", 1);
        logManager.addLog("testing3", 1);
        System.out.println(database.getLogDatabase());

        ArrayList<LogData> logArrayList = logManager.getUserLogs(new PatientData(patient));
        Assert.assertTrue("Since the user doesn't exist, we should get an empty arraylist",
                logArrayList.isEmpty());
    }

    /**
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
