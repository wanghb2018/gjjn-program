var domain = "../";

$(function(){

	$("input[name='phbtype']").change(function(){
	getphbinfo($("input[name='phbtype']:checked").val());
	});
})


function openkeyanview(){
	mui.post(domain+"getkeyandata/",function(data){
		if(data){
			$('#gjdj').html(data.gjdj); 
			$('#fydj').html(data.fydj); 
			$('#xldj').html(data.xldj); 
			$('#sddj').html(data.sddj); 
			$('#bjdj').html(data.bjdj); 
			$('#dbdj').html(data.dbdj); 
			$('#gjky').html(data.gjky);
			$('#fyky').html(data.fyky);
			$('#xlky').html(data.xlky);
			$('#sdky').html(data.sdky);
			$('#bjky').html(data.bjky);
			$('#dbky').html(data.dbky);
			$('#gjwz').html(data.gjwz);
			$('#fywz').html(data.fywz);
			$('#xlwz').html(data.xlwz);
			$('#sdwz').html(data.sdwz);
			$('#bjwz').html(data.bjwz);
			$('#dbwz').html(data.dbwz);
			$('#jianduisx').hide();
			$('#keyanview').show();
		}
	});	
}
function keyan_back(){
	$('#keyanview').hide();
	$('#jianduisx').show();
}

function changetouxiang(){
	mui.post(domain+"changetouxiang/",{'id':$('#jninfohideid').val()},function(data){
		if(data.result=='success'){
			$('#roletouxiang').attr('src',data.tx);
			mui.toast("设置成功！");
		}
	});
}

function jnshengxing(index){
	mui.confirm("是否使用对应舰娘碎片(不足则使用布里碎片)进行升星？","提示",["取消","确定"],function(e){
		if(e.index==1){
			mui.post(domain+"jnshengxing/",{'id':$('#jninfohideid').val()},function(data){
				if(data.result=='success'){					
					mui.toast("升星成功！");
					getRoleData();
					showsplist();
				}else if(data.result=='defaultwz'){
					mui.toast("物资不足，缺少"+data.wuzi);
				}else if(data.result=='defaultsp'){
					mui.toast("碎片不足，缺少"+data.suipian);
				}else if(data.result=='max'){
					mui.toast("已达到最大星级！");
				}
			});
		}
	});
	
}


function win(a,b){
	mui.toast("敬请期待！");
}

function keyansj(type){
	mui.post(domain+'keyansj/',{'type':type},function(data){
		if(data.result=="success"){
			mui.toast("升级成功！");
			openkeyanview();
			$('wz_num').html(data.wuzi);
			$('#rolekeyandian').html(data.keyandian);
		}else if(data.result=='limit'){
			mui.toast("已达等级上限！");
		}else{
			mui.toast("所需资源不足！");
		}
	});
}

function jianzao(){
	mui.post(domain+"jianzao/",function(data){
				if(data.result=='success'){
					var str = "建造成功！<br/><font color='"+data.color+"'>"+data.name+"碎片："+data.num+"个</font>";				
					mui.toast(str,{ duration:'long', type:'div' });
					$('#rolemofang').html(data.rolemofang);
					$('#wz_num').html(data.rolewuzi);
				}else if(data.result=='wuzi'){
					mui.toast("物资不足!");
				}else if(data.result=='mofang'){
					mui.toast("魔方不足");
				}
			});
}
function gjjianzao(){
	mui.post(domain+"gjjianzao/",function(data){
				if(data.result=='success'){
					var str = "建造成功！<br/><font color='"+data.color+"'>"+data.name+"碎片："+data.num+"个</font>";				
					mui.toast(str,{ duration:'long', type:'div' });
					$('#rolemofang').html(data.rolemofang);
					$('#wz_num').html(data.rolewuzi);
				}else if(data.result=='wuzi'){
					mui.toast("物资不足!");
				}else if(data.result=='mofang'){
					mui.toast("魔方不足");
				}
			});
}

function xianshijianzao(){
	mui.post(domain+"xianshijianzao/",function(data){
				if(data.result=='success'){
					var str = "建造成功！<br/><font color='"+data.color+"'>"+data.name+"碎片："+data.num+"个</font>";				
					mui.toast(str,{ duration:'long', type:'div' });
					$('#rolemofang').html(data.rolemofang);
					$('#wz_num').html(data.rolewuzi);
				}else if(data.result=='wuzi'){
					mui.toast("物资不足!");
				}else if(data.result=='mofang'){
					mui.toast("魔方不足");
				}
			});
}

function paihangbang(){
	$('#menus').hide();
	$('#phb').show();
	getphbinfo($("input[name='phbtype']:checked").val());
}

function back_phb(){
	$('#phb').hide();$('#menus').show();
}

function getlosejn(){
	$('#menus').hide();
	$('#losejn').show();
	mui.post(domain+'getlosejn/',function(data){
		if(data.jns){
			var str = "";
			if (data.jns.length>0){
				for(var i = 0;i<data.jns.length;i++){
				str = str + "<font color = "+data.jns[i].color+">"+data.jns[i].name+"</font><br/>";
				}
			}else{
				str = "<font color='Gold'>恭喜您已解锁全部舰娘!</font>"
			}
			$('#losejnul').html(str);
		}
	})
}

function back_losejn(){
	$('#losejn').hide();$('#menus').show();
}

function getphbinfo(type){
	mui.post(domain+'getphbinfo/',{'type':type},function(data){
		if(data){
			var str="";
			if(data.djphb){
				for(var i = 0 ;i<data.djphb.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data.djphb[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data.djphb[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 Lv."+data.djphb[i].lv+"</p></div></div></li>";
				}
			}else if(data.zlphb){
				for(var i = 0 ;i<data.zlphb.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data.zlphb[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data.zlphb[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 战斗力："+data.zlphb[i].zdl+"</p></div></div></li>";
				}
			}else if(data.tjphb){
				for(var i = 0 ;i<data.tjphb.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data.tjphb[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data.tjphb[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 图鉴数："+data.tjphb[i].tjs+"个</p></div></div></li>";
				}
			}
			else if(data.jnphb){
				for(var i = 0 ;i<data.jnphb.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data.jnphb[i].tx+"'><div class='mui-media-body'><font color='"+data.jnphb[i].color+"'>"+data.jnphb[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 <font color='cyan'>战斗力："+data.jnphb[i].zdl+"</font>  <font color='gold'>"+data.jnphb[i].rolename+"</font></p></div></div></li>";
				}
			}
			$('#phbul').empty();
			$('#phbul').html(str);
		}
	});
}

function zuanshisd(){
	mui.get(domain+"zuanshisd/",function(data){
		if(data.result=='success'){
			mui.toast("购买成功！");
			$('#zs_num').html(data.zuanshi);
			$('#roleshiyou').html(data.shiyou);
		}else{
			mui.toast("砖石不足！");
		}
	});
}

function salemofang(){
	mui.get(domain+"salemofang/",function(data){
		if(data.result=='success'){
			$('#rolemofang').html(data.mofang);
			$('#wz_num').html(data.wuzi);
			mui.toast("兑换成功！");
		}else{
			mui.toast("魔方不足！");
		}
	});
}

function buymofang(){
	mui.get(domain+"buymofang/",function(data){
		if(data.result=='success'){
			$('#rolemofang').html(data.mofang);
			$('#wz_num').html(data.wuzi);
			mui.toast("购买成功！");
		}else{
			mui.toast("物资不足！");
		}
	});
}

function salezbl(){
	mui.get(domain+"salezbl/",function(data){
		if(data.result=='success'){
			mui.toast("兑换成功！");
		}else{
			mui.toast("紫布里不足！");
		}
	});
}

