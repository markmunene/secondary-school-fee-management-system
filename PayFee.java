package sample;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.awt.*;
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

import javax.swing.*;

public class PayFee implements Initializable {


    public JFXTextField bank1;
    public JFXTextField balanceTxt;

    private ResourceBundle resources;

    private URL location;

    @FXML
    private JFXTextField admissionNo;

    @FXML
    private JFXTextField amount;

    @FXML
    private JFXTextField recieptNo;

    @FXML
    private JFXTextField bank;

    @FXML
    private JFXComboBox term;

    @FXML
    void payFee(ActionEvent event) {
        try {
            double amountPaid = Integer.parseInt(amount.getText());
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");

            Statement st = con.createStatement();
            st.executeUpdate("insert into FEEPAYMENT values" +
                    "('" + admissionNo.getText() + "','"+amountPaid+"','"+recieptNo.getText()+"','"
                    +bank.getText()+"','"+term.getValue()+"')");
            updateAccount();
           // new EmptyWarn().stop("fee added");
            clear();
            con.close();
        } catch (Exception var9) {
            System.out.println(var9);
            EmptyWarn emptyWarn = new EmptyWarn();
            emptyWarn.stop("Enter all Values");
           // JOptionPane.showMessageDialog((Component)null, "Enter all Values");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceTxt.setEditable(false);
        ObservableList<String> termList = FXCollections.observableArrayList();
        termList.addAll("term1", "term2", "term3");
        term.setItems(termList);
        //adding autocomplete for admission number
        try {
            JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

            //initComboBox();

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select ADMMISSIONNUMBER from STUDENTS where STATUS = 'S'");

            while(rs.next()) {
                autoCompletePopup.getSuggestions().add(rs.getString(1));
            }
            autoCompletePopup.setSelectionHandler( event ->{
                admissionNo.setText(event.getObject());


            });
            //Product name autocomplete.
            admissionNo.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(admissionNo.getText().toLowerCase()));
                if(autoCompletePopup.getFilteredSuggestions().isEmpty() || admissionNo.getText().isEmpty()  || admissionNo.isFocused()==false){
                    autoCompletePopup.hide();
                }
                else {
                    autoCompletePopup.show(admissionNo);
                }
            });
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void clear(){
        admissionNo.clear();
        amount.clear();
        recieptNo.clear();
        bank.clear();
    }

    public void setBalance(ActionEvent actionEvent) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String form = null;
            String accommodation = null;
            ResultSet rs2 = st2.executeQuery("select FORMLEVEL,ACCOMMODATION from STUDENTS where ADMMISSIONNUMBER = '"+admissionNo.getText()+"'");
            if (rs2.next()) {
                form = rs2.getString(1);
                accommodation = rs2.getString(2);
            }

            ResultSet rs4 = st.executeQuery("select "+term.getValue()+" , OVERFLOW from" +
                    " TERMBALANCE where ADMISSION = '"+admissionNo.getText()+"' " +
                    "and FORM = '"+form+"'");

            if(rs4.next()) {
                double termBalance = rs4.getDouble(1);
                double overflow = rs4.getDouble(2);
                double totalDebt = termBalance - overflow;
                balanceTxt.setText(String.valueOf(totalDebt));
            }
            else {
                //Get data from fee structure.
                ResultSet rs5 = st.executeQuery("select * from FEESTRUCTURE where FORM = '"
                        +form+"' and ACOMOOPTION = '"+accommodation+"'");

                if(rs5.next()) {
                    int term1 = rs5.getInt(2);
                    int term2 = rs5.getInt(3);
                    int term3 = rs5.getInt(4);

                    //Set data.
                    Statement st3 = con.createStatement();
                    st3.executeUpdate("insert into TERMBALANCE values" +
                            "('" + admissionNo.getText() + "','" + form + "','" + term1 + "','"
                            + term2 + "','" + term3 + "','" + 0 + "')");

                    ResultSet rs6 = st.executeQuery("select "+term.getValue()+",OVERFLOW from" +
                            " TERMBALANCE where ADMISSION = '"+admissionNo.getText()+"' " +
                            "and FORM = '"+form+"'");

                    if(rs6.next()) {
                        double termBalance = rs6.getDouble(1);
                        double overflow = rs6.getDouble(2);
                        double totalDebt = termBalance - overflow;
                        balanceTxt.setText(String.valueOf(totalDebt));
                    }
                }
            }
con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void updateAccount(){
        try {
            int paidAmount = Integer.parseInt(amount.getText());
            String getTerm = (String) term.getValue();
            String getAdmission = admissionNo.getText();


            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();

            ResultSet rs2 = st2.executeQuery("select FORMLEVEL,ACCOMMODATION from STUDENTS where ADMMISSIONNUMBER = '"+admissionNo.getText()+"' and STATUS = 'S' ");
            if (rs2.next()){
                String form = rs2.getString(1);
                String accommodation = rs2.getString(2);



                ResultSet rs = st.executeQuery("select * from TERMBALANCE where ADMISSION = '"

                        +admissionNo.getText()+"' and FORM = '"+form+"'");

                if(!rs.next()) {

                    //Get data from fee structure.
                    ResultSet rs4 = st.executeQuery("select * from FEESTRUCTURE where FORM = '"
                            +form+"' and ACOMOOPTION = '"+accommodation+"' ");

                    if(rs4.next()) {
                        int term1 = rs4.getInt(2);
                        int term2 = rs4.getInt(3);
                        int term3 = rs4.getInt(4);

                        //Set data.
                        Statement st3 = con.createStatement();
                        st3.executeUpdate("insert into TERMBALANCE values" +
                                "('" + admissionNo.getText() + "','" + form + "','" + term1 + "','"
                                + term2 + "','" + term3 + "','" + 0 + "')");
                    }
                }

                //checking the ovepayment situation
                ResultSet rs3 = st.executeQuery("select * from TERMBALANCE where ADMISSION = '"

                        +admissionNo.getText()+"' and FORM = '"+form+"'");
                if (rs3.next()){
                    double getTerm1= rs3.getDouble(3);
                    double getTerm2 = rs3.getDouble(4);
                    double getTerm3 = rs3.getDouble(5);
                    double getOverflow = rs3.getDouble(6);
                    double setOverflow = 0;
                    double setTerm2 = getTerm2;
                    double setTerm3 = getTerm3;
                    double setTerm1 = getTerm1 - paidAmount;
                    if (setTerm1 < 0){
                    setTerm2 = getTerm2 - (setTerm1 * -1);
                    if(setTerm2 < 0){
                         setTerm3 = getTerm3 - (setTerm2 * -1);

                        if(setTerm3 < 0){
                           setOverflow  = (setTerm3 * -1);
                            System.out.println(setOverflow);
                        }
                        else {
                            setTerm3 = getTerm3 - (setTerm2 * -1);
                        }
                    }
                    else {
                        setTerm2 = getTerm2 - (setTerm1 * -1);
                    }
                    }

                    if(setTerm1 < 0){setTerm1 = 0;}
                    if(setTerm2 < 0){setTerm2 = 0;}
                    if(setTerm3 < 0){setTerm3 = 0;}


                    setOverflow += getOverflow;
//                    else {
//                        setTerm1 = getTerm1- paidAmount;
//                    }
                    st.executeUpdate("update TERMBALANCE set TERM1  = '" + setTerm1 +
                            "', TERM2 = '"+setTerm2+"', TERM3 = '"+setTerm3+"', OVERFLOW = '"+setOverflow+"' where ADMISSION = '" + admissionNo.getText() + "' and FORM = '" + form + "'");

                    new EmptyWarn().stop("Account updated successively");
                    clear();


                }


                //
//                ResultSet rs4 = st.executeQuery("select "+term.getValue()+",OVERFLOW from" +
//                        " TERMBALANCE where ADMISSION = '"+admissionNo.getText()+"' " +
//                        "and FORM = '"+form+"' ");
//
//                if(rs4.next()) {
//                    double termBalance = rs4.getDouble(1);
//                    double termOverflow = rs4.getDouble(2);
//                    System.out.println("::PP"+termOverflow);
//                    double newBalance = termBalance - (paidAmount  + termOverflow);
//
//                    if(newBalance<0){//check for overflow.
//
//                        termOverflow = (newBalance * -1);//make it positive.
//                        newBalance = 0;
//                        System.out.println(termOverflow);
//
//                    }
//                    else {
//                        termOverflow = 0;
//                    }
//
//                    System.out.println(termOverflow+"::");
//                    st.executeUpdate("update TERMBALANCE set " + term.getValue() + " = '" + newBalance +
//                            "', OVERFLOW = '"+termOverflow+"' where ADMISSION = '" + admissionNo.getText() + "' and FORM = '" + form + "'");
//
//                        new EmptyWarn().stop("Account updated successively");
//                        clear();

                //}


            }

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }


}
