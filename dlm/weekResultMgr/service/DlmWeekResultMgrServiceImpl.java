package com.web.dlm.weekResultMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.dlm.weekResultMgr.dao.DlmWeekResultMgrDAO;

/**
 * 
 * @파일명		: DlmWeekResultMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 9. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		주간실적 ServiceImpl	
 * </pre>
 */
@Service("dlm.weekResultMgr.dlmWeekResultMgrService")
public class DlmWeekResultMgrServiceImpl extends CommonServiceImpl implements DlmWeekResultMgrService
{

	@Resource(name = "dlm.weekResultMgr.dlmWeekResultMgrDAO")
	private DlmWeekResultMgrDAO dlmWeekResultMgrDAO;

	/**
	 * 
	 * @메소드명	: weekManageList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적관리 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> weekManageList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmWeekResultMgrDAO.weekManageList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if(listData.size() > 0) {
			listRowCnt = listData.get(0).get("CNT").toString();
		} 
		else 
		{
			listRowCnt = 0;
		}
		
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
	
	/**
	 * 
	 * @메소드명	: saveWeekManage
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적관리 저장	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> saveWeekManage(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;
		
		if(commandMap.get("seq_id").equals("")) 
		{
			// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
			result = getDuplicationCnt(commandMap.getMap());
			if (result.equals(Constants.RESULT_SUCCESS))
			{
				//근태관리 신청정보 테이블 SEQ 증가
				commandMap.put(Constants.SEQ_ID, getSeqType(Constants.DLM_WEEK_SEQ));
				int insertResult = dlmWeekResultMgrDAO.saveWeekManageInsert(commandMap.getMap());
				
				if (insertResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
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
		else 
		{
			int updateResult = dlmWeekResultMgrDAO.saveWeekManageUpdate(commandMap.getMap());
			
			if (updateResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
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
	
	/**
	 * 
	 * @메소드명	: weekManageOnClick
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적관리 상세화면	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> weekManageOnClick(CommandMap commandMap)
	{
		return dlmWeekResultMgrDAO.weekManageOnClick(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명	: weekManageOnClick
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적관리 신규등록화면	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> weekManageOnClick2(CommandMap commandMap)
	{
		return dlmWeekResultMgrDAO.weekManageOnClick2(commandMap.getMap());
	}
	
	/**
	 * 
	 * @메소드명	: weekInfoList
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적현황 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> weekInfoList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = dlmWeekResultMgrDAO.weekInfoList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if(listData.size() > 0) {
			listRowCnt = listData.get(0).get("CNT").toString();
		} 
		else 
		{
			listRowCnt = 0;
		}
		
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

	/**
	 * 
	 * @메소드명	: popUpWeekInfoDetail
	 * @날짜		: 2018. 5. 9.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		주간실적현황 상세화면	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> popUpWeekInfoDetail(CommandMap commandMap)
	{
		return dlmWeekResultMgrDAO.weekInfoDetail(commandMap.getMap());
	}

}
