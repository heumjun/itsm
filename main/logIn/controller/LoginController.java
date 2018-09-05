package com.web.main.logIn.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.admin.menuMgr.service.ManageMenuService;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.main.logIn.service.LoginService;

/**
 * 
 * @파일명 : LoginController.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 27.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		로그인 Controller
 *     </pre>
 */
@Controller
public class LoginController extends CommonController
{

	@Resource(name = "loginService")
	private LoginService		loginService;

	@Resource(name = "admin.menuMgr.manageMenuService")
	private ManageMenuService	manageMenuService;

	/**
	 * 
	 * @메소드명 : login
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		로그인 페이지 이동
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login.do")
	public ModelAndView login(CommandMap commandMap) throws Exception
	{
		return new ModelAndView(Constants.LOGIN_LINK);
	}

	/**
	 * 
	 * @메소드명 : loginCheck
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		로그인 체크 : 유저인경우 메인페이지 이동 유저가 아닌경우 에러 메시지
	 *     </pre>
	 * 
	 * @param commandMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loginCheck.do")
	public ModelAndView loginCheck(CommandMap commandMap, HttpServletRequest request) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		if (loginService.isUser(commandMap, request))
		{
			mav.addObject("loginId", commandMap.get("login_id"));
			mav.addObject(Constants.VIEW_PARENT_URL, Constants.MAINSCREEN_URL);
			
			mav.addAllObjects(commandMap.getMap());
			mav.addObject(Constants.VIEW_TREE_MENU_LIST, manageMenuService.getTreeMenuList(commandMap));

			// 팝업리스트 호출
			mav.addObject("popupList", loginService.popupList(commandMap));
			mav.setViewName(Constants.MAINSCREEN_URL + Constants.JSP_MAINFRAME);
		}
		else
		{
			mav.addObject("errMessage", "로그인 정보가 틀립니다. 확인후 다시 시도해 주세요.");
			mav.setViewName(Constants.LOGIN_LINK);
		}
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: updateUserInfo
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		개인정보 수정화면
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userInfo.do")
	public ModelAndView updateUserInfo(CommandMap commandMap) throws Exception
	{
		ModelAndView mav = new ModelAndView(Constants.LOGIN_UPDATE);
		mav.addAllObjects(commandMap.getMap());
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: userInfoSave
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		개인정보 수정 저장	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userInfoSave.do")
	public @ResponseBody Map<String, String> userInfoSave(CommandMap commandMap) throws Exception
	{
		return loginService.userInfoSave(commandMap);
	}
	
	
	
	/************************************************************************************************************/
	/**
	 * 
	 * @메소드명	: autoLogin
	 * @날짜		: 2018. 8. 23.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		BackDoor 화면 이동	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "autoLogin.do")
	public ModelAndView autoLogin(CommandMap commandMap) throws Exception
	{
		return new ModelAndView(Constants.AUTO_LOGIN_LINK);
	}
	
	/**
	 * 
	 * @메소드명	: autoLoginUserList
	 * @날짜		: 2018. 8. 23.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		Back Door 화면 로그인 사용자 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.logIn.AutoLoginUserList.do")
	public @ResponseBody Map<String, Object> autoLoginUserList(CommandMap commandMap) throws Exception
	{
		return loginService.autoLoginUserList(commandMap);
	}
	
	
	

}
