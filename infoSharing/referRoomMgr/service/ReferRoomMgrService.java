package com.web.infoSharing.referRoomMgr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: ReferRoomMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 25. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		자료실 관리 Service
 * </pre>
 */
public interface ReferRoomMgrService extends CommonService
{
	// 자료실 리스트
	Map<String, Object> referRoomList(CommandMap commandMap);

	// 자료실 저장
	Map<String, String> saveReferRoom(CommandMap commandMap) throws Exception;
	
	// 자료실 첨부파일 저장
	void saveReferRoomAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 자료실 상세
	Map<String, String> getReferRoom(CommandMap commandMap);

	// 자료실 첨부파일 리스트
	Map<String, Object> popUpReferRoomAttachList(CommandMap commandMap);

	// 자료실 파일정보
	List<Map<String, Object>> selectFileInfo(CommandMap commandMap);

	// 자료실 첨부 삭제
	Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception;

	// 자료실 삭제
	Map<String, String> referRoomDelete(CommandMap commandMap) throws Exception;

	// 메인 자료실 리스트
	List<Map<String, Object>> getMainRefRoomList(CommandMap commandMap);
}
