package com.web.infoSharing.boardMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: ManageUserDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	사용자 관리	
 * </pre>
 */
@Repository("infoSharing.boardMgr.boardMgrDAO")
public class BoardMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> boardMgrList(Map<String, Object> map)
	{
		return selectList("manageUserList.list1", map);
	}
	
}
