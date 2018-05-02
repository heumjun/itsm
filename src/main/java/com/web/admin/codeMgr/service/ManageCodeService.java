package com.web.admin.codeMgr.service;

import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명 : ManageCodeService.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     공통코드관리
 *     </pre>
 */
public interface ManageCodeService extends CommonService
{

	Map<String, String> saveManageCode(CommandMap commandMap) throws Exception;

}
