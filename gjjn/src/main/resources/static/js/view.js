var allRoleSJ = null;
var allMap = null;
var allJunxian = null;
var allKeyanSJ = null;
var guajiid = null;
var role = null;
$(function(){
	$.get('allRoleSJ', function(data){
		allRoleSJ = data;
	});
	$.get('allGameMap', function(data){
		allMap = data;
	});
	$.get('allJunxian', function(data){
		allJunxian = data;
	});
	$.get('allKeyanSj', function(data){
		allKeyanSJ = data;
	});
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
});
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
	mui.post('register',
	{
		'userName':username,
		'password':password,
		'email':email
	},function(data){
		if(data==0){
			mui.toast("注册成功！");	
			$('#reg').hide();
			$('#cjrole').show();
		}else if(data==-1){
			mui.toast('该帐号已存在！');
		}
	},'json');
}

function login(type){
	if(type == 'post'){
		var username = $('#login_username').val().trim();
		var password = $('#login_password').val().trim();
		if(username.length<5||username.length>20){
			mui.toast('帐号必须为5-20位字符，不含特殊字符！');
			return;
		}
		if(password.length<5|| password.length>20){
			mui.toast('密码必须为5-20位字符，不含特殊字符！');
			return;
		}
		mui.post('login',{
			'userName':username,
			'password':password
		},function(data){
			if(data.hr==0){				
				$('#login').hide();
				if(data.data!=null){
					$('#zhop').hide();
					$('#mainview').show();
					renderRoleDuiwuWithData(data.data);
					rendermap(data.data.openmapId,data.data.guajimapId);
					guajiid = data.data.guajimapId;
					role = data.data;
				}else{
					$('#login').hide();
					$('#cjrole').show();
				}
			}else if(data.hr==-1){
				mui.toast('帐号或密码错误，请核对喔~');
			}
		},'json');
	}else{
		$.get('login',function(data){
			if(data.hr==0){
				$('#login').hide();
				if(data.data!=null){
					$('#zhop').hide();
					$('#mainview').show();
					renderRoleDuiwuWithData(data.data);
					rendermap(data.data.openmapId,data.data.guajimapId);
					guajiid = data.data.guajimapId;
					role = data.data;
					fetchNotice();
				}else{
					$('#cjrole').show();
				}
			}else{
				$('#login').show();
			}	
		});
	}
}

function fetchNotice() {
	$.get('getLatestNotice', function (data) {
		if (data) {
			mui.confirm(data.content, data.title,["我知道了","查看详情"],function(e){
				if (e.index === 0) {
					confirmNotice(data.id);
				} else {
					confirmNotice(data.id);
					getNoticeList();
				}
			});
		}
	})
}

function confirmNotice(id) {
	$.post('confirmNotice', {noticeId: id})
}

function logout(){
	mui.get('logout',function(data){
			if(data==0){
				$('#mainview').hide();
				$('#zhop').show();
				$('#login').show();	
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
	mui.post('changePassword/',
	{
		'userName':username,
		'password':password,
		'email':email
	},function(data){
		if(data==0){			
			$('#changepasswdview').hide();
			$('#login').show();
		}else if(data==1){
			mui.toast('该帐号不存在！');
		}else if(dat==-1){
			mui.toast('邮箱不正确喔！');
		}
	},'json');
	
}

function Button_div_login(type){
	if(type == 'reg'){
		$('#login').hide();
		$('#reg').show();
	}else if(type == 'login'){
		$('#reg').hide();
		$('#changepasswdview').hide();
		$('#login').show();
	}else{
		$('#login').hide();
		$('#changepasswdview').show();
	}
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
	mui.post("createRole",{
		'jnImgId':jnid,
		'roleName':rolename
	},function(data){
		if(data){
			location.reload();
			$('#zhop').hide();
			$('#mainview').show();
			renderRoleData(data);
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
	mui.get('mapJiesuan',{'id':id},function(data){
		if(data.hr==0){
			$('#roleguajitime').html(formatDate(data.data.guajitime));
			var sec = data.data.sec;
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
			var str = "<font color='white'>挂机时间"+time+"</font><br /><font color='green'>获得经验："+data.data.jy+"<font><br/><font color='gold'>获得物资"+data.data.wz+"</font><br/>";
			if(data.data.sps){
				for(var i =0 ;i<data.data.sps.length;i++){
					str = str + "<font color='"+data.data.sps[i].color+"'>"+data.data.sps[i].name+"碎片"+data.data.sps[i].num+"个、</font>";
				}
			}
			mui.toast(str,{ duration:'long', type:'div' });
			$('#wz_num').html(parseInt($('#wz_num').html()) + data.data.wz);
			renderRoleDuiwu();
		}else if(data.hr == -1){
			mui.toast("时间过短");
		}
		$("#mapbtn"+id).attr("disabled",false); 
	});
}

function mapboss(id){
	$.get('mapBoss',{'id':id},function(data){
		if (data.hr == 0 || data.hr == 1){
			if (data.hr == 1){
				rendermap(data.data.openId,data.data.guajiId);
			}
			var str = "<font color='yellow'>挑战成功！</font><br/><font color='white'>恭喜您获得以下奖励：</font><br/><font color='yellow'>指挥官经验："+data.data.zhgjy+"</font><br/><font color='green;'>舰娘经验："+data.data.jnjy+"</font><br/><font color='gold'>物资："+data.data.wz+"</font>";
			if(data.data.sps){
				for(var i =0 ;i<data.data.sps.length;i++){
					str = str + "<br/><font color='"+data.data.sps[i].color+"'>"+data.data.sps[i].name+"碎片："+data.data.sps[i].num+"个</font>";
				}
			}
			mui.toast(str,{ duration:'short', type:'div' });
			$('#wz_num').html(parseInt($('#wz_num').html()) + data.data.wz);
		} else if (data.hr == -1){
			mui.toast("胜败乃兵家常事，大虾请提升战力后再来！");
		}else{
			mui.toast("哥哥，没油了！");
		}
	});
}

function rolejinjie(){
	if (role.level % 10 != 0){
		mui.toast("整十级才能进阶！");
		return;
	}
	mui.confirm("确定要消耗"+allRoleSJ[role.level-1].needwz+"物资进行进阶？","提示",["取消","确定"],function(e){
		if (e.index == 1){
			mui.get('jinjie',function(data){
				if(data==0){
					var str = "升阶成功！<br/>您的军衔已提升至<font color='gold'>"+allJunxian[role.junxianId].label+"</font><br/>并开放等级上限：<font color='green'>"+(role.djsx+10)+"级</font><br/>舰队全属性提升5%";
					mui.toast(str,{ duration:'long', type:'div' });
					renderRoleDuiwu();
				}else if(data==1){
					mui.toast("物资不足");
				} else {
					mui.toast("等级或经验不足");
				}
			});
		}
	});
}

function renderRoleData(data){
	if(data){
		$('#rolename').html(data.rolename);
		$('#rolelevel').html("Lv."+data.level);
		$('#roletouxiang').attr('src',data.touxiang);
		$('#zs_num').html(data.zuanshi);
		$('#wz_num').html(data.wuzi);
		$('#roleid').html(data.id);
		$('#rolejunxian').html(allJunxian[data.junxianId-1].label);
		$('#roleshiyou').html(data.shiyou);
		$('#rolemofang').html(data.mofang);
		$('#rolekeyandian').html(data.keyandian);
		$('#roleexp').html(data.exp);
		$('#roleexprate').html((data.exp/allRoleSJ[data.level-1].needjy*100).toFixed(2)+'%');
		$('#rolesj').html(allRoleSJ[data.level-1].needjy);			
		$('#roleguajitime').html(formatDate(data.guajitime));
	}
}

function rendermap(index,id){
	var mapstr = "";
	for(var i=0;i<index;i++){
		mapstr = mapstr + "<li class='mui-table-view-cell' id='mapid_"+allMap[i].id+"'><div style='float: left;'><div><font color='#FFCC00'>"+allMap[i].place+"-"+allMap[i].point+" "+allMap[i].label+"</font><p class='myp mui-ellipsis'>推荐战力：<span>"+allMap[i].zdl+"</span></p></div></div><div style='float: right;'><button id='mapbtn"+allMap[i].id+"' type='button' class='mui-btn gjbtn' onclick='mapguaji("+allMap[i].id+")'>挂机</button> <button id='mapbtnboss"+allMap[i].id+"' type='button' class='mui-btn' onclick='mapboss("+allMap[i].id+")'>BOSS</button></div></li>";
	}
	$('#mapul').empty();
	$('#mapul').html(mapstr);
	if(id){
		$("#mapbtn"+id).addClass('mychujibutton');
		$("#mapbtn"+id).text("结算");
	}
	$('#roleguajijy').html(allMap[index-1].jnjy);
}

function gotomap(){
	$("#jianduiview").hide();
	$("#jianniangview").hide();
	$("#cangkuview").hide();
	$("#chujiview").show();
	$("#caidanview").hide();
	if(guajiid){
		location.href = "#mapid_"+guajiid;
		window.scrollBy(0, -95);
	}
}

function showcaidanview(){
	$("#jianduiview").hide();
	$("#jianniangview").hide();
	$("#cangkuview").hide();
	$("#chujiview").hide();
	$("#caidanview").show();
}

function jninfo_back(){
	$('#jiannianginfo').hide();
	$('#jianduisx').show();
	renderRoleDuiwu();
}

function jninfo_back2(){
	$('#jiannianginfo2').hide();
	$('#jnlistdiv').show();
}

function formatDate(time){
    var date = new Date(time);

    var year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    var newTime = year + '-' +
                month + '-' +
                day + ' ' +
                hour + ':' +
                min + ':' +
                sec;
    return newTime;         
}

function salesuipianexist(){
	mui.confirm("确定要出售所有已拥有舰娘的碎片换取魔方？","提示",["取消","确定"],function(e){
		if(e.index==1){
			$('#btn_exist').attr('disabled',true);
			mui.get("saleSuipianExist",function(data){
				if(data>0){
					var str = "<font color='gold'>获得魔方"+data+"个</font>";
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

function salesuipianfull(){
	mui.confirm("确定要出售所有满破舰娘的碎片换取魔方？","提示",["取消","确定"],function(e){
		if(e.index==1){
			$('#btn_full').attr('disabled',true);
			mui.get("saleSuipianFull",function(data){
				if(data>0){
					var str = "<font color='gold'>获得魔方"+data+"个</font>";
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

function qiandao(){
	mui.get('qiandao',function(data){
		if(data.hr==0){
			var str = "<font color='yellow'>签到成功！</font><br/><font color='white'>恭喜您获得以下奖励：</font><br/><font color='red'>钻石："+data.data.zuanshi+"个</font><br/><font color='green;'>魔方："+data.data.mofang+"个</font><br/><font color='white'>石油："+data.data.shiyou+"点</font>";
			if(data.data.sps){
				for(var i =0 ;i<data.data.sps.length;i++){
					str = str + "<br/><font color='"+data.data.sps[i].color+"'>"+data.data.sps[i].name+"碎片："+data.data.sps[i].num+"个</font>";
				}
			}
			$('#zs_num').html(parseInt($('#zs_num').html()) + data.data.zuanshi);
			mui.toast(str,{ duration:'long', type:'div' });
		}else{
			mui.toast("今日已签到！");
		}
	});
}

function jianzao(){
	mui.get("jianzao",function(data){
				if(data.hr==0){
					var str = "建造成功！<br/><font color='"+data.data.color+"'>"+data.data.name+"碎片："+data.data.num+"个</font>";				
					mui.toast(str,{ duration:'long', type:'div' });
					$('#wz_num').html(parseInt($('#wz_num').html()) - 1000);
				}else if(data.hr==1){
					mui.toast("物资不足!");
				}else if(data.hr==-1){
					mui.toast("魔方不足");
				}
			});
}
function gjjianzao(){
	mui.get("gjJianzao",function(data){
				if(data.hr==0){
					var str = "建造成功！<br/><font color='"+data.data.color+"'>"+data.data.name+"碎片："+data.data.num+"个</font>";				
					mui.toast(str,{ duration:'long', type:'div' });
					$('#wz_num').html(parseInt($('#wz_num').html()) - 3000);
				}else if(data.hr==1){
					mui.toast("物资不足!");
				}else if(data.hr==-1){
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
function getphbinfo(type){
	mui.get('phb',{'type':type},function(data){
		if(data){
			var str="";
			if(type == 'a'){
				for(var i = 0 ;i<data.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 Lv."+data[i].num+"</p></div></div></li>";
				}
			}else if(type == 'b'){
				for(var i = 0 ;i<data.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 战斗力："+data[i].num+"</p></div></div></li>";
				}
			}else if(type == 'c'){
				for(var i = 0 ;i<data.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data[i].tx+"'><div class='mui-media-body'><font color='gold'>"+data[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 图鉴数："+data[i].num+"个</p></div></div></li>";
				}
			}
			else if(type == 'd'){
				for(var i = 0 ;i<data.length;i++){
					str = str + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data[i].tx+"'><div class='mui-media-body'><font color='"+data[i].color+"'>"+data[i].name+"</font><p class='myp mui-ellipsis'>第"+(i+1)+"名 <font color='cyan'>战斗力："+data[i].num+"</font>  <font color='gold'>"+data[i].label+"</font></p></div></div></li>";
				}
			}
			$('#phbul').empty();
			$('#phbul').html(str);
		}
	});
}

function changetouxiang(){
	mui.get("changeTouxiang",{'id':$('#jninfohideid').val()},function(data){
		if(data){
			$('#roletouxiang').attr('src',data);
			mui.toast("设置成功！");
		}
	});
}
function zuanshisd(){
	mui.get("zuanshisd",function(data){
		if(data==0){
			loadRoleData(null);
			mui.toast("购买成功！");
		}else{
			mui.toast("钻石不足！");
		}
	});
}
function salemofang(){
	mui.get("saleMofang",function(data){
		if(data==0){
			$('#wz_num').html(parseInt($('#wz_num').html())+160000);
			mui.toast("兑换成功！");
		}else{
			mui.toast("魔方不足！");
		}
	});
}
function buymofang(){
	mui.get("buyMofang",function(data){
		if(data==0){
			$('#wz_num').html(parseInt($('#wz_num').html())-200000);
			mui.toast("购买成功！");
		}else{
			mui.toast("物资不足！");
		}
	});
}
function salezbl(){
	mui.get("saleZbl",function(data){
		if(data==0){
			mui.toast("兑换成功！");
		}else{
			mui.toast("紫布里不足！");
		}
	});
}
function dhzbl(){
	mui.get("dhZbl",function(data){
		if(data==0){
			mui.toast("兑换成功！");
		}else{
			mui.toast("魔方不足！");
		}
	});
}

function openkeyanview(){
	mui.get("keyanData",function(data){
		if(data){
			$('#gjdj').html(data.gjdj); 
			$('#fydj').html(data.fydj); 
			$('#xldj').html(data.xldj); 
			$('#sddj').html(data.sddj); 
			$('#bjdj').html(data.bjdj); 
			$('#dbdj').html(data.dbdj); 
			$('#gjky').html(allKeyanSJ[data.gjdj].needjy);
			$('#fyky').html(allKeyanSJ[data.fydj].needjy);
			$('#xlky').html(allKeyanSJ[data.xldj].needjy);
			$('#sdky').html(allKeyanSJ[data.sddj].needjy);
			$('#bjky').html(allKeyanSJ[data.bjdj].needjy);
			$('#dbky').html(allKeyanSJ[data.dbdj].needjy);
			$('#gjwz').html(allKeyanSJ[data.gjdj].needwz);
			$('#fywz').html(allKeyanSJ[data.fydj].needwz);
			$('#xlwz').html(allKeyanSJ[data.xldj].needwz);
			$('#sdwz').html(allKeyanSJ[data.sddj].needwz);
			$('#bjwz').html(allKeyanSJ[data.bjdj].needwz);
			$('#dbwz').html(allKeyanSJ[data.dbdj].needwz);
			$('#jianduisx').hide();
			$('#keyanview').show();
		}
	});	
}
function keyan_back(){
	$('#keyanview').hide();
	$('#jianduisx').show();
}
function keyansj(type){
	mui.get('keyanSj',{'type':type},function(r){
		mui.toast(r.msg);
		if(r.hr === 0) {
			openkeyanview();
			renderRoleDuiwu();
		}
	});
}
