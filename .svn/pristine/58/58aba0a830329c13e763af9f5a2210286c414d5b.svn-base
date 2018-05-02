package com.web.admin.userMgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: ManageUserService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	사용자관리	
 * </pre>
 */
public interface ManageUserService extends CommonService
{

	Map<String, String> saveManageUser(CommandMap commandMap) throws Exception;

	// 엑셀 출력
	View manageUserListExcelExport(CommandMap commandMap, Map<String, Object> modelMap);

	List<Map<String, Object>> getUserRankList(CommandMap commandMap);

}
