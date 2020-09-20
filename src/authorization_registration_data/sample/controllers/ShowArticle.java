package authorization_registration_data.sample.controllers;


import authorization_registration_data.sample.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ShowArticle {

    @FXML
    private Label title_new_id;

    @FXML
    private VBox article_id;

    @FXML
    private Button btn_return_id;

    @FXML
    private Label article_id_new;

    @FXML
    private DB db = new DB();

    private String id;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        title_new_id.setText(db.getTitle(String.valueOf(MainController.id)));
       article_id_new.setText(db.getText(String.valueOf(MainController.id)));


        btn_return_id.setOnAction(event -> {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/authorization_registration_data/sample/scenes/main.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Кабинет пользователя");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
}
