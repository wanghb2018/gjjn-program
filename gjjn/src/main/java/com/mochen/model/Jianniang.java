package com.mochen.model;

public class Jianniang {
    private Integer id;

    private String name;

    private Integer pinji;

    private Integer gongji;

    private Integer fangyu;

    private Integer xueliang;

    private Integer sudu;

    private Integer baoji;

    private Integer duobi;

    private Integer star;

    private String color;

    private Integer spnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPinji() {
        return pinji;
    }

    public void setPinji(Integer pinji) {
        this.pinji = pinji;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Integer getSpnum() {
        return spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }
}