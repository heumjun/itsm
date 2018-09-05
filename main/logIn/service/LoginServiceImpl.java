package com.web.main.logIn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.CodeUtil;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.common.util.SessionUtil;
import com.web.main.logIn.dao.LoginDAO;

/**
 * 
 * @파일명 : LoginServiceImpl.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 27.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     LoginServiceImpl
 *     </pre>
 */
@Service("loginService")
public class LoginServiceImpl extends CommonServiceImpl implements LoginService
{

	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;

	/**
	 * 
	 * @메소드명 : isUser
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *     유저체크
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public boolean isUser(CommandMap commandMap, HttpServletRequest request)
	{

		if (commandMap.get("loginID") != null)
		{
			commandMap.put("loginID", CodeUtil.decrypt(commandMap.get("loginID") + ""));
			SessionUtil.setObject("loginID", commandMap.get("loginID"));
		}

		// 비밀번호 암호화
		commandMap.put("login_pw", CodeUtil.encrypt((String) commandMap.get("login_pw")));
		// 로그인 유저를 DB로부터 취득
		Object loginUser = loginDAO.selectLogin(commandMap.getMap());
		if (loginUser != null)
		{
			// 유저정보가 있는경우 유저정보를 세션에 설정
			SessionUtil.setLoginUserObject(loginUser);
			
			// 로그인 사용자 로그 저장
			String client_ip = request.getHeader("X-FORWARDED-FOR");
			if (client_ip == null)
			{
				client_ip = request.getRemoteAddr();
			}
			
			commandMap.put("login_id", SessionUtil.getUserId());
			commandMap.put("login_seq_id", SessionUtil.getUserSeqId());
			commandMap.put("login_ip", client_ip);
			loginDAO.saveUserLoginLog(commandMap.getMap());
			
			return true;
		}
		// 유저정보가 없는경우
		return false;
	}

	@Override
	public List<Map<String, Object>> popupList(CommandMap commandMap)
	{
		return loginDAO.popupList(commandMap.getMap());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> userInfoSave(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 개인정보 수정
		commandMap.put("p_password", CodeUtil.encrypt((String) commandMap.get("p_password")));
		commandMap.put("p_new_password", CodeUtil.encrypt((String) commandMap.get("p_new_password")));
		int saveResult = loginDAO.userInfoSave(commandMap.getMap());

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
			//return MessageUtil.getResultMessage(result);
			Map<String, String> map = new HashMap<String, String>();
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, MessageUtil.getMessage("common.password.succ"));
			return map;
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			//throw new CommonException();
			Map<String, String> map = new HashMap<String, String>();
			map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
			map.put(Constants.RESULT_MASAGE_KEY, MessageUtil.getMessage("common.password.fail"));
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
	 * @메소드명	: autoLoginUserList
	 * @날짜		: 2018. 8. 23.
	 * @작성자		: Cho HeumJun
	 * @설명 
	 * 	<pre>
	 *		Back Door 화면 로그인 사용자 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> autoLoginUserList(CommandMap commandMap) throws Exception
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = getGridData(commandMap.getMap());
		
		// 사용자에 대한 비밀번호 복호화
		for(Map<String, Object> data : listData) {
			data.put("PASSWORD", CodeUtil.decrypt((String) data.get("PASSWORD")));
		}
		
		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			listRowCnt = getGridListSize(commandMap.getMap());
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

}
