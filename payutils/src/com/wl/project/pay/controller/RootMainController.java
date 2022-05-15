package com.wl.project.pay.controller;


import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.wl.project.pay.MainApp;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wl.project.pay.util.ParamConfig;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RootMainController implements Initializable, ColorChangeCallback {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField text_amount;

    @FXML
    private JFXTextField text_channelId;

    @FXML
    private JFXTextField text_userId;

    @FXML
    private JFXTextField text_gameId;

    @FXML
    private JFXRadioButton isTest;

    @FXML
    private JFXTextField text_url;

    @FXML
    private JFXDialog dialog;
    private StackPane dialogStackPane;

    private ParamConfig paramConfig = new ParamConfig();

// 点击鼠标时的x坐标值

    private double dragOffsetX;

// 点击鼠标时的y坐标值

    private double dragOffsetY;

    public static Stage miniStage = new Stage();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        miniStage.initStyle(StageStyle.UNDECORATED);
        miniStage.setTitle("payutil");
        //设置图标
        miniStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("image/qiandai.png")));


        if (!MainApp.isSplashLoaded) {
            loadSplashScreen();
        }

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/sidepanel.fxml"));
            VBox box = loader.load();
            SidePanelController controller = loader.getController();
            controller.setCallback(this);
            drawer.setSidePane(box);
            text_channelId.requestFocus();
        } catch (IOException ex) {
            Logger.getLogger(RootMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }

    private void loadSplashScreen() {
        try {
            MainApp.isSplashLoaded = true;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/splash.fxml"));
            StackPane pane = loader.load();

            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    FXMLLoader loaders = new FXMLLoader();
                    loaders.setLocation(MainApp.class.getResource("view/root-view.fxml"));
                    Pane parentContent = loaders.load();
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(RootMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(RootMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateColor(String newColor) {
        root.setStyle("-fx-background-color:" + newColor);
    }


    public void runAction() {

        String amount = text_amount.getText();
        String channelId = text_channelId.getText();
        String userId = "".equals(text_userId.getText()) ? "241755" : text_userId.getText();
        String gameId = "".equals(text_gameId.getText()) ? "7777" : text_gameId.getText();
        String fid = "FR" + System.currentTimeMillis();

        String str = "pf=wanli&fid=" + fid + "&uid=" + userId + "&cid=" + channelId + "&amount=" + amount + "&sign=30a688eb6e0ae9a3e3286b902688d0e0&gid=" + gameId;
        try {


            boolean isTestSelected = isTest.isSelected();

            String link = isTestSelected ? paramConfig.getValue("TEST_URL") : paramConfig.getValue("LOCAL_URL");
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





    public void close(ActionEvent actionEvent) {
        dialog.close();
        dialogStackPane.getChildren().remove(dialog);
        root.getChildren().remove(dialogStackPane);
    }

    public void closeAndupd(ActionEvent actionEvent) {
        String url = text_url.getText();
        if(!"".equals(url)){
            paramConfig.setValue("TEST_URL",url);
        }


        dialog.close();
        dialogStackPane.getChildren().remove(dialog);
        root.getChildren().remove(dialogStackPane);

    }

    public void alert(ActionEvent actionEvent) {
        text_url.setText("");
        addDialogStackPane();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.show(dialogStackPane);
    }


    /**新增JFXDialog弹窗容器**/
    private void addDialogStackPane() {
        dialogStackPane = new StackPane();
        dialogStackPane.setPrefHeight(root.getHeight());
        dialogStackPane.setPrefWidth(root.getWidth());
        root.getChildren().add(dialogStackPane);
    }


    public void miniAction(ActionEvent actionEvent) {
        MainApp.mystage.hide();



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/mini-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();

            root.setOnMousePressed((ev) -> handleMousePressed(ev,miniStage));
            root.setOnMouseDragged(e -> handleMouseDragged(e,miniStage));

            Scene scene = new Scene(root);
            miniStage.setScene(scene);
            miniStage.setOpacity(0.8f);
            miniStage.setAlwaysOnTop(true);
            miniStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     @class DraggingStage
     @date 2020/5/24
     @author qiaowei
     @version 1.0
     @brief 点击鼠标时触发事件
      * @param e 鼠标事件
     * @param stage
     */
    protected void handleMousePressed(MouseEvent e, Stage stage) {
        // 点击鼠标时，获取鼠标在窗体上点击时相对应窗体左上角的偏移
        this.dragOffsetX = e.getScreenX() - stage.getX();
        this.dragOffsetY = e.getScreenY() - stage.getY();
    }

    protected void handleMouseDragged(MouseEvent e, Stage stage) {
        // Move the stage by the drag amount
        // 拖动鼠标后，获取鼠标相对应显示器坐标减去鼠标相对窗体的坐标，并将其设置为窗体在显示器上的坐标
        stage.setX(e.getScreenX() - this.dragOffsetX);
        stage.setY(e.getScreenY() - this.dragOffsetY);
    }


}
