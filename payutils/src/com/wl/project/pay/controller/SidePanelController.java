package com.wl.project.pay.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.wl.project.pay.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SidePanelController implements Initializable {
    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;

    private ColorChangeCallback callback;

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.stage = MainApp.mystage;

    }

    public void setCallback(ColorChangeCallback callback) {
        this.callback = callback;
    }

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        //System.out.println(btn.getText());
        switch (btn.getText()) {
            case "支付":
                //callback.updateColor("#00FF00");
                showView("view/pay-view.fxml");
                break;
            case "代付":
                //callback.updateColor("#0000FF");
                showView("view/payout-view.fxml");
                break;
            case "回调工具":
                //
                callback.updateColor("#FF0000");
                break;
        }
    }



    private void showView(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(url));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }




}
