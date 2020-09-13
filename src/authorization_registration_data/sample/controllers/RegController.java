package authorization_registration_data.sample.controllers;

import authorization_registration_data.sample.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegController {


    @FXML
    private TextField login_reg;

    @FXML
    private CheckBox check_id;

    @FXML
    private TextField email_reg;

    @FXML
    private PasswordField password_reg;

    @FXML
    private Button btn_reg;

    @FXML
    private TextField login_auth;

    @FXML
    private PasswordField password_auth;

    @FXML
    private Button btn_auth;

    @FXML
    private DB db = new DB();

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {
            login_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            password_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Registration");

            if (login_reg.getCharacters().length() <= 3) {
                login_reg.setStyle("-fx-border-color: red");
                return;
            } else if (email_reg.getCharacters().length() <= 3) {
                email_reg.setStyle("-fx-border-color: red");
                return;
            } else if (password_reg.getCharacters().length() <= 3) {
                password_reg.setStyle("-fx-border-color: red");
                return;
            } else if (!check_id.isSelected()) {
                btn_reg.setText("Please check");
                return;
            }
            String password  = md5String(password_reg.getCharacters().toString());

            try {
                boolean isAuth = db.reqUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), password);
                if (isAuth) {
                    login_reg.setText("");
                    email_reg.setText("");
                    password_reg.setText("");
                    btn_reg.setText("Ready");
                } else {
                    btn_reg.setText("Please enter another login");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btn_auth.setOnAction(event -> {
            login_auth.setStyle("-fx-border-color: #fafafa");
            password_auth.setStyle("-fx-border-color: #fafafa");

            if (login_auth.getCharacters().length() <= 3) {
                login_auth.setStyle("-fx-border-color: red");
                return;
            } else if (password_auth.getCharacters().length() <= 3) {
                password_auth.setStyle("-fx-border-color: red");
                return;
            }
            String password  = md5String(password_auth.getCharacters().toString());

            try {
                boolean isReg = db.authUser(login_auth.getCharacters().toString(), password);
                if (isReg) {
                    login_auth.setText("");
                    password_auth.setText("");
                    btn_auth.setText("Ready");
                } else {
                    btn_auth.setText("Not found");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    public static String md5String(String password) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}


