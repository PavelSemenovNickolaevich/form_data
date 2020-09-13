package update_data.sample;

import java.sql.*;

public class DB {

        private final String HOST = "localhost";
        private final String PORT = "3306";
        private final String DB_NAME = "itproger_java";
        private final String LOGIN = "root";
        private final String PASS = "root";

        private Connection dbConn = null;

        private Connection getDbConnection() throws ClassNotFoundException, SQLException {
            String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
            return dbConn;
        }

        public void isConnected() throws SQLException, ClassNotFoundException {
            dbConn = getDbConnection();
            System.out.println(dbConn.isValid(1000));
        }



        public String getLogin() throws SQLException, ClassNotFoundException {
            Statement statement = getDbConnection().createStatement();
            String sql = "SELECT `login` FROM `users` WHERE `id` = 11";
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                return res.getString("login");
            }
            return "";
        }

        public String getEmail() throws SQLException, ClassNotFoundException {
            Statement statement = getDbConnection().createStatement();
            String sql = "SELECT `email` FROM `users` WHERE `id` = 11";
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                return res.getString("email");
            }
            return "";
        }

        public void updateUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
            String sql = "UPDATE `users` SET  `login` = ?, `email` = ? , `password` = ?  WHERE `id` = 11";

            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);
            prSt.setString(3, password);
            prSt.executeUpdate();

        }
    }


