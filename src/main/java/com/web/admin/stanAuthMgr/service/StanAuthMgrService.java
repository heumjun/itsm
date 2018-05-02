package com.web.admin.stanAuthMgr.service;

import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: StanAuthMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 17. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		표준권한 정보 서비스
 * </pre>
 */
public interface StanAuthMgrService extends CommonService
{

	// 표준권한 정보 리스트
	Map<String, Object> stanAuthMgrList(CommandMap commandMap) throws Exception;
	
	// 표준권한에 따른 상세정보 리스트
	Map<String, Object> stanAuthMgrMenuList(CommandMap commandMap);

	// 표준권한정보 저장
	Map<String, String> saveStanAuthMgrMenu(CommandMap commandMap) throws Exception;

}
