package com.mochen.controller.uidata;

import com.mochen.model.Suipian;
import com.mochen.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class UserSpResponse {
    private int zbl = 0;
    private int jbl = 0;
    private List<SuipianResponse> sps;

    public UserSpResponse() {}

    public UserSpResponse(List<Suipian> sps) {
        if (sps != null) {
            this.sps = new ArrayList<>();
            for (Suipian sp : sps) {
                if (sp.getJn().getId() == Constant.JN_ZBL) {
                    zbl = sp.getNum();
                } else if (sp.getJn().getId() == Constant.JN_JBL) {
                    jbl = sp.getNum();
                } else {
                    this.sps.add(new SuipianResponse(sp));
                }
            }
        }
    }

    public int getZbl() {
        return zbl;
    }

    public void setZbl(int zbl) {
        this.zbl = zbl;
    }

    public int getJbl() {
        return jbl;
    }

    public void setJbl(int jbl) {
        this.jbl = jbl;
    }

    public List<SuipianResponse> getSps() {
        return sps;
    }

    public void setSps(List<SuipianResponse> sps) {
        this.sps = sps;
    }
}
