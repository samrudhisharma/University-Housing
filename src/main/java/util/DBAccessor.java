package util;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * User: Nikhil
 * Date: 08-03-15
 * Time: 06:54 PM
 */
public class DBAccessor {

    private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    final static Logger logger = Logger.getLogger(DBAccessor.class);


    private static final String username = "";      //unity id
    private static final String password = "";      //9 digit student id

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public static void executeUpdateSQL(Connection conn, String sql) throws Exception {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static void executeQuery(Connection conn, String query) throws SQLException{
        logger.info(query + "\n");
        logger.info("-------------------------------------------------------------------------\n");
        Statement statement = conn.createStatement();
        statement.executeQuery(query);
        statement.close();
    }

    public static void executeBatchQuery(Connection conn, List<String> queries) throws SQLException{

        Statement statement = conn.createStatement();
        for(String query : queries){
            logger.info(query + "\n");
            logger.info("-------------------------------------------------------------------------\n");
            statement.addBatch(query);
        }
        statement.executeBatch();
        statement.close();
    }

    public static ResultSet selectQuery(Connection conn, String query) throws SQLException{
        Statement statement = conn.createStatement();
        logger.info(query + "\n");
        logger.info("-------------------------------------------------------------------------\n");
        return statement.executeQuery(query);
    }
}