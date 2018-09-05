package com.web.dlm.geuntaeMgr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.FileScanner;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.common.util.SessionUtil;
import com.web.common.util.StringUtil;
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
		// holiday 추가
		List<Map<String, Object>> holidayList = dlmGeuntaeMgrDAO.getHoliday(commandMap.getMap());
		
		commandMap.put("idx", holidayList.get(holidayList.size()-1).get("EVENT_NUMBER"));
		
		List<Map<String, Object>> resultList = dlmGeuntaeMgrDAO.monthViewList(commandMap.getMap());
		
		holidayList.addAll(resultList);
		
		return holidayList;
		
	}
	
	@Override
	public Map<String, Object> gtmLessOneYearInfoList(CommandMap commandMap)
	{
		
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		//List<Map<String, Object>> listData = tempMgrDAO.referRoomList(commandMap.getMap());
		
		List<Map<String, Object>> listData = dlmGeuntaeMgrDAO.gtmLessOneYearInfoList(commandMap.getMap());
		
		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if (listData.size() > 0)
		{
			listRowCnt = listData.get(0).get("CNT").toString();
		}
		else
		{
			listRowCnt = 0;
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
	public List<Map<String, Object>> gtmLessOneYearUserList(CommandMap commandMap)
	{
		List<Map<String, Object>> resultList = dlmGeuntaeMgrDAO.gtmLessOneYearUserList(commandMap.getMap());
		return resultList;
	}
	
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveLessOneYearInfo(CommandMap commandMap) throws Exception
	{
		// 제이슨 데이터를 List Map 형식으로 형변환하기 위한 타입참조
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
		
		// 그리드로부터 데이타리스트를 제이슨 형식으로 받아온다.
		String gridDataList = commandMap.get(Constants.FROM_GRID_DATA_LIST).toString();
		commandMap.remove(Constants.FROM_GRID_DATA_LIST);

		// List Map 형식으로 형변환
		List<Map<String, Object>> saveList = new ObjectMapper().readValue(gridDataList, typeRef);
		
		System.out.println(saveList.size());
		System.out.println(saveList.get(0).toString());

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
				rowData.put("seqId", rowData.get(Constants.SEQ_ID));
				// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
				result = dlmGeuntaeMgrDAO.dataDuplicationCnt(rowData);
				
				
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.OYU_BASE_INFO_SEQ));
					result = dlmGeuntaeMgrDAO.lessOneYearInfoInsert(rowData);
				}
				else if (result.equals(Constants.RESULT_FAIL))
				{
					throw new CommonException(MessageUtil.getMessage("common.default.duplication"), "");
				}
				else
				{
					throw new CommonException(result);
				}
			}
			// UPDATE 인경우
			else if (Constants.UPDATE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = dlmGeuntaeMgrDAO.lessOneYearInfoUpdate(rowData);
			}
			// DELETE 인경우
			else if (Constants.DELETE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = dlmGeuntaeMgrDAO.lessOneYearInfoDelete(rowData);
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
				
				rowData.put(Constants.MSG_BODY, rowData.get("user_name") + "님 대체휴가 " + rowData.get("bonus_day") + "일 부여 되었습니다.\n사유 : " + rowData.get("bonus_occur_reason"));
				
				// system.properties 에서 SMS설정이 Y가 되어있어야 발송
				if(StringUtil.nullString(MessageUtil.getMessage("common.smsSendSwitch")).equals("Y")) 
				{
					// SMS 발송
					int smsInsertResult = dlmGeuntaeMgrDAO.gtmBonusSmsInsert(rowData);
					if (smsInsertResult == 0)
					{
						result = Constants.RESULT_FAIL;
					}
					else
					{
						result = Constants.RESULT_SUCCESS;
					}
					
					// SMS 발송 이력
					rowData.put(Constants.SMS_SEQ, getSeqType(Constants.SMS_SEQ));
	
					// 근태관리 타입 : B
					rowData.put(Constants.SMS_TYPE, "B");
					
					int smsInsertInfoResult = dlmGeuntaeMgrDAO.gtmBonusSmsInsertInfo(rowData);
					if (smsInsertInfoResult == 0)
					{
						result = Constants.RESULT_FAIL;
					}
					else
					{
						result = Constants.RESULT_SUCCESS;
					}
				}
				
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
				
				// system.properties 에서 SMS설정이 Y가 되어있어야 발송
				if(StringUtil.nullString(MessageUtil.getMessage("common.smsSendSwitch")).equals("Y")) 
				{
					// SMS 발송
					rowData.put("msg_body", rowData.get("user_name") + "님 대체휴가 " + rowData.get("bonus_day") + "일 부여 취소되었습니다.");
					
					int smsInsertResult = dlmGeuntaeMgrDAO.gtmBonusSmsInsert(rowData);
					if (smsInsertResult == 0)
					{
						result = Constants.RESULT_FAIL;
					}
					else
					{
						result = Constants.RESULT_SUCCESS;
					}
					
					// SMS 발송 이력
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.SMS_SEQ));
	
					// 근태관리 타입 : B
					rowData.put("p_sms_type", "B");
					
					int smsInsertInfoResult = dlmGeuntaeMgrDAO.gtmBonusSmsInsertInfo(rowData);
					if (smsInsertInfoResult == 0)
					{
						result = Constants.RESULT_FAIL;
					}
					else
					{
						result = Constants.RESULT_SUCCESS;
					}
				} // system.properties 에서 SMS설정이 Y가 되어있어야 발송 END
				
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
	public String getBonusDay(CommandMap commandMap)
	{
		commandMap.put("p_icui_seq_id", SessionUtil.getUserSeqId());
		return dlmGeuntaeMgrDAO.getBonusDay(commandMap.getMap());
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
		
		// 받는 사람
		commandMap.put(Constants.SEND_PHONE, MessageUtil.getMessage("approval.phone"));
		// 메세지 내용
		commandMap.put(Constants.MSG_BODY, commandMap.get("p_user_name") + "님이 " + commandMap.get("p_vac_name") + "을(를) 신청하였습니다.\nITMS 시스템에 접속하여 승인 또는 반려 부탁드립니다.");
		
		// SMS 발송 이력
		commandMap.put(Constants.SMS_SEQ, getSeqType(Constants.SMS_SEQ));
		// 근태관리 타입 : B
		commandMap.put(Constants.SMS_TYPE, "B");
		// 수신자에 관리자를 넣어준다.
		commandMap.put("p_icui_seq_id", dlmGeuntaeMgrDAO.gtmReqSmsAdminUser(commandMap.getMap()));
		
		// SMS 발송
		Map<String, String> sendResult = sendSms(commandMap);
		if(sendResult.get(Constants.RESULT_KEY).equals(Constants.RESULT_SUCCESS)) {
			result = Constants.RESULT_SUCCESS;
		} else {
			result = Constants.RESULT_FAIL;
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	@Override
	public Map<String, String> saveGtmReqApproveProcess(CommandMap commandMap) throws CommonException
	{
		
		String result = Constants.RESULT_FAIL;
		
		try {

			String[] p_seq_id = commandMap.get("p_seq_id").toString().split(",");
			
			for (String seq_id : p_seq_id) {
				
				Map<String, Object> pkgParam = new HashMap<String, Object>();

				pkgParam.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
				pkgParam.put("p_seq_id", seq_id);
				pkgParam.put("p_status", commandMap.get("status"));
				pkgParam.put("approvalAdmin", commandMap.get("approvalAdmin"));
				
				int updateResult = dlmGeuntaeMgrDAO.saveGtmReqApproveProcess(pkgParam);
				if (updateResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
				
				List<Map<String, Object>> userInfo = dlmGeuntaeMgrDAO.getReqUserInfo(pkgParam);
				
				// 받는 사람
				String status = (String) commandMap.get("status");
				
				// 승인취소요청 시 관리자에게 SMS 발신
				if(status.equals("C")) 
				{
					// 수신자에 관리자를 넣어준다.
					commandMap.put(Constants.SEND_PHONE, commandMap.get(Constants.APPROVAL_PHONE));
					commandMap.put("p_icui_seq_id", dlmGeuntaeMgrDAO.gtmReqSmsAdminUser(commandMap.getMap()));
				}
				else 
				{
					commandMap.put(Constants.SEND_PHONE, userInfo.get(0).get("PHONE"));
					commandMap.put("p_icui_seq_id", userInfo.get(0).get("ICUI_SEQ_ID"));
				}
				
				// 메세지 내용
				commandMap.put(Constants.MSG_BODY, userInfo.get(0).get("USER_NAME") + "님이 " + userInfo.get(0).get("REQ_DATE") + "에 신청하신 " + userInfo.get(0).get("VAC_TYPE_NAME") + "가(이) " + userInfo.get(0).get("STATUS_NAME") + " 되었습니다.");
				
				// SMS 발송 이력
				commandMap.put(Constants.SMS_SEQ, getSeqType(Constants.SMS_SEQ));
				// 근태관리 타입 : B
				commandMap.put(Constants.SMS_TYPE, "B");
				
				// SMS 발송
				Map<String, String> sendResult = sendSms(commandMap);
				if(sendResult.get(Constants.RESULT_KEY).equals(Constants.RESULT_SUCCESS)) {
					result = Constants.RESULT_SUCCESS;
				} else {
					result = Constants.RESULT_FAIL;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			commandMap.put(Constants.RESULT_MASAGE_KEY, e.getLocalizedMessage());
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

	/**
	 * 
	 * @메소드명	: gtmReqDeleteProcess
	 * @날짜		: 2018. 6. 15.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		근태신청 취소버튼 - 신청상태일때 취소버튼 클릭시 바로 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws CommonException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	@Override
	public Map<String, String> gtmReqDeleteProcess(CommandMap commandMap) throws CommonException
	{
		String result = Constants.RESULT_FAIL;
		
		try {

			String[] p_seq_id = commandMap.get("p_seq_id").toString().split(",");
			
			for (String seq_id : p_seq_id) {
				
				Map<String, Object> pkgParam = new HashMap<String, Object>();

				pkgParam.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
				pkgParam.put("p_seq_id", seq_id);
				pkgParam.put("p_status", commandMap.get("status"));
				pkgParam.put("approvalAdmin", commandMap.get("approvalAdmin"));
				
				int deleteResult = dlmGeuntaeMgrDAO.gtmReqDeleteProcess(pkgParam);
				if (deleteResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			commandMap.put(Constants.RESULT_MASAGE_KEY, e.getLocalizedMessage());
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
	public List<Map<String, Object>> getDatePickerHoliday(CommandMap commandMap)
	{
		return dlmGeuntaeMgrDAO.getDatePickerHoliday(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명	: popUpGtmReqInfoExcelImportAction
	 * @날짜		: 2018. 7. 10.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		근태조회 엑셀 업로드 DB Insert	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws CommonException 
	 * @throws IOException 
	 */
	@Override
	public void popUpGtmReqInfoExcelImportAction(CommandMap commandMap, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
		
		String result = Constants.RESULT_FAIL;
		
		try {

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = FileScanner.excelToList((MultipartFile) commandMap.get("file"), 1, true, 0);
			
			for(Map<String, Object> str : list ) {
				
				Map<String, Object> pkgParam = new HashMap<String, Object>();

				pkgParam.put(Constants.SEQ_ID, getSeqType(Constants.DLM_BASE_SEQ));
				pkgParam.put("icui_seq_id", str.get("column0"));
				pkgParam.put("year", str.get("column1"));
				pkgParam.put("work_start_date", str.get("column2"));
				pkgParam.put("work_end_date", str.get("column3"));
				pkgParam.put("gubun", str.get("column4"));
				pkgParam.put("gen_using_poss_day", str.get("column5"));
				pkgParam.put("spe_using_poss_day", str.get("column6"));
				pkgParam.put("pre_year_using_day", str.get("column7"));
				pkgParam.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
				
				int smsInsertInfoResult = dlmGeuntaeMgrDAO.popUpGtmReqInfoExcelImportAction(pkgParam);
				if (smsInsertInfoResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('시스템 오류입니다.\n전산담당자에게 문의해주세요.');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('처리가 완료되었습니다.');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('업로드 실패');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		
	}

}
