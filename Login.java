//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Login {
    public TextField userName;
    public PasswordField password;
    public JFXCheckBox passBox;

    public JFXButton loginbtn;

    public Login() {
    }

    public void login() {
        String userId = this.userName.getText();
        String pass = this.password.getText();


        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");

            Statement st = con.createStatement();

            ResultSet rs;

                rs = st.executeQuery("select PASSWORD from EMPLOYEE where STAFFID = '" + userId + "'");


            if (rs.next()) {
                String password = rs.getString(1);
                if (pass.equals(password)) {

                    Stage primaryStage;
                    Parent root;
                    Stage stage;


                        primaryStage = new Stage();
                        root = (Parent)FXMLLoader.load(this.getClass().getResource("/sample/home.fxml"));
                        primaryStage.setTitle("Finetech solutions");
                        primaryStage.setMaximized(true);
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();

                        stage = (Stage)this.loginbtn.getScene().getWindow();
                        stage.close();

                } else {
                   new EmptyWarn().stop("Incorrect Details");
                }

            }
            else { new EmptyWarn().stop("confirm password");
                //  JOptionPane.showMessageDialog((Component)null, "please select the advanced checkbox or confirm password");
            }
            con.close();
        } catch (Exception var14) {
            System.out.println(var14);
            int ans=JOptionPane.showConfirmDialog(null,"Confirm creating a new Database.\nThis action will delete all records if Database existed!!!");
           if(ans==0){
               new feeDatabase().run();
               new EmptyWarn().stop("Database Created successfully.");
           }

        }

    }

    public void passcheckBox() {
        if (this.passBox.isSelected()) {
            this.password.setPromptText(this.password.getText());
            this.password.setText("");
            this.password.setDisable(true);
        } else {
            this.password.setText(this.password.getPromptText());
            this.password.setDisable(false);
        }

    }

}
