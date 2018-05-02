package com.web.admin.stanAuthMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.admin.stanAuthMgr.service.StanAuthMgrService;
import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명		: StanAuthMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 17. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		표준권한 정보 컨트롤러
 * </pre>
 */
@Controller
public class StanAuthMgrController extends CommonController
{

	@Resource(name = "admin.stanAuthMgr.stanAuthMgrService")
	private StanAuthMgrService stanAuthMgrService;

	/**
	 * 
	 * @메소드명	: stanAuthMgr
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		표준권한정보 페이지 이동
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.stanAuthMgr.stanAuthMgr.do")
	public ModelAndView stanAuthMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}

	/**
	 * 
	 * @메소드명	: stanAuthMgrList
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		표준권한정보 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.stanAuthMgr.stanAuthMgrList.do")
	public @ResponseBody Map<String, Object> stanAuthMgrList(CommandMap commandMap) throws Exception
	{
		return stanAuthMgrService.stanAuthMgrList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: stanAuthMgrMenuList
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		표준권한정보에 따른 상세정보 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.stanAuthMgr.stanAuthMgrMenuList.do")
	public @ResponseBody Map<String, Object> stanAuthMgrMenuList(CommandMap commandMap) throws Exception
	{
		return stanAuthMgrService.stanAuthMgrMenuList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: saveStanAuthMgrMenu
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		표준권한 정보 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.stanAuthMgr.saveStanAuthMgrMenu.do")
	public @ResponseBody Map<String, String> saveStanAuthMgrMenu(CommandMap commandMap) throws Exception
	{
		return stanAuthMgrService.saveStanAuthMgrMenu(commandMap);
	}

}
