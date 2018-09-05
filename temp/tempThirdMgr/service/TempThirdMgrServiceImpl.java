package com.web.temp.tempThirdMgr.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.service.CommonServiceImpl;
import com.web.temp.tempThirdMgr.dao.TempThirdMgrDAO;

@Service("temp.tempThirdMgr.tempThirdMgrService")
public class TempThirdMgrServiceImpl extends CommonServiceImpl implements TempThirdMgrService
{
	/**
	 * tempDAO 등록
	 */
	@Resource(name = "temp.tempThirdMgr.tempThirdMgrDAO")
	private TempThirdMgrDAO tempThirdMgrDAO;

}
