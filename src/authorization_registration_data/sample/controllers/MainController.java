package authorization_registration_data.sample.controllers;


import authorization_registration_data.sample.DB;
import authorization_registration_data.sample.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Button btn_exit, btn_add_article;

    @FXML
    private VBox paneVBox;

    @FXML
    private DB db = new DB();


    public static int id =0;


//   // public static String getId() {
//        return String.valueOf(id);
//    }

    @FXML
    void initialize() throws IOException, SQLException, ClassNotFoundException {
        ResultSet res = db.getArticles();
       // String resId = db.getId();

        //  Node[] nodes = new Node[10];
        while (res.next()) {
            Node node = null;
            node = FXMLLoader.load(getClass().getResource("/authorization_registration_data/sample/scenes/article.fxml"));

            Label title = (Label) node.lookup("#title");
            title.setText(res.getString("title"));

            Label intro = (Label) node.lookup("#intro");
            intro.setText(res.getString("intro"));

            final Node nodeSet = node;

            node.setOnMouseEntered(event -> {
                nodeSet.setStyle("-fx-background-color: #707173");
            });
            node.setOnMouseExited(event -> {
                nodeSet.setStyle("-fx-background-color: #343434");
            });
            node.setId(res.getString("id"));
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
            Node finalNode = node;
            node.setOnMouseClicked(event -> {

                try {
                    id = Integer.parseInt(nodeSet.getId());
                    System.out.println(id);
                    Parent root = FXMLLoader.load(getClass().getResource("/authorization_registration_data/sample/scenes/showArticle.fxml"));
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setTitle("Статья");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.setResizable(false);
                    primaryStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
        btn_exit.setOnAction(event -> {

            try {
                FileOutputStream fos = new FileOutputStream("user.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new User(""));
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/authorization_registration_data/sample/scenes/sample.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Кабинет пользователя");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_add_article.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/authorization_registration_data/sample/scenes/addArticle.fxml"));
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
