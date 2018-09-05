package com.web.lec.ipgoMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.lec.ipgoMgr.service.IpgoMgrService;

/**
 * 
 * @파일명		: IpgoMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 23. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Controller
public class IpgoMgrController extends CommonController
{

	@Resource(name = "lec.ipgoMgr.ipgoMgrService")
	private IpgoMgrService ipgoMgrService;
	
	/**
	 * 
	 * @메소드명	: wqmsIfMgr
	 * @날짜		: 2018. 8. 23.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "lec.ipgoMgr.ipgoMgr.do")
	public ModelAndView ipgoMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	@RequestMapping(value = "lec.ipgoMgr.ipgoMgrList.do")
	public @ResponseBody Map<String, Object> ipgoMgrList(CommandMap commandMap) throws Exception
	{
		return ipgoMgrService.ipgoMgrList(commandMap);
	}
	
	@RequestMapping(value = "lec.ipgoMgr.ipgoMgrList1.do")
	public @ResponseBody Map<String, Object> ipgoMgrList1(CommandMap commandMap) throws Exception
	{
		return ipgoMgrService.ipgoMgrList1(commandMap);
	}
	
	@RequestMapping(value = "lec.ipgoMgr.ipgoMgrList2.do")
	public @ResponseBody Map<String, Object> ipgoMgrList2(CommandMap commandMap) throws Exception
	{
		return ipgoMgrService.ipgoMgrList2(commandMap);
	}
	
	@RequestMapping(value = "lec.ipgoMgr.ipgoMgrList3.do")
	public @ResponseBody Map<String, Object> ipgoMgrList3(CommandMap commandMap) throws Exception
	{
		return ipgoMgrService.ipgoMgrList3(commandMap);
	}
	
	
}
