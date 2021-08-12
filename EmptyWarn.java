//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmptyWarn {
    public EmptyWarn() {
    }

    public void stop(String message) {
        Stage primaryStsge = new Stage();
        Button ok = new Button("Got It");
        ok.setTooltip(new Tooltip("Closes Prompt"));
        Label label = new Label(message);
        //Label labelOne = new Label(" If Must be empty, replace with zero.");
        label.applyCss();
        label.wrapTextProperty();
        VBox vBox = new VBox(new Node[]{label, ok});
        vBox.setSpacing(20.0D);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(40.0D, 40.0D, 40.0D, 40.0D));
        GridPane gridPane = new GridPane();
        gridPane.add(vBox, 50, 50);
        gridPane.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        Scene scene = new Scene(gridPane, 450.0D, 150.0D);
        gridPane.setAlignment(Pos.CENTER);
        primaryStsge.initStyle(StageStyle.TRANSPARENT);
        primaryStsge.initModality(Modality.APPLICATION_MODAL);
        gridPane.getStylesheets().add("/sample/empty.css");
        primaryStsge.setScene(scene);
        ok.setOnAction((e) -> {
            primaryStsge.close();
        });
        primaryStsge.showAndWait();
    }
}
