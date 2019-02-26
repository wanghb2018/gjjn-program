var domain = "../";
var guajiid = '';
$(function(){
	$('.select_jn').on('click',function(){
		$('.select_jn').removeClass('cjjs_jnimg_xuanzhong');
		$('.select_jn').addClass('cjjs_jnimg');
		$(this).addClass('cjjs_jnimg_xuanzhong');
		var idstr = $(this).attr('id');
		$('#selectedId').val(idstr);
		if(idstr == 'jnimg1'){
			$('#jnworld').html('本森级驱逐舰拉菲听候您的吩咐……指挥官，这个耳朵不是真的，请不要再盯着看了……');
		}else if(idstr == 'jnimg2'){
			$('#jnworld').html('我是标枪，指挥官指挥官，请多指教啦～欸嘿嘿，因为在意的女生突然自己加入到手下来，不知所措了？');
		}else{
			$('#jnworld').html('我还以为你从来都不会选我呢。');
		}
	});

	$("input[name='phbtype']").change(function(){
	getphbinfo($("input[name='phbtype']:checked").val());
	});
})
function register(){
	var username = $('#reg_username').val().trim();
	var password = $('#reg_password').val().trim();
	var password1 = $('#reg_password1').val().trim();
	var email = $('#reg_Email').val().trim();
	if(username.length<5||username.length>20){
		mui.toast('帐号必须为5-20位字符，不含特殊字符！');
		return;
	}
	if(password.length<5  || password.length>20){
		mui.toast('密码必须为5-20位字符，不含特殊字符！');
		return;
	}
	if(password != password1){
		mui.toast('认真点，两次密码都不一致啦，是不是撸多了？');
		return;
	}
	if(email==''||!/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(email)){
		mui.toast('邮箱找回密码很重要的说，记得要填写哦！');
		return;
	}
	mui.post(domain+'register/',
	{
		'username':username,
		'password':password,
		'email':email
	},function(data){
		if(data.result == 'success'){
			mui.toast("注册成功！");	
			$('#reg').hide();
 			$('#login_username').val(username);
 			$('#login_password').val(password);
			$('#login').show();
		

		}else if(data.result=='exist'){
			mui.toast('该帐号已存在！');
		}else{
			mui.toast('请好好玩耍！');
		}
	},'json');
}

function changepasswd(){
	var username = $('#cpd_username').val().trim();
	var password = $('#cpd_password').val().trim();
	var password1 = $('#cpd_password1').val().trim();
	var email = $('#cpd_Email').val().trim();
	if(username.length<5||username.length>20){
		mui.toast('帐号必须为5-20位字符，不含特殊字符！');
		return;
	}
	if(email==''||!/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(email)){
		mui.toast('邮箱格式都输不对，看来是不想修改密码啦！');
		return;
	}
	if(password.length<5  || password.length>20){
		mui.toast('密码必须为5-20位字符，不含特殊字符！');
		return;
	}
	if(password != password1){
		mui.toast('认真点，两次密码都不一致啦，是不是撸多了？');
		return;
	}
	mui.post(domain+'changepasswd/',
	{
		'username':username,
		'password':password,
		'email':email
	},function(data){
		if(data.result == 'success'){			
			$('#changepasswdview').hide();
			$('#login').show();
		}else if(data.result=='notexit'){
			mui.toast('该帐号不存在！');
		}else if(data.result=='error'){
			mui.toast('邮箱不正确喔！');
		}else{
			mui.toast('请好好玩耍！');
		}
	},'json');
	
}

function createrole(){
	jnid = $('#selectedId').val();
	rolename = $('#cjrole_zhgname').val().trim();
	if(jnid==""){
		mui.toast("请选择初始舰娘！");
		return;
	}
	if(rolename=="" || rolename.length > 6){
		mui.toast("指挥官名称不能为空，且必须在6个字符以内");
		return;
	}
	mui.post(domain+"createrole/",{
		'jnid':jnid,
		'rolename':rolename
	},function(data){
		if(data.result=="success"){
			location.reload();
			$('#zhop').hide();
			$('#mainview').show();
		}else{
			mui.alert("兄弟，给个面子，能不能别闹！");
		}
	});
}
function getRoleData(){
	mui.post(domain+'getroledata/','',function(data){
		if(data.role){
			$('#rolename').html(data.role.rolename);
			$('#rolelevel').html("Lv."+data.role.level);
			$('#roletouxiang').attr('src',data.role.touxiang);
			$('#zs_num').html(data.role.zuanshi);
			$('#wz_num').html(data.role.wuzi);
			$('#roleid').html(data.role.id);
			$('#rolejunxian').html(data.role.junxian);
			$('#roleshiyou').html(data.role.shiyou);
			$('#rolemofang').html(data.role.mofang);
			$('#rolekeyandian').html(data.role.keyandian);
			$('#roleexp').html(data.role.exp);
			$('#roleexprate').html(data.role.exprate);
			$('#rolesj').html(data.role.sjjy);			
			$('#roleguajitime').html(data.role.guajitime);
			renderduiwu(data.duiwu);
			maps = data.maps;
			guajiid = data.role.guajimap;
			rendermap(data.role.openmap,guajiid,data.role.guajijy);
			$('#mainview').show();
		}
	});
}
function getRoleDataWithoutMap(){
	mui.post(domain+'getroledata/','',function(data){
		if(data.role){
			$('#rolename').html(data.role.rolename);
			$('#rolelevel').html("Lv."+data.role.level);
			$('#roletouxiang').attr('src',data.role.touxiang);
			$('#zs_num').html(data.role.zuanshi);
			$('#wz_num').html(data.role.wuzi);
			$('#roleid').html(data.role.id);
			$('#rolejunxian').html(data.role.junxian);
			$('#roleshiyou').html(data.role.shiyou);
			$('#rolemofang').html(data.role.mofang);
			$('#rolekeyandian').html(data.role.keyandian);
			$('#roleexp').html(data.role.exp);
			$('#roleexprate').html(data.role.exprate);
			$('#rolesj').html(data.role.sjjy);			
			$('#roleguajitime').html(data.role.guajitime);
			renderduiwu(data.duiwu);
			maps = data.maps;
			guajiid = data.role.guajimap;
		}
	});
}

function renderduiwu(dw){
	var l = dw.dwlist;
	var liststr = "";
	for(var i=0;i<l.length;i++){
		liststr = liststr + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+ l[i].tx+"'><div class='mui-media-body'><font color='"+l[i].color+"'>"+l[i].name+"</font><p class='mui-ellipsis'><font color='white'>Lv."+l[i].lv+"</font><font style='margin-left: 5px;' color='white'>战斗力："+l[i].zdl+"</font></p></div></div><div style='float: right;'><button type='button' class='imglibutton mui-btn' onclick='jnxiuxi("+l[i].id+")'>休息</button> <button type='button' class='imglibutton mui-btn' onclick='jninfoview("+l[i].id+")'>查看</button></div></li>";
	}
	$('#rolezdl').html(dw.totalzdl);
	$('#mydwlist').empty();
	$('#mydwlist').html(liststr);
}
function rendermap(index,id,jy){
	var mapstr = "";
	for(var i=0;i<index;i++){
		mapstr = mapstr + "<li class='mui-table-view-cell' id='mapid_"+maps[i].id+"'><div style='float: left;'><div><font color='#FFCC00'>"+maps[i].place+"-"+maps[i].point+" "+maps[i].label+"</font><p class='myp mui-ellipsis'>推荐战力：<span>"+maps[i].zdl+"</span></p></div></div><div style='float: right;'><button id='mapbtn"+maps[i].id+"' type='button' class='mui-btn gjbtn' onclick='mapguaji("+maps[i].id+")'>挂机</button> <button id='mapbtnboss"+maps[i].id+"' type='button' class='mui-btn' onclick='mapboss("+maps[i].id+")'>BOSS</button></div></li>";
	}
	$('#mapul').empty();
	$('#mapul').html(mapstr);
	if(id){
		$("#mapbtn"+id).addClass('mychujibutton');
		$("#mapbtn"+id).text("结算");
	}
	$('#roleguajijy').html(jy);
}
function testdzd(){
	mui.get(domain+'testdzd/',"",function(data){
			if(data.dzd=='false'){
				mui.alert('您的账号已在别处登录！','警告',function(){
					$('#welcome').hide();
					$('#login').show();
				},'div');	
			}	
		},'json');
}

function jninfoview(id){
	mui.post(domain+'getjninfobyid/',{
		'id':id
	},function(data){
		if(data.result=='success'){
			$('#jninfo_touxiang').attr('src',data.jninfo.tx);
			$('#jninfo_name').html("<font color='"+data.jninfo.color+"'>"+data.jninfo.name+"</font>");
			$('#jninfo_zdl').html(data.jninfo.zdl);
			$('#jninfo_id').html(data.jninfo.lv);
			$('#jninfo_star').html(data.jninfo.star);
			$('#jninfo_gj').html(data.jninfo.gj);
			$('#jninfo_fy').html(data.jninfo.fy);
			$('#jninfo_xl').html(data.jninfo.xl);
			$('#jninfo_sd').html(data.jninfo.sd);
			$('#jninfo_bj').html(data.jninfo.bj);
			$('#jninfo_db').html(data.jninfo.db);
			$('#jninfo_jy').html(data.jninfo.jy);
			$('#jninfo_sj').html(data.jninfo.sj);
			$('#jninfo_lh').attr('src',data.jninfo.lh);
			$('#jianduisx').hide();
			$('#jiannianginfo').show();
			$('#jninfohideid').val(data.jninfo.id);
		}
	});
}

function showjninfo2(id){
	mui.post(domain+'getjninfobyid/',{
		'id':id
	},function(data){
		if(data.result=='success'){
			$('#jninfo_touxiang2').attr('src',data.jninfo.tx);
			$('#jninfo_name2').html("<font color='"+data.jninfo.color+"'>"+data.jninfo.name+"</font>");
			$('#jninfo_zdl2').html(data.jninfo.zdl);
			$('#jninfo_id2').html(data.jninfo.lv);
			$('#jninfo_star2').html(data.jninfo.star);
			$('#jninfo_gj2').html(data.jninfo.gj);
			$('#jninfo_fy2').html(data.jninfo.fy);
			$('#jninfo_xl2').html(data.jninfo.xl);
			$('#jninfo_sd2').html(data.jninfo.sd);
			$('#jninfo_bj2').html(data.jninfo.bj);
			$('#jninfo_db2').html(data.jninfo.db);
			$('#jninfo_jy2').html(data.jninfo.jy);
			$('#jninfo_sj2').html(data.jninfo.sj);
			$('#jninfo_lh2').attr('src',data.jninfo.lh);
			if(data.jninfo.iswar == 1)
				$('#jninfo_shangzhen2').hide();
			else
				$('#jninfo_shangzhen2').show();
			$('#jnlistdiv').hide();
			$('#jiannianginfo2').show();
			$('#jninfohideid').val(data.jninfo.id);
		}
	});
}

function jninfo_back(){
	$('#jiannianginfo').hide();
	$('#jianduisx').show();
}
function jninfo_back2(){
	$('#jiannianginfo2').hide();
	$('#jnlistdiv').show();
}

function showjnlist(){
	mui.post(domain+'showjnlist/',function(data){
		if(data){
			var jnliststr = "";
			for(var i=0;i<data.jnlist.length;i++){
				jnliststr = jnliststr + "<img src='"+data.jnlist[i].tx+"' class='jntxlist' style='border: 3px "+data.jnlist[i].color+" solid' onclick='showjninfo2("+data.jnlist[i].id+")'/> ";
			}
			$('#jnlistdiv').empty();
			$('#jnlistdiv').html(jnliststr);
		}
	});
}

function showsplist(){
	mui.post(domain+'showsplist/',function(data){
		if(data){	
			$('#zbl').html(data.zbl);
			$('#jbl').html(data.jbl);
			var spliststr = "";
			for(var i=0;i<data.splist.length;i++){
				spliststr = spliststr + "<li id='splistid"+data.splist[i].id+"' class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data.splist[i].tx+"'><div class='mui-media-body'><font color='"+data.splist[i].color+"'>"+data.splist[i].name+"</font><p class='myp mui-ellipsis'>数量：<span>"+data.splist[i].num+"</span>/<span>"+data.splist[i].neednum+"</span></p></div></div><div style='float: right;'><div id='numbox"+data.splist[i].id+"' class='mui-numbox' data-numbox-step='1' data-numbox-min='0' data-numbox-max='"+data.splist[i].num+"'><button class='mui-btn mui-numbox-btn-minus' type='button'>-</button><input class='mui-numbox-input' type='number' /><button class='mui-btn mui-numbox-btn-plus' type='button'>+</button></div> <button type='button' class='imglibutton mui-btn' onclick='salesuipian("+data.splist[i].id+","+data.splist[i].pinji+")'>出售</button> <button type='button' class='imglibutton mui-btn' onclick='jnhecheng("+data.splist[i].id+","+data.splist[i].neednum+")'>合成</button></div></li>";
			}
			if(spliststr!=""){				
				$('#splistul').empty();
				$('#splistul').html(spliststr);
				mui('.mui-numbox').numbox();
			}
		}
	});
}

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

function gotomap(){
	if(guajiid){
		location.href = "#mapid_"+guajiid;
		window.scrollBy(0, -95);
	}
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