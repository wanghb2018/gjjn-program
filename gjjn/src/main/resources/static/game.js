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

