package com.wl.project.pay.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.wl.project.pay.MainApp;
import com.wl.project.pay.pojo.PayConfigParam;
import com.wl.project.pay.util.ParamConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class PayViewController implements Initializable {

    @FXML
    private AnchorPane payroot;
    @FXML
    private TableColumn<PayConfigParam, Integer> tcid;
    @FXML
    private TableColumn<PayConfigParam, Integer> tcindex;
    @FXML
    private TableColumn<PayConfigParam, Integer> tccid;
    @FXML
    private TableColumn<PayConfigParam, String> tcname;
    @FXML
    private TableColumn<PayConfigParam, String> tcvalue;
    @FXML
    private TableColumn<PayConfigParam, String> tctype;
    @FXML
    private TableColumn<PayConfigParam, Integer> tcpush;
    @FXML
    private TableColumn<PayConfigParam, String> tcenum;
    @FXML
    private JFXTextField payId;
    @FXML
    private JFXTextField paramName;
    @FXML
    private JFXComboBox<String> paramReplace = new JFXComboBox<>();
    @FXML
    private JFXTextField count;
    @FXML
    private JFXDialog notifydialog;
    @FXML
    private JFXTextArea textare;
    private StackPane dialogStackPane;

    @FXML
    TableView<PayConfigParam> tableConfig = new TableView<>();

    int index = 1;

    ParamConfig paramConfig = new ParamConfig();

    ObservableList<PayConfigParam> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("payConfigId"));
        tcindex.setCellValueFactory(new PropertyValueFactory<>("payIndex"));
        tccid.setCellValueFactory(new PropertyValueFactory<>("payChinnelId"));
        tcname.setCellValueFactory(new PropertyValueFactory<>("payName"));
        tcvalue.setCellValueFactory(new PropertyValueFactory<>("payValue"));
        tctype.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcpush.setCellValueFactory(new PropertyValueFactory<>("push"));
        tcenum.setCellValueFactory(new PropertyValueFactory<>("configEnum"));

    }


    public void addAction(ActionEvent actionEvent) {
        String id = payId.getText().trim();
        String counts = count.getText().trim();
        String name = paramName.getText().trim();
        String repname = paramReplace.getSelectionModel().getSelectedItem().toString().trim();

        if(id.isEmpty() || name.isEmpty())  return;
        if(counts.isEmpty())  counts = "1";

        boolean isText = "text".equals(repname) || "random{{位数}}".equals(repname) || "URLEncoder".equals(repname)
                || "md5Upper".equals(repname) || "md5Lower".equals(repname);

        int num = Integer.parseInt(counts);
        for (int i = 0; i < num; i++) {
            PayConfigParam payConfigParam = new PayConfigParam(Integer.parseInt(id), index++, 0, name, isText? "":repname, isText? repname:"", 1, "request");
            data.add(payConfigParam);
        }

        tableConfig.setItems(data);
        payId.setText(id);
        paramReplace.getSelectionModel().selectFirst();
        paramName.setText("");
        count.setText("");
    }


    public void reqAction(ActionEvent actionEvent) {
        if(tableConfig.getItems().size()==0) return;
        List<PayConfigParam> list = tableConfig.getItems();
        if(list.isEmpty()) return;
        String type = paramReplace.getSelectionModel().getSelectedItem().trim();
        if(!"md5Upper".equals(type) && !"md5Lower".equals(type)){
            paramReplace.getSelectionModel().select("md5Upper");
            type = "md5Upper";
        }

        Map<String,String> map = new TreeMap<>();
        for (PayConfigParam p:list) {
            if(p.getPayIndex() == 120) continue;
            map.put(p.getPayName(),p.getPayValue());
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> en:map.entrySet()) {
            if(en.getValue()==null || "".equals(en.getValue())){
                sb.append(en.getKey()+"={"+en.getKey()+"}&");
            }else{
                sb.append(en.getKey()+"={"+en.getValue()+"}&");
            }
        }
        sb.append("key={privateKey}");
        //System.out.println(sb.toString());
        PayConfigParam payConfigParam = new PayConfigParam(list.get(0).getPayConfigId(), index++, 0, "sign", sb.toString(), type, 1, "request");
        data.add(payConfigParam);

    }


    public void copyAction(ActionEvent actionEvent) {
        if(tableConfig.getItems().size()==0) return;
        StringBuilder sb = new StringBuilder();
        //System.out.println("共有="+tableConfig.getItems().size());
        //System.out.println(tableConfig.getItems().get(1));
        for (PayConfigParam p:tableConfig.getItems()) {
            sb.append(p.getPayConfigId()+"\t"+ p.getPayIndex()+"\t"+ p.getPayChinnelId()+"\t"+ p.getPayName()+"\t"+ p.getPayValue()+"\t"+ p.getType()+"\t"+ p.getPush()+"\t"+ p.getConfigEnum()).append("\n");
        }

        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(sb.toString());
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans,null);
    }


    public void resetAction(ActionEvent actionEvent) {
        index = 1;
        tableConfig.getItems().clear();
        payId.setText("");
        paramReplace.getSelectionModel().selectFirst();
        paramName.setText("");
        count.setText("");
    }


    public void notifyAction(ActionEvent actionEvent) {
        if(tableConfig.getItems().size()==0) return;
        textare.setText("");
        addDialogStackPane();
        notifydialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        notifydialog.show(dialogStackPane);
    }

    /**新增JFXDialog弹窗容器**/
    private void addDialogStackPane() {
        dialogStackPane = new StackPane();
        dialogStackPane.setPrefHeight(payroot.getHeight());
        dialogStackPane.setPrefWidth(payroot.getWidth());
        payroot.getChildren().add(dialogStackPane);
    }


    public void closeAndupd(ActionEvent actionEvent) {
        String t = "md5Upper";
        for (PayConfigParam param:data) {
            if("md5Lower".equals(param.getType())){
                t = "md5Lower";
                break;
            }
        }


        String areatext = textare.getText().replaceAll("\\n",",");
        if(!"".equals(areatext)){
            String[] params = areatext.split(",");

            Set<String> map = new HashSet<>();
            Collections.addAll(map, params);
            StringBuilder sb = new StringBuilder();
            for (String s : map) {
                sb.append(s+"={"+s+"}&");
            }
            sb.append("key={__privateKey}");

            PayConfigParam payConfigParam = new PayConfigParam(tableConfig.getItems().get(0).getPayConfigId(), 120, 0, "sign", sb.toString(), t, 1, "notify");
            data.add(payConfigParam);
        }


        notifydialog.close();
        dialogStackPane.getChildren().remove(notifydialog);
        payroot.getChildren().remove(dialogStackPane);
    }


    public void close(ActionEvent actionEvent) {
        notifydialog.close();
        dialogStackPane.getChildren().remove(notifydialog);
        payroot.getChildren().remove(dialogStackPane);
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
