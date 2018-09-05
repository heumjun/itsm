package com.web.infoSharing.boardMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

public interface BoardMgrService extends CommonService
{
	// 게시판 리스트
	Map<String, Object> boardMgrList(CommandMap commandMap);

	// 게시판 저장
	Map<String, String> boardMgrSave(CommandMap commandMap) throws Exception;

	// 댓글 리스트
	List<Map<String, Object>> getReplyList(CommandMap commandMap);

	// 게시판 상세 뷰
	List<Map<String, Object>> getBoardView(CommandMap commandMap);

	// 댓글 저장
	Map<String, String> boardReplySave(CommandMap commandMap) throws Exception;

	// 댓글 삭제
	Map<String, String> boardReplyDelete(CommandMap commandMap) throws Exception;

	// 게시글 조회 수 업데이트
	void updateBoardHitCount(CommandMap commandMap) throws Exception;

	// 게시글 삭제
	Map<String, String> boardDelete(CommandMap commandMap) throws Exception;

	List<Map<String, Object>> getMainBoardList(CommandMap commandMap);
}
