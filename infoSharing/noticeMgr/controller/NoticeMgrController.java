package com.web.infoSharing.noticeMgr.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.infoSharing.noticeMgr.service.NoticeMgrService;

/**
 * 
 * @파일명		: NoticeMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		공지사항 Controller
 * </pre>
 */
@Controller
public class NoticeMgrController extends CommonController
{

	@Resource(name = "infoSharing.noticeMgr.noticeMgrService")
	private NoticeMgrService noticeMgrService;
	
	/**
	 * 
	 * @메소드명	: noticeMgr
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		공지사항 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.noticeMgr.noticeMgr.do")
	public ModelAndView noticeMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: noticeList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		공지사항 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.noticeMgr.noticeList.do")
	public @ResponseBody Map<String, Object> noticeList(CommandMap commandMap)
	{
		return noticeMgrService.noticeList(commandMap);
		
	}
	
	/**
	 * 
	 * @메소드명	: popUpNoticeRegiste
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		공지사항 등록 및 수정 팝업	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.noticeMgr.popUpNoticeRegiste.do")
	public ModelAndView popUpNoticeRegiste(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.INFO + Constants.NOTICE + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		
		// SEQ 유무에 따른 등록 및 수정
		if(commandMap.get("p_seq_id") != null) {
			mav.addObject("notice", noticeMgrService.getNotice(commandMap));
		}
		
		return mav;
	}
	
	@RequestMapping(value = "infoSharing.noticeMgr.saveNotice.do")
	public @ResponseBody Map<String, String> saveNotice(CommandMap commandMap) throws Exception
	{
		return noticeMgrService.saveNotice(commandMap);
	}
	
	
}
