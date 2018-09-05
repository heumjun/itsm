package com.web.dlm.weekResultMgr.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.common.util.SessionUtil;
import com.web.dlm.weekResultMgr.service.DlmWeekResultMgrService;

/**
 * 
 * @파일명		: DlmWeekResultMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 3. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		주간실적 Controller
 * </pre>
 */
@Controller
public class DlmWeekResultMgrController extends CommonController
{

	@Resource(name = "dlm.weekResultMgr.dlmWeekResultMgrService")
	private DlmWeekResultMgrService dlmWeekResultMgrService;
	
	/**
	 * 
	 * @메소드명	: weekManage
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적관리 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.weekManage.do")
	public ModelAndView weekManage(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: weekManageList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적관리 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.weekManageList.do")
	public @ResponseBody Map<String, Object> weekManageList(CommandMap commandMap)
	{
		return dlmWeekResultMgrService.weekManageList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmReqInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적관리 등록/수정 팝업
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.popUpWeekResultDetail.do")
	public ModelAndView popUpGtmReqInfo(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.DLM + Constants.WEEK_RESULT + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		
		// 해당 월 / 주차 구하기
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR)); 
		String month = String.valueOf(c.get(Calendar.MONTH)+1); 
		String chasu = String.valueOf(c.get(Calendar.WEEK_OF_MONTH));
		
		// SEQ 유무에 따른 등록 및 수정
		if(commandMap.get("p_seq_id") == null) 
		{
			List<Map<String, Object>> resultMap = dlmWeekResultMgrService.weekManageOnClick2(commandMap);
			
			mav.addObject("icui_seq_id", SessionUtil.getUserSeqId());
			mav.addObject("user_name", SessionUtil.getUserName());
			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("chasu", chasu);

			// 신규등록시 실적내용은 디폴트로 전주차 내용을 표시함
			if(resultMap.size() == 0)
			{
				mav.addObject("contents", "");
			}
			else
			{
				mav.addObject("contents", resultMap.get(0).get("CONTENTS"));
			}
		} 
		else 
		{
			List<Map<String, Object>> resultMap = dlmWeekResultMgrService.weekManageOnClick(commandMap);
			mav.addObject("seq_id", resultMap.get(0).get("SEQ_ID"));
			mav.addObject("icui_seq_id", resultMap.get(0).get("ICUI_SEQ_ID"));
			mav.addObject("user_name", resultMap.get(0).get("USER_NAME"));
			mav.addObject("year", resultMap.get(0).get("YEAR"));
			mav.addObject("month", resultMap.get(0).get("MONTH"));
			mav.addObject("chasu", resultMap.get(0).get("CHASU"));
			mav.addObject("contents", resultMap.get(0).get("CONTENTS"));
		}
		
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: saveWeekManage
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적관리 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.weekResultMgr.saveWeekManage.do")
	public @ResponseBody Map<String, String> saveWeekManage(CommandMap commandMap) throws Exception
	{
		return dlmWeekResultMgrService.saveWeekManage(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: weekInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적현황 화면	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.weekInfo.do")
	public ModelAndView weekInfo(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: weekInfoList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적현황 리스트	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.weekInfoList.do")
	public @ResponseBody Map<String, Object> weekInfoList(CommandMap commandMap)
	{
		return dlmWeekResultMgrService.weekInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpWeekInfoDetail
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적현황 Data Merge 뷰
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.weekResultMgr.popUpWeekInfoDetail.do")
	public ModelAndView popUpWeekInfoDetail(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.DLM + Constants.WEEK_RESULT + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		List<Map<String, Object>> resultMap = dlmWeekResultMgrService.popUpWeekInfoDetail(commandMap);

		//String originCont = StringEscapeUtils.unescapeHtml3((String) resultMap.get(0).get("STR"));
		String originCont1 = StringEscapeUtils.unescapeHtml3((String) resultMap.get(0).get("CONTENTS"));
		
		//System.out.println(StringUtil.BackScriptReplace(originCont1));
		
		mav.addObject("str", originCont1);
		mav.addAllObjects(commandMap.getMap());
		
		return mav;
	}
	
}
