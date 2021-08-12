package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Home implements Initializable {

    public MenuItem menuReg;
    public MenuItem menuFee;
    public ImageView img2;
    public BarChart feeChart;
    private ResourceBundle resources;

    private URL location;
    @FXML
    void navToAbout(ActionEvent event) {

    }

    @FXML
    void navToReg(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/Registration.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) img2.getScene().getWindow();
        stage.close();
    }

    @FXML
    void navToStude(ActionEvent event) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/Fee.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) img2.getScene().getWindow();
        stage.close();
    }

    public void navtopromote(ActionEvent actionEvent) throws IOException {
        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/studentPromotion.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) img2.getScene().getWindow();
        stage.close();

    }

    public void logoutBtn(MouseEvent mouseEvent) {
        System.out.println("");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populating the the database.
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();

            double totalForm1=0;
            double totalForm2=0;
            double totalForm3=0;
            double totalForm4=0;

            int form1Count = 0;
            int form2Count = 0;
            int form3Count = 0;
            int form4Count = 0;

            ResultSet rst = st.executeQuery("select FORMLEVEL,ADMMISSIONNUMBER from STUDENTS where STATUS = 'S'");
            while (rst.next()) {
                String form = rst.getString(1);
                String admission = rst.getString(2);
                ResultSet rs5 = st2.executeQuery("select * from FEESTRUCTURE where FORM = '"
                        +form+"'");
                int term1=0;
                int term2=0;
                int term3=0;
                if(rs5.next()) {
                    term1= rs5.getInt(2);
                    term2 = rs5.getInt(3);
                    term3 = rs5.getInt(4);

                    double total = (term1 + term2 + term3);
                    ResultSet rs3 = st3.executeQuery("select * from TERMBALANCE where ADMISSION = '"

                            +admission+"' and FORM = '"+form+"'");
                    if (rs3.next()) {
                        double getTerm1 = rs3.getDouble(3);
                        double getTerm2 = rs3.getDouble(4);
                        double getTerm3 = rs3.getDouble(5);

                        double getTotal = (getTerm1 + getTerm2 + getTerm3);

                        double finalTotal = total - getTotal;

                        if(form.equals("Form1")){
                            totalForm1 += finalTotal;
                            form1Count++;

                        }
                        else if(form.equals("Form2")){
                            totalForm2 += finalTotal;
                            form2Count++;
                        }
                        else if(form.equals("Form3")){
                            totalForm3 += finalTotal;
                            form3Count++;

                        }
                        else if(form.equals("Form4")){
                            totalForm4 += finalTotal;
                            form4Count++;

                        }

                    }
                }
            }

            if(totalForm1<0){totalForm1 *= -1;};
            if(totalForm2<0){totalForm2 *= -1;};
            if(totalForm3<0){totalForm3 *= -1;};
            if(totalForm4<0){totalForm4 *= -1;};

            if(form1Count<=0){form1Count = 1;};
            if(form2Count<=0){form2Count = 1;};
            if(form3Count<=0){form3Count = 1;};
            if(form4Count<=0){form4Count = 1;};


            double form1Average = totalForm1/form1Count;
            double form2Average = totalForm2/form2Count;
            double form3Average = totalForm3/form3Count;
            double form4Average = totalForm4/form4Count;

            displayChart(form1Average,form2Average,form3Average,form4Average);


            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void displayChart(double fee1,double fee2,double fee3,double fee4){
        feeChart.getData().clear();
        XYChart.Series seriesA = new XYChart.Series();
        System.out.println(fee1+"::"+fee2+"::"+fee3+"::"+fee4);
        seriesA.getData().add(new XYChart.Data("Form1",fee1));
        seriesA.getData().add(new XYChart.Data("Form2",fee2));
        seriesA.getData().add(new XYChart.Data("Form3",fee3));
        seriesA.getData().add(new XYChart.Data("Form4",fee4));

        this.feeChart.getData().add(seriesA);
        // seriesA.setName("Product");
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {

        Parent root  =(Parent) FXMLLoader.load((this.getClass().getResource("/sample/login.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("staff registration");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage stage= new Stage();
        stage = (Stage) img2.getScene().getWindow();
        stage.close();
    }
}
