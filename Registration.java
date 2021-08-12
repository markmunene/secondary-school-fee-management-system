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

public class Registration {

    public JFXButton backbtn;
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
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) backbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void regStaff(ActionEvent event) throws IOException {
         Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/staffReg.fxml")));
         borderpane.setCenter(null);
         borderpane.setCenter(root);


//        Stage primaryStage = new Stage();
//        primaryStage.setTitle("staff registration");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

    }

    @FXML
    void regStudent(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/studentReg.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    @FXML
    void initialize() throws IOException {
        assert borderpane != null : "fx:id=\"borderpane\" was not injected: check your FXML file 'Registration.fxml'.";
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/studentReg.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }

    public void regForm(ActionEvent actionEvent) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/formReg.fxml")));
        borderpane.setCenter(null);
        borderpane.setCenter(root);
    }
}
