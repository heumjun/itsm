package com.web.temp.tempSecondMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

public interface TempSecondMgrService extends CommonService
{

	List<Map<String, Object>> getChartData(CommandMap commandMap) throws Exception;
	
}


