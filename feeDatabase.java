package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class feeDatabase {
    public static void main(String[] args) {
        new feeDatabase().run();
    }

    public void run() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FEE.db");
            Statement statement = connection.createStatement();
//CREATING TABLE FOR STAFF REGISTRATION
            String sql = "CREATE TABLE EMPLOYEE(" +
                    "NAME VARCHAR(20)  NOT NULL," +
                    "STAFFID VARCHAR(20) PRIMARY KEY NOT NULL," +
                    "EMAIL VARCHAR(20), " +
                    "CONTACT VARCHAR(20) NOT NULL," +
                    "ADRESS VARCHAR(20) , " +
                    "OCCUPATION VARCHAR(20) NOT NULL," +
                    "PASSWORD VARCHAR(20) NOT NULL UNIQUE" +
                    ")";
            statement.executeUpdate(sql);
            //CREATING TABLE STUDENTS
            String sql2 = "CREATE TABLE STUDENTS(" +
                    "FIRSTNAME VARCHAR(20)  NOT NULL," +
                    "SURNAME VARCHAR(20)  NOT NULL," +
                    "ADMMISSIONNUMBER VARCHAR(20) PRIMARY KEY NOT NULL," +
                    "CONTACT VARCHAR(20) NOT NULL," +
                    "GENDER VARCHAR(20) NOT NULL," +
                    "ACCOMMODATION VARCHAR(20) NOT NULL," +
                    "FORMLEVEL VARCHAR(20) NOT NULL," +
                    "CLASSSTREAM VARCHAR(20) NOT NULL," +
                    "STATUS VARCHAR(20) NOT NULL" +


                    ")";
            statement.executeUpdate(sql2);
            //CREATING TABLE FOR STREAM REGISTRATION
            String sql3 = "CREATE TABLE STREAMS(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "FORM VARCHAR(20)  NOT NULL," +
                   // "STREAMNUMBER VARCHAR(20)   NOT NULL," +
                    "STREAM1 VARCHAR(20) UNIQUE ," +
                    "STREAM2 VARCHAR(20) UNIQUE," +
                    "STREAM3 VARCHAR(20) UNIQUE," +
                    "STREAM4 VARCHAR(20) UNIQUE," +
                    "STREAM5 VARCHAR(20) UNIQUE," +
                    "STREAM6 VARCHAR(20) UNIQUE" +


                    ")";
                statement.executeUpdate(sql3);
            //CREATING TABLE FEEPAYMENT
            String sql4 = "CREATE TABLE FEEPAYMENT(" +
                    "ADMISSION VARCHAR(20) NOT NULL," +

                    "AMOUNT DOUBLE(20) NOT NULL," +
                    "RECIEPTNUMBER VARCHAR(20) NOT NULL," +
                    "BANK VARCHAR(20) NOT NULL," +
                    "TERM VARCHAR(20) NOT NULL" +
                    ")";
            statement.executeUpdate(sql4);

            //CREATING TABLE FOR FEE STRUCTURE
            String sql5 = "CREATE TABLE FEESTRUCTURE(" +
                    "FORM VARCHAR(20) NOT NULL," +
                    "TERM1 INTEGER(20) NOT NULL," +
                    "TERM2 INTEGER(20) NOT NULL," +
                    "TERM3 INTEGER(20) NOT NULL," +

                    "TUITION INTEGER(20) ," +
                    "EMULOMENTS INTEGER(20) ," +
                    "MEDICAL INTEGER(20) ," +
                    "INSURANCE INTEGER(20) ," +
                    "ACCOMMODATION INTEGER(20) ," +
                    "ADMINISTRATION INTEGER(20) ," +
                    "MAINTANANCE INTEGER(20) NOT NULL," +
                    "DEVELOPMENT INTEGER(20) NOT NULL," +
                    "ACTIVITIES INTEGER(20) NOT NULL," +
                    "BILLS INTEGER(20) NOT NULL," +
                    "UNIFORM INTEGER(20)," +
                    "TRANSPORT INTEGER(20) NOT NULL," +
                    "CAUTION INTEGER(20) NOT NULL," +
                    "EXAM INTEGER(20) ," +
                    " ACOMOOPTION VARCHAR(20) NOT NULL" +



                    ")";
            statement.executeUpdate(sql5);
            //CREATING TABLE for FEE BALANCES
            String sql6 = "CREATE TABLE TERMBALANCE(" +
                    "ADMISSION VARCHAR(20)  NOT NULL," +
                    "FORM VARCHAR(20)  NOT NULL," +
                    "TERM1 DOUBLE(20) NOT NULL," +
                    "TERM2 DOUBLE(20) NOT NULL," +
                    "TERM3 DOUBLE(20) NOT NULL," +
                    "OVERFLOW DOUBLE(20) NOT NULL" +

                    ")";
            statement.executeUpdate(sql6);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
