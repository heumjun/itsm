package com.web.dlm.geuntaeMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.constants.Constants;
import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명		: DlmGeuntaeMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 19. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Repository("dlm.geuntaeMgr.dlmGeuntaeMgrDAO")
public class DlmGeuntaeMgrDAO extends CommonDAO
{
	public  List<Map<String, Object>> gtmLessOneYearInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmLessOneYearInfoList", map);
	}
	
	
	public  List<Map<String, Object>> gtmLessOneYearUserList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmLessOneYearUserList", map);
	}
	
	public String dataDuplicationCnt(Map<String, Object> rowData)
	{
		int result = selectOne("GeunTaeMgr.dataDuplicationCnt", rowData);
		
		if (result > 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	
	public String lessOneYearInfoInsert(Map<String, Object> rowData)
	{
		int insertResult = insert("GeunTaeMgr.lessOneYearInfoInsert", rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	public String lessOneYearInfoUpdate(Map<String, Object> rowData)
	{
		int insertResult = insert("GeunTaeMgr.lessOneYearInfoUpdate", rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	
	public String lessOneYearInfoDelete(Map<String, Object> rowData)
	{
		int insertResult = insert("GeunTaeMgr.lessOneYearInfoDelete", rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	
	
	
	
	
	
	public List<Map<String, Object>> monthViewList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.monthViewList", map);
	}
	
	public List<Map<String, Object>> gtmBaseInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmBaseInfoList", map);
	}

	public List<Map<String, Object>> popUpGtmReqInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.popUpGtmReqInfoList", map);
	}

	public List<Map<String, Object>> popUpGtmBonusInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.popUpGtmBonusInfoList", map);
	}
	
	public List<Map<String, Object>> gtmBonusInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmBonusInfoList", map);
	}
	
	public List<Map<String, Object>> getUserSelectBoxList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.getUserSelectBoxList", map);
	}
	
	public List<Map<String, Object>> gtmReqInfoList(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.gtmReqInfoList", map);
	}
	
	public String getBonusDay(Map<String, Object> map)
	{
		return selectOne("GeunTaeMgr.getBonusDay", map);
	}
	
	public int saveGtmReqInfo(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.saveGtmReqInfo", map);
	}

	public int gtmReqSmsInsert(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmReqSmsInsert", map);
	}

	public int gtmReqSmsInsertInfo(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmReqSmsInsertInfo", map);
	}

	public int saveGtmReqApproveProcess(Map<String, Object> map)
	{
		return update("GeunTaeMgr.saveGtmReqApproveProcess", map);
	}

	public List<Map<String, Object>> getReqUserInfo(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.getReqUserInfo", map);
	}

	public int gtmReqDeleteProcess(Map<String, Object> map)
	{
		return delete("GeunTaeMgr.gtmReqDeleteProcess", map);
	}

	public String gtmReqSmsAdminUser(Map<String, Object> map)
	{
		return selectOne("GeunTaeMgr.gtmReqSmsAdminUser", map);
	}

	public List<Map<String, Object>> getHoliday(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.getHoliday", map);
	}

	public List<Map<String, Object>> getDatePickerHoliday(Map<String, Object> map)
	{
		return selectList("GeunTaeMgr.getDatePickerHoliday", map);
	}

	public int gtmBonusSmsInsert(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmBonusSmsInsert", map);
	}
	
	public int gtmBonusSmsInsertInfo(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.gtmBonusSmsInsertInfo", map);
	}

	public int popUpGtmReqInfoExcelImportAction(Map<String, Object> map)
	{
		return insert("GeunTaeMgr.popUpGtmReqInfoExcelImportAction", map);
	}

}
