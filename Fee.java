package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Fee {

    public JFXButton backBtn;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderpane;

    @FXML
    void close(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/home.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void feeBalance(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/feeBalance.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    @FXML
    void feeStructure(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/feeStructure.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    @FXML
    void payFee(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/payFee.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    @FXML
    void initialize() throws IOException {
        assert borderpane != null : "fx:id=\"borderpane\" was not injected: check your FXML file 'Fee.fxml'.";
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/feeBalance.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    public void updateFeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/feeAddition.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }
}
