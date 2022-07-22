import dataBundles.LogData;
import dataBundles.PatientData;
import database.Database;
import entities.Log;
import entities.Patient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import useCases.managers.LogManager;
import utilities.DeleteUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();
    @Test
    public void testAddingNewLog(){
        Database database = new Database(databaseFolder.toString());
        Patient patient = new Patient("dan","dervi", 1);
        Integer id = database.getPatientDatabase().add(patient);
        LogManager logManager = new LogManager(database);
        LogData logData = logManager.addLog("testing123", id);
        Assert.assertEquals(logData.getId(), id);
        Assert.assertNotNull(database.getLogDatabase().get(id));
    }

    @Test
    public void testGettingUserLogs(){
        Database database = new Database(databaseFolder.toString());
        Patient patient = new Patient("dan","dervi", 1);
        Integer id = database.getPatientDatabase().add(patient);
        LogManager logManager = new LogManager(database);


        LogData logA = logManager.addLog("testing1", id);
        LogData logB = logManager.addLog("testing2", id);
        LogData logC = logManager.addLog("testing3", id);
        System.out.println(database.getLogDatabase());

        ArrayList<LogData> logArrayList = logManager.getUserLogs(new PatientData(patient));
        Assert.assertFalse(logArrayList.stream().
                map(LogData::getUserId).
                filter(x -> x.equals(logA.getUserId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertFalse(logArrayList.stream().
                map(LogData::getId).
                filter(x -> x.equals(logB.getId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertFalse(logArrayList.stream().
                map(LogData::getId).
                filter(x -> x.equals(logC.getId())).
                collect(Collectors.toCollection(ArrayList::new)).
                isEmpty());
        Assert.assertEquals(3, logArrayList.size());
    }
    @Test
    public void testGettingNonExistentUserLogs(){
        Database database = new Database(databaseFolder.toString());
        Patient patient = new Patient("dan","dervi", 1);
        LogManager logManager = new LogManager(database);

        logManager.addLog("testing1", 1);
        logManager.addLog("testing2", 1);
        logManager.addLog("testing3", 1);
        System.out.println(database.getLogDatabase());

        ArrayList<LogData> logArrayList = logManager.getUserLogs(new PatientData(patient));
        Assert.assertTrue(logArrayList.isEmpty());
    }



    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
