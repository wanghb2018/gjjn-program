package com.mochen.model;

public class GameMap {
    private Integer id;

    private String label;

    private Integer place;

    private Integer point;

    private Integer zdl;

    private Integer zhgjy;

    private Integer jnjy;

    private Integer wz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getZdl() {
        return zdl;
    }

    public void setZdl(Integer zdl) {
        this.zdl = zdl;
    }

    public Integer getZhgjy() {
        return zhgjy;
    }

    public void setZhgjy(Integer zhgjy) {
        this.zhgjy = zhgjy;
    }

    public Integer getJnjy() {
        return jnjy;
    }

    public void setJnjy(Integer jnjy) {
        this.jnjy = jnjy;
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }
}