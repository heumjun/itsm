package com.web.dlm.geuntaeMgr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.common.util.MessageUtil;
import com.web.dlm.geuntaeMgr.service.DlmGeuntaeMgrService;

/**
 * 
 * @파일명		: DlmGeuntaeMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 18. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		근태관리 Controller
 * </pre>
 */
@Controller
public class DlmGeuntaeMgrController extends CommonController
{

	@Resource(name = "dlm.geuntaeMgr.dlmGeuntaeMgrService")
	private DlmGeuntaeMgrService dlmGeuntaeMgrService;
	
	
	
	
	 /** 
	 * @메소드명	: gtmLessOneYearInfo
	 * @날짜		: 2018. 8. 23.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		1년미만자기준정보 조회
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmLessOneYearInfo.do")
	public ModelAndView gtmLessOneYearInfo(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	
	@RequestMapping(value = "dlm.geuntaeMgr.gtmLessOneYearInfoList.do")
	public @ResponseBody Map<String, Object> gtmLessOneYearInfoList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.gtmLessOneYearInfoList(commandMap);
		
	}
	
	@RequestMapping(value = "dlm.geuntaeMgr.gtmLessOneYearUserList.do")
	public @ResponseBody List<Map<String, Object>> gtmLessOneYearUserList(CommandMap commandMap) throws Exception {
		return dlmGeuntaeMgrService.gtmLessOneYearUserList(commandMap);
	}
	
	@RequestMapping(value = "dlm.geuntaeMgr.saveLessOneYearInfo.do")
	public @ResponseBody Map<String, String> saveLessOneYearInfo(CommandMap commandMap) throws Exception
	{
		return dlmGeuntaeMgrService.saveLessOneYearInfo(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: monthView
	 * @날짜		: 2018. 4. 19.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태현황(월) 페이지
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.monthView.do")
	public ModelAndView monthView(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: geuntaeList
	 * @날짜		: 2018. 4. 19.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태현황(월) 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.monthViewList.do")
	public @ResponseBody List<Map<String, Object>> monthViewList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.monthViewList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmBaseInfo
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 화면	
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmBaseInfo.do")
	public ModelAndView gtmBaseInfo(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmBaseInfoList
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmBaseInfoList.do")
	public @ResponseBody Map<String, Object> gtmBaseInfoList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.gtmBaseInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmReqInfo
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 대체휴가 팝업
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmReqInfo.do")
	public ModelAndView popUpGtmReqInfo(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.DLM + Constants.GEUNTAE + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmReqInfoList
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 대체휴가 팝업 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmReqInfoList.do")
	public @ResponseBody Map<String, Object> popUpGtmReqInfoList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.popUpGtmReqInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmBonusInfo
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 사용된 일수 팝업
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmBonusInfo.do")
	public ModelAndView popUpGtmBonusInfo(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.DLM + Constants.GEUNTAE + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmBonusInfoList
	 * @날짜		: 2018. 4. 24.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회 사용된 일수 팝업 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmBonusInfoList.do")
	public @ResponseBody Map<String, Object> popUpGtmBonusInfoList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.popUpGtmBonusInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmBonusInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		대체휴가등록 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmBonusInfo.do")
	public ModelAndView gtmBonusInfo(CommandMap commandMap)
	{
		commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmBonusInfoList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		대체휴가 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmBonusInfoList.do")
	public @ResponseBody Map<String, Object> gtmBonusInfoList(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.gtmBonusInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: getUserSelectBoxList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		대체휴가등록 화면 그리드 내 사용자 SelectBox
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.getUserSelectBoxList.do")
	public @ResponseBody List<Map<String, Object>> getUserSelectBoxList(CommandMap commandMap) throws Exception {
		return dlmGeuntaeMgrService.getUserSelectBoxList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: getYear
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		대체휴가등록 화면 그리드 내 적용년도
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getYear.do")
	public @ResponseBody List<Map<String, Object>> getYear(CommandMap commandMap) throws Exception {
		return commonService.getYear(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmReqInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태신청및승인 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmReqInfo.do")
	public ModelAndView gtmReqInfo(CommandMap commandMap)
	{
		// 승인관리자ID
		commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
		return getUserRoleAndLink(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: gtmReqInfoList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태신청 리스트
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmReqInfoList.do")
	public @ResponseBody Map<String, Object> gtmReqInfoList(CommandMap commandMap)
	{
		commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
		return dlmGeuntaeMgrService.gtmReqInfoList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: saveGtmBonusInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		대체휴가 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.SaveGtmBonusInfo.do")
	public @ResponseBody Map<String, String> saveGtmBonusInfo(CommandMap commandMap) throws Exception
	{
		return dlmGeuntaeMgrService.saveGtmBonusInfo(commandMap);
	}
		
	/**
	 * 
	 * @메소드명	: vacTypeSelectBoxDataList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		휴가종류 SelectBox
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.vacTypeSelectBoxDataList.do")
	public @ResponseBody String vacTypeSelectBoxDataList(CommandMap commandMap) throws Exception {
		return dlmGeuntaeMgrService.getSelectBoxDataList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: statusSelectBoxDataList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		승인상태 SelectBox
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.statusSelectBoxDataList.do")
	public @ResponseBody String statusSelectBoxDataList(CommandMap commandMap) throws Exception {
		return dlmGeuntaeMgrService.getSelectBoxDataList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmReqRequest
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태 신청 팝업 
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmReqRequest.do")
	public ModelAndView popUpGtmReqRequest(CommandMap commandMap) 
	{
		ModelAndView mav = getUserRoleAndLink(commandMap);
		// 대체휴가 남은일수
		mav.addObject("bonusDay", dlmGeuntaeMgrService.getBonusDay(commandMap));
		mav.setViewName(Constants.DLM + Constants.GEUNTAE + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		return mav;
	}
	
	/**
	 * 
	 * @메소드명	: saveGtmReqInfo
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태신청 등록
	 *		관리자에게 SMS발송
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.saveGtmReqInfo.do")
	public @ResponseBody Map<String, String> saveGtmReqInfo(CommandMap commandMap) throws Exception
	{
		return dlmGeuntaeMgrService.saveGtmReqInfo(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: saveGtmReqApproveProcess
	 * @날짜		: 2018. 5. 10.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태신청 승인/반려/취소
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.saveGtmReqApproveProcess.do")
	public @ResponseBody Map<String, String> saveGtmReqApproveProcess(CommandMap commandMap) throws Exception
	{
		return dlmGeuntaeMgrService.saveGtmReqApproveProcess(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: saveGtmReqApproveProcess
	 * @날짜		: 2018. 6. 15.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태신청 취소버튼 - 신청상태일때 취소버튼 클릭시 바로 삭제
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.gtmReqDeleteProcess.do")
	public @ResponseBody Map<String, String> gtmReqDeleteProcess(CommandMap commandMap) throws Exception
	{
		return dlmGeuntaeMgrService.gtmReqDeleteProcess(commandMap);
	}
	
	@RequestMapping(value = "dlm.geuntaeMgr.getDatePickerHoliday.do")
	public @ResponseBody List<Map<String, Object>> getDatePickerHoliday(CommandMap commandMap)
	{
		return dlmGeuntaeMgrService.getDatePickerHoliday(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: popUpGtmReqInfoExcelImport
	 * @날짜		: 2018. 7. 4.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태조회에서 엑셀 첨부파일 화면으로 이동
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmReqInfoExcelImport.do")
	public ModelAndView popUpGtmReqInfoExcelImport(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.DLM + Constants.GEUNTAE + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		return mav;
	}
	
	@RequestMapping(value = "dlm.geuntaeMgr.popUpGtmReqInfoExcelImportAction.do")
	public void popUpGtmReqInfoExcelImportAction(@RequestParam(value = "fileName") MultipartFile file, CommandMap commandMap, HttpServletResponse response) throws Exception 
	{
		commandMap.put("file", file);
		dlmGeuntaeMgrService.popUpGtmReqInfoExcelImportAction(commandMap, response);
	}
}
