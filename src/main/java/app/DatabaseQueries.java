package app;

public class DatabaseQueries {
    public static final String MIN_WORKING_TIME_OF_EACH_TEST = "SELECT project.name AS PROJECT_NAME, test.name AS TEST_NAME, MIN(TIMESTAMPDIFF(second,test.start_time,test.end_time))" +
            " AS MIN_WORKING_TIME FROM test JOIN project ON test.project_id=project.id" +
            " WHERE test.start_time IS NOT NULL and test.end_time IS NOT NULL GROUP BY PROJECT_NAME, TEST_NAME ORDER BY PROJECT_NAME, TEST_NAME";
    public static final String COUNT_UNIQUE_TESTS = "SELECT project.name AS PROJECT_NAME, COUNT(DISTINCT test.name) AS COUNT_UNIQUE_TESTS FROM test JOIN project ON test.project_id=project.id GROUP BY PROJECT_NAME";
    public static final String TEST_EXECUTED_AFTER_DATE = "SELECT project.name AS PROJECT, test.name AS TEST, test.start_time AS START_TIME FROM test JOIN project" +
            " ON test.project_id=project.id WHERE START_TIME > '20151106 23:59:59' ORDER BY PROJECT, TEST";
    public static final String TESTS_EXECUTING_ON_CHROME_AND_FIREFOX = "SELECT test.browser AS BROWSER, COUNT(test.id) AS COUNT_EXECUTING FROM test WHERE BROWSER = 'Chrome'" +
            " GROUP BY BROWSER UNION " +
            "SELECT test.browser AS BROWSER, COUNT(test.id) AS COUNT_EXECUTING  FROM test WHERE BROWSER = 'Firefox' GROUP BY BROWSER";
}