package com.gjjn.dao.entity;

public class Junxian {
    private Integer id;

    private String label;

    private Integer powerrate;

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

    public Integer getPowerrate() {
        return powerrate;
    }

    public void setPowerrate(Integer powerrate) {
        this.powerrate = powerrate;
    }
}