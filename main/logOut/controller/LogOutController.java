package com.web.main.logOut.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명		: LogOutController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 27. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		로그아웃
 * </pre>
 */
@Controller
public class LogOutController extends CommonController
{

	/**
	 * 
	 * @메소드명	: logout
	 * @날짜		: 2018. 3. 27.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		로그아웃
	 * </pre>
	 * @param commandMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "logout.do")
	public ModelAndView logout(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		request.getSession().setAttribute(Constants.SESSION_LOGIN_USER_OBJ, null);
		return new ModelAndView(Constants.LOGIN_LINK);
	}
}
