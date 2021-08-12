package sample;

import javafx.scene.control.CheckBox;

public class feeUpdateTable {
    String admission;
    String name;
    String classStream;

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassStream() {
        return classStream;
    }

    public void setClassStream(String classStream) {
        this.classStream = classStream;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CheckBox getSelectBox() {
        return selectBox;
    }

    public void setSelectBox(Boolean select) {
        this.selectBox.setSelected(true);
    }

    String term;
    double balance;
    CheckBox selectBox;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    String form;

    public feeUpdateTable(String admission, String name, String classStream, String term, double balance,String form) {
        this.admission = admission;
        this.name = name;
        this.classStream = classStream;
        this.term = term;
        this.balance = balance;
        this.form = form;
       this.selectBox =new CheckBox();
    }
}
