package com.web.infoSharing.noticeMgr.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonService;

public interface NoticeMgrService extends CommonService
{

	Map<String, Object> noticeList(CommandMap commandMap);

	Map<String, String> saveNotice(CommandMap commandMap) throws Exception;

	Map<String, String> getNotice(CommandMap commandMap);

	List<Map<String, Object>> getMainNoticeList(CommandMap commandMap);

}
