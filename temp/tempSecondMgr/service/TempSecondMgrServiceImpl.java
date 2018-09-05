package com.web.temp.tempSecondMgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonServiceImpl;
import com.web.temp.tempSecondMgr.dao.TempSecondMgrDAO;

@Service("temp.tempSecondMgr.tempSecondMgrService")
public class TempSecondMgrServiceImpl extends CommonServiceImpl implements TempSecondMgrService
{
	/**
	 * tempDAO 등록
	 */
	@Resource(name = "temp.tempSecondMgr.tempSecondMgrDAO")
	private TempSecondMgrDAO tempSecondMgrDAO;

	@Override
	public List<Map<String, Object>> getChartData(CommandMap commandMap) throws Exception
	{
		return tempSecondMgrDAO.getChartData(commandMap.getMap());
	}

}
