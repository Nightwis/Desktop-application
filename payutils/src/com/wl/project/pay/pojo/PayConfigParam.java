package com.wl.project.pay.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PayConfigParam {

    private final IntegerProperty payConfigId;
    private final IntegerProperty payIndex;
    private final IntegerProperty payChinnelId;
    private final StringProperty payName;
    private final StringProperty payValue;
    private final StringProperty type;
    private final IntegerProperty push;
    private final StringProperty configEnum;

    public PayConfigParam() {
        this(null, null,null,null,null,null,null,null);
    }


    public PayConfigParam(int payConfigId, int payIndex, int payChinnelId, String payName, String payValue, String type, int push, String configEnum) {
        this.payConfigId = new SimpleIntegerProperty(payConfigId);
        this.payIndex = new SimpleIntegerProperty(payIndex);
        this.payChinnelId = new SimpleIntegerProperty(payChinnelId);
        this.payName = new SimpleStringProperty(payName);
        this.payValue = new SimpleStringProperty(payValue);
        this.type = new SimpleStringProperty(type);
        this.push = new SimpleIntegerProperty(push);
        this.configEnum = new SimpleStringProperty(configEnum);
    }

    public PayConfigParam(IntegerProperty payConfigId, IntegerProperty payIndex, IntegerProperty payChinnelId, StringProperty payName, StringProperty payValue, StringProperty type, IntegerProperty push, StringProperty configEnum) {
        this.payConfigId = payConfigId;
        this.payIndex = payIndex;
        this.payChinnelId = payChinnelId;
        this.payName = payName;
        this.payValue = payValue;
        this.type = type;
        this.push = push;
        this.configEnum = configEnum;
    }



    public int getPayChinnelId() {
        return payChinnelId.get();
    }

    public IntegerProperty payChinnelIdProperty() {
        return payChinnelId;
    }

    public void setPayChinnelId(int payChinnelId) {
        this.payChinnelId.set(payChinnelId);
    }

    public int getPayConfigId() {
        return payConfigId.get();
    }

    public IntegerProperty payConfigIdProperty() {
        return payConfigId;
    }

    public void setPayConfigId(int payConfigId) {
        this.payConfigId.set(payConfigId);
    }

    public int getPayIndex() {
        return payIndex.get();
    }

    public IntegerProperty payIndexProperty() {
        return payIndex;
    }

    public void setPayIndex(int payIndex) {
        this.payIndex.set(payIndex);
    }

    public String getPayName() {
        return payName.get();
    }

    public StringProperty payNameProperty() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName.set(payName);
    }

    public String getPayValue() {
        return payValue.get();
    }

    public StringProperty payValueProperty() {
        return payValue;
    }

    public void setPayValue(String payValue) {
        this.payValue.set(payValue);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getPush() {
        return push.get();
    }

    public IntegerProperty pushProperty() {
        return push;
    }

    public void setPush(int push) {
        this.push.set(push);
    }

    public String getConfigEnum() {
        return configEnum.get();
    }

    public StringProperty configEnumProperty() {
        return configEnum;
    }

    public void setConfigEnum(String configEnum) {
        this.configEnum.set(configEnum);
    }

    @Override
    public String toString() {
        return "PayConfigParam{" +
                "payConfigId=" + payConfigId +
                ", payIndex=" + payIndex +
                ", payChinnelId=" + payChinnelId +
                ", payName=" + payName +
                ", payValue=" + payValue +
                ", type=" + type +
                ", push=" + push +
                ", configEnum=" + configEnum +
                '}';
    }
}
