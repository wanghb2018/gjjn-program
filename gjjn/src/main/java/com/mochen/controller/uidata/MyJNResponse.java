package com.mochen.controller.uidata;

import com.mochen.model.Jianniang;
import com.mochen.model.MyJianniang;

public class MyJNResponse {
    private Integer id;

    private Integer gongji;

    private Integer fangyu;

    private Integer xueliang;

    private Integer sudu;

    private Integer baoji;

    private Integer duobi;

    private Integer star;

    private Integer level = 1;

    private Integer iswar;

    private Integer zdl;

    private Integer jingyan = 0;

    private Integer jnId;

    private Integer roleId;

    private String name;

    private Integer pinji;

    private String touxiang;

    private String lihui;

    private String color;

    private Integer spnum;

    public MyJNResponse() {
    }

    public MyJNResponse(MyJianniang data) {
        this.id = data.getId();
        this.gongji = data.getGongji();
        this.fangyu = data.getFangyu();
        this.xueliang = data.getXueliang();
        this.sudu = data.getSudu();
        this.baoji = data.getBaoji();
        this.duobi = data.getDuobi();
        this.star = data.getStar();
        this.level = data.getLevel();
        this.iswar = data.getIswar();
        this.zdl = data.getZdl();
        this.jingyan = data.getJingyan();
        this.jnId = data.getJnId();
        this.roleId = data.getRoleId();

        Jianniang jn = data.getJn();
        this.name = jn.getName();
        this.pinji = jn.getPinji();
        this.touxiang = jn.getTouxiang();
        this.lihui = jn.getLihui();
        this.color = jn.getColor();
        this.spnum = jn.getSpnum();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGongji() {
        return gongji;
    }

    public void setGongji(Integer gongji) {
        this.gongji = gongji;
    }

    public Integer getFangyu() {
        return fangyu;
    }

    public void setFangyu(Integer fangyu) {
        this.fangyu = fangyu;
    }

    public Integer getXueliang() {
        return xueliang;
    }

    public void setXueliang(Integer xueliang) {
        this.xueliang = xueliang;
    }

    public Integer getSudu() {
        return sudu;
    }

    public void setSudu(Integer sudu) {
        this.sudu = sudu;
    }

    public Integer getBaoji() {
        return baoji;
    }

    public void setBaoji(Integer baoji) {
        this.baoji = baoji;
    }

    public Integer getDuobi() {
        return duobi;
    }

    public void setDuobi(Integer duobi) {
        this.duobi = duobi;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIswar() {
        return iswar;
    }

    public void setIswar(Integer iswar) {
        this.iswar = iswar;
    }

    public Integer getZdl() {
        return zdl;
    }

    public void setZdl(Integer zdl) {
        this.zdl = zdl;
    }

    public Integer getJingyan() {
        return jingyan;
    }

    public void setJingyan(Integer jingyan) {
        this.jingyan = jingyan;
    }

    public Integer getJnId() {
        return jnId;
    }

    public void setJnId(Integer jnId) {
        this.jnId = jnId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPinji() {
        return pinji;
    }

    public void setPinji(Integer pinji) {
        this.pinji = pinji;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getLihui() {
        return lihui;
    }

    public void setLihui(String lihui) {
        this.lihui = lihui;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSpnum() {
        return spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }
}
