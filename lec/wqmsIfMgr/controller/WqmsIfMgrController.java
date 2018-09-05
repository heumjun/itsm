package com.web.lec.wqmsIfMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.lec.wqmsIfMgr.service.WqmsIfMgrService;

/**
 * 
 * @파일명		: WqmsIfMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		WQMS I/F 모니터링 Controller
 * </pre>
 */
@Controller
public class WqmsIfMgrController extends CommonController
{

	@Resource(name = "lec.wqmsIfMgr.wqmsIfMgrService")
	private WqmsIfMgrService wqmsIfMgrService;
	
	/**
	 * 
	 * @메소드명	: wqmsIfMgr
	 * @날짜		: 2018. 8. 20.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		WQMS I/F 모니터링 페이지
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "lec.wqmsIfMgr.wqmsIfMgr.do")
	public ModelAndView wqmsIfMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: wqmsIfMgrList
	 * @날짜		: 2018. 8. 20.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		WQMS I/F 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "lec.wqmsIfMgr.wqmsIfMgrList.do")
	public @ResponseBody Map<String, Object> wqmsIfMgrList(CommandMap commandMap) throws Exception
	{
		return wqmsIfMgrService.wqmsIfMgrList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: manageUserListExcelExport
	 * @날짜		: 2018. 8. 20.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		WQMS I/F 엑셀 출력
	 * </pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "lec.wqmsIfMgr.wqmsIfMgrExcelExport.do")
	public View manageUserListExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
	{
		return wqmsIfMgrService.wqmsIfMgrExcelExport(commandMap, modelMap);
	}
	
}
