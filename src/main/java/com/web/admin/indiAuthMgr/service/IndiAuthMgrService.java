package com.web.admin.indiAuthMgr.service;

import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: IndiAuthMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 12. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
public interface IndiAuthMgrService extends CommonService
{

	Map<String, Object> indiAuthMgrList(CommandMap commandMap) throws Exception;

	Map<String, String> indiAuthMgrSave(CommandMap commandMap) throws Exception;

}
