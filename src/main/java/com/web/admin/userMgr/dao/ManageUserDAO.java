package com.web.admin.userMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.constants.Constants;
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
@Repository("admin.userMgr.manageUserDAO")
public class ManageUserDAO extends CommonDAO
{
	
	public List<Map<String, Object>> getUserRankList(Map<String, Object> map)
	{
		return selectList("ManageUserList.getUserRankList", map);
	}
	
	/**
	 * 
	 * @메소드명	: manageUserListExcelExport
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	사용자관리 리스트 엑셀 출력	
	 * </pre>
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> manageUserListExcelExport(Map<String, Object> map)
	{
		return selectList("ManageUserList.list", map);
	}

	/**
	 * 
	 * @메소드명	: userAuthDuplicationCnt
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		유저의 개별권한 중복체크
	 * </pre>
	 * @param rowData
	 * @return
	 */
	public String userAuthDuplicationCnt(Map<String, Object> rowData)
	{
		int result = selectOne("SaveManageUser.userAuthDuplicationCnt", rowData);
		
		if (result > 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	/**
	 * 
	 * @메소드명	: userAuthInsert
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		유저생성시 개별권한 생성
	 * </pre>
	 * @param rowData
	 * @return
	 */
	public String userAuthInsert(Map<String, Object> rowData)
	{
		int insertResult = insert("SaveManageUser.userAuthInsert", rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}


}
