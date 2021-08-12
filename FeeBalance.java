package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

//import java.awt.*;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class FeeBalance implements Initializable {

    public TableColumn <feeBalanceTable,String> admissionNoTableCol;
    public JFXTextField admissionNoTxt;
    public TableColumn <feeBalanceTable,String> termCol;
    public TableColumn <feeBalanceTable,String> BalanceCol;
    public JFXComboBox term;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<feeBalanceTable> balanceTableView;

    @FXML
    private TableColumn <feeBalanceTable,String> firstName;

    @FXML
    private TableColumn <feeBalanceTable,String> accomodation;

    @FXML
    private TableColumn <feeBalanceTable,String> classStream;

    @FXML
    private JFXComboBox formLevel;

    @FXML
    private JFXComboBox stream;

    @FXML
    private JFXTextField balanceLimit;

    @FXML
    private JFXTextField totalBal;

    @FXML
    private JFXCheckBox paidStatus;

   ObservableList<feeBalanceTable> balanceList = FXCollections.observableArrayList();
    ObservableList<String> formList = FXCollections.observableArrayList();
    ObservableList<String> streamList = FXCollections.observableArrayList();
    ObservableList<feeBalanceTable> filterList = FXCollections.observableArrayList();


    public void printLeavingInfo(ActionEvent actionEvent) {
        deficitInfo info = new deficitInfo();
        ArrayList<String> infomation = info.addDeficitInfo();
        String message = infomation.get(0);
        String leaveDate = infomation.get(1);
        String returnDate = infomation.get(2);
//        System.out.println(message);

        //printing staff from the user.
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            String user = System.getProperty("user.name");
            String file2 = "C:/Users/" + user + "/Documents/Report-";
            SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date date2 = new Date();
            String dt = dFormat.format(date2);
            dFormat = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
            String dt01 = dFormat.format(date2);
            //for(int i=0;i<chartContents.size();i++){

//            Paragraph paragraph2 = new Paragraph("Date & Time: " + date2 + " " + dt + "\n\n");

//            doc.add(paragraph2);
//            PdfPTable tb1 = new PdfPTable(6);
//
//            tb1.addCell("SNo");
//            tb1.addCell("Admission");
//            tb1.addCell("Name");
//            tb1.addCell("Stream");
//            tb1.addCell("Term");
//            tb1.addCell("Balance");
//            doc.add(tb1);
            PdfPTable table = new PdfPTable(6);
            Document doc = new Document();

            PdfWriter.getInstance(doc, new FileOutputStream(file2 + "" + dt01 + ".pdf"));
            doc.open();
            for (int i = 0; i < filterList.size(); i++) {

                String admission="";
                admission = filterList.get(i).getAdmission();
                ResultSet rst = st.executeQuery("select SURNAME from STUDENTS where STATUS = 'S' and ADMMISSIONNUMBER = '"+admission+"'");
                String name = "";

                String stream2="";
                String term2="";
                String balance="";

                if (rst.next()) {

                    String name2 = rst.getString(1);
                    name =  filterList.get(i).getFirstName()+ " "+name2;

                }


                stream2 = filterList.get(i).getClassStream();
                term2 = filterList.get(i).getTerm();
                balance = filterList.get(i).getBalance();

                Paragraph paragraph2 = new Paragraph("Admission: "+admission+"\t\t\t\t   Name: "+name+"\n");
                doc.add(paragraph2);
                Paragraph paragraph3 = new Paragraph(message+"\n");
                doc.add(paragraph3);
                Paragraph paragraph4 = new Paragraph("Term: "+term2+"\t\t\t\t   Balance: "+balance+"\n");
                doc.add(paragraph4);
                Paragraph paragraph5 = new Paragraph("Leaving Date: "+leaveDate+"\t\t\t\t   Return Date: "+returnDate+"\n");
                doc.add(paragraph5);
                Paragraph paragraph6 = new Paragraph("\n");
                doc.add(paragraph6);
                doc.add(table);
            }

            new  EmptyWarn().stop(" Records printed");
            doc.close();

            // }
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void printBalanceInfo(ActionEvent actionEvent) {


        try {

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();

            String user = System.getProperty("user.name");
            String file2 = "C:/Users/" + user + "/Documents/Report-";
            SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date date2 = new Date();
            String dt = dFormat.format(date2);
            dFormat = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
            String dt01 = dFormat.format(date2);
            //for(int i=0;i<chartContents.size();i++){

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file2 + "" + dt01 + ".pdf"));
            doc.open();
            Paragraph paragraph1 = new Paragraph("                             CLASS OF 2021\n                                 \n\n");
            doc.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Date & Time: " + date2 + " " + dt + "\n\n");
            doc.add(paragraph2);
            PdfPTable tb1 = new PdfPTable(6);

            tb1.addCell("SNo");
            tb1.addCell("Admission");
            tb1.addCell("Name");
            tb1.addCell("Stream");
            tb1.addCell("Term");
            tb1.addCell("Balance");
            doc.add(tb1);

            PdfPTable table = new PdfPTable(6);

            for (int i = 0; i < filterList.size(); i++) {

                ResultSet rst = st.executeQuery("select SURNAME from STUDENTS where STATUS = 'S'");
                String name = "";

                if (rst.next()) {

                    String name2 = rst.getString(1);
                 name =  filterList.get(i).getFirstName()+ " "+name2;
                }

                table.addCell(""+(i+1));
                table.addCell(filterList.get(i).getAdmission());
                table.addCell(name);
                table.addCell(filterList.get(i).getClassStream());
                table.addCell(filterList.get(i).getTerm());
                table.addCell(filterList.get(i).getBalance());


            }

            doc.add(table);
            new  EmptyWarn().stop(" List printed");
//            JOptionPane.showMessageDialog((Component) null, "Records Printed");
            doc.close();

            // }
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //auto complete for admission number
        balanceLimit.setText(""+0);
        ObservableList<String> termList = FXCollections.observableArrayList();
        termList.addAll("term1", "term2", "term3");
        term.setItems(termList);


        formList.addAll("Form1", "Form2", "Form3", "Form4");
        formLevel.setItems(formList);

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
                admissionNoTxt.setText(event.getObject());

            });

            //adding streams from the database
            Statement st2 = con.createStatement();

            ResultSet rs2 = st2.executeQuery("select * from STREAMS");
            while (rs2.next()) {
                String stream1 = rs2.getString(3);
                String stream2 = rs2.getString(4);
                String stream3 = rs2.getString(5);
                String stream4 = rs2.getString(6);
                String stream5 = rs2.getString(7);
                String stream6 = rs2.getString(8);
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


            //Product name autocomplete.
            admissionNoTxt.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(admissionNoTxt.getText().toLowerCase()));
                if(autoCompletePopup.getFilteredSuggestions().isEmpty() || admissionNoTxt.getText().isEmpty()  || admissionNoTxt.isFocused()==false){
                    autoCompletePopup.hide();
                }
                else {
                    autoCompletePopup.show(admissionNoTxt);
                }
            });
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        //end of auto complete for admission number textField



        this.admissionNoTableCol.setCellValueFactory(new PropertyValueFactory("admission"));
        this.termCol.setCellValueFactory(new PropertyValueFactory("term"));
        this.BalanceCol.setCellValueFactory(new PropertyValueFactory("balance"));
        this.firstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        this.accomodation.setCellValueFactory(new PropertyValueFactory<>("accommodation"));
        this.classStream.setCellValueFactory(new PropertyValueFactory<>("classStream"));

    }

    public void termOnAction(ActionEvent actionEvent) {
        try {
            balanceList.clear();
            String getTerm = (String) term.getValue();

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs = st.executeQuery("select * from STUDENTS where STATUS = 'S'");
            while (rs.next()){
                String admission = rs.getString(3);
                String firstName = rs.getString(1);
                String classStream = rs.getString(8);
                String form = rs.getString(7);
                String accomodation = rs.getString(6);
                String term2 = null;
                ResultSet rs2 = st.executeQuery("select "+getTerm+" from TERMBALANCE where ADMISSION =" +
                        " '"+admission+"' and FORM = '"+form+"'");
                if (rs2.next()){
                    term2 = rs2.getString(1);
                }
                else {
                    ResultSet rs3 = st2.executeQuery("select "+getTerm+" from FEESTRUCTURE where FORM = '"+form+"'");
                    term2 = rs3.getString(1);
                }

                balanceList.addAll(new feeBalanceTable(admission,term2,firstName,accomodation,classStream, (String) term.getValue(),form));

            }

            balanceTableView.setItems(balanceList);
            balanceTableView.refresh();

            ;

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void formAction(ActionEvent actionEvent) {
    }

    public void streamAction(ActionEvent actionEvent) {
    }

    public void limitOnAction(ActionEvent actionEvent) {
    }

    public void filterData(){
        try {
filterList.clear();
            String getTerm = (String) term.getValue();
            int limit = Integer.parseInt(balanceLimit.getText());
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            ResultSet rs = st.executeQuery("select * from STUDENTS where STATUS = 'S'");
            while (rs.next()){
                String admission = rs.getString(3);
                String firstName = rs.getString(1);
                String classStream = rs.getString(8);
                String form = rs.getString(7);
                String accomodation = rs.getString(6);
                String term2 = null;

                ResultSet rs2 = st2.executeQuery("select "+getTerm+" from TERMBALANCE where ADMISSION =" +
                        " '"+admission+"' and FORM = '"+form+"'");
                if (rs2.next()){
                    term2 = rs2.getString(1);
                }
                else {
                    ResultSet rs3 = st3.executeQuery("select "+getTerm+" from FEESTRUCTURE where FORM = '"+form+"'");
                    term2 = rs3.getString(1);
                }

                filterList.addAll(new feeBalanceTable(admission,term2,firstName,accomodation,classStream, (String) term.getValue(),form));
                System.out.println(admission+":::"+form);
           }
con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void queryData(ActionEvent actionEvent) {
        filterData();

        if(!admissionNoTxt.getText().equals("")){
            //Statement.
           filterList = admissionFilter(filterList);
            System.out.println("ont");
        }
        else {


            if (!formLevel.getSelectionModel().isEmpty()) {
//Statement.
                filterList = formFilter(filterList);
                System.out.println("02");
            }
            if (!stream.getSelectionModel().isEmpty()) {
//Statement.
                filterList = streamFilter(filterList);
            }
//        if(!term.getSelectionModel().isEmpty()){
////Statement.
//            filterList = termFilter(filterList);
//        }

            //choosing between paid and unpaid students.
            filterList = paidFilter(filterList);

            if (!balanceLimit.getText().equals("")) {
                //Statement.
                filterList = balanceFilter(filterList);
            }
        }
        double totalBalance = 0;
        for (int i=0;i<filterList.size();i++){
            totalBalance += Double.parseDouble(filterList.get(i).getBalance());
        }
        totalBal.setText(String.valueOf(totalBalance));
        balanceTableView.setItems(filterList);
        balanceTableView.refresh();

    }

    public ObservableList<feeBalanceTable> admissionFilter(ObservableList<feeBalanceTable> observableList){
        String admission = admissionNoTxt.getText();
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
          if(observableList.get(i).getAdmission().equals(admission)){
              observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                      observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                      observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                      observableList.get(i).getTerm(),observableList.get(i).getForm()));
          }
        }
       return observableList1;
    }


    public ObservableList<feeBalanceTable> formFilter(ObservableList<feeBalanceTable> observableList){
        String form = (String) formLevel.getValue();
        System.out.println(form);
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            if(observableList.get(i).getForm().equals(form)){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }

    public ObservableList<feeBalanceTable> streamFilter(ObservableList<feeBalanceTable> observableList){
        String stream1 = (String) stream.getValue();
        System.out.println("");
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            if(observableList.get(i).getClassStream().equals(stream1)){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }
    public ObservableList<feeBalanceTable> termFilter(ObservableList<feeBalanceTable> observableList){
        String Termfilter = (String) term.getValue();
        System.out.println("");
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            if(observableList.get(i).getTerm().equals(Termfilter)){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }
    public ObservableList<feeBalanceTable> balanceFilter(ObservableList<feeBalanceTable> observableList){
        double balance = Double.parseDouble(balanceLimit.getText());
        System.out.println("");
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            double dbBalance = Double.parseDouble(observableList.get(i).getBalance());

            if(dbBalance >= balance){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }
    public ObservableList<feeBalanceTable> paidFilter(ObservableList<feeBalanceTable> observableList){
        boolean balance = paidStatus.isSelected();
        System.out.println("");
        ObservableList<feeBalanceTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            double getBalance = Double.parseDouble(observableList.get(i).getBalance());
            if(getBalance<=0 && balance==true){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }
            else if(getBalance>0 && balance==false){
                observableList1.add(new feeBalanceTable(observableList.get(i).getAdmission(),
                        observableList.get(i).getBalance(),observableList.get(i).getFirstName(),
                        observableList.get(i).getAccommodation(),observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),observableList.get(i).getForm()));
            }

        }
        return observableList1;
    }

}
