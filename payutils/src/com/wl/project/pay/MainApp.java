package com.wl.project.pay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static Boolean isSplashLoaded = false;

    public static Stage mystage;

    @Override
    public void start(Stage stage) throws Exception {

        mystage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/root-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //设置图标
        stage.getIcons().add(new Image(getClass().getResourceAsStream("image/qiandai.png")));
        //设置不可改变窗口大小

        stage.setScene(scene);
        stage.setResizable(false); //会有额外外边距


        stage.setTitle(" 支付百宝箱");
        stage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
