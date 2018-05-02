package com.web.main.logIn.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.CodeUtil;
import com.web.common.util.SessionUtil;
import com.web.main.logIn.dao.LoginDAO;

/**
 * 
 * @파일명 : LoginServiceImpl.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 27.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     LoginServiceImpl
 *     </pre>
 */
@Service("loginService")
public class LoginServiceImpl extends CommonServiceImpl implements LoginService
{

	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;

	/**
	 * 
	 * @메소드명 : isUser
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *     유저체크
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public boolean isUser(CommandMap commandMap)
	{

		if (commandMap.get("loginID") != null)
		{
			commandMap.put("loginID", CodeUtil.decrypt(commandMap.get("loginID") + ""));
			SessionUtil.setObject("loginID", commandMap.get("loginID"));
		}

		// 로그인 유저를 DB로부터 취득
		Object loginUser = loginDAO.selectLogin(commandMap.getMap());
		if (loginUser != null)
		{
			// 유저정보가 있는경우 유저정보를 세션에 설정
			SessionUtil.setLoginUserObject(loginUser);
			return true;
		}
		// 유저정보가 없는경우
		return false;
	}

}
