package com.web.main.passwdInit.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MailUtil;
import com.web.common.util.MessageUtil;
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
			int updateResult = passwdInitDAO.update("PasswdInit.updateUserPasswd", resultMap);
			
			Template template = velocityEngine.getTemplate("./mailTemplate/passwdInit.template", "UTF-8");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", resultMap.get("name"));
			velocityContext.put("login_id", resultMap.get("login_id"));
			velocityContext.put("init_password", resultMap.get("init_password"));
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			commandMap.put("fromEmail", MessageUtil.getMessage("mail.admin.address"));
			commandMap.put("toEmail", resultMap.get("e_mail"));
			commandMap.put("ccEmail", "");
			commandMap.put("subject", "[ITSM] 비밀번호 초기화가 완료되었습니다.");
			commandMap.put("body", stringWriter.toString());
			
			if (updateResult == 0)
			{
				map.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
				map.put(Constants.RESULT_MASAGE_KEY, "시스템 오류입니다.\n전산담당자에게 문의해주세요.");
			}
			else
			{
				MailUtil.sendEmail(commandMap);
				map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
				map.put(Constants.RESULT_MASAGE_KEY, "비밀번호를 초기화 하였습니다.\n가입하신 이메일을 확인해주세요.");
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
