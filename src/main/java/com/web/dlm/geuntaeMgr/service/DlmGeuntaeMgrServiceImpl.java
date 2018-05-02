package com.web.dlm.geuntaeMgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.dlm.geuntaeMgr.dao.DlmGeuntaeMgrDAO;

/**
 * 
 * @파일명		: ManageUserServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	사용자 관리	
 * </pre>
 */
@Service("dlm.geuntaeMgr.dlmGeuntaeMgrService")
public class DlmGeuntaeMgrServiceImpl extends CommonServiceImpl implements DlmGeuntaeMgrService
{

	@Resource(name = "dlm.geuntaeMgr.dlmGeuntaeMgrDAO")
	private DlmGeuntaeMgrDAO dlmGeuntaeMgrDAO;
	
	@Override
	public List<Map<String, Object>> monthViewList(CommandMap commandMap)
	{
		List<Map<String, Object>> resultList = dlmGeuntaeMgrDAO.monthViewList(commandMap.getMap());
		return resultList;
	}
	
	@Override
	public Map<String, Object> gtmBaseInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.gtmBaseInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
		}
		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}
	
	@Override
	public Map<String, Object> popUpGtmReqInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		
		List<String> typeList = new ArrayList<String>();
		
		if(commandMap.get("p_vac_type").equals("ger")) {
			typeList.add("VAC_BAN"); //in 조건에 넣을 정보
			typeList.add("VAC_YEAR");
		} else if(commandMap.get("p_vac_type").equals("spe")) {
			typeList.add("VAC_SPE");
		} else if(commandMap.get("p_vac_type").equals("rep")) {
			typeList.add("VAC_BON");
		} else if(commandMap.get("p_vac_type").equals("etc")) {
			typeList.add("VAC_ENT");
			typeList.add("VAC_EDU");
			typeList.add("VAC_ETC");
		}
		
		commandMap.put("typeList", typeList);
		
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.popUpGtmReqInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
		}
		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}
	
	@Override
	public Map<String, Object> popUpGtmBonusInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.popUpGtmBonusInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
		}
		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}
	
	@Override
	public Map<String, Object> gtmBonusInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.gtmBonusInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
		}
		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}
	
	@Override
	public List<Map<String, Object>> getUserSelectBoxList(CommandMap commandMap)
	{
		List<Map<String, Object>> resultList = dlmGeuntaeMgrDAO.getUserSelectBoxList(commandMap.getMap());
		return resultList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveGtmBonusInfo(CommandMap commandMap) throws Exception
	{
		// 제이슨 데이터를 List Map 형식으로 형변환하기 위한 타입참조
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
		
		// 그리드로부터 데이타리스트를 제이슨 형식으로 받아온다.
		String gridDataList = commandMap.get(Constants.FROM_GRID_DATA_LIST).toString();
		commandMap.remove(Constants.FROM_GRID_DATA_LIST);

		// List Map 형식으로 형변환
		List<Map<String, Object>> saveList = new ObjectMapper().readValue(gridDataList, typeRef);

		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		for (Map<String, Object> rowData : saveList)
		{
			// CommandMap에 저장되어있는 DB용 로그인 아이디, 맵퍼네임 등을 설정한다.
			rowData.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
			rowData.put(Constants.MAPPER_NAME, commandMap.get(Constants.MAPPER_NAME));
			
			// INSERT인경우 중복체크
			if (Constants.INSERT.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				rowData.put(Constants.SEQ_ID, getSeqType(Constants.DLM_BONUS_SEQ));
				result = gridDataInsert(rowData);
			}
			// UPDATE 인경우
			else if (Constants.UPDATE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = gridDataUpdate(rowData);
			}
			// DELETE 인경우
			else if (Constants.DELETE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = gridDataDelete(rowData);
			}
		}
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			return MessageUtil.getResultMessage(result);
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}
	
	@Override
	public Map<String, Object> gtmReqInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.gtmReqInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
		}
		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}

	@Override
	public Map<String, String> saveGtmReqInfo(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		//근태관리 신청정보 테이블 SEQ 증가
		commandMap.put(Constants.SEQ_ID, getSeqType(Constants.DLM_REQ_SEQ));
		int insertResult = dlmGeuntaeMgrDAO.saveGtmReqInfo(commandMap.getMap());
		
		if (insertResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}
		
		commandMap.put("approvalPhone", MessageUtil.getMessage("approval.phone"));
		commandMap.put("msg_body", commandMap.get("p_user_name") + "님이 " + commandMap.get("p_vac_name") + " 승인신청을 하였습니다.\nITSM시스템에 접속하여 승인 부탁드립니다.");
		int smsInsertResult = dlmGeuntaeMgrDAO.gtmReqSmsInsert(commandMap.getMap());
		if (smsInsertResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}
		
		commandMap.put(Constants.SEQ_ID, getSeqType(Constants.SMS_SEQ));
		commandMap.put("p_sms_type", "B");
		int smsInsertInfoResult = dlmGeuntaeMgrDAO.gtmReqSmsInsertInfo(commandMap.getMap());
		if (smsInsertInfoResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}
		
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			return MessageUtil.getResultMessage(result);
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}
	
}
