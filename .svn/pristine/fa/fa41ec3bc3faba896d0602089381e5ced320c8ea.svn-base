package com.web.main.memberJoin.service;

import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: MemberJoinService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 27. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		회원 가입 Service
 * </pre>
 */
public interface MemberJoinService extends CommonService
{
	// 회원 아이디 체크
	Map<String, String> userIdCheck(CommandMap commandMap) throws Exception;
	
	// 회원 등록
	Map<String, String> memberJoinRegiste(CommandMap commandMap) throws Exception;

	Map<String, Object> getSuggestion(CommandMap commandMap);
}
