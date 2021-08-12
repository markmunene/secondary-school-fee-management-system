package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class formReg implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField stream1;

    @FXML
    private JFXTextField stream2;

    @FXML
    private JFXTextField stream3;

    @FXML
    private JFXTextField stream4;

    @FXML
    private JFXTextField stream5;

    @FXML
    private JFXTextField stream6;

    @FXML
    private JFXComboBox form;

    @FXML
    private JFXTextField streamNo;

    ArrayList<JFXTextField> textFieldArrayList=new ArrayList<>();

    @FXML
    void addStream(ActionEvent event) {
        try {
            if (form.getValue().equals("")){
                new EmptyWarn().stop("Enter all values");
            }
            else{


                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();
//
                st.executeUpdate("insert into STREAMS values('"+0+"','" + form.getValue() + "', '" + stream1.getText() + "'," +
                        " '" + stream2.getText() +
                        "', '" + stream3.getText() + "', '" + stream4.getText() + "', '" +
                        stream5.getText() + "','" + stream6.getText() +
                        "')");
                new EmptyWarn().stop("new Form added successively");
                clear();
            }
            //}


        }
        catch (Exception e){
            System.out.println(e);
            new EmptyWarn().stop(""+e);
        }
    }

    public void show() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            //String form = fo.getText();

            try {
                ResultSet rs2 = st.executeQuery("select * from STREAMS where FORM = '" + form.getValue() + "'");
                if (rs2.next()) {

                    stream1.setText(rs2.getString(3));
                    stream2.setText(rs2.getString(4));
                    stream3.setText(rs2.getString(5));
                    stream4.setText(rs2.getString(6));
                    stream5.setText(rs2.getString(7));
                    stream6.setText(rs2.getString(8));
                    //formLevel.set(rs2.getString(5));
                    //stream.setText(rs2.getString(6));
                    // accomodation.setText(rs2.getString(7));
                    // admissionNum.setText(rs2.getString(7));


                }
            } catch (Exception var6) {
                var6.printStackTrace();
                new EmptyWarn().stop("Form does not exist");
                //JOptionPane.showMessageDialog((Component)null, );
            }
            con.close();
        } catch (Exception var7) {
            new EmptyWarn().stop(""+var7);
            //JOptionPane.showMessageDialog((Component)null, var7);
        }
    }

    public void clear(){
        streamNo.clear();
        stream1.clear();
        stream2.clear();
        stream3.clear();
        stream4.clear();
        stream5.clear();
        stream6.clear();


    }

    @FXML
    void updateFormsOnAction(ActionEvent event) {
show();
    }

    @FXML
    void updateStream(ActionEvent event) {
        try {
            if(form.getValue().equals("")){
                new EmptyWarn().stop("Nothing to update");
            }
            else {

                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
                Statement st = con.createStatement();

                st.executeUpdate("update STREAMS set STREAM1 = '" + stream1.getText() + "'," +
                        " STREAM2 = '" + stream2.getText() + "', STREAM3 = '" + stream3.getText() +
                        "', STREAM4 = '" + stream4.getText() + "', STREAM5 = '" + stream5.getText() +
                        "',STREAM6 = '"+stream6.getText()+"' where FORM = '"+form.getValue()+"'" );

                new EmptyWarn().stop("Stream update successively");
                clear();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
//
//    @FXML
//    void initialize() {
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> formList = FXCollections.observableArrayList();
        stream1.setVisible(false);
        stream2.setVisible(false);
        stream3.setVisible(false);
        stream4.setVisible(false);
        stream5.setVisible(false);
        stream6.setVisible(false);
        formList.addAll("Form1","Form2","Form3","Form4");
        form.setItems(formList);
        textFieldArrayList.add(stream1);
        textFieldArrayList.add(stream2);
        textFieldArrayList.add(stream3);
        textFieldArrayList.add(stream4);
        textFieldArrayList.add(stream5);
        textFieldArrayList.add(stream6);

    }

    public void addTextField(ActionEvent actionEvent) {
        if(Integer.parseInt(streamNo.getText())>6){
            new EmptyWarn().stop("Maximum number of stream is 6");
        }
        else if (Integer.parseInt(streamNo.getText())<0) {
            new EmptyWarn().stop("Invalid input");
        }
        else {
            int noOfStreams = Integer.parseInt(streamNo.getText());
            stream1.setVisible(false);
            stream2.setVisible(false);
            stream3.setVisible(false);
            stream4.setVisible(false);
            stream5.setVisible(false);
            stream6.setVisible(false);
            for (int i = 0; i < noOfStreams; i++) {
                textFieldArrayList.get(i).setVisible(true);
            }
        }
    }
}
