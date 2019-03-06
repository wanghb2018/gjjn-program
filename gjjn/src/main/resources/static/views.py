

def rolejinjie(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	role = req.user.role_user.first()
	if role:
		if role.junxian_id == 25:
			context['result'] = 'full'
			return HttpResponse(json.dumps(context), content_type="application/json")
		if role.level%10!=0:
			context['result'] = 'level'
		else:
			need = Rolesj.objects.filter(id=role.level).first()
			if role.exp < need.needjy:
				context['result'] = 'exp'
			elif role.wuzi < need.needwz:
				context['result'] = 'wuzi'
			else:
				role.exp -= need.needjy
				role.wuzi -= need.needwz
				role.djsx = role.level+10
				role.level += 1
				role.junxian_id += 1
				role.save()
				context['result'] = 'success'
				context['junxian'] = role.junxian.label
				context['djsx'] = role.djsx
	return HttpResponse(json.dumps(context), content_type="application/json")

def keyansj(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	role = req.user.role_user.first()
	t = req.POST.get('type')
	roleky = Keyan.objects.filter(role=role).first()
	if t == 'gj':
		lv = roleky.gjdj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.gjdj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'fy':
		lv = roleky.fydj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.fydj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'xl':
		lv = roleky.xldj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.xldj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'sd':
		lv = roleky.sddj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.sddj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'bj':
		lv = roleky.bjdj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.bjdj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'db':
		lv = roleky.dbdj
		if lv == 50:
			context['result'] = 'limit'
			return HttpResponse(json.dumps(context), content_type="application/json")
		kysj = Keyansj.objects.filter(id = lv+1).first()
		if role.keyandian < kysj.needjy or role.wuzi<kysj.needwz:
			context['result'] = 'notenough'
		else:
			role.keyandian -= kysj.needjy
			role.wuzi -= kysj.needwz
			roleky.dbdj += 1
			roleky.save()
			role.save()
			updatedwshuxing(role)
			context['result'] = 'success'
			context['keyandian'] = role.keyandian
			context['wuzi'] = role.wuzi
		return HttpResponse(json.dumps(context), content_type="application/json")

def getlosejn(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	role = req.user.role_user.first()
	sql = "select * from work_jianniang where id not in (select jianniang_id from work_myjianniang where role_id = "+str(role.id)+" ) and id>2 ORDER BY pinji desc"
	a = Jianniang.objects.raw(sql)
	jns = []
	for i in a:
		d = {}
		d['name'] = i.name
		d['color'] = i.color
		jns.append(d)
	context['jns'] = jns
	return HttpResponse(json.dumps(context), content_type="application/json")


def getkeyandata(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	role = req.user.role_user.first()
	keyan = Keyan.objects.filter(role=role).first()
	context['gjdj'] = keyan.gjdj
	context['fydj'] = keyan.fydj
	context['xldj'] = keyan.xldj
	context['sddj'] = keyan.sddj
	context['bjdj'] = keyan.bjdj
	context['dbdj'] = keyan.dbdj
	keyansj = Keyansj.objects.all()
	context['gjky'] = keyansj[context['gjdj']].needjy
	context['fyky'] = keyansj[context['fydj']].needjy
	context['xlky'] = keyansj[context['xldj']].needjy
	context['sdky'] = keyansj[context['sddj']].needjy
	context['bjky'] = keyansj[context['bjdj']].needjy
	context['dbky'] = keyansj[context['dbdj']].needjy
	context['gjwz'] = keyansj[context['gjdj']].needwz
	context['fywz'] = keyansj[context['fydj']].needwz
	context['xlwz'] = keyansj[context['xldj']].needwz
	context['sdwz'] = keyansj[context['sddj']].needwz
	context['bjwz'] = keyansj[context['bjdj']].needwz
	context['dbwz'] = keyansj[context['dbdj']].needwz
	return HttpResponse(json.dumps(context), content_type="application/json")
	
def qiandao(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	role = req.user.role_user.first()
	if role:
		now = datetime.datetime.now().replace(tzinfo=utc)
		qdsj = role.qdsj.replace(tzinfo=utc)
		key = now.day-qdsj.day
		if now.month != qdsj.month:
			key = 1
		if key != 0:
			role.qdts = role.qdts+1
			role.qdsj = now
			role.shiyou = role.shiyou + 500 + role.qdts*10
			role.mofang = role.mofang + 100 + role.qdts*3
			role.zuanshi = role.zuanshi + 50 + role.qdts
			role.save()
			context['result'] = 'success'
			context['obtsp'] = addsuipian(role)
			context['shiyou'] = role.shiyou
			context['mofang'] = role.mofang
			context['zuanshi'] = role.zuanshi
			context['getzs'] = 50 + role.qdts
			context['getmf'] = 100 + role.qdts*3
			context['getsy'] = 500 + role.qdts*10
		else:
			context['result'] = 'refause'
	return HttpResponse(json.dumps(context), content_type="application/json")

def jnhecheng(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	if role:
		sp = Suipian.objects.filter(role=role).filter(id=id).first()
		jn = Myjianniang.objects.filter(role=role).filter(jianniang=sp.jnsp).first()
		if jn:
			context['result'] = 'exist'
		else:
			if sp:
				neednum = sp.jnsp.spnum
				if sp.num<neednum:
					bl = ''
					if sp.jnsp.pinji<3:
						bl = Suipian.objects.filter(role=role).filter(jnsp__id=1).first()
					else:
						bl = Suipian.objects.filter(role=role).filter(jnsp__id=2).first()
					if bl:
						if bl.num+sp.num>=neednum:
							bl.num = bl.num+sp.num-neednum
							bl.save()
							sp.num=0
							sp.save()
							addjn(role.id,sp.jnsp)
							context['result'] = 'success'
				else:
					sp.num = sp.num-neednum
					sp.save()
					addjn(role.id,sp.jnsp)
					context['result'] = 'success'
	return HttpResponse(json.dumps(context), content_type="application/json")

def changetouxiang(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	myjn = Myjianniang.objects.filter(role=role).filter(id=id).first()
	if role and myjn:
		role.touxiang = myjn.jianniang.touxiang
		role.save()
		context['result'] = 'success'
		context['tx'] = role.touxiang
	return HttpResponse(json.dumps(context), content_type="application/json")


def updatedwshuxing(role):
	dw = Duiwu.objects.filter(role=role).first()
	zdl = 0
	if dw.one:		
		caljnshuzhi(role,dw.one)
		zdl += dw.one.zdl
	if dw.two:		
		caljnshuzhi(role,dw.two)
		zdl += dw.two.zdl
	if dw.three:
		caljnshuzhi(role,dw.three)
		zdl += dw.three.zdl
	if dw.four:
		caljnshuzhi(role,dw.four)
		zdl += dw.four.zdl
	if dw.five:
		caljnshuzhi(role,dw.five)
		zdl += dw.five.zdl
	if dw.six:
		caljnshuzhi(role,dw.six)
		zdl += dw.six.zdl
	dw.totalzdl = zdl
	dw.save()


def jnshengxing(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	myjn = Myjianniang.objects.filter(role=role).filter(id=id).first()
	if myjn:
		if myjn.mystar-myjn.jianniang.star >= 3 and myjn.jianniang.pinji != 6:
			context['result'] = 'max'
			return HttpResponse(json.dumps(context), content_type="application/json")
		sp = Suipian.objects.filter(role=role).filter(jnsp=myjn.jianniang).first()
		needwz = Jnshengxing.objects.filter(id=myjn.mystar).first().needwz
		neednum = myjn.jianniang.spnum
		if sp:
			if sp.num>= neednum:
				if role.wuzi >= needwz:
					role.wuzi -= needwz
					sp.num -= neednum
					myjn.mystar += 1
					caljnshuzhi(role,myjn)
					sp.save()
					role.save()
					context['result'] = 'success'
					return HttpResponse(json.dumps(context), content_type="application/json")
				else:
					context['result'] = 'defaultwz'
					context['wuzi'] = needwz - role.wuzi
					return HttpResponse(json.dumps(context), content_type="application/json")
			else:
				bl = ''
				if myjn.jianniang.pinji<3:
					bl = Suipian.objects.filter(role=role).filter(jnsp__id = 1).first()
				if myjn.jianniang.pinji >=3:
					bl = Suipian.objects.filter(role=role).filter(jnsp__id = 2).first()
				if bl:
					if sp.num + bl.num >= neednum:
						if role.wuzi >= needwz:
							role.wuzi -= needwz
							bl.num = bl.num + sp.num - neednum
							sp.num = 0
							myjn.mystar += 1
							caljnshuzhi(role,myjn)
							sp.save()
							bl.save()
							role.save()
							context['result'] = 'success'
							return HttpResponse(json.dumps(context), content_type="application/json")
						else:
							context['result'] = 'defaultwz'
							context['wuzi'] = needwz-role.wuzi
							return HttpResponse(json.dumps(context), content_type="application/json")
					else:
						context['result'] = 'defaultsp'
						context['suipian'] = neednum - bl.num-sp.num
						return HttpResponse(json.dumps(context), content_type="application/json")
				else:
					context['result'] = 'defaultsp'
					context['suipian'] = neednum -sp.num
					return HttpResponse(json.dumps(context), content_type="application/json")
		else:
			bl = ''
			if myjn.jianniang.pinji<3:
				bl = Suipian.objects.filter(role=role).filter(jnsp__id = 1).first()
			if myjn.jianniang.pinji >=3:
				bl = Suipian.objects.filter(role=role).filter(jnsp__id = 2).first()
			if bl:
				if bl.num >= neednum:
					if role.wuzi >= needwz:
						role.wuzi -= needwz
						bl.num = bl.num - neednum
						myjn.mystar += 1
						caljnshuzhi(role,myjn)
						bl.save()
						role.save()
						context['result'] = 'success'
						return HttpResponse(json.dumps(context), content_type="application/json")
					else:
						context['result'] = 'defaultwz'
						context['wuzi'] = needwz-role.wuzi
						return HttpResponse(json.dumps(context), content_type="application/json")
				else:
					context['result'] = 'defaultsp'
					context['suipian'] = neednum - bl.num
					return HttpResponse(json.dumps(context), content_type="application/json")
			else:
				context['result'] = 'defaultsp'
				context['suipian'] = neednum 
				return HttpResponse(json.dumps(context), content_type="application/json")
	return HttpResponse(json.dumps(context), content_type="application/json")

def salesuipian(req):
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	num=int(req.POST.get('num'))
	sp = Suipian.objects.filter(id=id).first()
	if num >=0:
		if sp.num>=num:
			sp.num -= num
			mfnum = (sp.jnsp.pinji+1)*num
			role.mofang += mfnum
			sp.save()
			role.save()
			context['result'] = 'success'
			context['num'] = mfnum
	else:
		mfnum = (sp.jnsp.pinji+1)*sp.num
		context['result'] = 'success'
		context['num'] = mfnum
		context['mofang'] = role.mofang
		sp.num = 0
		role.mofang += mfnum
		sp.save()
		role.save()
	return HttpResponse(json.dumps(context), content_type="application/json")

def jianzao(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.mofang<100:
			context['result'] ='mofang'
		elif role.wuzi<1000:
			context['result'] ='wuzi'
		else:
			num =50-int(random.randint(0,900)**0.5)
			rate = random.randint(1,100)		
			if rate <=30:
				jn = Jianniang.objects.filter(pinji=0).order_by('?')[:1][0]
			elif rate <=65:
				jn = Jianniang.objects.filter(pinji=1).order_by('?')[:1][0]
			elif rate <=85:
				jn = Jianniang.objects.filter(pinji=2).order_by('?')[:1][0]
			else:
				jn = Jianniang.objects.filter(pinji__gte=3).order_by('?')[:1][0]			
			role.mofang -= 100
			role.wuzi -= 1000
			role.save()
			sp = Suipian.objects.filter(role=role).filter(jnsp=jn).first()
			if sp:
				sp.num += num
				sp.save()
			else:
				Suipian(role=role,jnsp=jn,num=num).save()
			context['result'] = 'success'
			context['name'] = jn.name
			context['color'] = jn.color
			context['num'] = num
			context['rolemofang'] = role.mofang
			context['rolewuzi'] = role.wuzi
	return HttpResponse(json.dumps(context), content_type="application/json")

def gjjianzao(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.mofang<268:
			context['result'] ='mofang'
		elif role.wuzi<3000:
			context['result'] ='wuzi'
		else:
			num =100-int(random.randint(0,4900)**0.5)
			rate = random.randint(1,1000)
			if rate <=630:
				jn = Jianniang.objects.filter(pinji=2).order_by('?')[:1][0]
			elif rate <=830:
				jn = Jianniang.objects.filter(pinji=3).order_by('?')[:1][0]
			elif rate <=930:
				jn = Jianniang.objects.filter(pinji=4).order_by('?')[:1][0]
			elif rate <=980:
				jn = Jianniang.objects.filter(pinji=5).order_by('?')[:1][0]
			else:
				jn = Jianniang.objects.filter(pinji=6).order_by('?')[:1][0]
			role.mofang -= 268
			role.wuzi -= 3000
			role.save()
			sp = Suipian.objects.filter(role=role).filter(jnsp=jn).first()
			if sp:
				sp.num += num
				sp.save()
			else:
				Suipian(role=role,jnsp=jn,num=num).save()
			context['result'] = 'success'
			context['name'] = jn.name
			context['color'] = jn.color
			context['num'] = num
			context['rolemofang'] = role.mofang
			context['rolewuzi'] = role.wuzi
	return HttpResponse(json.dumps(context), content_type="application/json")

def xianshijianzao(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.mofang<268:
			context['result'] ='mofang'
		elif role.wuzi<5888:
			context['result'] ='wuzi'
		else:
			role.mofang -= 268
			role.wuzi -= 5888
			role.save()
			rate = random.randint(1,1000)
			num =100-int(random.randint(0,4900)**0.5)
			if rate <= 70:
				jnid = random.randint(909,937)
				jn = Jianniang.objects.filter(id=jnid).first()
			elif rate <=630:
				jn = Jianniang.objects.filter(pinji=2).order_by('?')[:1][0]
			elif rate <=830:
				jn = Jianniang.objects.filter(pinji=3).order_by('?')[:1][0]
			elif rate <=930:
				jn = Jianniang.objects.filter(pinji=4).order_by('?')[:1][0]
			elif rate <=980:
				jn = Jianniang.objects.filter(pinji=5).order_by('?')[:1][0]
			else:
				jn = Jianniang.objects.filter(pinji=6).order_by('?')[:1][0]
			sp = Suipian.objects.filter(role=role).filter(jnsp=jn).first()
			if sp:
				sp.num += num
				sp.save()
			else:
				Suipian(role=role,jnsp=jn,num=num).save()
			context['result'] = 'success'
			context['name'] = jn.name
			context['color'] = jn.color
			context['num'] = num
			context['rolemofang'] = role.mofang
			context['rolewuzi'] = role.wuzi
	return HttpResponse(json.dumps(context), content_type="application/json")

def zuanshisd(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.zuanshi<50:
			return HttpResponse(json.dumps(context), content_type="application/json")
		else:
			role.zuanshi -= 50
			role.shiyou += 500
			role.save()
			context['result'] = 'success'
			context['zuanshi'] = role.zuanshi
			context['shiyou'] = role.shiyou
	return HttpResponse(json.dumps(context), content_type="application/json")

def salemofang(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.mofang<1000:
			return HttpResponse(json.dumps(context), content_type="application/json")
		else:
			role.mofang -= 1000
			role.wuzi += 160000
			role.save()
			context['result'] = 'success'
			context['mofang'] = role.mofang
			context['wuzi'] = role.wuzi
	return HttpResponse(json.dumps(context), content_type="application/json")

def buymofang(req):
	context={}
	role = req.user.role_user.first()
	if role:
		if role.wuzi<200000:
			return HttpResponse(json.dumps(context), content_type="application/json")
		else:
			role.mofang += 1000
			role.wuzi -= 200000
			role.save()
			context['result'] = 'success'
			context['mofang'] = role.mofang
			context['wuzi'] = role.wuzi
	return HttpResponse(json.dumps(context), content_type="application/json")

def salezbl(req):
	context={}
	role = req.user.role_user.first()
	zbl = Suipian.objects.filter(role=role).filter(jnsp__id=1).first()
	if zbl:
		if zbl.num < 20:
			context['result'] = 'less'
		else:
			zbl.num = zbl.num-20
			jbl = Suipian.objects.filter(role=role).filter(jnsp__id=2).first()
			if jbl:
				jbl.num = jbl.num + 1
				jbl.save()
			else:
				Suipian(role_id=role.id,jnsp_id=2,num=1).save()
			zbl.save()
			context['result'] = 'success'
	else:
		context['result'] = 'less'
	return HttpResponse(json.dumps(context), content_type="application/json")



def getAllJN(req):
	insertlog(req,sys._getframe().f_code.co_name)
	jn = Jianniang.objects.all()
	return render(req,'alljn.html',{'jn':jn})


#随机增加碎片
def addsuipian(role):
	obtlist = []
	count = 3
	if count < int(role.qdts/4):
		count = int(role.qdts/4)
	if count > 16:
		count = 16
	jns = Jianniang.objects.order_by('?')[:count]
	for item in jns:
		num = random.randint(1,role.qdts)
		sp = Suipian.objects.filter(role_id=role.id).filter(jnsp=item).first()
		if sp:
			sp.num=sp.num+num
			sp.save()
		else:
			Suipian(role_id=role.id,jnsp=item,num=num).save()
		d= {}
		d['name'] = item.name
		d['color'] = item.color
		d['num'] = num
		obtlist.append(d)
	return obtlist

#通过列表增加碎片
def addsuipianbylist(roleid,jns):
	obtlist = []
	for item in jns:
		sp = Suipian.objects.filter(role_id=roleid).filter(jnsp=item).first()
		if sp:
			sp.num=sp.num+1
			sp.save()
		else:
			Suipian(role_id=roleid,jnsp=item,num=1).save()
		d= {}
		d['name'] = item.name
		d['color'] = item.color
		d['num'] = 1
		obtlist.append(d)
	return obtlist

	
def getphbinfo(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	t = req.POST.get('type')
	if t == 'a':
		djphb = []
		rolelist = Role.objects.order_by('-level','-exp')[:50]
		for item in rolelist:
			d={}
			d['name'] = item.rolename
			d['tx'] = item.touxiang
			d['lv'] = item.level
			djphb.append(d)
		context['djphb'] = djphb
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'b':
		zlphb = []
		dwlist = Duiwu.objects.order_by('-totalzdl')[:50]
		for item in dwlist:
			role = item.role
			d = {}
			d['name'] = role.rolename
			d['tx'] = role.touxiang
			d['zdl'] = item.totalzdl
			zlphb.append(d)
		context['zlphb'] = zlphb
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'c':
		tjphb = []
		a = Role.objects.raw('select work_role.id,rolename,touxiang,count(*) c from work_myjianniang,work_role where work_myjianniang.role_id = work_role.id GROUP BY role_id ORDER BY count(*) desc limit 50')
		for i in a:
			d = {}
			d['name'] = i.rolename
			d['tx'] = i.touxiang
			d['tjs'] = i.c
			tjphb.append(d)
		context['tjphb'] = tjphb
		return HttpResponse(json.dumps(context), content_type="application/json")
	if t == 'd':
		jnphb = []
		dwlist = Myjianniang.objects.exclude(role_id = 21).order_by('-zdl')[:100]
		for item in dwlist:
			role = item.role
			jn = item.jianniang
			d = {}
			d['name'] = jn.name
			d['tx'] = jn.touxiang
			d['zdl'] = item.zdl
			d['color'] = jn.color
			d['rolename'] = role.rolename
			jnphb.append(d)
		context['jnphb'] = jnphb
		return HttpResponse(json.dumps(context), content_type="application/json")

def salesuipianexist(req):
	context={}
	role = req.user.role_user.first()
	num = 0
	sps = Suipian.objects.filter(num__gt = 0).filter(jnsp__id__gt=2).filter(role=role).filter(jnsp__pinji__lt=6)
	if len(sps) == 0:
		context['num'] = num
		return HttpResponse(json.dumps(context), content_type="application/json")
	myjns = Myjianniang.objects.filter(role=role).filter(jianniang__pinji__lt=6)
	jns = []
	for myjn in myjns:
		jns.append(myjn.jianniang)
	for sp in sps:
		if sp.jnsp in jns:
			num = num+sp.num*(sp.jnsp.pinji+1)
			sp.num = 0
			sp.save()
	role.mofang += num
	role.save()
	context['num'] = num
	return HttpResponse(json.dumps(context), content_type="application/json")

def salesuipianfull(req):
	context={}
	role = req.user.role_user.first()
	num = 0
	sps = Suipian.objects.filter(num__gt = 0).filter(jnsp__id__gt=2).filter(role=role).filter(jnsp__pinji__lt=6)
	if len(sps) == 0:
		context['num'] = num
		return HttpResponse(json.dumps(context), content_type="application/json")
	myjns = Myjianniang.objects.filter(role=role).filter(jianniang__pinji__lt=6)
	jns = []
	for myjn in myjns:
		if myjn.mystar - myjn.jianniang.star == 3:
			jns.append(myjn.jianniang)
	for sp in sps:
		if sp.jnsp in jns:
			num = num+sp.num*(sp.jnsp.pinji+1)
			sp.num = 0
			sp.save()
	role.mofang += num
	role.save()
	context['num'] = num
	return HttpResponse(json.dumps(context), content_type="application/json")