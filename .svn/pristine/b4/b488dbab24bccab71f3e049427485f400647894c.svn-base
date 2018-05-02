package com.web.admin.stanAuthMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: StanAuthMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 17. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		표준권한 페이지 맵퍼
 * </pre>
 */
@Repository("admin.stanAuthMgr.stanAuthMgrDAO")
public class StanAuthMgrDAO extends CommonDAO
{
	// 표준권한 리스트
	public List<Map<String, Object>> stanAuthMgrList(Map<String, Object> map)
	{
		return selectList("StanAuthMgr.stanAuthMgrList", map);
	}

	// 상세권한 리스트
	public List<Map<String, Object>> stanAuthMgrMenuList(Map<String, Object> map)
	{
		return selectList("StanAuthMgr.stanAuthMgrMenuList", map);
	}

	// 표준권한 중복체크
	public int getStanAuthDuplicationCnt(Map<String, Object> map)
	{
		return selectOne("StanAuthMgr.getStanAuthDuplicationCnt", map);
	}

	// 표준권한 생성
	public int stanAuthDataInsert(Map<String, Object> map)
	{
		return insert("StanAuthMgr.stanAuthDataInsert", map);
	}

	// 표준권한 업데이트
	public int stanAuthDataUpdate(Map<String, Object> map)
	{
		return update("StanAuthMgr.stanAuthDataUpdate", map);
	}

	// 표준권한 삭제
	public int stanAuthDataDelete(Map<String, Object> map)
	{
		return delete("StanAuthMgr.stanAuthDataDelete", map);
	}
	
	public int stanAuthMasterToDetailDataDelete(Map<String, Object> map)
	{
		return delete("StanAuthMgr.stanAuthMasterToDetailDataDelete", map);
	}

	// 상세권한 생성
	public int stanAuthDetailDataInsert(Map<String, Object> map)
	{
		return insert("StanAuthMgr.stanAuthDetailDataInsert", map);
	}

	// 상세권한 삭제
	public int stanAuthDetailDataDelete(Map<String, Object> map)
	{
		return delete("StanAuthMgr.stanAuthDetailDataDelete", map);
	}

	// 상세권한의 부모권한 중복체크
	public int stanAuthDetailUpDataDuplication(Map<String, Object> map)
	{
		return selectOne("StanAuthMgr.stanAuthDetailUpDataDuplication", map);
	}

	// 상세권한의 부모권한 생성
	public int stanAuthDetailUpDataInsert(Map<String, Object> map)
	{
		return insert("StanAuthMgr.stanAuthDetailUpDataInsert", map);
	}

	// 상세권한의 부모권한 중복체크
	public int stanAuthDetailUpDeleteDataDuplication(Map<String, Object> map)
	{
		return selectOne("StanAuthMgr.stanAuthDetailUpDeleteDataDuplication", map);
	}

	// 상세권한의 부모권한 삭제
	public int stanAuthDetailUpDataDelete(Map<String, Object> map)
	{
		return delete("StanAuthMgr.stanAuthDetailUpDataDelete", map);
	}

}
