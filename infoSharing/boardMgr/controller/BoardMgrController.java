package com.web.infoSharing.boardMgr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.common.util.SessionUtil;
import com.web.infoSharing.boardMgr.service.BoardMgrService;

/**
 * 
 * @파일명		: boardMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 6. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		게시판 
 * </pre>
 */
@Controller
public class BoardMgrController extends CommonController
{
	
	@Resource(name = "infoSharing.boardMgr.boardMgrService")
	private BoardMgrService	 boardMgrService;

	/**
	 * 
	 * @메소드명	: boardMgr
	 * @날짜		: 2018. 4. 6.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgr.do")
	public ModelAndView boardMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: boardMgrList
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 리스트	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgrList.do")
	public @ResponseBody Map<String, Object> boardMgrList(CommandMap commandMap)
	{
		return boardMgrService.boardMgrList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: boardMgrEdit
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 등록
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgrEdit.do")
	public ModelAndView boardMgrEdit(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(
				Constants.INFO + Constants.BBS + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		mav.addObject("icui_seq_id", SessionUtil.getUserSeqId());
		
		if(commandMap.get("p_seq_id") != null){ //게시글 수정
            //정보를 가져온다.
			List<Map<String, Object>> resultMap = boardMgrService.getBoardView(commandMap);
			mav.addObject("seq_id", resultMap.get(0).get("SEQ_ID"));
			mav.addObject("icui_seq_id", resultMap.get(0).get("ICUI_SEQ_ID"));
			mav.addObject("user_name", resultMap.get(0).get("USER_NAME"));
			mav.addObject("title", resultMap.get(0).get("TITLE"));
			mav.addObject("contents", resultMap.get(0).get("CONTENTS"));
			mav.addObject("last_update_date", resultMap.get(0).get("LAST_UPDATE_DATE"));
        }
		
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: boardMgrSave
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 저장	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgrSave.do")
	public @ResponseBody Map<String, String> boardMgrSave(CommandMap commandMap) throws Exception
	{
		return boardMgrService.boardMgrSave(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: boardMgrView
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 상세 보기
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardMgrView.do")
	public ModelAndView boardMgrView(CommandMap commandMap) throws Exception
	{
		ModelAndView mav = new ModelAndView(
				Constants.INFO + Constants.BBS + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		
		// 조회글 업데이트 +1
		boardMgrService.updateBoardHitCount(commandMap);
		mav.addObject("replyList", boardMgrService.getReplyList(commandMap));
		
		List<Map<String, Object>> resultMap = boardMgrService.getBoardView(commandMap);
		mav.addObject("seq_id", resultMap.get(0).get("SEQ_ID"));
		mav.addObject("icui_seq_id", resultMap.get(0).get("ICUI_SEQ_ID"));
		mav.addObject("user_name", resultMap.get(0).get("USER_NAME"));
		mav.addObject("title", resultMap.get(0).get("TITLE"));
		mav.addObject("contents", resultMap.get(0).get("CONTENTS"));
		mav.addObject("last_update_date", resultMap.get(0).get("LAST_UPDATE_DATE"));
		
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: boardReplySave
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 댓글 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardReplySave.do")
	public @ResponseBody Map<String, String> boardReplySave(CommandMap commandMap) throws Exception
	{
		return boardMgrService.boardReplySave(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: boardReplyDelete
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 댓글 삭제	
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardReplyDelete.do")
	public @ResponseBody Map<String, String> boardReplyDelete(CommandMap commandMap) throws Exception
	{
		return boardMgrService.boardReplyDelete(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: boardDelete
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		게시판 삭제
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.boardMgr.boardDelete.do")
	public @ResponseBody Map<String, String> boardDelete(CommandMap commandMap) throws Exception
	{
		return boardMgrService.boardDelete(commandMap);
	}
	
}
