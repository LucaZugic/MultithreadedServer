// TODO: change package name to match yours
package your.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    // TODO: Make sure that password is set to the password of your database
    private static String password = "";
    private static String databaseCommand = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static String database = "postgres";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void add(String name) {
        try {
            // TODO: make sure to adjust this method to match your database requirements
            PreparedStatement preparedStatement = connection.prepareStatement("insert into database values(?)");
            preparedStatement.setString(1, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
