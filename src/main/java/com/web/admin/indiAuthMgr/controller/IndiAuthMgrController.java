package com.web.admin.indiAuthMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.admin.indiAuthMgr.service.IndiAuthMgrService;
import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명		: IndiAuthMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 12. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Controller
public class IndiAuthMgrController extends CommonController
{
	
	@Resource(name = "admin.indiAuthMgr.indiAuthMgrService")
	private IndiAuthMgrService	indiAuthMgrService;

	/**
	 * 
	 * @메소드명	: indiAuthMgr
	 * @날짜		: 2018. 4. 12.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.indiAuthMgr.indiAuthMgr.do")
	public ModelAndView indiAuthMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: indiAuthSelectBoxDataList
	 * @날짜		: 2018. 4. 12.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.indiAuthMgr.indiAuthSelectBoxDataList.do")
	public @ResponseBody String indiAuthSelectBoxDataList(CommandMap commandMap) throws Exception {
		return indiAuthMgrService.getSelectBoxDataList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: indiAuthMgrUserList
	 * @날짜		: 2018. 4. 12.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.indiAuthMgr.indiAuthMgrList.do")
	public @ResponseBody Map<String, Object> indiAuthMgrList(CommandMap commandMap) throws Exception
	{
		return indiAuthMgrService.indiAuthMgrList(commandMap);
	}
	
	
	/**
	 * 
	 * @메소드명	: indiAuthMgrUserSave
	 * @날짜		: 2018. 4. 12.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.indiAuthMgr.indiAuthMgrSave.do")
	public @ResponseBody Map<String, String> indiAuthMgrSave(CommandMap commandMap) throws Exception
	{
		return indiAuthMgrService.indiAuthMgrSave(commandMap);
	}
	
}
