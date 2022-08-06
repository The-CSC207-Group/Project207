import dataBundles.LogData;
import dataBundles.PatientData;
import database.Database;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.LogManager;
import useCases.PatientManager;
import utilities.DeleteUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    @Test
    public void testAddingNewLog() {
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        PatientData patientData = patientManager.createPatient("dan", "dervi");
        LogManager logManager = new LogManager(database);
        LogData logData = logManager.addLog(patientData, "testing123");

        Assert.assertNotNull("Make sure that log actually exists in the database with the correct ID",
                database.getLogDatabase().get(logData.getId()));
        Assert.assertEquals("Make sure the message of the log in the database is the same as the body passed in",
                database.getLogDatabase().get(logData.getId()).getMessage(), "testing123");
    }

    @Test
    public void testGettingUserLogs(){
        Database database = new Database(databaseFolder.toString());
        PatientManager patientManager = new PatientManager(database);
        PatientData patientData = patientManager.createPatient("dan", "dervi");
        LogManager logManager = new LogManager(database);


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

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }

}
