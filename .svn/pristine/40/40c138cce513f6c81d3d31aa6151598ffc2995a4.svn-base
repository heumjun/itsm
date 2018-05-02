package com.web.admin.menuMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

@Repository("admin.menuMgr.manageMenuDAO")
public class ManageMenuDAO extends CommonDAO
{

	public List<Map<String, Object>> getTreeMenuList(Map<String, Object> map)
	{
		return selectList("ManageMenu.treeMenuListByRole", map);
	}

	public List<Map<String, Object>> getAdminTreeMenuList(Map<String, Object> map)
	{
		return selectList("ManageMenu.adminTreeMenuList", map);
	}

}
