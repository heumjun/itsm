package com.web.lec.wqmsIfMgr.service;

import java.util.Map;

import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: WqmsIfMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		WQMS I/F Service
 * </pre>
 */
public interface WqmsIfMgrService extends CommonService
{

	Map<String, Object> wqmsIfMgrList(CommandMap commandMap) throws Exception;
	
	// 엑셀 출력
	View wqmsIfMgrExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception;

}
