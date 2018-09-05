package com.web.infoSharing.noticeMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

@Repository("infoSharing.noticeMgr.noticeMgrDAO")
public class NoticeMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> noticeList(Map<String, Object> map)
	{
		return selectList("NoticeMgr.noticeList", map);
	}

	public int saveNotice(Map<String, Object> map)
	{
		return insert("NoticeMgr.saveNotice", map);
	}
	
	public int saveNoticeUpdate(Map<String, Object> map)
	{
		return update("NoticeMgr.saveNoticeUpdate", map);
	}

	public Map<String, String> getNotice(Map<String, Object> map)
	{
		return selectOne("NoticeMgr.getNotice", map);
	}

	public List<Map<String, Object>> getMainNoticeList(Map<String, Object> map)
	{
		return selectList("NoticeMgr.getMainNoticeList", map);
	}

}
