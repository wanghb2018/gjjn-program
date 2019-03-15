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

def getAllJN(req):
	insertlog(req,sys._getframe().f_code.co_name)
	jn = Jianniang.objects.all()
	return render(req,'alljn.html',{'jn':jn})

	