package com.wl.project.pay.controller;

import cn.hutool.json.JSONObject;
import com.jfoenix.controls.*;
import com.wl.project.pay.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class PayoutViewController implements Initializable {

    @FXML
    private JFXToggleButton jfx_tb;
    @FXML
    private JFXButton btnParam;
    @FXML
    private TextArea textare_payout_param;
    @FXML
    private TextArea textare_bank_param;
    @FXML
    private TextArea textare_bank_json;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void paramAction(ActionEvent actionEvent) {
        String text = textare_payout_param.getText().trim();
        if (text.isEmpty()) return;
        text = text.replaceAll("\\s", ",");
        String[] params = text.split(",");
        List<String> li = new ArrayList<>();
        for (String n : params) {
            if (!"".equals(n)) li.add(n);
        }
        Set<String> list = new TreeSet<>(li);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append("native1.set('" + s + "','');").append("\n");
        }
        textare_payout_param.setText(sb.toString());
        btnParam.setDisable(true);

    }


    public void copyAction(ActionEvent actionEvent) {
        String text = textare_payout_param.getText().trim();
        if (text.isEmpty()) return;
        textare_payout_param.setText("");
        btnParam.setDisable(false);
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    public void formatAction(ActionEvent actionEvent) {
        String str = textare_bank_param.getText().trim();

        if (str.isEmpty()) return;
        str = str.replaceAll("\\n", ",").replaceAll("\\s{2,}|\t", " ");
        //System.out.println(str);

        String[] params = str.split(",");
        StringBuilder sb = new StringBuilder();
        StringBuilder jsonsb = new StringBuilder();
        List<String> list = new ArrayList<>();


        for (String a : params) {
            a = a.trim();
            if (a.isEmpty()) continue;
            String[] arr = a.split(" ");

            String tt = "";
            if (isSelectedAction(null)) {
                tt = chuli(arr,0,1,sb);
                jsonsb.append("\""+tt.trim()+"\":\""+arr[arr.length - 1]+"\",");
                list.add(tt.trim()+"#"+arr[arr.length - 1]);
            } else {
                tt = chuli(arr,1,0,sb);
                jsonsb.append("\""+tt.trim()+"\":\""+arr[0]+"\",");
                list.add(tt.trim()+"#"+arr[0]);
            }
            sb.delete(0, sb.length());
        }
        sb.delete(0, sb.length());
        jsonsb.delete(0, sb.length());
        Set<String> set = new HashSet<String>(list);
        JSONObject jsonObject = new JSONObject();
        for (String s : set) {
            String[] arr = s.split("#");
            jsonObject.put(arr[0],arr[1]);
        }
        list.clear();
        set.clear();
        textare_bank_param.setText("");
        textare_bank_json.setText(jsonObject.toString());
    }

    public String chuli(String[] arr,int n,int m,StringBuilder sb){
        for (int i = n; i < arr.length-m; i++) {
            sb.append(arr[i]).append(" ");
        }
        return sb.toString();
    }






    @FXML
    public boolean isSelectedAction(ActionEvent actionEvent) {
        boolean check = jfx_tb.isSelected();
        if (check) {
            jfx_tb.setText("银行名称->编码");
        } else {
            jfx_tb.setText("编码->银行名称");
        }
        return check;
    }

    public void backAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/root-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        MainApp.mystage.setScene(scene);
        MainApp.mystage.show();
    }



}
