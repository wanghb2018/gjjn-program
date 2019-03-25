package com.mochen.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mochen.model.Work;
import com.mochen.service.WorkService;

@Controller
public class QCController {
	@Autowired
	WorkService workService;
	
	@GetMapping("/qc")
	public String qcHome(Integer pwd) {
		if (pwd == null || pwd != 1314520) {
			return "webgame";
		}
		return "qc";
	}
	
	@PostMapping("/showWork")
	@ResponseBody
	public List<Work> showWork(){
		return workService.getByStatus(0);
	}
	
	@PostMapping("/showHistory")
	@ResponseBody
	public List<Work> showHistory(){
		return workService.getByStatus(1);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/saveWork")
	@ResponseBody
	public String saveWork(String data) {
		Map<String, Object> dataMap = (Map<String, Object>) JSON.parseArray(data).get(0);
		Work work = mapToWork(dataMap);
		if (dataMap.get("_state").equals("added")) {
			work.setStatus(0);
			workService.create(work);
			return "success";
		} else {
			work.setId((Integer)(dataMap.get("id")));
			workService.update(work);
		}
		return "success";
	}
	
	@GetMapping("/complete")
	@ResponseBody
	public String complete(Integer id) {
		Work work = workService.getById(id);
		work.setStatus(1);
		workService.update(work);
		return "success";
	}
	
	private Work mapToWork(Map<String, Object> map) {
		Work work = new Work();
		work.setCode((String) map.get("code"));
		work.setDesc((String) map.get("desc"));
		work.setMyBranch((String) map.get("myBranch"));
		work.setNowBranch((String) map.get("nowBranch"));
		work.setParentBranch((String) map.get("parentBranch"));
		work.setRemark((String) map.get("remark"));
		work.setVersion((String) map.get("version"));
		return work;
	}
}
