package com.web.infoSharing.addrBookMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명	: AddrBookMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 04.
 * @작성자	: LEE BYOUNGJIN
 * @설명
 * <pre>
 * 	주소록관리(운영팀)	
 * </pre>
 */
@Repository("infoSharing.addrBookMgr.addrBookMgrDAO")
public class AddrBookMgrDAO extends CommonDAO
{
	/**
	 * 
	 * @메소드명	: addrBookMgrListExcelExport
	 * @날짜		: 2018. 5. 04.
	 * @작성자	: LEE BYOUNGJIN
	 * @설명
	 * <pre>
	 *	주소록관리(운영팀) 리스트 엑셀 출력	
	 * </pre>
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> addrBookMgrListExcelExport(Map<String, Object> map)
	{
		return selectList("addrBookMgrList.list", map);
	}	
}
