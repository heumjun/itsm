package com.web.dlm.weekResultMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: DlmWeekResultMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 3. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		주간실적 Service
 * </pre>
 */
public interface DlmWeekResultMgrService extends CommonService
{
	// 주간실적관리 리스트
	Map<String, Object> weekManageList(CommandMap commandMap);

	// 주간실적관리 저장
	Map<String, String> saveWeekManage(CommandMap commandMap) throws Exception;

	// 주간실적관리 신규등록화면
	List<Map<String, Object>> weekManageOnClick2(CommandMap commandMap);
	
	// 주간실적관리 상세화면
	List<Map<String, Object>> weekManageOnClick(CommandMap commandMap);

	// 주간실적현황 리스트
	Map<String, Object> weekInfoList(CommandMap commandMap);

	// 주간실적현황 상세화면
	List<Map<String, Object>> popUpWeekInfoDetail(CommandMap commandMap);
}
