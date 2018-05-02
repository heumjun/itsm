package com.web.common.controller;

import javax.annotation.Resource;

import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.service.CommonService;
import com.web.common.util.SessionUtil;

/**
 * 
 * @파일명		: CommonController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	컨트롤러의 공통 처리를 담당한다. 각 로직의 컨트롤러에서 상속됨	
 * </pre>
 */
public class CommonController
{
	@Resource(name = "commonService")
	public CommonService commonService;

	/**
	 * 
	 * @메소드명	: getUserRoleAndLink
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		공통 : 유저정보를 취득하고 각 로직별 해당 페이지로 이동
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	public ModelAndView getUserRoleAndLink(CommandMap commandMap)
	{

		// 상위 링크가 없을시에는 세션에서 상위링크를 가져온다.
		String nextViewLink = commandMap.get(Constants.NEXT_VIEW_LINK).toString();
		String parentUrl = "";
		if (commandMap.get(Constants.VIEW_PARENT_URL) == null)
		{
			parentUrl = SessionUtil.getObject(Constants.VIEW_PARENT_URL).toString();
			if (nextViewLink.startsWith(Constants.POPUP))
			{
				nextViewLink = Constants.POPUP + nextViewLink;
			}
			nextViewLink = parentUrl + nextViewLink;
		}

		ModelAndView mv = new ModelAndView(nextViewLink);
		mv.addAllObjects(commandMap.getMap());
		// TODO 권한정보
		// mv.addObject(Constants.VIEW_USER_ROLE_KEY, commonService.getUserRole(commandMap));
		return mv;
	}
	

}
