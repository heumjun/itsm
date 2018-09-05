package com.web.temp.tempMgr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

 /** 
 * @파일명		: TempMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 9. 
 * @작성자		: bhLee
 * @설명
 * <pre>
 * 		템플릿(First)
 * </pre>
 */
public interface TempMgrService extends CommonService
{
	// 템플릿 리스트
	Map<String, Object> tempList(CommandMap commandMap);

	// 템플릿 저장
	Map<String, String> saveTempMgr(CommandMap commandMap) throws Exception;
	
	// 템플릿 첨부파일 저장
	void saveTempMgrAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 템플릿 상세
	Map<String, String> getTempMgr(CommandMap commandMap);
	
	// 템플릿 첨부파일 리스트
	Map<String, Object> popUpTempMgrAttachList(CommandMap commandMap);

	// 템플릿 파일정보
	List<Map<String, Object>> selectFileInfo(CommandMap commandMap);
	
	// 템플릿 첨부 삭제
	Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception;
	
	// 템플릿 삭제
	Map<String, String> tempMgrDelete(CommandMap commandMap) throws Exception;
	
	// 템플릿 일괄등록
	void popUpTempInfoExcelImportAction(CommandMap commandMap, HttpServletResponse response) throws Exception;
	
	// 템플릿 엑셀저장
	View tempMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap);
	
	// 템플릿 메일전송
	Map<String, String> tempMgrSendEmail(CommandMap commandMap) throws Exception;
	
	
}


