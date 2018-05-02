package com.web.main.logIn.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명 : LoginDAO.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 14.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 
 *     </pre>
 */
@Repository("loginDAO")
public class LoginDAO extends CommonDAO
{

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectLogin(Map<String, Object> map)
	{
		return (Map<String, Object>) selectOne("LoginUser.selectLogin", map);
	}
}