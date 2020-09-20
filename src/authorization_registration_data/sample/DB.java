package authorization_registration_data.sample;

import authorization_registration_data.sample.controllers.MainController;
import javafx.scene.Node;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

    public boolean reqUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "'LIMIT 1");
        if (res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);
        prSt.executeUpdate();
        return true;
    }

    public boolean authUser(String login, String password) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "'LIMIT 1";
        ResultSet res = statement.executeQuery(sql);
        return res.next();
    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        String sql  = "SELECT `title`, `intro`, `id` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

    public void  addArticle(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`,`views`) VALUES(?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setString(2, intro);
        prSt.setString(3, text);
        prSt.setInt(4, 15);
        prSt.executeUpdate();
    }
    public String getId() throws SQLException, ClassNotFoundException {
        String sql  = "SELECT `id` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
       // return res;
        while (res.next()) {
            return res.getString("id");
        }
        return null;
    }

    public String getText(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT `text` FROM `articles` WHERE `id` = " + MainController.id;
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while (res.next()) {
            return res.getString("text");
        }

        return null;

    }

    public String getTitle(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT `title` FROM `articles` WHERE `id` = " + MainController.id;
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while (res.next()) {
            return res.getString("title");
        }

        return null;
       // return res;


    }


}
