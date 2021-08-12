package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FeeStructure implements Initializable {

    public JFXComboBox form;
    public JFXTextField term1;
    public JFXTextField term2;
    public JFXTextField term3;
    public JFXComboBox accomo;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField tuition;

    @FXML
    private JFXTextField accommodation;

    @FXML
    private JFXTextField activities;

    @FXML
    private JFXTextField cautionMoney;

    @FXML
    private JFXTextField personalEmoluments;

    @FXML
    private JFXTextField administration;

    @FXML
    private JFXTextField bills;

    @FXML
    private JFXTextField transport;

    @FXML
    private JFXTextField medical;

    @FXML
    private JFXTextField maintainance;

    @FXML
    private JFXTextField uniform;

    @FXML
    private JFXTextField exam;

    @FXML
    private JFXTextField insurance;

    @FXML
    private JFXTextField development;

    @FXML
    void addFeeStructure(ActionEvent event) {
        try {
            if(form.getSelectionModel().isEmpty()){
                new EmptyWarn().stop("Choose both form and Stream");
            }
            else{


                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();

                ResultSet rs2 = st2.executeQuery("select * from FEESTRUCTURE where FORM = '"
                        + form.getValue() + "' and ACOMOOPTION = '"+accomo.getValue()+"'");
                if (rs2.next()) {
                    new EmptyWarn().stop("Fee Structure Already Exist");
                }
                else {

                    st.executeUpdate("insert into FEESTRUCTURE values('" + form.getValue().toString() +
                            "', '" + Integer.parseInt(term1.getText()) + "', '" + Integer.parseInt(term2.getText()) +
                            "', '" + Integer.parseInt(term3.getText()) + "', '" + Integer.parseInt(tuition.getText()) +
                            "', '" + Integer.parseInt(personalEmoluments.getText()) + "','" + Integer.parseInt(medical.getText()) +
                            "','" + Integer.parseInt(insurance.getText()) + "','" + Integer.parseInt(accommodation.getText()) +
                            "','" + Integer.parseInt(administration.getText()) + "','" + Integer.parseInt(maintainance.getText()) +
                            "','" + Integer.parseInt(development.getText()) + "','" + Integer.parseInt(activities.getText()) +
                            "','" + Integer.parseInt(bills.getText()) + "','" + Integer.parseInt(uniform.getText()) +
                            "','" + Integer.parseInt(transport.getText()) + "','" + Integer.parseInt(cautionMoney.getText()) +
                            "','" + Integer.parseInt(exam.getText()) + "','"+accomo.getValue()+"')");
                    new EmptyWarn().stop("Details added successively");
                    clear();
                }

               con.close();
            }

        }
        catch (Exception e){
            System.out.println(e);
            new EmptyWarn().stop(""+e);
        }
    }

    @FXML
    void updateFeeStructureDetails(ActionEvent event) {
        try {
            if(form.getSelectionModel().isEmpty()){
                new EmptyWarn().stop("Choose both form and Stream");
            }
            else {

                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();

                st.executeUpdate("update FEESTRUCTURE set TERM1 = '" + term1.getText() + "'," +
                        " TERM2 = '" + term2.getText() + "', TERM3 = '" + term3.getText() +
                        "', TUITION = '" + tuition.getText() + "', EMULOMENTS = '" + personalEmoluments.getText() +
                        "',MEDICAL = '" + medical.getText() + "',INSURANCE = '" + insurance.getText() + "'" +
                        ",ACCOMMODATION = '" + accommodation.getText() + "',ADMINISTRATION = '" + accommodation.getText() + "'" +
                        ",MAINTANANCE = '" + maintainance.getText() + "',DEVELOPMENT = '" + development.getText() + "'" +
                        ",ACTIVITIES = '" + activities.getText() + "',BILLS = '" + bills.getText() +
                        "',UNIFORM = '" + uniform.getText() + "',TRANSPORT = '" + transport.getText() +
                         "',CAUTION = '" + cautionMoney.getText() + "',EXAM = '" + exam.getText() +
                        "' where FORM = '" + form.getValue() + "'");

                new EmptyWarn().stop("Details added successively");
                clear();
                con.close();
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                //populating the form comboBox.
        ObservableList<String> formList = FXCollections.observableArrayList();
        formList.addAll("Form1", "Form2", "Form3", "Form4");
        form.setItems(formList);

        ObservableList<String> accomoList = FXCollections.observableArrayList();
        accomoList.addAll("Boarding", "Day");
        accomo.setItems(accomoList);
    }

    public void updateFeeStrucuteOnAction(ActionEvent actionEvent) {
        //Populate fields.
        //show();

    }
    public void clear(){
        term1.clear();
        term2.clear();
        term3.clear();

    }
    public void show() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            String form2 = (String) form.getValue();
            String accOption = (String) accomo.getValue();

            try {
                ResultSet rs2 = st.executeQuery("select * from FEESTRUCTURE where FORM = '"
                            + form2 + "' and ACOMOOPTION = '"+accOption+"'");
                if (rs2.next()) {

                    term1.setText(rs2.getString(2));
                    term2.setText(rs2.getString(3));
                    term3.setText(rs2.getString(4));
                    tuition.setText(rs2.getString(5));
                    personalEmoluments.setText(rs2.getString(6));
                    medical.setText(rs2.getString(7));
                    insurance.setText(rs2.getString(8));
                    accommodation.setText(rs2.getString(9));
                    administration.setText(rs2.getString(10));
                    maintainance.setText(rs2.getString(11));
                    development.setText(rs2.getString(12));
                    activities.setText(rs2.getString(13));
                    bills.setText(rs2.getString(14));
                    uniform.setText(rs2.getString(15));
                    transport.setText(rs2.getString(16));
                    cautionMoney.setText(rs2.getString(17));
                    exam.setText(rs2.getString(18));

                }
            } catch (Exception var6) {
                var6.printStackTrace();
                new EmptyWarn().stop("Form  does not exist");
                //JOptionPane.showMessageDialog((Component)null, );
            }
            con.close();
        } catch (Exception var7) {
            new EmptyWarn().stop(""+var7);
            //JOptionPane.showMessageDialog((Component)null, var7);
        }
    }

    public void accomodationOnAction(ActionEvent actionEvent) {
        show();
    }
}
