package event.details.impl;

import event.details.attributes.EventDetails;
import event.details.persist.PersistEventDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

public class PersistEventDetailsImpl implements PersistEventDetails {
    private static final Logger LOGGER = Logger.getLogger(PersistEventDetailsImpl.class.getName());
    private static final String URL = "jdbc:hsqldb:file:C:/working/";
    private static final String USER = "sa";
    private static final String PASS = "";
    private Connection connection;

    public PersistEventDetailsImpl() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            LOGGER.severe("Error while setting up connection to HSQLDB with message "+e);
        }
    }

    public void persistData(EventDetails event) {
        final String sql = "insert into event(id,state,timestamp,type,host,alert) values (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, event.getEventID());
            preparedStatement.setString(2, event.getEventState());
            preparedStatement.setLong(3, event.getEventTimeStamp());
            preparedStatement.setString(4, event.getEventType());
            preparedStatement.setString(5, event.getEventHost());
            preparedStatement.setBoolean(6, event.alertNeeded());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.severe("Could not insert data in HSQLDB with message "+e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            LOGGER.severe("Could not close connection to HSQLDB  "+e);
        }
    }
}
