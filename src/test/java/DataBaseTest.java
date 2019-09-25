import framework.models.Database;
import framework.utils.DatabaseUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static app.DatabaseQueries.*;
import static framework.utils.PropertyManager.getDatabaseProperty;

public class DataBaseTest {
    private Database database;

    @DataProvider(name = "dataForExecutingQuery")
    private Object[][] dataForExecutingQuery() {
        return new Object[][]{{MIN_WORKING_TIME_OF_EACH_TEST, "1_case_result.csv"}, {COUNT_UNIQUE_TESTS, "2_case_result.csv"}, {TEST_EXECUTED_AFTER_DATE, "3_case_result.csv"},
                {TESTS_EXECUTING_ON_CHROME_AND_FIREFOX, "4_case_result.csv"}};
    }

    @BeforeTest
    private void createDatabaseModel() {
        database = new Database(getDatabaseProperty("url"), getDatabaseProperty("user"), getDatabaseProperty("password"), getDatabaseProperty("driver"));
    }

    @Test(dataProvider = "dataForExecutingQuery")
    public void dataBaseTest(String query, String pathToCSV) {
        DatabaseUtils.executeQueryAndOutputInCSV(database, query, pathToCSV);
    }
}
