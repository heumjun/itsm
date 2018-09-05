package com.web.main.passwdInit.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.main.passwdInit.service.PasswdInitService;

/**
 * 
 * @파일명		: PasswdInitController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 3. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		비밀번호 초기화 Controller
 * </pre>
 */
@Controller
public class PasswdInitController extends CommonController
{

	@Resource(name = "main.passwdInit.passwdInitService")
	private PasswdInitService passwdInitService;
	
	/**
	 * 
	 * @메소드명	: passwdInit
	 * @날짜		: 2018. 4. 3.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	비밀번호 초기화 페이지 이동	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.passwdInit.passwdInit.do")
	public ModelAndView passwdInit(CommandMap commandMap) throws Exception
	{
		return new ModelAndView(Constants.FORGOTPASSWORD_LINK);
	}
	
	/**
	 * 
	 * @메소드명	: passwdInitAction
	 * @날짜		: 2018. 4. 3.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		비밀번호 초기화 & 메일발송
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.passwdInit.passwdInitAction.do")
	public @ResponseBody Map<String, String> passwdInitAction(CommandMap commandMap) throws Exception
	{
		return passwdInitService.passwdInitAction(commandMap);
	}
	

}
