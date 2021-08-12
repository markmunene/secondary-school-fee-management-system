package sample;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class staffReg implements Initializable {

    public JFXTextField occupation;
    public JFXPasswordField confirmpassword1;
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField staffId;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField address;

//    @FXML
//    private JFXComboBox<?> occupation;


    @FXML
    private JFXTextField contact;

    @FXML
    void addStaff(ActionEvent event) {
try {
    if (staffId.getText().equals("")){
        new EmptyWarn().stop("Enter all values");
    }
    else{


    Class.forName("org.sqlite.JDBC");
    Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
    Statement st = con.createStatement();
    if(staffId.getText().length()<4){
       new EmptyWarn().stop("Staff Id must be greater than four characters");
    }
else {
        if (!password.getText().equals(confirmpassword1.getText())) {
            new EmptyWarn().stop("Password do not match");

        } else {
            st.executeUpdate("insert into EMPLOYEE values('" + name.getText() + "', '" + staffId.getText() + "', '" + email.getText() +
                    "', '" + contact.getText() + "', '" + address.getText() + "', '" +
                    occupation.getText() + "','" + password.getText() + "')");
            new EmptyWarn().stop("new staff added successively");
            clear();
        }
    }
}

}
catch (Exception e){
    new EmptyWarn().stop(""+e);
}
    }

    @FXML
    void deleteStaff(ActionEvent event) {
try {
    if (staffId.getText().equals("")) {
        new EmptyWarn().stop("enter staff id to delete");
    } else {

        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
        Statement st = con.createStatement();
        st.executeUpdate("delete from EMPLOYEE where STAFFID = '" + staffId.getText() + "'");
        new EmptyWarn().stop("staff deleted");
        clear();
    }
}
catch (Exception e){
    System.out.println(e);
}
    }

    @FXML
    void updateStaff(ActionEvent event) {
try {
    if(staffId.getText().equals("")){
        new EmptyWarn().stop("Nothing to update");
    }
    else {

        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
        Statement st = con.createStatement();

        st.executeUpdate("update EMPLOYEE set NAME = '" + name.getText() + "'," +
                " EMAIL = '" + email.getText() + "', PASSWORD = '" + password.getText() +
                "', ADRESS = '" + address.getText() + "', OCCUPATION = '" + occupation.getText() +
                "',CONTACT = '" + contact.getText() + "' where STAFFID = '" + staffId.getText() + "'");

        new EmptyWarn().stop("staff update successively");
        clear();
    }
}
catch (Exception e){
    System.out.println(e);
}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select STAFFID from EMPLOYEE");

            while(rs.next()) {

                autoCompletePopup.getSuggestions().add(rs.getString(1));
            }
            autoCompletePopup.setSelectionHandler( event ->{
                staffId.setText(event.getObject());
                show();

            });
            //Product name autocomplete.
            staffId.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(staffId.getText().toLowerCase()));
                if(autoCompletePopup.getFilteredSuggestions().isEmpty() || staffId.getText().isEmpty()  || staffId.isFocused()==false){
                    autoCompletePopup.hide();
                }
                else {
                    autoCompletePopup.show(staffId);
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }



    }
    public void show() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            String STAFFID = staffId.getText();

            try {
                ResultSet rs2 = st.executeQuery("select * from EMPLOYEE where STAFFID = '" + STAFFID + "'");
                if (rs2.next()) {

                    name.setText(rs2.getString(1));
                    email.setText(rs2.getString(3));
                    contact.setText(rs2.getString(4));
                    address.setText(rs2.getString(5));
                    occupation.setText(rs2.getString(6));
                    password.setText(rs2.getString(7));
                    confirmpassword1.setText(rs2.getString(7));


                }
            } catch (Exception var6) {
                var6.printStackTrace();
                new EmptyWarn().stop("StaffID  does not exist");
                //JOptionPane.showMessageDialog((Component)null, );
            }
            con.close();
        } catch (Exception var7) {
            new EmptyWarn().stop(""+var7);
            //JOptionPane.showMessageDialog((Component)null, var7);
        }
    }

    public void clear(){
        name.clear();
        staffId.clear();
        email.clear();
        occupation.clear();password.clear();
        address.clear();
        confirmpassword1.clear();

    }
}
