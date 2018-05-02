package com.web.infoSharing.boardMgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.service.CommonServiceImpl;
import com.web.infoSharing.boardMgr.dao.BoardMgrDAO;

@Service("infoSharing.boardMgr.boardMgrService")
public class BoardMgrServiceImpl extends CommonServiceImpl implements BoardMgrService
{

	@Resource(name = "infoSharing.boardMgr.boardMgrDAO")
	private BoardMgrDAO boardMgrDAO;
	
	@Override
	public List<Map<String, Object>> boardMgrList(CommandMap commandMap)
	{
		return boardMgrDAO.boardMgrList(commandMap.getMap());
	}

}
