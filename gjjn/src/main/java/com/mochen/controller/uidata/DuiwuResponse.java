package com.mochen.controller.uidata;

import com.mochen.service.data.DuiwuData;

import java.util.List;
import java.util.stream.Collectors;

public class DuiwuResponse {
    private List<MyJNResponse> myJns;
    private Integer zdl = 0;

    public DuiwuResponse() {
    }

    public DuiwuResponse(DuiwuData data) {
        if (data.getMyJns() != null) {
            this.myJns = data.getMyJns().stream().map(MyJNResponse::new).collect(Collectors.toList());
        }
        this.zdl = data.getZdl();
    }

    public List<MyJNResponse> getMyJns() {
        return myJns;
    }

    public void setMyJns(List<MyJNResponse> myJns) {
        this.myJns = myJns;
    }

    public Integer getZdl() {
        return zdl;
    }

    public void setZdl(Integer zdl) {
        this.zdl = zdl;
    }
}
