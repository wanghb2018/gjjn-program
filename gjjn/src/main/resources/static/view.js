var allRoleSJ = null;
var allMap = null;
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
 		// 	$('#login_username').val(username);
 		// 	$('#login_password').val(password);
			// $('#login').show();
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
					renderRoleData(data.data);
					// getRoleData();
					// showjnlist();
					// showsplist();
					// showalert();	
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
					renderRoleData(data.data);
					// getRoleData();
					// showjnlist();
					// showsplist();
					// showalert();
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
		$('#roleguajitime').html(data.guajitime);
	}
	
}