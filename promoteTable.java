package sample;

import javafx.scene.control.CheckBox;

public class promoteTable {
    String admissionNoCol;
    String formLevelCol;
    CheckBox promoteCol1;
    String nameCol;
    String surnameCol;


    public String getAdmissionNoCol() {
        return admissionNoCol;
    }

    public void setAdmissionNoCol(String admissionNoCol) {
        this.admissionNoCol = admissionNoCol;
    }

    public String getNameCol() {
        return nameCol;
    }

    public void setNameCol(String nameCol) {
        this.nameCol = nameCol;
    }

    public String getSurnameCol() {
        return surnameCol;
    }

    public void setSurnameCol(String surnameCol) {
        this.surnameCol = surnameCol;
    }

    public String getFormLevelCol() {
        return formLevelCol;
    }

    public void setFormLevelCol(String formLevelCol) {
        this.formLevelCol = formLevelCol;
    }

    public CheckBox getPromoteCol1() {
        return promoteCol1;
    }

    public void setPromoteCol1(Boolean b) {
       // this.promoteCol1 = promoteCol1;
        promoteCol1.setSelected(true);
    }


    public promoteTable(String admissionNoCol, String nameCol, String surnameCol, String formLevelCol) {
        this.admissionNoCol = admissionNoCol;
        this.nameCol = nameCol;
        this.surnameCol = surnameCol;
        this.formLevelCol = formLevelCol;
        this.promoteCol1 = new CheckBox();
    }


}
