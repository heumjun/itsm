package com.web.infoSharing.noticeMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.infoSharing.noticeMgr.dao.NoticeMgrDAO;

@Service("infoSharing.noticeMgr.noticeMgrService")
public class NoticeMgrServiceImpl extends CommonServiceImpl implements NoticeMgrService
{

	@Resource(name = "infoSharing.noticeMgr.noticeMgrDAO")
	private NoticeMgrDAO noticeMgrDAO;

	/**
	 * 
	 * @메소드명	: noticeList
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		공지사항 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> noticeList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = noticeMgrDAO.noticeList(commandMap.getMap());

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

	/**
	 * 
	 * @메소드명	: saveNotice
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		공지사항 저장	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveNotice(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		if(commandMap.get("p_seq_id").equals("")) {
		
			// 등록
			// 공지사항 테이블 SEQ 증가
			commandMap.put(Constants.SEQ_ID, getSeqType(Constants.NOTICE_SEQ));
			int insertResult = noticeMgrDAO.saveNotice(commandMap.getMap());
			
			if (insertResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		} else {
			// 수정
			int updateResult = noticeMgrDAO.saveNoticeUpdate(commandMap.getMap());
			
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
	 * @메소드명	: getNotice
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		공지사항 상세보기	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, String> getNotice(CommandMap commandMap)
	{
		return noticeMgrDAO.getNotice(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명	: getMainNoticeList
	 * @날짜		: 2018. 6. 7.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		공지사항 메인 팝업 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getMainNoticeList(CommandMap commandMap)
	{
		return noticeMgrDAO.getMainNoticeList(commandMap.getMap());
	}

}
