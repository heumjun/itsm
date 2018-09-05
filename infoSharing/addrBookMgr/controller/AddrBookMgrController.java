package com.web.infoSharing.addrBookMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.web.infoSharing.addrBookMgr.service.AddrBookMgrService;
import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명	: AddrBookMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 04.
 * @작성자	: LEE BYOUNGJIN
 * @설명
 * <pre>
 * 	주소록관리(운영팀)
 * </pre>
 */
@Controller
public class AddrBookMgrController extends CommonController
{
	@Resource(name = "infoSharing.addrBookMgr.addrBookMgrService")
	private AddrBookMgrService	addrBookMgrService;

	/**
	 * 
	 * @메소드명	: addrBookMgr
	 * @날짜		: 2018. 5. 04.
	 * @작성자	: LEE BYOUNGJIN
	 * @설명
	 * <pre>
	 *	주소록관리(운영팀) 페이지 이동	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.addrBookMgr.addrBookMgr.do")
	public ModelAndView addrBookMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: addrBookMgrList
	 * @날짜		: 2018. 5. 04.
	 * @작성자	: LEE BYOUNGJIN
	 * @설명
	 * <pre>
	 *	주소록관리(운영팀) 리스트	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.addrBookMgr.addrBookMgrList.do")
	public @ResponseBody Map<String, Object> addrBookMgrList(CommandMap commandMap)
	{
		return addrBookMgrService.getGridList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: addrBookMgrListExcelExport
	 * @날짜		: 2018. 5. 04.
	 * @작성자	: LEE BYOUNGJIN
	 * @설명
	 * <pre>
	 *	주소록관리(운영팀) 리스트 엑셀 출력	
	 * </pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.addrBookMgr.addrBookMgrListExcelExport.do")
	public View addrBookMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
	{
		return addrBookMgrService.addrBookMgrListExcelExport(commandMap, modelMap);
	}	
}
