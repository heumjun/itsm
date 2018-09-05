package com.web.dlm.weekResultMgr.dao;

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
@Repository("dlm.weekResultMgr.dlmWeekResultMgrDAO")
public class DlmWeekResultMgrDAO extends CommonDAO
{
	public List<Map<String, Object>> weekManageList(Map<String, Object> map)
	{
		return selectList("WeekResultMgr.weekManageList", map);
	}

	public int saveWeekManageInsert(Map<String, Object> map)
	{
		return insert("WeekResultMgr.saveWeekManageInsert", map);
	}
	
	public int saveWeekManageUpdate(Map<String, Object> map)
	{
		return update("WeekResultMgr.saveWeekManageUpdate", map);
	}
	
	public List<Map<String, Object>> weekManageOnClick(Map<String, Object> map)
	{
		return selectList("WeekResultMgr.weekManageOnClick", map);
	}

	public List<Map<String, Object>> weekManageOnClick2(Map<String, Object> map)
	{
		return selectList("WeekResultMgr.weekManageOnClick2", map);
	}
	
	public List<Map<String, Object>> weekInfoList(Map<String, Object> map)
	{
		return selectList("WeekResultMgr.weekInfoList", map);
	}

	public List<Map<String, Object>> weekInfoDetail(Map<String, Object> map)
	{
		return selectList("WeekResultMgr.weekInfoDetail", map);
	}

}
