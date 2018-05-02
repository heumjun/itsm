package com.web.admin.menuMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

public interface ManageMenuService extends CommonService
{
	
	List<Map<String, Object>> getTreeMenuList(CommandMap commandMap);

	// 시스템관리 > 메뉴관리 좌측 메뉴 트리 구성
	List<Map<String, Object>> getAdminTreeMenuList(CommandMap commandMap);

	Map<String, String> saveManageMenu(CommandMap commandMap) throws Exception;
}
