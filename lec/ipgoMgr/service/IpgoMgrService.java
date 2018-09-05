package com.web.lec.ipgoMgr.service;

import java.util.Map;

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
public interface IpgoMgrService extends CommonService
{

	Map<String, Object> ipgoMgrList(CommandMap commandMap) throws Exception;

	Map<String, Object> ipgoMgrList1(CommandMap commandMap) throws Exception;

	Map<String, Object> ipgoMgrList2(CommandMap commandMap) throws Exception;

	Map<String, Object> ipgoMgrList3(CommandMap commandMap) throws Exception;
}
