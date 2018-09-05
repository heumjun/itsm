package com.web.main.passwdInit.service;

import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: PasswdInitService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 3. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		비밀번호 초기화 Service
 * </pre>
 */
public interface PasswdInitService extends CommonService
{

	Map<String, String> passwdInitAction(CommandMap commandMap) throws Exception;
	
}
