package com.web.infoSharing.boardMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: BoardMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Repository("infoSharing.boardMgr.boardMgrDAO")
public class BoardMgrDAO extends CommonDAO
{
	public List<Map<String, Object>> boardMgrList(Map<String, Object> map)
	{
		return selectList("BoardMgr.boardList", map);
	}

	public int boardMgrSave(Map<String, Object> map)
	{
		return update("BoardMgr.boardMgrSave", map);
	}

	public int boardMgrInsert(Map<String, Object> map)
	{
		return insert("BoardMgr.boardMgrInsert", map);
	}

	public int boardMgrUpdate(Map<String, Object> map)
	{
		return update("BoardMgr.boardMgrUpdate", map);
	}
	
	public void updateBoardHitCount(Map<String, Object> map)
	{
		update("BoardMgr.updateBoardHitCount", map);
	}

	public List<Map<String, Object>> getReplyList(Map<String, Object> map)
	{
		return selectList("BoardReplyMgr.getReplyList", map);
	}

	public List<Map<String, Object>> getBoardView(Map<String, Object> map)
	{
		return selectList("BoardMgr.getBoardView", map);
	}

	public int boardReplySave(Map<String, Object> map)
	{
		return insert("BoardReplyMgr.boardReplySave", map);
	}

	public int boardReplyDelete(Map<String, Object> map)
	{
		return delete("BoardReplyMgr.boardReplyDelete", map);
	}

	public int boardDelete(Map<String, Object> map)
	{
		return delete("BoardMgr.boardDelete", map);
	}

	public int boardToReplyDelete(Map<String, Object> map)
	{
		return delete("BoardReplyMgr.boardToReplyDelete", map);
	}

	public List<Map<String, Object>> getMainBoardList(Map<String, Object> map)
	{
		return selectList("BoardMgr.getMainBoardList", map);
	}

}
