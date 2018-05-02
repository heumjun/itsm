package com.web.dlm.geuntaeMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: DlmGeuntaeMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 19. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Repository("dlm.geuntaeMgr.dlmGeuntaeMgrDAO")
public class DlmGeuntaeMgrDAO extends CommonDAO
{
	public List<Map<String, Object>> monthViewList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.monthViewList", map);
	}
	
	public List<Map<String, Object>> gtmBaseInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmBaseInfoList", map);
	}

	public List<Map<String, Object>> popUpGtmReqInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.popUpGtmReqInfoList", map);
	}

	public List<Map<String, Object>> popUpGtmBonusInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.popUpGtmBonusInfoList", map);
	}
	
	public List<Map<String, Object>> gtmBonusInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmBonusInfoList", map);
	}
	
	public List<Map<String, Object>> getUserSelectBoxList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.getUserSelectBoxList", map);
	}
	
	public List<Map<String, Object>> gtmReqInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmReqInfoList", map);
	}
	
	public int saveGtmReqInfo(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.saveGtmReqInfo", map);
	}

	public int gtmReqSmsInsert(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmReqSmsInsert", map);
	}

	public int gtmReqSmsInsertInfo(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmReqSmsInsertInfo", map);
	}

}
