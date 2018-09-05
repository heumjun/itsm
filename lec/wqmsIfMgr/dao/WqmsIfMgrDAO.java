package com.web.lec.wqmsIfMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: WqmsIfMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		WQMS I/F DAO
 * </pre>
 */
@Repository("lec.wqmsIfMgr.wqmsIfMgrDAO")
public class WqmsIfMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> wqmsIfMgrList(Map<String, Object> map)
	{
		return selectList("WqmsIfMgr.wqmsIfMgrList", map);
	}
	
	public List<Map<String, Object>> wqmsIfMgrExcelExport(Map<String, Object> map)
	{
		return selectList("WqmsIfMgr.wqmsIfMgrExcelExport", map);
	}

}
