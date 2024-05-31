function renderRoleDuiwu(a){
    if (a){
        $("#jianduiview").show();
        $("#jianniangview").hide();
        $("#cangkuview").hide();
        $("#chujiview").hide();
        $("#caidanview").hide();
    }
    renderRoleDuiwuWithData(null);
}

function renderRoleDuiwuWithData(data){
    if (data){
        renderRoleData(data);
    } else {
        $.get('getRoleInfo',function(data){
            role = data;
            renderRoleData(data);
        });
    }
    getAndRenderDuiwu()
}

function getAndRenderDuiwu(){
    $.get('getDuiwuInfo',function(data){
        renderduiwu(data);
    });
}

function renderduiwu(dw){
    var liststr = "";
    if (dw.myJns){
        for(var i=0;i<dw.myJns.length;i++){
            liststr = liststr + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+ dw.myJns[i].touxiang+"'><div class='mui-media-body'><font color='"+dw.myJns[i].color+"'>"+dw.myJns[i].name+"</font><p class='mui-ellipsis'><font color='white'>Lv."+dw.myJns[i].level+"</font><font style='margin-left: 3px;' color='white'>战斗力:"+dw.myJns[i].zdl+"</font></p></div></div><div style='float: right;'><button type='button' class='imglibutton mui-btn' onclick='jnxiuxi("+dw.myJns[i].id+")'>休息</button> <button type='button' class='imglibutton mui-btn' onclick='jninfoview("+dw.myJns[i].id+")'>查看</button></div></li>";
        }
    }
    $('#rolezdl').html(dw.zdl);
    $('#mydwlist').empty();
    $('#mydwlist').html(liststr);
}

function shangzhen(){
    mui.get("shangzhen",{'id':$('#jninfohideid').val()},function(data){
        if(data==0){
            mui.toast("上阵成功！");
        }else if(data==-1){
            mui.toast("舰队人数已满6人！");
        }else if(data==1){
            mui.toast("该舰娘已在队伍中！");
        }
        showjninfo2($('#jninfohideid').val());
    });
}

function jnxiuxi(id){
    mui.get('xiuxi',{'id':id},function(data){
        if(data==0){
            getAndRenderDuiwu();
            mui.toast("操作成功！");
        }
    });
}

function jninfoview(id){
    mui.get('showJnInfo',{
        'myJnId':id
    },function(data){
        if(data){
            $('#jninfo_touxiang').attr('src',data.jn.touxiang);
            $('#jninfo_name').html("<font color='"+data.jn.color+"'>"+data.jn.name+"</font>");
            $('#jninfo_zdl').html(data.jn.zdl);
            $('#jninfo_id').html(data.jn.level);
            $('#jninfo_star').html(data.jn.star);
            $('#jninfo_gj').html(data.jn.gongji);
            $('#jninfo_fy').html(data.jn.fangyu);
            $('#jninfo_xl').html(data.jn.xueliang);
            $('#jninfo_sd').html(data.jn.sudu);
            $('#jninfo_bj').html(data.jn.baoji);
            $('#jninfo_db').html(data.jn.duobi);
            $('#jninfo_jy').html(data.jn.jingyan + " / " + data.sj.needjy);
            var spstr = "<span style='color: "+data.jn.color+"'>" + data.num + "</span>";
            if (data.zblNum) {
                spstr = spstr + " + <span style='color: violet'>" + data.zblNum + "</span>";
            }
            if (data.jblNum) {
                spstr = spstr + " + <span style='color: gold'>" + data.jblNum + "</span>";
            }
            $('#jninfo_sj').html(spstr + " / " + "<span style='color: "+data.jn.color+"'>" + data.jn.spnum + "</span>");
            $('#jninfo_lh').attr('src',data.jn.lihui);
            $('#jianduisx').hide();
            $('#jiannianginfo').show();
            $('#jninfohideid').val(data.jn.id);
        }
    });
}