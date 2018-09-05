package com.web.lec.ipgoMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: IpgoMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 23. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Repository("lec.ipgoMgr.ipgoMgrDAO")
public class IpgoMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> ipgoMgrList(Map<String, Object> map)
	{
		return selectList("IpgoMgr.ipgoMgrList", map);
	}

	public List<Map<String, Object>> ipgoMgrList1(Map<String, Object> map)
	{
		return selectList("IpgoMgr.ipgoMgrList1", map);
	}
	
	public List<Map<String, Object>> ipgoMgrList2(Map<String, Object> map)
	{
		return selectList("IpgoMgr.ipgoMgrList2", map);
	}
	
	public List<Map<String, Object>> ipgoMgrList3(Map<String, Object> map)
	{
		return selectList("IpgoMgr.ipgoMgrList3", map);
	}
	
}
