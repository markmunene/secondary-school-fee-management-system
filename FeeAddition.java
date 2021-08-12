package sample;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeeAddition implements Initializable {

    public TableView<feeUpdateTable> balanceTableView;
    public TableColumn<feeUpdateTable, String> admissionNoTableCol;
    public TableColumn<feeUpdateTable, String> firstName;
    public TableColumn<feeUpdateTable, String> classStream;
    public TableColumn<feeUpdateTable, String> termCol;
    public TableColumn<feeUpdateTable, Double> BalanceCol;
    public TableColumn<feeUpdateTable, String> selectCol;
    public JFXComboBox term;
    public JFXTextField admissionNoTxt;
    public JFXComboBox formLevel;
    public JFXComboBox stream;
    public JFXTextField balanceLimit;
    ObservableList<String> formList = FXCollections.observableArrayList();
    ObservableList<String> streamList = FXCollections.observableArrayList();
    ObservableList<feeUpdateTable> feeUpdateList = FXCollections.observableArrayList();


    public void queryData(ActionEvent actionEvent) {
init();

        if(!admissionNoTxt.getText().equals("")){
            //Statement.
            feeUpdateList = admissionFilter(feeUpdateList);

        }
        else {


            if (!formLevel.getSelectionModel().isEmpty()) {

                feeUpdateList = formFilter(feeUpdateList);

            }
            if (!stream.getSelectionModel().isEmpty()) {

                feeUpdateList = streamFilter(feeUpdateList);

            }
        }

        System.out.println("04");

        for (feeUpdateTable n : feeUpdateList) {
            n.setSelectBox(true);
        }

        balanceTableView.setItems(feeUpdateList);
        balanceTableView.refresh();

    }

    public void UpdateFee(ActionEvent actionEvent) {
       try{

           Class.forName("org.sqlite.JDBC");
           Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
           Statement st = con.createStatement();
          // Statement st2 = con.createStatement();

           int amount = Integer.parseInt(balanceLimit.getText());
           for(int i=0;i<feeUpdateList.size();i++){
               double amount2 = feeUpdateList.get(i).getBalance();
               double totalAmount = amount + amount2;
               if(feeUpdateList.get(i).getSelectBox().isSelected()) {
                   st.executeUpdate("update TERMBALANCE set  " + term.getValue() + " = '" + totalAmount +
                           "' where ADMISSION = '" + feeUpdateList.get(i).getAdmission() +
                           "' and FORM = '" + feeUpdateList.get(i).getForm() + "'");
               }
           }

           new EmptyWarn().stop("Term Fee updated successively");
          con.close();
       }catch (Exception e){
           System.out.println(e);
       }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
            ResultSet rs = st.executeQuery("select ADMMISSIONNUMBER from STUDENTS");

            while(rs.next()) {
                autoCompletePopup.getSuggestions().add(rs.getString(1));
            }
            autoCompletePopup.setSelectionHandler( event ->{
                admissionNoTxt.setText(event.getObject());


            });
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

                for(int i=0;i<streamArray.size();i++){
                    if(!streamArray.get(i).equals("")){
                        streamList.add(streamArray.get(i));
                    }
                }
            }
            stream.setItems(streamList);

//            balanceTableView.setEditable(true);
//
//            //populating the table for students promotions
//            this.admissionNoTableCol.setCellValueFactory(new PropertyValueFactory("admission"));
//            this.firstName.setCellValueFactory(new PropertyValueFactory("name"));
//            this.classStream.setCellValueFactory(new PropertyValueFactory("classStream"));
//            this.termCol.setCellValueFactory(new PropertyValueFactory("term"));
//            this.BalanceCol.setCellValueFactory(new PropertyValueFactory("balance"));
//            this.selectCol.setCellValueFactory(new PropertyValueFactory<>("selectBox"));
//
//            ResultSet rst = st.executeQuery("select * from STUDENTS");
//            while (rst.next()) {
//                feeUpdateList.add(new feeUpdateTable(
//                        rst.getString(3),
//                        rst.getString(1),
//                        rst.getString(2),
//                        rst.getString(7)
//
//                ));
//            }
//            for (feeUpdateTable n : feeUpdateList) {
//                n.setSelectBox(true);
//            }
//            balanceTableView.setItems(feeUpdateList);
//            balanceTableView.refresh();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
    public void init(){


        feeUpdateList.clear();


        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            balanceTableView.setEditable(true);

            //populating the table for students promotions
            this.admissionNoTableCol.setCellValueFactory(new PropertyValueFactory("admission"));
            this.firstName.setCellValueFactory(new PropertyValueFactory("name"));
            this.classStream.setCellValueFactory(new PropertyValueFactory("classStream"));
            this.termCol.setCellValueFactory(new PropertyValueFactory("term"));
            this.BalanceCol.setCellValueFactory(new PropertyValueFactory("balance"));
            this.selectCol.setCellValueFactory(new PropertyValueFactory<>("selectBox"));

            ResultSet rst = st.executeQuery("select FIRSTNAME,ADMMISSIONNUMBER,FORMLEVEL,CLASSSTREAM from STUDENTS");
            while (rst.next()) {

                String name = rst.getString(1);
                String admission = rst.getString(2);
                String form = rst.getString(3);
                String stream = rst.getString(4);
                //System.out.println("Me::"+admission);

                ResultSet rst2 = st2.executeQuery("select "+term.getValue()+" from TERMBALANCE where ADMISSION" +
                        " = '"+admission+"' and FORM = '"+form+"'");
                if (rst2.next()) {
                   // System.out.println("<>");
                    double termBalance = rst2.getDouble(1);

                    feeUpdateList.add(new feeUpdateTable(admission, name, stream, (String) term.getValue(), termBalance,form
                            ));
                }
            }
            for (feeUpdateTable n : feeUpdateList) {
                n.setSelectBox(true);
            }
            balanceTableView.setItems(feeUpdateList);
            balanceTableView.refresh();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<feeUpdateTable> admissionFilter(ObservableList<feeUpdateTable> observableList){
        String admission = admissionNoTxt.getText();

        ObservableList<feeUpdateTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            //System.out.println(observableList.get(i).getAdmission()+"::::"+admission);
            if(observableList.get(i).getAdmission().equals(admission)){

                observableList1.add(new feeUpdateTable(
                        observableList.get(i).getAdmission(),
                        observableList.get(i).getName(),
                        observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),
                        observableList.get(i).getBalance(),
                        observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }


    public ObservableList<feeUpdateTable> formFilter(ObservableList<feeUpdateTable> observableList){
        String form = (String) formLevel.getValue();
        //System.out.println(form);
        ObservableList<feeUpdateTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            if(observableList.get(i).getForm().equals(form)){
                observableList1.add(new feeUpdateTable(
                        observableList.get(i).getAdmission(),
                        observableList.get(i).getName(),
                        observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),
                        observableList.get(i).getBalance(),
                        observableList.get(i).getForm()));
            }
        }
        return observableList1;
    }

    public ObservableList<feeUpdateTable> streamFilter(ObservableList<feeUpdateTable> observableList){
        String stream1 = (String) stream.getValue();
        System.out.println("");
        ObservableList<feeUpdateTable> observableList1 = FXCollections.observableArrayList();
        for(int i = 0; i<observableList.size(); i++){
            if(observableList.get(i).getClassStream().equals(stream1)){
                observableList1.add(new feeUpdateTable(
                        observableList.get(i).getAdmission(),
                        observableList.get(i).getName(),
                        observableList.get(i).getClassStream(),
                        observableList.get(i).getTerm(),
                        observableList.get(i).getBalance(),
                        observableList.get(i).getForm()));

            }

        }
        return observableList1;
    }
}
