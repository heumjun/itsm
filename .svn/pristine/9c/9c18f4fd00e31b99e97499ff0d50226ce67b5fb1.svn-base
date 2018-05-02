package com.web.admin.userMgr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.web.admin.userMgr.service.ManageUserService;
import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명		: ManageUserController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	사용자관리	
 * </pre>
 */
@Controller
public class ManageUserController extends CommonController
{

	@Resource(name = "admin.userMgr.manageUserService")
	private ManageUserService	manageUserService;

	/**
	 * 
	 * @메소드명	: linkSelectedMenu
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	사용자관리 페이지 이동	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.userMgr.manageUser.do")
	public ModelAndView manageUser(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	@RequestMapping(value = "admin.userMgr.getUserRankList.do")
	public @ResponseBody List<Map<String, Object>> getUserRankList(CommandMap commandMap) throws Exception {
		return manageUserService.getUserRankList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: manageUserList
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	사용자관리 리스트	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.userMgr.ManageUserList.do")
	public @ResponseBody Map<String, Object> manageUserList(CommandMap commandMap)
	{
		return manageUserService.getGridList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: saveGridListAction
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	사용자관리 저장	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.userMgr.SaveManageUser.do")
	public @ResponseBody Map<String, String> saveManageUser(CommandMap commandMap) throws Exception
	{
		return manageUserService.saveManageUser(commandMap);
		//return manageUserService.saveGridList(commandMap);
	}

	/**
	 * 
	 * @메소드명	: manageUserListExcelExport
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *	사용자관리 리스트 엑셀 출력	
	 * </pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.userMgr.manageUserListExcelExport.do")
	public View manageUserListExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
	{
		return manageUserService.manageUserListExcelExport(commandMap, modelMap);
	}

}
