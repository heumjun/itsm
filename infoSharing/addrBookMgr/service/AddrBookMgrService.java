package com.web.infoSharing.addrBookMgr.service;

import java.util.Map;

import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명	: AddrBookMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 04.
 * @작성자	: LEE BYOUNGJIN
 * @설명
 * <pre>
 * 	주소록관리(운영팀)	
 * </pre>
 */
public interface AddrBookMgrService extends CommonService
{

	View addrBookMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap);
}
