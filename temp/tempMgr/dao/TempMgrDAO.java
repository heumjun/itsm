package com.web.temp.tempMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

 /** 
 * @파일명		: TempMgrDAO.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 9. 
 * @작성자		: bhLee
 * @설명
 * <pre>
 * 		템플릿(First) list
 * </pre>
 */
@Repository("temp.tempMgr.tempMgrDAO")
public class TempMgrDAO extends CommonDAO
{

	public List<Map<String, Object>> tempList(Map<String, Object> map)
	{
		return selectList("TempMgr.tempList", map);
	}

	public int saveTempMgrInsert(Map<String, Object> map)
	{
		return insert("TempMgr.saveTempMgrInsert", map);
	}

	public int saveTempMgrUpdate(Map<String, Object> map)
	{
		return update("TempMgr.saveTempMgrUpdate", map);
	}
	
	public int 	saveTempMgrAttachInsert(Map<String, Object> map)
	{
		return insert("TempMgr.saveTempMgrAttachInsert", map);
	}
	
	
	public Map<String, String> getTempMgr(Map<String, Object> map)
	{
		return selectOne("TempMgr.getTempMgr", map);
	}
	
	public List<Map<String, Object>> popUpTempMgrAttachList(Map<String, Object> map)
	{
		return selectList("TempMgr.popUpTempMgrAttachList", map);
	}
		
	public List<Map<String, Object>> selectFileInfo(Map<String, Object> map)
	{
		return selectList("TempMgr.selectFileInfo", map);
	}
	
	public int refAttachFileDelete(Map<String, Object> map)
	{
		return delete("TempMgr.refAttachFileDelete", map);
	}
	
	public List<Map<String, Object>> getAttachFileInfo(Map<String, Object> map)
	{
		return selectList("TempMgr.getAttachFileInfo", map);
	}

	public int tempMgrAttachDelete(Map<String, Object> map)
	{
		return delete("TempMgr.tempMgrAttachDelete", map);
	}
	
	public int tempMgrDelete(Map<String, Object> map)
	{
		return delete("TempMgr.tempMgrDelete", map);
	}
	
	public int popUpTempInfoExcelImportAction(Map<String, Object> map)
	{
		return insert("TempMgr.saveTempMgrInsert", map);
	}

}
