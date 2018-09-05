package com.web.main.memberJoin.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.admin.userMgr.dao.ManageUserDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.CodeUtil;
import com.web.common.util.MessageUtil;
import com.web.main.memberJoin.dao.MemberJoinDAO;

/**
 * 
 * @파일명 : MemberJoinServiceImpl.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 27.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		회원 가입 ServiceImpl
 *     </pre>
 */
@Service("main.memberJoin.memberJoinService")
public class MemberJoinServiceImpl extends CommonServiceImpl implements MemberJoinService
{

	@Resource(name = "main.memberJoin.memberJoinDAO")
	private MemberJoinDAO memberJoinDAO;
	
	@Resource(name = "admin.userMgr.manageUserDAO")
	private ManageUserDAO manageUserDAO;

	/**
	 * 
	 * @메소드명	: getSuggestion
	 * @날짜		: 2018. 4. 26.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		회원가입 제안문	
	 * 	</pre>
	 * @return
	 */
	@Override
	public Map<String, Object> getSuggestion(CommandMap commandMap)
	{
		return memberJoinDAO.getSuggestion(commandMap.getMap());
	}

	
	/**
	 * 
	 * @메소드명 : userIdCheck
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		회원 아이디 체크
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> userIdCheck(CommandMap commandMap) throws Exception
	{

		Map<String, String> map = new HashMap<String, String>();

		Object result = memberJoinDAO.selectOne("MemberJoin.userIdCheck", commandMap.getMap());
		if (Integer.parseInt(result.toString()) == 0)
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, "사용 가능한 아이디 입니다.");
		}
		else
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
			map.put(Constants.RESULT_MASAGE_KEY, "이미 사용중인 아이디 입니다.");
		}

		return map;
	}

	/**
	 * 
	 * @메소드명 : userRegiste
	 * @날짜 : 2018. 3. 27.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		회원 등록
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> memberJoinRegiste(CommandMap commandMap) throws Exception
	{

		String result = Constants.RESULT_FAIL;

		commandMap.put(Constants.SEQ_ID, getSeqType(Constants.USER_SEQ));
		
		// 비밀번호 암호화
		commandMap.put("p_password", CodeUtil.encrypt((String) commandMap.get("p_password")));
		
		int insertResult = memberJoinDAO.insert("MemberJoin.memberJoinRegiste", commandMap.getMap());

		if (insertResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}
		
		commandMap.put("loginSeqId", commandMap.get(Constants.SEQ_ID));
		// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
		result = manageUserDAO.userAuthDuplicationCnt(commandMap.getMap());
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			commandMap.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SEQ_ID));
			commandMap.put(Constants.SEQ_ID, getSeqType(Constants.INDI_AUTH_SEQ));
			result = manageUserDAO.userAuthInsert(commandMap.getMap());
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			throw new CommonException(MessageUtil.getMessage("common.default.duplication"), "");
		}
		else
		{
			throw new CommonException(result);
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


}
