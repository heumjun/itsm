package com.web.main.passwdInit.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.CodeUtil;
import com.web.main.passwdInit.dao.PasswdInitDAO;


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
@Service("main.passwdInit.passwdInitService")
public class PasswdInitServiceImpl extends CommonServiceImpl implements PasswdInitService
{

	@Resource(name = "main.passwdInit.passwdInitDAO")
	private PasswdInitDAO passwdInitDAO;

	@Resource(name = "velocityEngine")
	private VelocityEngine velocityEngine;
	
	/**
	 * 
	 * @메소드명	: passwdInitAction
	 * @날짜		: 2018. 4. 3.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		비밀번호 초기화 + 이메일 발송	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> passwdInitAction(CommandMap commandMap) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> resultMap = passwdInitDAO.selectOne("PasswdInit.selectUserConfirm", commandMap.getMap());
		
		if (resultMap != null)
		{
			String newPassWd = (String) resultMap.get("INIT_PASSWORD");
			resultMap.put("init_password", CodeUtil.encrypt( (String) resultMap.get("INIT_PASSWORD") ) );
			int updateResult = passwdInitDAO.update("PasswdInit.updateUserPasswd", resultMap);
			
			// 받는 사람
			commandMap.put(Constants.SEND_PHONE, resultMap.get("PHONE1"));
			// 메세지 내용
			commandMap.put(Constants.MSG_BODY, resultMap.get("NAME") + "님 " + "비밀번호가 초기화 되었습니다. 신규 비밀번호는 " + newPassWd + " 입니다. 로그인 후 개인정보에서 비밀번호를 변경해주세요.");

			// SMS 발송 이력
			commandMap.put(Constants.SMS_SEQ, getSeqType(Constants.SMS_SEQ));
			// 근태관리 타입 : null
			commandMap.put(Constants.SMS_TYPE, "P");
			commandMap.put("p_icui_seq_id", resultMap.get("SEQ_ID"));
			commandMap.put("loginId", resultMap.get("LOGIN_ID"));
			
			// SMS 발송
			Map<String, String> sendResult = sendSms(commandMap);
			if(sendResult.get(Constants.RESULT_KEY).equals(Constants.RESULT_SUCCESS)) {
				updateResult = 1;
			} else {
				updateResult = 0;
			}
			
			if (updateResult == 0)
			{
				map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
				map.put(Constants.RESULT_MASAGE_KEY, "시스템 오류입니다.\n전산담당자에게 문의해주세요.");
			}
			else
			{
				//MailUtil.sendEmail(commandMap);
				map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
				map.put(Constants.RESULT_MASAGE_KEY, "비밀번호를 초기화 하였습니다.");
			}
			
		}
		else
		{
			map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
			map.put(Constants.RESULT_MASAGE_KEY, "본인 인증에 실패 하였습니다.");
		}

		return map;
	}

}
