package com.mochen.model;

import java.util.Date;

public class Role {
    private Integer id;

    private String rolename;

    private Integer level = 1;

    private Integer exp = 0;

    private Integer zuanshi = 0;

    private Integer mofang = 0;

    private Date guajitime;

    private Integer shiyou = 200;

    private Integer qdts = 0;

    private Date qdsj;

    private Integer djsx = 10;

    private Integer guajimapId;

    private Integer junxianId = 1;

    private Integer userId;

    private Integer keyandian = 0;

    private Integer wuzi = 0;

    private Integer openmapId = 1;

    private String touxiang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getZuanshi() {
        return zuanshi;
    }

    public void setZuanshi(Integer zuanshi) {
        this.zuanshi = zuanshi;
    }

    public Integer getMofang() {
        return mofang;
    }

    public void setMofang(Integer mofang) {
        this.mofang = mofang;
    }

    public Date getGuajitime() {
        return guajitime;
    }

    public void setGuajitime(Date guajitime) {
        this.guajitime = guajitime;
    }

    public Integer getShiyou() {
        return shiyou;
    }

    public void setShiyou(Integer shiyou) {
        this.shiyou = shiyou;
    }

    public Integer getQdts() {
        return qdts;
    }

    public void setQdts(Integer qdts) {
        this.qdts = qdts;
    }

    public Date getQdsj() {
        return qdsj;
    }

    public void setQdsj(Date qdsj) {
        this.qdsj = qdsj;
    }

    public Integer getDjsx() {
        return djsx;
    }

    public void setDjsx(Integer djsx) {
        this.djsx = djsx;
    }

    public Integer getGuajimapId() {
        return guajimapId;
    }

    public void setGuajimapId(Integer guajimapId) {
        this.guajimapId = guajimapId;
    }

    public Integer getJunxianId() {
        return junxianId;
    }

    public void setJunxianId(Integer junxianId) {
        this.junxianId = junxianId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKeyandian() {
        return keyandian;
    }

    public void setKeyandian(Integer keyandian) {
        this.keyandian = keyandian;
    }

    public Integer getWuzi() {
        return wuzi;
    }

    public void setWuzi(Integer wuzi) {
        this.wuzi = wuzi;
    }

    public Integer getOpenmapId() {
        return openmapId;
    }

    public void setOpenmapId(Integer openmapId) {
        this.openmapId = openmapId;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang == null ? null : touxiang.trim();
    }
}