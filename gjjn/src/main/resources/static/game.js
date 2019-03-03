var domain = "../";

$(function(){

	$("input[name='phbtype']").change(function(){
	getphbinfo($("input[name='phbtype']:checked").val());
	});
})



function salesuipian(id,pinji){
	var num=mui('#numbox'+id).numbox().getValue();
	if (num >0){
		mui.confirm("确定要出售"+num+"个碎片换取魔方？","提示",["取消","确定"],function(e){
		if(e.index==1){
			mui.post(domain+'salesuipian/',{
			'id':id,
			'num':num
			},function(data){
			if(data.result=='success'){
			var str = "<font color='gold'>获得魔方"+data.num+"个</font>";
			mui.toast(str,{ duration:'long', type:'div' });
			}
			showsplist();
			$("#rolemofang").html(data.mofang);
		});
		}
	});

		
	}else{
		if(pinji>3){
			mui.confirm("确定要全部出售该碎片？","提示",["取消","确定"],function(e){
				if(e.index==1){
					mui.post(domain+'salesuipian/',{
					'id':id,
					'num':-1
					},function(data){
					if(data.result=='success'){
					var str = "<font color='gold'>获得魔方"+data.num+"个</font>";
					mui.toast(str,{ duration:'long', type:'div' });
					}
					$("#splistid"+id).hide();
					$("#rolemofang").html(data.mofang);
						});
					}
			});
		}else{
			mui.post(domain+'salesuipian/',{
					'id':id,
					'num':-1
					},function(data){
					if(data.result=='success'){
					var str = "<font color='gold'>获得魔方"+data.num+"个</font>";
					mui.toast(str,{ duration:'long', type:'div' });
					}
					$("#splistid"+id).hide();
					$("#rolemofang").html(data.mofang);
						});
		}
		
	}
}

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
function qiandao(){
	mui.post(domain+'qiandao/',function(data){
		if(data.result=='success'){
			var str = "<font color='yellow'>签到成功！</font><br/><font color='white'>恭喜您获得以下奖励：</font><br/><font color='red'>钻石："+data.getzs+"个</font><br/><font color='green;'>魔方："+data.getmf+"个</font><br/><font color='white'>石油："+data.getsy+"点</font>";
			if(data.obtsp){
				for(var i =0 ;i<data.obtsp.length;i++){
					str = str + "<br/><font color='"+data.obtsp[i].color+"'>"+data.obtsp[i].name+"碎片："+data.obtsp[i].num+"个</font>";
				}
			}
			$('#zs_num').html(data.zuanshi);
			$('#rolemofang').html(data.mofang);
			$('#roleshiyou').html(data.shiyou);
			mui.toast(str,{ duration:'long', type:'div' });
		}else{
			mui.toast("今日已签到！");
		}
	});
}

function jnhecheng(id,neednum){
	mui.confirm("是否使用"+neednum+"个碎片(不足则使用布里碎片)合成？","提示",["取消","确定"],function(e){
		if(e.index==1){
			mui.post(domain+'jnhecheng/',{'id':id},function(data){
				if(data.result=='exist'){
					mui.toast("对应舰娘已存在！");
				}else if(data.result=='success'){
					showjnlist();
					showsplist();
					mui.toast("恭喜你合成成功！");
				}else{
					mui.toast("碎片不足！");
				}
			});
		}
	});
	
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

function shangzhen(){
	mui.post(domain+"shangzhen/",{'id':$('#jninfohideid').val()},function(data){
		if(data.result=='success'){	
			getRoleDataWithoutMap();
			mui.toast("上阵成功！");
		}else if(data.result=='full'){
			mui.toast("舰队人数已满6人！");
		}else if(data.result=='exist'){
			mui.toast("该舰娘已在队伍中！");
		}
	});
}

function jnxiuxi(id){
	mui.post(domain+'jnxiuxi/',{'id':id},function(data){
		if(data.result=='success'){
			getRoleDataWithoutMap();
			mui.toast("操作成功！");
		}
	});
}

function mapguaji(id){
	guajiid = id;
	$('.gjbtn').removeClass('mychujibutton');
	$('.gjbtn').text("挂机");
	$("#mapbtn"+id).addClass('mychujibutton');
	$("#mapbtn"+id).text("结算");
	$("#mapbtn"+id).attr("disabled",true); 
	mui.post(domain+'mapguaji/',{'id':id},function(data){
		if(data.result=='success'){
			$('#roleguajitime').html(data.guajitime);
			var sec = data.sec;
			day = parseInt(sec/86400);
			hour =parseInt(sec%86400/3600);
			min = parseInt(sec%86400%3600/60);
			second = sec-86400*day-hour*3600-min*60;
			var time = "";
			if(day>0)
				time = time + day + "天";
			if(hour>0)
				time = time + hour + "小时";
			if(min>0)
				time = time + min + "分钟";
			if(second>0)
				time = time + second + "秒";
			var str = "<font color='white'>挂机时间"+time+"</font><br /><font color='green'>获得经验："+data.jy+"<font><br/><font color='gold'>获得物资"+data.wz+"</font><br/>";
			if(data.obtsp){
				console.log(data.obtsp);
				for(var i =0 ;i<data.obtsp.length;i++){
					str = str + "<font color='"+data.obtsp[i].color+"'>"+data.obtsp[i].name+"碎片"+data.obtsp[i].num+"个、</font>";
				}
			}
			mui.toast(str,{ duration:'long', type:'div' });
			getRoleDataWithoutMap();
		}else if(date.result=='start'){
			$('#roleguajitime').html(data.guajitime);
			mui.toast("开始挂机");
		}else if(data.result == 'short'){
			mui.toast("时间过短");
		}
		$("#mapbtn"+id).attr("disabled",false); 
	});
}

function mapboss(id){
	$.post(domain+'mapboss/',{'id':id},function(data){
		if(data.result=="success"){
			if(data.status=='1'){
				rendermap(data.openid,data.guajiid,data.guajijy);
			}
			var str = "<font color='yellow'>挑战成功！</font><br/><font color='white'>恭喜您获得以下奖励：</font><br/><font color='yellow'>指挥官经验："+data.zhgjy+"</font><br/><font color='green;'>舰娘经验："+data.jnjy+"</font><br/><font color='gold'>物资："+data.wz+"</font>";
			if(data.obtsp){
				for(var i =0 ;i<data.obtsp.length;i++){
					str = str + "<br/><font color='"+data.obtsp[i].color+"'>"+data.obtsp[i].name+"碎片："+data.obtsp[i].num+"个</font>";
				}
			}
			mui.toast(str,{ duration:'long', type:'div' });
			$("#rolelevel").html("Lv."+data.zhgdengji);
			$("#wz_num").html(data.zhgwuzi);
		}else if (data.result=="default"){
			mui.toast("胜败乃兵家常事，大虾请提升战力后再来！");
		}else if (data.result=="none"){
			mui.toast("哥哥，没油了！");
		}else{
			mui.toast("你没有挑战这关的权限！");
		}
	});
}


function win(a,b){
	mui.toast("敬请期待！");
}

function rolejinjie(){
	mui.post(domain+'rolejinjie/',function(data){
		if(data.result=='success'){
			var str = "升阶成功！<br/>您的军衔已提升至<font color='gold'>"+data.junxian+"</font><br/>并开放等级上限：<font color='green'>"+data.djsx+"级</font><br/>舰队全属性提升5%";
			mui.toast(str,{ duration:'long', type:'div' });
			getRoleData();
		}else if(data.result=='level'){
			mui.toast("等级未满整10级");
		}else if(data.result == 'exp'){
			mui.toast("经验不足");
		}else if(data.result == 'wuzi'){
			mui.toast("物资不足");
		}
	});
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

function salesuipianfull(){
	mui.confirm("确定要出售所有满破舰娘的碎片换取魔方？","提示",["取消","确定"],function(e){
		if(e.index==1){
			$('#btn_full').attr('disabled',true);
			mui.get(domain+"salesuipianfull/",function(data){
				if(data.num>0){
					var str = "<font color='gold'>获得魔方"+data.num+"个</font>";
					mui.toast(str,{ duration:'long', type:'div' });
				} else{
					var str = "<font color='red'>没有要出售的碎片</font>";
					mui.toast(str,{ duration:'long', type:'div' });
				}
				$('#btn_full').attr('disabled',false);
			})
		}
	});
}

function salesuipianexist(){
	mui.confirm("确定要出售所有已拥有舰娘的碎片换取魔方？","提示",["取消","确定"],function(e){
		if(e.index==1){
			$('#btn_exist').attr('disabled',true);
			mui.get(domain+"salesuipianexist/",function(data){
				if(data.num>0){
					var str = "<font color='gold'>获得魔方"+data.num+"个</font>";
					mui.toast(str,{ duration:'long', type:'div' });
				}else{
					var str = "<font color='red'>没有要出售的碎片</font>";
					mui.toast(str,{ duration:'long', type:'div' });
				}
				$('#btn_exist').attr('disabled',false);
			})
		}
	});
}