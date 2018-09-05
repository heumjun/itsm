package com.web.infoSharing.boardMgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.infoSharing.boardMgr.dao.BoardMgrDAO;

@Service("infoSharing.boardMgr.boardMgrService")
public class BoardMgrServiceImpl extends CommonServiceImpl implements BoardMgrService
{

	@Resource(name = "infoSharing.boardMgr.boardMgrDAO")
	private BoardMgrDAO boardMgrDAO;

	/**
	 * 
	 * @메소드명	: boardMgrList
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		게시판 리스트
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> boardMgrList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = boardMgrDAO.boardMgrList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if (listData.size() > 0)
		{
			listRowCnt = listData.get(0).get("CNT").toString();
		}
		else
		{
			listRowCnt = 0;
		}

		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}

	/**
	 * 
	 * @메소드명	: boardMgrSave
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		게시판 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> boardMgrSave(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		if (commandMap.get("p_seq_id").equals(""))
		{
			// 공지사항 테이블 SEQ 증가
			commandMap.put(Constants.SEQ_ID, getSeqType(Constants.BBS_SEQ));
			// 등록
			int insertResult = boardMgrDAO.boardMgrInsert(commandMap.getMap());

			if (insertResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		}
		else
		{
			// 수정
			int updateResult = boardMgrDAO.boardMgrUpdate(commandMap.getMap());

			if (updateResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			return MessageUtil.getResultMessage(result);
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}

	/**
	 * 
	 * @메소드명	: getReplyList
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		댓글 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getReplyList(CommandMap commandMap)
	{

		List<Map<String, Object>> boardReplyList = boardMgrDAO.getReplyList(commandMap.getMap());

		// 부모
		List<Map<String, Object>> boardReplyListParent = new ArrayList<Map<String, Object>>();
		// 자식
		List<Map<String, Object>> boardReplyListChild = new ArrayList<Map<String, Object>>();
		// 통합
		List<Map<String, Object>> newBoardReplyList = new ArrayList<Map<String, Object>>();

		// 1.부모와 자식 분리
		for (Map<String, Object> boardReply : boardReplyList)
		{
			if (boardReply.get("DEPTH").toString().equals("0"))
			{
				boardReplyListParent.add(boardReply);
			}
			else
			{
				boardReplyListChild.add(boardReply);
			}
		}

		// 2.부모를 돌린다.
		for (Map<String, Object> boardReplyParent : boardReplyListParent)
		{
			// 2-1. 부모는 무조건 넣는다.
			newBoardReplyList.add(boardReplyParent);
			// 3.자식을 돌린다.
			for (Map<String, Object> boardReplyChild : boardReplyListChild)
			{
				// 3-1. 부모의 자식인 것들만 넣는다.
				if (boardReplyParent.get("SEQ_ID").equals(boardReplyChild.get("PARENT_ID")))
				{
					newBoardReplyList.add(boardReplyChild);
				}

			}

		}

		// 정리한 list return
		return boardReplyList;

	}

	/**
	 * 
	 * @메소드명	: getBoardView
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		게시판 상세 뷰	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getBoardView(CommandMap commandMap)
	{
		return boardMgrDAO.getBoardView(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명	: boardReplySave
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		댓글 저장	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> boardReplySave(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 게시판 댓글 테이블 SEQ 증가
		commandMap.put(Constants.SEQ_ID, getSeqType(Constants.BBS_REPLY_SEQ));

		// 등록
		int saveResult = boardMgrDAO.boardReplySave(commandMap.getMap());

		if (saveResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			Map<String, String> map = new HashMap<String, String>();
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put("reply_id", (String) commandMap.get(Constants.SEQ_ID));
			map.put("depth", (String) commandMap.get("depth"));
			map.put(Constants.RESULT_MASAGE_KEY, "처리가 완료되었습니다.");
			return map;
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}

	/**
	 * 
	 * @메소드명	: boardReplyDelete
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		댓글 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> boardReplyDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 댓글 삭제
		int delResult = boardMgrDAO.boardReplyDelete(commandMap.getMap());
		
		if (delResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}

		// 결과값에 따른 메시지를 담아 전송
		Map<String, String> map = new HashMap<String, String>();
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, "처리가 완료되었습니다.");
			return map;
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
			map.put(Constants.RESULT_MASAGE_KEY, "삭제에 실패했습니다.\n패스워드를 확인해주세요.");
			return map;
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}

	/**
	 * 
	 * @메소드명	: updateBoardHitCount
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		게시판 조회수 업데이트
	 * 	</pre>
	 * @param commandMap
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateBoardHitCount(CommandMap commandMap) throws Exception
	{
		boardMgrDAO.updateBoardHitCount(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명	: boardDelete
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		게시판 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> boardDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 게시글 삭제
		int delResult = boardMgrDAO.boardDelete(commandMap.getMap());
		
		if (delResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}
		
		// 게시글 삭제
		// 게시글에 따른 댓글 복구를 위해 미처리
		/*delResult = boardMgrDAO.boardToReplyDelete(commandMap.getMap());
		
		if (delResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}*/

		// 결과값에 따른 메시지를 담아 전송
		Map<String, String> map = new HashMap<String, String>();
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, "처리가 완료되었습니다.");
			return map;
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
			map.put(Constants.RESULT_MASAGE_KEY, "삭제에 실패했습니다.");
			return map;
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}

	@Override
	public List<Map<String, Object>> getMainBoardList(CommandMap commandMap)
	{
		return boardMgrDAO.getMainBoardList(commandMap.getMap());
	}

}
