package sample;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class studentReg implements Initializable {

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField surname;

    @FXML
    private JFXTextField addmissionNumber;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXComboBox formLevel;

    @FXML
    private JFXComboBox stream;

    @FXML
    private JFXComboBox accomodation;

    @FXML
    private JFXComboBox gender;

    ObservableList<String> accomodationList = FXCollections.observableArrayList();
    ObservableList<String> genderList = FXCollections.observableArrayList();
    ObservableList<String> formList = FXCollections.observableArrayList();
    ObservableList<String> streamList = FXCollections.observableArrayList();

    @FXML
    void addStudent(ActionEvent event) {
        try {
            if (addmissionNumber.getText().equals("")){
                new EmptyWarn().stop("Enter all values");
            }
            else{


                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();
//                if(addmissionNumber.getText().length()<4){
//                    new EmptyWarn().stop(" must be greater than four characters");
//                }
//                else {

                        st.executeUpdate("insert into STUDENTS values" +
                                "('" + fname.getText() + "'," +
                                " '" + surname.getText() + "', '" +
                                addmissionNumber.getText() +
                                "', '" + contact.getText() + "', " +
                                "'" + gender.getSelectionModel().getSelectedItem() + "', '" +
                                accomodation.getSelectionModel().getSelectedItem()  + "','"
                                +formLevel.getSelectionModel().getSelectedItem() +
                                "', '"+ stream.getSelectionModel().getSelectedItem()+"','S')");

                        new EmptyWarn().stop("new Student added successively");
                        clear();
                        init();
                    }
                //}


        }
        catch (Exception e){
            System.out.println(e);
            new EmptyWarn().stop(""+e);
        }
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        try {
            if (addmissionNumber.getText().equals("")) {
                new EmptyWarn().stop("Enter Admission Number to delete");
            } else {

                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();
                st.executeUpdate("delete from STUDENTS where ADMMISSIONNUMBER = '" + addmissionNumber.getText() + "'");
                new EmptyWarn().stop("Student deleted");
                clear();
                init();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void updateStudent(ActionEvent event) {
        try {
            if(addmissionNumber.getText().equals("")){
                new EmptyWarn().stop("Nothing to update");
            }
            else {

                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();

                st.executeUpdate("update STUDENTS set FIRSTNAME = '" + fname.getText() + "', SURNAME = '"+surname.getText()+"'" +
                        ", CONTACT = '" + contact.getText() + "', GENDER = '" + gender.getValue() +
                        "', ACCOMMODATION = '" + accomodation.getValue() + "', FORMLEVEL = '" + formLevel.getValue() +
                        "',CLASSSTREAM = '" + stream.getValue() + "' where ADMMISSIONNUMBER = '" + addmissionNumber.getText() + "'");

                new EmptyWarn().stop("Student update successively");
                clear();
                init();
            }
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
            String admissionNum = addmissionNumber.getText();

            try {
                ResultSet rs2 = st.executeQuery("select * from STUDENTS where ADMMISSIONNUMBER = '" + admissionNum + "'");
                if (rs2.next()) {

                    fname.setText(rs2.getString(1));
                    surname.setText(rs2.getString(2));
                    contact.setText(rs2.getString(4));
                    int result=0;
                    result = index(genderList,rs2.getString(5 ));
                    gender.getSelectionModel().select(result);
                    result = index(formList,rs2.getString(7));
                    formLevel.getSelectionModel().select(result);
                    result = index(streamList,rs2.getString(8));
                    stream.getSelectionModel().select(result);
                    result = index(accomodationList,rs2.getString(6));
                    accomodation.getSelectionModel().select(result);

                }
            } catch (Exception var6) {
                var6.printStackTrace();
                new EmptyWarn().stop("Admission Number  does not exist");
                //JOptionPane.showMessageDialog((Component)null, );
            }
            con.close();
        } catch (Exception var7) {

            new EmptyWarn().stop(""+var7);
            //JOptionPane.showMessageDialog((Component)null, var7);
        }
    }

    public void clear(){
        fname.clear();
        surname.clear();
        contact.clear();
        //accomodation.clear();
       // formLevel.clear();
       // stream.clear();
        addmissionNumber.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

            initComboBox();

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select ADMMISSIONNUMBER from STUDENTS");

            while(rs.next()) {
                autoCompletePopup.getSuggestions().add(rs.getString(1));
            }
            autoCompletePopup.setSelectionHandler( event ->{
                addmissionNumber.setText(event.getObject());
                show();

            });
            //Product name autocomplete.
            addmissionNumber.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(addmissionNumber.getText().toLowerCase()));
                if(autoCompletePopup.getFilteredSuggestions().isEmpty() || addmissionNumber.getText().isEmpty()  || addmissionNumber.isFocused()==false){
                    autoCompletePopup.hide();
                }
                else {
                    autoCompletePopup.show(addmissionNumber);
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void initComboBox(){
        try{
            genderList.addAll("Male","Female");
            gender.setItems(genderList);
            accomodationList.addAll("Day","Boarding");
            accomodation.setItems(accomodationList);
            formList.addAll("Form1","Form2","Form3","Form4");
            formLevel.setItems(formList);

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from STREAMS");

            while(rs.next()) {

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



        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public int index(ObservableList<String> observableList,String value){
        int result=0;
        for (int i=0;i<observableList.size();i++){
            if(observableList.get(i).equals(value)){
                result = i;
            }
        }
        return result;
    }

    public void init(){
        try {
            JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

            initComboBox();

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select ADMMISSIONNUMBER from STUDENTS");

            while(rs.next()) {
                autoCompletePopup.getSuggestions().add(rs.getString(1));
            }
            autoCompletePopup.setSelectionHandler( event ->{
                addmissionNumber.setText(event.getObject());
                show();

            });
            //Product name autocomplete.
            addmissionNumber.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(addmissionNumber.getText().toLowerCase()));
                if(autoCompletePopup.getFilteredSuggestions().isEmpty() || addmissionNumber.getText().isEmpty()  || addmissionNumber.isFocused()==false){
                    autoCompletePopup.hide();
                }
                else {
                    autoCompletePopup.show(addmissionNumber);
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
