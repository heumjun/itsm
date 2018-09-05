package com.web.lec.wipChkMgr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.web.lec.wipChkMgr.service.WipChkMgrService;

/**
 * 
 * @파일명		: WipChkMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Controller
public class WipChkMgrController extends CommonController
{

	@Resource(name = "lec.wipChkMgr.wipChkMgrService")
	private WipChkMgrService wipChkMgrService;
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgr.do")
	public ModelAndView wipChkMgr(CommandMap commandMap) throws ParseException
	{
		List<String> dateList = new ArrayList<String>(); 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date currentTime = new Date();
		String mTime = format.format(currentTime);
		Date date = format.parse(mTime);

		for (int i = 0; i <= 12; i++)
		{

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1); // 날짜 더하기
			cal.add(Calendar.MONTH, -7); // 월 더하기
			cal.add(Calendar.MONTH, +i); // 월 더하기
			//System.out.println("날짜 확인 >>> " + format.format(cal.getTime()));
			
			dateList.add(format.format(cal.getTime()));
		}
		commandMap.put("dateList", dateList);
		
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
		mv.addObject("dateList", dateList);
		return mv;
	}
	
	// Tab1
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList.do")
	public @ResponseBody Map<String, Object> wipChkMgrList(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList(commandMap);
	}
	
	// Tab2
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList1.do")
	public @ResponseBody Map<String, Object> wipChkMgrList1(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList1(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList2.do")
	public @ResponseBody Map<String, Object> wipChkMgrList2(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList2(commandMap);
	}
	
	// Tab3
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList3.do")
	public @ResponseBody Map<String, Object> wipChkMgrList3(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList3(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList4.do")
	public @ResponseBody Map<String, Object> wipChkMgrList4(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList4(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList5.do")
	public @ResponseBody Map<String, Object> wipChkMgrList5(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList5(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList6.do")
	public @ResponseBody Map<String, Object> wipChkMgrList6(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList6(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList7.do")
	public @ResponseBody Map<String, Object> wipChkMgrList7(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList7(commandMap);
	}
	
	// Tab4
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList8.do")
	public @ResponseBody Map<String, Object> wipChkMgrList8(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList8(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList9.do")
	public @ResponseBody Map<String, Object> wipChkMgrList9(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList9(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList10.do")
	public @ResponseBody Map<String, Object> wipChkMgrList10(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList10(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList11.do")
	public @ResponseBody Map<String, Object> wipChkMgrList11(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList11(commandMap);
	}
	
	// Tab5
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList12.do")
	public @ResponseBody Map<String, Object> wipChkMgrList12(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList12(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList13.do")
	public @ResponseBody Map<String, Object> wipChkMgrList13(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList13(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList14.do")
	public @ResponseBody Map<String, Object> wipChkMgrList14(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList14(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList15.do")
	public @ResponseBody Map<String, Object> wipChkMgrList15(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList15(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList16.do")
	public @ResponseBody Map<String, Object> wipChkMgrList16(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList16(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList17.do")
	public @ResponseBody Map<String, Object> wipChkMgrList17(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList17(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList18.do")
	public @ResponseBody Map<String, Object> wipChkMgrList18(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList18(commandMap);
	}
	
	// Tab6
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList19.do")
	public @ResponseBody Map<String, Object> wipChkMgrList19(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList19(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList20.do")
	public @ResponseBody Map<String, Object> wipChkMgrList20(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList20(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList21.do")
	public @ResponseBody Map<String, Object> wipChkMgrList21(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList21(commandMap);
	}
	
	// Tab7
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList22.do")
	public @ResponseBody Map<String, Object> wipChkMgrList22(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList22(commandMap);
	}
	
	@RequestMapping(value = "lec.wipChkMgr.wipChkMgrList23.do")
	public @ResponseBody Map<String, Object> wipChkMgrList23(CommandMap commandMap)
	{
		return wipChkMgrService.wipChkMgrList23(commandMap);
	}
	
}
