package com.web.infoSharing.boardMgr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.common.util.JsonUtil;
import com.web.infoSharing.boardMgr.service.BoardMgrService;

/**
 * 
 * @파일명		: boardMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 6. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Controller
public class BoardMgrController extends CommonController
{
	
	@Resource(name = "infoSharing.boardMgr.boardMgrService")
	private BoardMgrService	 boardMgrService;

	/**
	 * 
	 * @메소드명	: boardMgr
	 * @날짜		: 2018. 4. 6.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgr.do")
	public ModelAndView boardMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	@RequestMapping(value = "infoSharing.boardMgr.boardMgrList.do", produces="text/plain;charset=UTF-8")
	public @ResponseBody String manageUserList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		int pageSize = Integer.parseInt((String) commandMap.get("length"));
		int curPageNo = Integer.parseInt((String) commandMap.get("start"));
		commandMap.put("length", pageSize);
		commandMap.put("start", curPageNo);
		
		//json string으로 parsing
		List<Map<String, Object>> listData = boardMgrService.boardMgrList(commandMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap.put("data", listData);
		resultMap.put("iTotalRecords", 10);
		resultMap.put("iTotalDisplayRecords", 10);
		resultMap.put("aaData", listData);
		
		String aa = JsonUtil.getJsonStringFromMap(resultMap).toJSONString();
		System.out.println(aa);
		return aa;
        
	}
	
}
