package com.web.main.passwdInit.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: PasswdInitDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 3. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		비밀번호 초기화 DAO
 * </pre>
 */
@Repository("main.passwdInit.passwdInitDAO")
public class PasswdInitDAO extends CommonDAO
{

	public int passwdInitSmsInsert(Map<String, Object> map)
	{
		return insert("PasswdInit.passwdInitSmsInsert", map);
	}

}
