import useCases.dataBundles.LogData;
import useCases.dataBundles.PatientData;
import database.Database;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;
import utilities.DeleteUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class of unit tests for LogManager use case class.
 */
public class LogManagerTests {

    /**
     * The variable representing the temporary folder where the databases used in these tests are stored until it is
     * deleted after the tests.
     */
    @Rule
    public final TemporaryFolder databaseFolder = new TemporaryFolder();
    private Database database;
    private PatientData patientData;
    private LogManager logManager;

    /**
     * Initializes the variables used by all the tests before each unit test.
     */
    @Before
    public void before() {
        database = new Database(databaseFolder.toString());
        patientData = new PatientManager(database).createPatient("ryan12345", "123456789");
        logManager = new LogManager(database);
    }

    /**
     * Tests the log manager method that adds a log to a user's logs.
     */
    @Test
    public void testAddingNewLog() {
        LogData logData = logManager.addLog(patientData, "testing123");
        Assert.assertEquals("Make sure that the right log is returned to the user", logData.getId(), patientData.getId());
        Assert.assertNotNull("Make sure that log actually exists in the database",
                database.getLogDatabase().get(patientData.getId()));
    }

    /**
     * Tests the log manager getter method that returns a user's logs with an existing user.
     */
    @Test
    public void testGettingExistingUserLogs() {
        LogData logA = logManager.addLog(patientData, "testing1");
        LogData logB = logManager.addLog(patientData, "testing2");
        LogData logC = logManager.addLog(patientData, "testing3");

        ArrayList<LogData> logArrayList = logManager.getUserLogs(patientData);
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
     * Deletes the temporary database folder used to store the database for tests after tests are done.
     */
    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
