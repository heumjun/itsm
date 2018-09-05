package com.web.temp.tempSecondMgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.temp.tempSecondMgr.service.TempSecondMgrService;

@Controller
public class TempSecondMgrController extends CommonController
{
	/**
	 * TempService 등록
	 */
	@Resource(name = "temp.tempSecondMgr.tempSecondMgrService")
	private TempSecondMgrService tempSecondMgrService;

	@RequestMapping(value = "temp.tempSecondMgr.chartTest.do")
	public ModelAndView chartTest(CommandMap commandMap)  throws Exception
	{
		return getUserRoleAndLink(commandMap);
	}
	
	@RequestMapping(value = "temp.tempSecondMgr.getChartData.do")
	public @ResponseBody List<Map<String, Object>> getChartData(CommandMap commandMap) throws Exception
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> resultList = tempSecondMgrService.getChartData(commandMap);
		resultMap.put("resultList", resultList);
		
		return resultList;
	}

}
