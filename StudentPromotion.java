package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;

public class StudentPromotion implements Initializable {

    public JFXButton backMen;
    public TableView<promoteTable> promoteView;
    public TableColumn formLevelCol;
    public TableColumn promoteCol1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox form;

    @FXML
    private JFXComboBox stream;

    @FXML
    private TableColumn<promoteTable, String> admissionNoCol;

    @FXML
    private TableColumn<promoteTable, String> fNameCol;

    @FXML
    private TableColumn<promoteTable, String> surnameCol;

    @FXML
    private TableColumn<promoteTable, String> promoteCol;

    @FXML

    ObservableList<String> formList = FXCollections.observableArrayList();
    ObservableList<String> streamList = FXCollections.observableArrayList();
    ObservableList<promoteTable> promote = FXCollections.observableArrayList();

    int num2 = 0;

    @FXML
    void backAction(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/home.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) backMen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void loadstudentForPromotion(ActionEvent event) {
        promote.clear();
        String form2 = (String) form.getValue();
        String stream2 = (String) stream.getValue();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            ResultSet rst = st.executeQuery("select * from STUDENTS where FORMLEVEL = '"+form2+"' and CLASSSTREAM = '"+stream2+"' and  STATUS = 'S'");
            while(rst.next()){
                promote.add(new promoteTable(
                        rst.getString(3),
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(7)

                ));
            }
            for (promoteTable n : promote) {
                n.setPromoteCol1(true);
            }
            promoteView.setItems(promote);
            promoteView.refresh();
con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void promoteStudent(ActionEvent event) {
        try {

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
             Statement st3 = con.createStatement();
            System.out.println("0");
            if (form.getSelectionModel().isEmpty() || stream.getSelectionModel().isEmpty()) {
                new EmptyWarn().stop("Choose both form and Stream");
            }
         else {

            int formNum = 0;

num2 =0;
            for (int i = 0; i < promote.size(); i++) {
                num2++;
                if (promote.get(i).getPromoteCol1().isSelected()) {
                    String formLevel = promote.get(i).getFormLevelCol().substring(0, 4);

                    //adding term fee increment
                    String getAdmission = promote.get(i).getAdmissionNoCol();
                    String getForm = promote.get(i).getFormLevelCol();
                    System.out.println("01");
                    ResultSet rs = st.executeQuery("select ACCOMMODATION from STUDENTS where ADMMISSIONNUMBER" +
                            "= '" + getAdmission + "' and STATUS = 'S'");
                    String accomodation = "";
                    if (rs.next()) {
                        accomodation = rs.getString(1);
                        rs.close();
                    }


                    formNum = Integer.parseInt(promote.get(i).getFormLevelCol().substring(4, 5));
                    formNum += 1;
                    String finalForm = formLevel + formNum;

                 int num =   FeeOverflowAddition(getAdmission, getForm, finalForm, accomodation);


                    String admission = promote.get(i).getAdmissionNoCol();
 if (num==1) {


    if (form.getValue().equals("Form4")) {
        //new EmptyWarn().stop("Student can not be promoted");
        int ans = JOptionPane.showConfirmDialog(null,
                "Add Form 4 Student to Alumni Table");
        if (ans == 0) {
            st2.executeUpdate("update STUDENTS set STATUS = 'A' " +
                    "where ADMMISSIONNUMBER = '" + admission + "'");
        }

    } else {

        st3.executeUpdate("update STUDENTS set FORMLEVEL = '" + finalForm + "' where ADMMISSIONNUMBER = '" + admission + "'");
    }
     new EmptyWarn().stop("Students updated successively");

 }

                }
            }


        }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        init();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


            formList.addAll("Form1", "Form2", "Form3", "Form4");
            form.setItems(formList);
            try {
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("select * from STREAMS ");
                while (rs.next()) {
                    String stream1 = rs.getString(3);
                    String stream2 = rs.getString(4);
                    String stream3 = rs.getString(5);
                    String stream4 = rs.getString(6);
                    String stream5 = rs.getString(7);
                    String stream6 = rs.getString(8);
                    ArrayList<String> streamArray = new ArrayList<>();
                    streamArray.add(stream1);
                    streamArray.add(stream2);
                    streamArray.add(stream3);
                    streamArray.add(stream4);
                    streamArray.add(stream5);
                    streamArray.add(stream6);

                    for(int i=0;i<streamArray.size();i++){
                        if(!streamArray.get(i).equals("")){
                            streamList.add(streamArray.get(i));
                        }
                    }

                }
                stream.setItems(streamList);

                promoteView.setEditable(true);

                //populating the table for students promotions
                this.admissionNoCol.setCellValueFactory(new PropertyValueFactory("admissionNoCol"));
                this.fNameCol.setCellValueFactory(new PropertyValueFactory("nameCol"));
                this.surnameCol.setCellValueFactory(new PropertyValueFactory("surnameCol"));
                this.formLevelCol.setCellValueFactory(new PropertyValueFactory("formLevelCol"));
                this.promoteCol1.setCellValueFactory(new PropertyValueFactory<>("promoteCol1"));

                ResultSet rst = st.executeQuery("select * from STUDENTS where STATUS = 'S'");
                while (rst.next()) {
                    promote.add(new promoteTable(
                            rst.getString(3),
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(7)

                    ));
                }
                for (promoteTable n : promote) {
                    n.setPromoteCol1(true);
                }
                promoteView.setItems(promote);
                promoteView.refresh();
            con.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }


    }
    public  int FeeOverflowAddition(String Admission,String Form,String finalForm,String accomodation){
        int num =0;

        try {

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            ResultSet rs3 = st.executeQuery("select * from TERMBALANCE where ADMISSION = '"

                    +Admission+ "' and FORM = '"+Form+"'");
            if (rs3.next()) {
                double getTerm1 = rs3.getDouble(3);
                double getTerm2 = rs3.getDouble(4);
                double getTerm3 = rs3.getDouble(5);
                double totalTerm = (getTerm1 + getTerm2 + getTerm3);

                double getOverflow = rs3.getDouble(6);

                //Set data.

                ResultSet rs5 = st2.executeQuery("select * from FEESTRUCTURE where FORM = '"
                        +finalForm+"' and ACOMOOPTION = '"+accomodation+"'");
                int term1=0;
                int term2=0;
                int term3=0;
                if(rs5.next()) {
                    term1= rs5.getInt(2);
                    term1 +=totalTerm;
                    term2 = rs5.getInt(3);
                     term3 = rs5.getInt(4);
                    Statement st6 = con.createStatement();
                    st6.executeUpdate("insert into TERMBALANCE values" +
                            "('" + Admission + "','" + finalForm + "','" + term1 + "','"
                            + term2 + "','" + term3 + "','" + 0 + "')");
                    num=1;
                }
                else {
                    if(num2==1) {
                        new EmptyWarn().stop("please enter fee structure for next form");
                    }
                }
                double setOverflow = 0;
                double setTerm2 = term2;
                double setTerm3 = term3;
                double setTerm1 = term1 - getOverflow;
                if (setTerm1 < 0){
                    setTerm2 = term2 - (setTerm1 * -1);
                    if(setTerm2 < 0){
                        setTerm3 = term3 - (setTerm2 * -1);

                        if(setTerm3 < 0){
                            setOverflow  = (setTerm3 * -1);

                        }
                        else {
                            setTerm3 = term3 - (setTerm2 * -1);
                        }
                    }
                    else {
                        setTerm2 = term2 - (setTerm1 * -1);
                    }
                }
                if(setTerm1 < 0){setTerm1 = 0;}
                if(setTerm2 < 0){setTerm2 = 0;}
                if(setTerm3 < 0){setTerm3 = 0;}



                st3.executeUpdate("update TERMBALANCE set TERM1  = '" + setTerm1 +
                        "', TERM2 = '"+setTerm2+"', TERM3 = '"+setTerm3+"', OVERFLOW = '"+setOverflow+"' " +
                        "where ADMISSION = '" + Admission + "' and FORM = '" + finalForm + "'");

                st4.executeUpdate("update TERMBALANCE set OVERFLOW = '"+0+"' " +
                        "where ADMISSION = '" + Admission + "' and FORM = '" + Form + "'");

            }
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return  num;
    }

    public void init(){

        formList.clear();
        streamList.clear();
        promote.clear();

        formList.addAll("Form1", "Form2", "Form3", "Form4");
        form.setItems(formList);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from STREAMS");
            while (rs.next()) {
                String stream1 = rs.getString(3);
                String stream2 = rs.getString(4);
                String stream3 = rs.getString(5);
                String stream4 = rs.getString(6);
                String stream5 = rs.getString(7);
                String stream6 = rs.getString(8);
                ArrayList<String> streamArray = new ArrayList<>();
                streamArray.add(stream1);
                streamArray.add(stream2);
                streamArray.add(stream3);
                streamArray.add(stream4);
                streamArray.add(stream5);
                streamArray.add(stream6);

                for (int i = 0; i < streamArray.size(); i++) {
                    if (!streamArray.get(i).equals("")) {
                        streamList.add(streamArray.get(i));
                    }
                }
            }

            promoteView.setEditable(true);

            //populating the table for students promotions
            this.admissionNoCol.setCellValueFactory(new PropertyValueFactory("admissionNoCol"));
            this.fNameCol.setCellValueFactory(new PropertyValueFactory("nameCol"));
            this.surnameCol.setCellValueFactory(new PropertyValueFactory("surnameCol"));
            this.formLevelCol.setCellValueFactory(new PropertyValueFactory("formLevelCol"));
            this.promoteCol1.setCellValueFactory(new PropertyValueFactory<>("promoteCol1"));

            ResultSet rst = st.executeQuery("select * from STUDENTS where STATUS = 'S'");
            while (rst.next()) {
                promote.add(new promoteTable(
                        rst.getString(3),
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(7)

                ));
            }
            for (promoteTable n : promote) {
                n.setPromoteCol1(true);
            }
            promoteView.setItems(promote);
            promoteView.refresh();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
