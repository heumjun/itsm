package com.web.admin.codeMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.admin.codeMgr.service.ManageCodeService;
import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;

/**
 * 
 * @파일명 : ManageCodeController.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     공통코드관리
 *     </pre>
 */
@Controller
public class ManageCodeController extends CommonController
{

	@Resource(name = "admin.codeMgr.manageCodeService")
	private ManageCodeService manageCodeService;

	/**
	 * 
	 * @메소드명 : manageCode
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *	공통코드관리 페이지 이동
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.codeMgr.manageCode.do")
	public ModelAndView manageCode(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}

	/**
	 * 
	 * @메소드명 : manageMasterCodeList
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *	마스터코드 리스트
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.codeMgr.ManageMasterCodeList.do")
	public @ResponseBody Map<String, Object> manageMasterCodeList(CommandMap commandMap)
	{
		return manageCodeService.getGridList(commandMap);
	}

	/**
	 * 
	 * @메소드명 : manageDetailCodeList
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *	상세코드 리스트
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "admin.codeMgr.ManageDetailCodeList.do")
	public @ResponseBody Map<String, Object> manageDetailCodeList(CommandMap commandMap)
	{
		return manageCodeService.getGridList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: saveManageCode
	 * @날짜		: 2018. 4. 17.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		공통코드 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin.codeMgr.SaveManageCode.do")
	public @ResponseBody Map<String, String> saveManageCode(CommandMap commandMap) throws Exception
	{
		return manageCodeService.saveManageCode(commandMap);
	}

}
