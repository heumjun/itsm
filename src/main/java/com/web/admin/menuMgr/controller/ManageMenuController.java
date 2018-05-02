package com.web.admin.menuMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.admin.menuMgr.service.ManageMenuService;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명		: ManageMenuController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	메뉴 관리	
 * </pre>
 */
@Controller
public class ManageMenuController extends CommonController
{

	@Resource(name = "admin.menuMgr.manageMenuService")
	private ManageMenuService manageMenuService;

	/**
	 * 
	 * @메소드명	: linkSelectedMenu
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	메뉴관리 페이지 이동	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.menuMgr.menu.do")
	public ModelAndView menu(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}

	/**
	 * 
	 * @메소드명	: menuTree
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	메뉴관리 좌측 메뉴 트리 구성	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.menuMgr.menuTree.do")
	public ModelAndView menuTree(CommandMap commandMap)
	{
		ModelAndView mv = getUserRoleAndLink(commandMap);
		mv.addObject(Constants.VIEW_TREE_MENU_LIST, manageMenuService.getAdminTreeMenuList(commandMap));
		return mv;
	}

	/**
	 * 
	 * @메소드명	: manageMenu
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	메뉴관리 우측 페이지 호출
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.menuMgr.manageMenu.do")
	public ModelAndView manageMenu(CommandMap commandMap)
	{
		ModelAndView mv = getUserRoleAndLink(commandMap);
		mv.addObject(Constants.MENU_UP_MENU_ID_KEY, commandMap.get(Constants.MENU_UP_MENU_ID_KEY));
		return mv;
	}

	/**
	 * 
	 * @메소드명	: getGridListAction
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	메뉴관리 우측 리스트	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.menuMgr.ManageMenuList.do")
	public @ResponseBody Map<String, Object> manageMenuList(CommandMap commandMap)
	{
		return manageMenuService.getGridList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: saveManageMenu
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	메뉴관리 우측 저장	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.menuMgr.SaveManageMenu.do")
	public @ResponseBody Map<String, String> saveManageMenu(CommandMap commandMap) throws Exception
	{
		return manageMenuService.saveManageMenu(commandMap);
	}

	/**
	 * 
	 * @메소드명 : getMenuRole
	 * @날짜 : 2018. 3. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		입력된 메뉴의 권한정보를 취득한다.
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "getMenuRole.do")
	public @ResponseBody Map<String, Object> getMenuRole(CommandMap commandMap)
	{
		return manageMenuService.getUserRole(commandMap);
	}

}
