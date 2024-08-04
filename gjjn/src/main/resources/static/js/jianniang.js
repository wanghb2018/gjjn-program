function showjnlist(){
    $("#jianduiview").hide();
    $("#jianniangview").show();
    $("#cangkuview").hide();
    $("#chujiview").hide();
    $("#caidanview").hide();
    $.get('showJnList',function(data){
        if(data){
            var jnliststr = "";
            for(var i=0;i<data.length;i++){
                jnliststr = jnliststr + "<img src='"+data[i].touxiang+"' class='jntxlist' style='border: 3px "+data[i].color+" solid' onclick='showjninfo2("+data[i].id+")'/> ";
            }
            $('#jnlistdiv').empty();
            $('#jnlistdiv').html(jnliststr);
        }
    });
}

function showjninfo2(id){
    mui.get('showJnInfo',{
        'myJnId':id
    },function(data){
        if(data){
            $('#jninfo_touxiang2').attr('src',data.jn.touxiang);
            $('#jninfo_name2').html("<font color='"+data.jn.color+"'>"+data.jn.name+"</font>");
            $('#jninfo_zdl2').html(data.jn.zdl);
            $('#jninfo_id2').html(data.jn.level);
            $('#jninfo_star2').html(data.jn.star);
            $('#jninfo_gj2').html(data.jn.gongji);
            $('#jninfo_fy2').html(data.jn.fangyu);
            $('#jninfo_xl2').html(data.jn.xueliang);
            $('#jninfo_sd2').html(data.jn.sudu);
            $('#jninfo_bj2').html(data.jn.baoji);
            $('#jninfo_db2').html(data.jn.duobi);
            $('#jninfo_jy2').html(data.jn.jingyan + " / " + data.sj.needjy);
            var spstr = "<span style='color: "+data.jn.color+"'>" + data.num + "</span>";
            if (data.zblNum) {
                spstr = spstr + " + <span style='color: violet'>" + data.zblNum + "</span>";
            }
            if (data.jblNum) {
                spstr = spstr + " + <span style='color: gold'>" + data.jblNum + "</span>";
            }
            $('#jninfo_sj2').html(spstr + " / " + "<span style='color: "+data.jn.color+"'>" + data.jn.spnum + "</span>");
            if (data.jn.lihui.endsWith('.mp4')) {
                $('#jninfo_lh2_video').attr('src',data.jn.lihui);
                $('#jninfo_lh2_video').show();
                $('#jninfo_lh2').hide();
            } else {
                $('#jninfo_lh2').attr('src',data.jn.lihui);
                $('#jninfo_lh2').show();
                $('#jninfo_lh2_video').hide();
            }

            if(data.jn.iswar == 1)
                $('#jninfo_shangzhen2').hide();
            else
                $('#jninfo_shangzhen2').show();
            $('#jnlistdiv').hide();
            $('#jiannianginfo2').show();
            $('#jninfohideid').val(data.jn.id);
        }
    });
}

function jnshengxing(index){
    mui.confirm("是否使用对应舰娘碎片(粉色及以下不足则使用布里碎片)进行升星？","提示",["取消","确定"],function(e){
        if(e.index==1){
            mui.get("jnShengxing",{'myJnId':$('#jninfohideid').val()},function(r){
                mui.toast(r.data);
                if(r.hr === 0) {
                    if (index === 1) {
                        jninfoview($('#jninfohideid').val());
                    } else {
                        showjninfo2($('#jninfohideid').val());
                    }
                }
            });
        }
    });
}