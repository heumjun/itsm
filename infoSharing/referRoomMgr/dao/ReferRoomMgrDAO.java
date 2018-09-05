package com.web.infoSharing.referRoomMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: ReferRoomMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 25. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		자료실 관리 DAO
 * </pre>
 */
@Repository("infoSharing.referRoomMgr.referRoomMgrDAO")
public class ReferRoomMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> referRoomList(Map<String, Object> map)
	{
		return selectList("ReferRoomMgr.referRoomList", map);
	}

	public int saveReferRoomInsert(Map<String, Object> map)
	{
		return insert("ReferRoomMgr.referRoomInsert", map);
	}

	public int saveReferRoomUpdate(Map<String, Object> map)
	{
		return update("ReferRoomMgr.referRoomUpdate", map);
	}

	public int saveReferRoomAttachInsert(Map<String, Object> map)
	{
		return insert("ReferRoomMgr.referRoomAttachInsert", map);
	}

	public Map<String, String> getReferRoom(Map<String, Object> map)
	{
		return selectOne("ReferRoomMgr.getReferRoom", map);
	}

	public List<Map<String, Object>> popUpReferRoomAttachList(Map<String, Object> map)
	{
		return selectList("ReferRoomMgr.popUpReferRoomAttachList", map);
	}

	public List<Map<String, Object>> selectFileInfo(Map<String, Object> map)
	{
		return selectList("ReferRoomMgr.selectFileInfo", map);
	}

	public int refAttachFileDelete(Map<String, Object> map)
	{
		return delete("ReferRoomMgr.refAttachFileDelete", map);
	}

	public List<Map<String, Object>> getAttachFileInfo(Map<String, Object> map)
	{
		return selectList("ReferRoomMgr.getAttachFileInfo", map);
	}

	public int refRoomAttachDelete(Map<String, Object> map)
	{
		return delete("ReferRoomMgr.refRoomAttachDelete", map);
	}

	public int refRoomDelete(Map<String, Object> map)
	{
		return delete("ReferRoomMgr.refRoomDelete", map);
	}

	public List<Map<String, Object>> getMainRefRoomList(Map<String, Object> map)
	{
		return selectList("ReferRoomMgr.getMainRefRoomList", map);
	}

}
