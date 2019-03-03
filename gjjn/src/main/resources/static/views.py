

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

def shangzhen(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	id = req.POST.get('id')
	role = req.user.role_user.first()
	myjn = Myjianniang.objects.filter(role_id=role.id).filter(id=id).first()
	if myjn and role:
		jxrate = role.junxian.powerrate/100+1
		keyan = Keyan.objects.filter(role=role).first()
		gjrate = 1.1**keyan.gjdj
		fyrate = 1.1**keyan.fydj
		xlrate = 1.1**keyan.xldj
		sdrate = 1.1**keyan.sddj
		bjrate = 2*keyan.bjdj
		dbrate = 2*keyan.dbdj
		jn = myjn.jianniang
		lvrate = 1+(0.11+0.02*jn.pinji)*(myjn.level-1)
		myjn.mygongji = int(jn.gongji*lvrate*jxrate*gjrate)
		myjn.myfangyu = int(jn.fangyu*lvrate*jxrate*fyrate)
		myjn.myxueliang = int(jn.xueliang*lvrate*jxrate*xlrate)
		myjn.mysudu = int(jn.sudu*lvrate*jxrate*sdrate)
		myjn.mybaoji = jn.baoji+bjrate
		myjn.myduobi = jn.duobi+dbrate
		myjn.zdl = caljnzdl(myjn.mygongji,myjn.myfangyu,myjn.myxueliang,myjn.mysudu,myjn.mybaoji,myjn.myduobi,myjn.mystar-jn.star)
		myjn.save()
		dw = Duiwu.objects.filter(role=role).first()
		if dw.count == 6:
			context['result'] = 'full'
		elif dw.one == myjn or dw.two == myjn or dw.three == myjn or dw.four == myjn or dw.five == myjn or dw.six == myjn:
			context['result'] = 'exist'
		else:
			zdl = 0
			myjn.iswar=1
			myjn.save()
			if dw.one is None:
				if myjn:
					dw.one = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl +dw.one.zdl
			if dw.two is None:
				if myjn:
					dw.two = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl + dw.two.zdl
			if dw.three is None:
				if myjn:
					dw.three = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl + dw.three.zdl
			if dw.four is None:
				if myjn:
					dw.four = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl + dw.four.zdl
			if dw.five is None:
				if myjn:
					dw.five = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl + dw.five.zdl
			if dw.six is None:
				if myjn:
					dw.six = myjn
					zdl = zdl + myjn.zdl
					myjn = ''
			else:
				zdl = zdl + dw.six.zdl
			dw.totalzdl =zdl
			dw.count += 1
			dw.save()
			context['result'] = 'success'			
	return HttpResponse(json.dumps(context), content_type="application/json")

def jnxiuxi(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context= {}
	id = req.POST.get('id')
	role = req.user.role_user.first()
	myjn = Myjianniang.objects.filter(role_id=role.id).filter(id=id).first()
	if myjn and role:
		flag = 1
		dw = Duiwu.objects.filter(role=role).first()
		if myjn == dw.one:
			flag = 0
			dw.one = None
		elif myjn == dw.two:
			flag = 0
			dw.two = None
		elif myjn == dw.three:
			flag = 0
			dw.three = None
		elif myjn == dw.four:
			flag = 0
			dw.four = None
		elif myjn == dw.five:
			flag = 0
			dw.five = None
		elif myjn == dw.six:
			flag = 0
			dw.six = None
		if flag==0:
			dw.totalzdl = dw.totalzdl - myjn.zdl
			myjn.iswar = 0
			myjn.save()
			dw.count -= 1
			dw.save()
			context['result'] = 'success'
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

def mapguaji(req):
	insertlog(req,sys._getframe().f_code.co_name)
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	now = datetime.datetime.now().replace(tzinfo=utc)
	if role:
		if role.guajimap:	
			gjtime = role.guajitime.replace(tzinfo=utc)
			count = int((now - gjtime).total_seconds()/5)
			#if count > 34560:
				#count = 34560
			if count == 0:
				context['result'] = 'short'
			else:
				obtsplist = []
				c = int(count/300)
				if random.randint(1,300) == 250:
					c += 1				
				if c > 0:	
					allJns = role.guajimap.jianniang_set.all()
					n = len(allJns) - 1 
					d = {}
					for i in range(c):
						r = random.randint(0,n)
						if allJns[r] not in d:
							d[allJns[r]] = 1
						else:
							d[allJns[r]] = d[allJns[r]] + 1
					for key,value in d.items():
						sp = Suipian.objects.filter(role=role).filter(jnsp=key).first()
						if sp:
							sp.num += 1
							sp.save()
						else:
							Suipian(role=role,jnsp=jn,num=1).save()
						t = {}
						t['name'] = key.name
						t['color'] = key.color
						t['num'] = value
						obtsplist.append(t)		
				wz = role.openmap.wz*count
				jy= role.openmap.jnjy*count
				dwaddjy(role,jy)
				role.wuzi +=wz
				context['result'] = 'success'
				context['wz'] = wz
				context['jy'] = jy
				context['sec'] = count*5
				context['obtsp'] = obtsplist
		else:
			context['result'] = 'start'
		role.guajimap_id = id
		role.guajitime = now
		role.save()
		context['guajitime'] = datetime.datetime.strftime(now,'%Y-%m-%d %H:%M:%S')
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

# def dwaddjy(role,jy):
# 	dw = Duiwu.objects.filter(role=role).first()
# 	jnsj = Jnsj.objects.all()
# 	zdl = 0
# 	if dw.one:
# 		dw.one.jingyan = dw.one.jingyan + jy
# 		flag = 0
# 		while dw.one.jingyan>= jnsj[dw.one.level-1].needjy and dw.one.level < 100:			
# 			dw.one.jingyan-= jnsj[dw.one.level-1].needjy
# 			dw.one.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.one)
# 		dw.one.save()
# 		zdl += dw.one.zdl
# 	if dw.two:
# 		dw.two.jingyan = dw.two.jingyan + jy
# 		flag = 0
# 		while dw.two.jingyan>= jnsj[dw.two.level-1].needjy and dw.two.level < 100:			
# 			dw.two.jingyan-= jnsj[dw.two.level-1].needjy
# 			dw.two.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.two)
# 		dw.two.save()
# 		zdl += dw.two.zdl
# 	if dw.three:
# 		dw.three.jingyan = dw.three.jingyan + jy
# 		flag = 0
# 		while dw.three.jingyan>= jnsj[dw.three.level-1].needjy and dw.three.level < 100:			
# 			dw.three.jingyan-= jnsj[dw.three.level-1].needjy
# 			dw.three.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.three)
# 		dw.three.save()
# 		zdl += dw.three.zdl
# 	if dw.four:
# 		dw.four.jingyan = dw.four.jingyan + jy
# 		flag = 0
# 		while dw.four.jingyan>= jnsj[dw.four.level-1].needjy and dw.four.level < 100:			
# 			dw.four.jingyan-= jnsj[dw.four.level-1].needjy
# 			dw.four.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.four)
# 		dw.four.save()
# 		zdl += dw.four.zdl
# 	if dw.five:
# 		dw.five.jingyan = dw.five.jingyan + jy
# 		flag = 0
# 		while dw.five.jingyan>= jnsj[dw.five.level-1].needjy and dw.five.level < 100:			
# 			dw.five.jingyan-= jnsj[dw.five.level-1].needjy
# 			dw.five.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.five)
# 		dw.five.save()
# 		zdl += dw.five.zdl
# 	if dw.six:
# 		dw.six.jingyan = dw.six.jingyan + jy
# 		flag = 0
# 		while dw.six.jingyan>= jnsj[dw.six.level-1].needjy and dw.six.level < 100:			
# 			dw.six.jingyan-= jnsj[dw.six.level-1].needjy
# 			dw.six.level += 1
# 			flag = 1
# 		if flag == 1:
# 			caljnshuzhi(role,dw.six)
# 		dw.six.save()
# 		zdl += dw.six.zdl
# 	dw.totalzdl = zdl
# 	dw.save()
def dwaddjy(role,jy):
	dw = Duiwu.objects.filter(role=role).first()
	jnsj = Jnsj.objects.all()
	jnlist = []
	if dw.one:
		jnlist.append(dw.one)
	if dw.two:
		jnlist.append(dw.two)
	if dw.three:
		jnlist.append(dw.three)
	if dw.four:
		jnlist.append(dw.four)
	if dw.five:
		jnlist.append(dw.five)
	if dw.six:
		jnlist.append(dw.six)
	total = 0
	zdl = 0
	for jn in jnlist:
		if jn.level == 100:
			total = total + int(jy*0.1)
		else:
			jn.jingyan += jy
			flag = 0
			while jn.jingyan >= jnsj[jn.level-1].needjy and jn.level < 100:
				jn.jingyan -= jnsj[jn.level-1].needjy
				jn.level += 1
				flag = 1
			if flag == 1:
				caljnshuzhi(role,jn)
			else:
				jn.save()
		zdl += jn.zdl
	for jn in jnlist:
		if jn.level < 100:
			jn.jingyan += total
			flag = 0
			while jn.jingyan >= jnsj[jn.level-1].needjy and jn.level < 100:
				jn.jingyan -= jnsj[jn.level-1].needjy
				jn.level += 1
				flag = 1
			if flag == 1:
				zdl -= jn.zdl
				caljnshuzhi(role,jn)
				zdl += jn.zdl
			else:
				jn.save()
			break
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

def mapboss(req):
	context={}
	role = req.user.role_user.first()
	id = req.POST.get('id')
	bossmap = Map.objects.filter(id=id).first()
	context['openid'] = role.openmap_id
	context['guajijy'] = role.openmap.jnjy
	if role.guajimap:
		context['guajiid'] = role.guajimap.id
	else:
		context['guajiid'] = ''
	if bossmap.id > role.openmap_id+1:
		context['result'] = 'refause'
	else:
		if bossmap.id < role.openmap_id+1:
			context['status'] = '0'			
		dw = Duiwu.objects.filter(role=role).first()
		if role.shiyou >= dw.count:
			if dw.totalzdl*random.randint(9,11)/10 > bossmap.zdl:
				context['result'] = 'success'
				if bossmap.id == role.openmap_id:
					context['status'] = '1'
				wz = bossmap.wz*10*dw.count
				jnjy = bossmap.jnjy*10*dw.count
				jns = bossmap.jianniang_set.order_by('?')[:dw.count]
				obtlist = addsuipianbylist(role.id,jns)
				zhjy = bossmap.zhgjy * dw.count
				context['obtsp'] = obtlist
				context['wz'] = wz
				context['jnjy'] = jnjy
				context['zhgjy'] = zhjy
				context['kyd'] = 1
				role.wuzi += wz
				role.exp += zhjy
				if role.openmap_id == bossmap.id and bossmap.id!=48:
					role.openmap_id = bossmap.id + 1
				context['openid'] = role.openmap_id
				context['guajijy'] = role.openmap.jnjy
				role.keyandian += dw.count
				role.shiyou -= dw.count
				if role.level<role.djsx:
					needjy = Rolesj.objects.filter(id=role.level).first().needjy
					if role.exp>=needjy:
						role.exp -= needjy
						role.level += 1
				role.save()
				context['zhgdengji'] = role.level
				context['zhgwuzi'] = role.wuzi
				dwaddjy(role,jnjy)
			else:
				context['result'] = 'default'

		else:
			context['result'] = 'none'
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