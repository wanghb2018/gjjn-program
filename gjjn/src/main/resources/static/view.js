var allRoleSJ = null;
var allMap = null;
var guajiid = null;
$(function(){
	$.get('allRoleSJ', function(data){
		allRoleSJ = data;
	});
	$.get('allGameMap', function(data){
		allMap = data;
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
					// showjnlist();
					// showsplist();
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
					// showjnlist();
					// showsplist();
				}else{
					$('#cjrole').show();
				}
			}else{
				$('#login').show();
			}	
		});
	}
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
	mui.post(domain+'changePassword/',
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

function renderRoleDuiwu(){
	renderRoleDuiwuWithData(null);
}

function renderRoleDuiwuWithData(data){
	if (data){
		renderRoleData(data);
	} else {
		$.get('getRoleInfo',function(data){
			renderRoleData(data);
		});
	}
	$.get('getDuiwuInfo',function(data){
		renderduiwu(data);
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
		$('#rolejunxian').html(data.junxianId);
		$('#roleshiyou').html(data.shiyou);
		$('#rolemofang').html(data.mofang);
		$('#rolekeyandian').html(data.keyandian);
		$('#roleexp').html(data.exp);
		$('#roleexprate').html((data.exp/allRoleSJ[data.level-1].needjy*100).toFixed(2)+'%');
		$('#rolesj').html(allRoleSJ[data.level-1].needjy);			
		$('#roleguajitime').html(formatDate(data.guajitime));
	}
}

function renderduiwu(dw){
	var liststr = "";
	for(var i=0;i<dw.myJns.length;i++){
		liststr = liststr + "<li class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+ dw.myJns[i].touxiang+"'><div class='mui-media-body'><font color='"+dw.myJns[i].color+"'>"+dw.myJns[i].name+"</font><p class='mui-ellipsis'><font color='white'>Lv."+dw.myJns[i].level+"</font><font style='margin-left: 5px;' color='white'>战斗力："+dw.myJns[i].zdl+"</font></p></div></div><div style='float: right;'><button type='button' class='imglibutton mui-btn' onclick='jnxiuxi("+dw.myJns[i].id+")'>休息</button> <button type='button' class='imglibutton mui-btn' onclick='jninfoview("+dw.myJns[i].id+")'>查看</button></div></li>";
	}
	$('#rolezdl').html(dw.zdl);
	$('#mydwlist').empty();
	$('#mydwlist').html(liststr);
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

function showjnlist(){
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

function showsplist(){
	mui.get('showSpList/',function(data){
		if(data){	
			$('#zbl').html(data[0].num);
			$('#jbl').html(data[1].num);
			var spliststr = "";
			for(var i=2;i<data.length;i++){
				spliststr = spliststr + "<li id='splistid"+data[i].id+"' class='mui-table-view-cell mui-media'><div style='float: left;'><img class='mui-media-object mui-pull-left' src='"+data[i].touxiang+"'><div class='mui-media-body'><font color='"+data[i].color+"'>"+data[i].name+"</font><p class='myp mui-ellipsis'>数量：<span>"+data[i].num+"</span>/<span>"+data[i].spnum+"</span></p></div></div><div style='float: right;'><div id='numbox"+data[i].id+"' class='mui-numbox' data-numbox-step='1' data-numbox-min='0' data-numbox-max='"+data[i].num+"'><button class='mui-btn mui-numbox-btn-minus' type='button'>-</button><input class='mui-numbox-input' type='number' /><button class='mui-btn mui-numbox-btn-plus' type='button'>+</button></div> <button type='button' class='imglibutton mui-btn' onclick='salesuipian("+data[i].id+","+data[i].pinji+")'>出售</button> <button type='button' class='imglibutton mui-btn' onclick='jnhecheng("+data[i].id+","+data[i].spnum+")'>合成</button></div></li>";
			}
			if(spliststr!=""){				
				$('#splistul').empty();
				$('#splistul').html(spliststr);
				mui('.mui-numbox').numbox();
			}
		}
	});
}

function jninfoview(id){
	mui.get('showJnInfo',{
		'jnId':id
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
			$('#jninfo_jy').html(data.jn.jingyan);
			$('#jninfo_sj').html(data.sj.needjy);
			$('#jninfo_lh').attr('src',data.jn.lihui);
			$('#jianduisx').hide();
			$('#jiannianginfo').show();
			$('#jninfohideid').val(data.jn.id);
		}
	});
}

function showjninfo2(id){
	mui.get('showJnInfo',{
		'jnId':id
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
			$('#jninfo_jy2').html(data.jn.jingyan);
			$('#jninfo_sj2').html(data.sj.needjy);
			$('#jninfo_lh2').attr('src',data.jn.lihui);
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

function gotomap(){
	if(guajiid){
		location.href = "#mapid_"+guajiid;
		window.scrollBy(0, -95);
	}
}

function jninfo_back(){
	$('#jiannianginfo').hide();
	$('#jianduisx').show();
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