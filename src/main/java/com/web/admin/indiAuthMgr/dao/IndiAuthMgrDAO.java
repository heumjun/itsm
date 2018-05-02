package com.web.admin.indiAuthMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: IndiAuthMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 12. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Repository("admin.indiAuthMgr.indiAuthMgrDAO")
public class IndiAuthMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> indiAuthMgrList(Map<String, Object> map)
	{
		return selectList("IndiAuthMgr.indiAuthMgrList", map);
	}

	public int indiAuthMgrSave(Map<String, Object> pkgParam)
	{
		return update("IndiAuthMgr.indiAuthMgrSave", pkgParam);
	}
	
}
