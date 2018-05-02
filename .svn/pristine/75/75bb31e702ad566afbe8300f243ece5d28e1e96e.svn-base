package com.web.main.memberJoin.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.main.memberJoin.service.MemberJoinService;

/**
 * 
 * @파일명		: MemberJoinController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 27. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	회원 가입 Controller
 * </pre>
 */
@Controller
public class MemberJoinController extends CommonController
{

	@Resource(name = "main.memberJoin.memberJoinService")
	private MemberJoinService memberJoinService;

	/**
	 * 
	 * @메소드명	: userRegister
	 * @날짜		: 2018. 3. 27.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		회원가입 화면
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.memberJoin.memberJoin.do")
	public ModelAndView memberJoin(CommandMap commandMap) throws Exception
	{
		ModelAndView mav = new ModelAndView(Constants.REGISTER_LINK);
		
		// 회원가입 제안
		Map<String, Object> getSuggestionMap = memberJoinService.getSuggestion(commandMap);
		commandMap.put("clause", getSuggestionMap.get("CLAUSE"));
		commandMap.put("policy", getSuggestionMap.get("POLICY"));
		
		mav.addAllObjects(commandMap.getMap());
		
		return mav;
	}

	/**
	 * 
	 * @메소드명	: userIdCheck
	 * @날짜		: 2018. 3. 27.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		회원 가입 아이디 중복 체크
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.memberJoin.userIdCheck.do")
	public @ResponseBody Map<String, String> userIdCheck(CommandMap commandMap) throws Exception
	{
		return memberJoinService.userIdCheck(commandMap);
	}

	/**
	 * 
	 * @메소드명	: userRegiste
	 * @날짜		: 2018. 3. 27.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		회원 가입 저장
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.memberJoin.memberJoinRegiste.do")
	public @ResponseBody Map<String, String> memberJoinRegiste(CommandMap commandMap) throws Exception
	{
		return memberJoinService.memberJoinRegiste(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: idQuestionSelectBoxDataList
	 * @날짜		: 2018. 4. 2.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		계정보호 질문 리스트 셀렉트 박스 반환
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.memberJoin.memberQuestionSelectBoxDataList.do")
	public @ResponseBody String idQuestionSelectBoxDataList(CommandMap commandMap) throws Exception {
		return memberJoinService.getSelectBoxDataList(commandMap);
	}
	
	/**
	 * 
	 * @메소드명	: memberPositionSelectBoxDataList
	 * @날짜		: 2018. 4. 2.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		직급 리스트 셀렉트 박스 반환
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main.memberJoin.memberPositionSelectBoxDataList.do")
	public @ResponseBody String memberPositionSelectBoxDataList(CommandMap commandMap) throws Exception {
		return memberJoinService.getSelectBoxDataList(commandMap);
	}
	
	

}
