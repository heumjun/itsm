package com.web.main.logIn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명 : LoginService.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 27.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     LoginService
 *     </pre>
 */
public interface LoginService extends CommonService
{

	/**
	 * 
	 * @메소드명 : isUser
	 * @날짜 : 2018. 3. 5.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		유저 체크
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	boolean isUser(CommandMap commandMap, HttpServletRequest request);

	List<Map<String, Object>> popupList(CommandMap commandMap);

	Map<String, String> userInfoSave(CommandMap commandMap) throws Exception;

	// Back Door 화면 로그인 사용자 리스트
	Map<String, Object> autoLoginUserList(CommandMap commandMap) throws Exception;

}
