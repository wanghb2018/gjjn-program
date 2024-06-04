function showhclist(){
    $("#jianduiview").hide();
    $("#jianniangview").hide();
    $("#cangkuview").show();
    $("#chujiview").hide();
    $("#caidanview").hide();

    mui.get('showHcList',function(d){
        if(d){
            $('#zbl').html(d.zbl);
            $('#jbl').html(d.jbl);
            if (d.sps && d.sps.length > 0) {
                var data = d.sps;
                var spliststr = "";
                for(var i=0;i<data.length;i++){
                    spliststr += `
                        <div class="jntxlist" style="margin-top: 0; margin-bottom: 0" onclick="jnhecheng(${data[i].jnId}, ${data[i].spnum}, ${data[i].hecheng},'${data[i].name}')">
                            <img src="${data[i].touxiang}" class="sp-img${data[i].hecheng ? '':' opacity-img'}" style="border: 3px ${data[i].color} solid">
                            <span class="sp-num-span" style="color: ${data[i].color}">${data[i].num > 0 ? data[i].num : ''}</span>
                        </div>`
                }
                $('#splistul').empty();
                $('#splistul').html(spliststr);
            } else {
                $('#splistul').empty();
                $('#splistul').html('<span style="color: gold; margin: auto; padding-top: 32px;">恭喜你已解锁全部舰娘！</span>');
            }
        }
    });
}

function jnhecheng(jnId,neednum,hecheng,name){
    if (!hecheng) {
        return;
    }
    mui.confirm("是否使用"+neednum+"个碎片(红色品级以下不足可使用布里碎片)合成"+name+"？","提示",["取消","确定"],function(e){
        if(e.index==1){
            mui.get('jnHecheng',{'jnId':jnId},function(data){
                if(data==1){
                    mui.toast("对应舰娘已存在！");
                }else if(data==0){
                    showhclist();
                    mui.toast("恭喜你合成成功！");
                }else{
                    mui.toast("碎片不足！");
                }
            });
        }
    });
}