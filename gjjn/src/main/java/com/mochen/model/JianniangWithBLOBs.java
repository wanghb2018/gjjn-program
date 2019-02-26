package com.mochen.model;

public class JianniangWithBLOBs extends Jianniang {
    private String touxiang;

    private String lihui;

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang == null ? null : touxiang.trim();
    }

    public String getLihui() {
        return lihui;
    }

    public void setLihui(String lihui) {
        this.lihui = lihui == null ? null : lihui.trim();
    }
}