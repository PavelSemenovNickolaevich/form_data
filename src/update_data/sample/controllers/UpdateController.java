package update_data.sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import update_data.sample.DB;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateController {

    @FXML
    private TextField login_id;

    @FXML
    private TextField email_id;

    @FXML
    private PasswordField password_id;

    @FXML
    private Button change_data_id;

    @FXML
    private DB db = new DB();


    public void initialize() {
//        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//        email_id.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!p.matcher(newValue).matches()) email_id.setText(oldValue);
//        });

        try {
            email_id.setText(String.valueOf(db.getEmail()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        change_data_id.setOnAction(event -> {
            String EMAIL_PATTERN = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            Pattern p = Pattern.compile(EMAIL_PATTERN);


            if (login_id.getCharacters().length() <= 3) {
                login_id.setStyle("-fx-border-color: red");
                return;
            } else if (email_id.getCharacters().length() <= 3) {
                email_id.setStyle("-fx-border-color: red");
                return;
            } else if (password_id.getCharacters().length() <= 3) {
                password_id.setStyle("-fx-border-color: red");
                return;
            }
            String password = md5String(password_id.getCharacters().toString());
            String email = email_id.getCharacters().toString();
            try {
                if(p.matcher(email).matches()) {

                    db.updateUser(login_id.getCharacters().toString(), email_id.getCharacters().toString(), password);
                    login_id.setText("");
                    password_id.setText("");
                    email_id.setText("");
                    change_data_id.setText("Готово");
                } else  {
                    email_id.setStyle("-fx-border-color: black");
                    change_data_id.setText("Введен некорректный email");
                    return;
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