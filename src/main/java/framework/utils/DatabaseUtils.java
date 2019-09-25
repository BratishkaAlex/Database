package framework.utils;

import com.opencsv.CSVWriter;
import framework.models.Database;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import static framework.utils.LoggerUtil.LOGGER;

public class DatabaseUtils {

    public static void executeQueryAndOutputInCSV(Database database, String query, String pathToCSV) {
        try {
            LOGGER.info("Getting query result and output in .csv file");
            CSVWriter writer = new CSVWriter(new FileWriter(pathToCSV), '\t');
            writer.writeAll(executeQuery(database, query), true);
            writer.close();
        } catch (SQLException e) {
            LOGGER.error("Error while getting query from database");
        } catch (IOException e) {
            LOGGER.error("Error in writing file");
        }
    }

    public static ResultSet executeQuery(Database database, String query) {
        CachedRowSet cachedRowSet = null;
        LOGGER.info("Creating instance of statement");
        try (Connection connection = getConnectionToDatabase(database);
             Statement statement = connection.createStatement()) {
            LOGGER.info("Getting cachedRowSet");
            cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(statement.executeQuery(query));
        } catch (SQLException e) {
            LOGGER.error("Error while getting cachedRowSet");
        }
        return cachedRowSet;
    }

    private static Connection getConnectionToDatabase(Database database) {
        Connection connection = null;
        try {
            Class.forName(database.getDriverName());
            try {
                LOGGER.info("Connecting to the database");
                connection = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPassword());
            } catch (SQLException e) {
                LOGGER.error("Error while connecting to database");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error in downloading jdbc connector");
        }
        return connection;
    }

    public static String getQueryResultByString(Database database, String query) {
        ResultSet resultSet = executeQuery(database, query);
        StringBuilder resultString = new StringBuilder();
        try {
            LOGGER.info("Getting query result by String line");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            resultString = new StringBuilder();
            int i = 1;
            while (i <= columnCount) {
                resultString.append(resultSetMetaData.getColumnName(i)).append(" ");
                i++;
            }
            resultString.append("\n");
            while (resultSet.next()) {
                i = 1;
                while (i <= columnCount) {
                    resultString.append(resultSet.getString(i)).append(" ");
                    i++;
                }
                resultString.append("\n");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getting query result by String line");
        }
        return resultString.toString();
    }
}
