package com.web.main.memberJoin.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: MemberJoinDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 27. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		회원 가입 Dao
 * </pre>
 */
@Repository("main.memberJoin.memberJoinDAO")
public class MemberJoinDAO extends CommonDAO
{

	public Map<String, Object> getSuggestion(Map<String, Object> map)
	{
		return selectOne("MemberJoin.getSuggestion", map);
	}

}
