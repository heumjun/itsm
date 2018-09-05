package com.web.temp.tempSecondMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

@Repository("temp.tempSecondMgr.tempSecondMgrDAO")
public class TempSecondMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> getChartData(Map<String, Object> map)
	{
		return selectList("TempSecondMgr.getChartData", map);
	}

}
