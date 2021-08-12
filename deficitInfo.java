//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class deficitInfo {
    public deficitInfo() {
    }

    public ArrayList addDeficitInfo() {
        Stage primaryStsge = new Stage();
        Button ok = new Button("close");
        ok.setTooltip(new Tooltip("Closes Prompt"));

        Button okay = new Button("Okay");
        ok.setTooltip(new Tooltip("Generate pdf"));
       // Label label = new Label(message);
        //Label labelOne = new Label(" If Must be empty, replace with zero.");
        JFXTextArea area = new JFXTextArea();
        area.setPrefSize(400,300);

        DatePicker leaveDate = new DatePicker();
        //leaveDate.setPrefSize(40,40);

        DatePicker returnDate = new DatePicker();
        //returnDate.setPrefSize(40,40);

        HBox hBox = new HBox(new Node[]{new Label("LeaveDate"),leaveDate,new Label("ReturnDate"),returnDate});
        hBox.setSpacing(20.0D);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding( new Insets(10.0D, 10.0D, 10.0D, 10.0D));

        HBox hBox2 = new HBox(new Node[]{okay,ok});
        hBox2.setSpacing(20.0D);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding( new Insets(10.0D, 10.0D, 10.0D, 10.0D));

        VBox vBox = new VBox(new Node[]{area, hBox,hBox2});
        vBox.setSpacing(20.0D);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50.0D, 50.0D, 50.0D, 50.0D));
        GridPane gridPane = new GridPane();
        gridPane.add(vBox, 50, 50);
//        gridPane.add(hBox,76,75);
        gridPane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        Scene scene = new Scene(gridPane, 800.0D, 350.0D);
        gridPane.setAlignment(Pos.CENTER);
        primaryStsge.initStyle(StageStyle.TRANSPARENT);
        primaryStsge.initModality(Modality.APPLICATION_MODAL);
        gridPane.getStylesheets().add("/sample/empty.css");
        primaryStsge.setScene(scene);
        ok.setOnAction((e) -> {
            primaryStsge.close();
        });
        ArrayList<String> info = new ArrayList<>();
        okay.setOnAction((e) -> {
          String message =  area.getText();
          String leave = leaveDate.getValue().toString();
          String returnD = returnDate.getValue().toString();

            info.add(message);
            info.add(leave);
            info.add(returnD);
            EmptyWarn err = new EmptyWarn();
            err.stop("information added successively");

        });
        primaryStsge.showAndWait();
        return info;
    }
}
