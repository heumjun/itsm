package com.web.dlm.geuntaeMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: DlmGeuntaeMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 19. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
public interface DlmGeuntaeMgrService extends CommonService
{

	List<Map<String, Object>> monthViewList(CommandMap commandMap);

	Map<String, Object> gtmBaseInfoList(CommandMap commandMap);

	Map<String, Object> popUpGtmReqInfoList(CommandMap commandMap);

	Map<String, Object> popUpGtmBonusInfoList(CommandMap commandMap);

	Map<String, Object> gtmBonusInfoList(CommandMap commandMap);

	List<Map<String, Object>> getUserSelectBoxList(CommandMap commandMap);

	Map<String, String> saveGtmBonusInfo(CommandMap commandMap) throws Exception;

	Map<String, Object> gtmReqInfoList(CommandMap commandMap);

	Map<String, String> saveGtmReqInfo(CommandMap commandMap) throws Exception;

}
