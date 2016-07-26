package com.el.oa.domain.kaoqi;

import java.io.Serializable;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @User : Hapic
 * @Date : 2016/7/26 19:40
 */
public class SignDayRecord implements Serializable{

    private Integer signId;//打卡id
    private Integer uid;//用户id
    private String day;//日期
    private String onDuty;//上班时间
    private String offDuty;//下班时间
    private int jiaban;//以分钟为单位
    private int chidao;//以分钟为单位

    public SignDayRecord(Integer signId, Integer uid, String day, String onDuty, String offDuty) {
        this.signId = signId;
        this.uid = uid;
        this.day = day;
        this.onDuty = onDuty;
        this.offDuty = offDuty;

    }

    public int getJiaban() {
        return jiaban;
    }

    public void setJiaban(int jiaban) {
        this.jiaban = jiaban;
    }

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(String onDuty) {
        this.onDuty = onDuty;
    }

    public String getOffDuty() {
        return offDuty;
    }

    public void setOffDuty(String offDuty) {
        this.offDuty = offDuty;
    }

    public int getChidao() {
        return chidao;
    }

    public void setChidao(int chidao) {
        this.chidao = chidao;
    }

    @Override
    public String toString() {
        return "SignDayRecord{" +
                "signId=" + signId +
                ", uid=" + uid +
                ", day='" + day + '\'' +
                ", onDuty='" + onDuty + '\'' +
                ", offDuty='" + offDuty + '\'' +
                ", jiaban=" + jiaban +
                ", chidao=" + chidao +
                '}';
    }
}
