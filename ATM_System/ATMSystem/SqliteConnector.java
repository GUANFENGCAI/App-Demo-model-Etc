import java.sql.*;

public class SqliteConnector {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if( connection == null ) {
            connection = DriverManager.getConnection("jdbc:sqlite:./ATM.db");
        }
        return connection;
    }

    public static String getINFO() {
        String Info = null;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sqlstring = "select * from card;";
            ResultSet rs = statement.executeQuery(sqlstring);
            while (rs.next()) {
                String cid = rs.getString(1);
                Info = cid;
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return Info;
    }
}