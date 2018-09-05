package com.web.main.mainScreen.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.infoSharing.boardMgr.service.BoardMgrService;
import com.web.infoSharing.noticeMgr.service.NoticeMgrService;
import com.web.infoSharing.referRoomMgr.service.ReferRoomMgrService;

/**
 * 
 * @파일명		: MainScreenController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	메인화면
 * </pre>
 */
@Controller
public class MainScreenController extends CommonController
{
	
	@Resource(name = "infoSharing.noticeMgr.noticeMgrService")
	private NoticeMgrService noticeMgrService;
	
	@Resource(name = "infoSharing.referRoomMgr.referRoomMgrService")
	private ReferRoomMgrService referRoomMgrService;
	
	@Resource(name = "infoSharing.boardMgr.boardMgrService")
	private BoardMgrService	 boardMgrService;
	
	/**
	 * 
	 * @메소드명 : layoutMainContents
	 * @날짜 : 2018. 3. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		메인 화면에서 필요한 정보를 취득하여 페이지 이동
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "main.mainScreen.layoutMainContents.do")
	public ModelAndView layoutMainContents(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(Constants.MAINSCREEN_URL + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		
		// TODO 메인에서 사용되어지는 리스트 가져오기. 
		mav.addObject("noticeList", noticeMgrService.getMainNoticeList(commandMap));
		mav.addObject("boardList", boardMgrService.getMainBoardList(commandMap));
		mav.addObject("refRoomList", referRoomMgrService.getMainRefRoomList(commandMap));
		
		//메인화면의 통계 데이터를 가져온다.
		//mav.addObject("noticeList", noticeMgrService.getMainNoticeList(commandMap));
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: eventPopup
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		메인 페이지 팝업
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "eventPopup.do")
	public ModelAndView eventPopup(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(Constants.MAINSCREEN_URL + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		commandMap.put("mapperName", "popUpNotice");
		mav.addObject("notice", noticeMgrService.getDbDataOne(commandMap));
		return mav;
	}
}
