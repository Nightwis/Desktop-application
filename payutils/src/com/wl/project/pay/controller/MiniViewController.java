package com.wl.project.pay.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import com.wl.project.pay.MainApp;
import com.wl.project.pay.util.ParamConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MiniViewController implements Initializable {

    @FXML
    private JFXRadioButton isTest;
    @FXML
    private JFXTextField text_channelId;
    @FXML
    private JFXTextField text_amount;

    private ParamConfig paramConfig = new ParamConfig();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void backAction(ActionEvent actionEvent) {
        MainApp.mystage.show();
        RootMainController.miniStage.hide();
    }

    public void runAction(ActionEvent actionEvent) {
        String amount = text_amount.getText();
        String channelId = text_channelId.getText();
        Pattern pattern = Pattern.compile("\\d+");
        if(channelId.isEmpty() || !pattern.matcher(channelId).matches() ||
                amount.isEmpty() ||!pattern.matcher(amount).matches()) return;

        String userId = "241755";
        String gameId = "7777";
        String fid = "FR" + System.currentTimeMillis();

        String str = "pf=wanli&fid=" + fid + "&uid=" + userId + "&cid=" + channelId + "&amount=" + amount + "&sign=30a688eb6e0ae9a3e3286b902688d0e0&gid=" + gameId;
        try {
            boolean isLocal = isTest.isSelected();
            String link = isLocal ? paramConfig.getValue("TEST_URL"): paramConfig.getValue("LOCAL_URL") ;
            link = link + "?" + str;

            if (Desktop.isDesktopSupported()) {
                // 获取当前平台桌面实例
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(link));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
