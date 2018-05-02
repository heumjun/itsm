package com.web.admin.indiAuthMgr.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.admin.indiAuthMgr.dao.IndiAuthMgrDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;

/**
 * 
 * @파일명		: IndiAuthMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 12. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		
 * </pre>
 */
@Service("admin.indiAuthMgr.indiAuthMgrService")
public class IndiAuthMgrServiceImpl extends CommonServiceImpl implements IndiAuthMgrService
{

	@Resource(name = "admin.indiAuthMgr.indiAuthMgrDAO")
	private IndiAuthMgrDAO indiAuthMgrDAO;
	
	/**
	 * @메소드명	: indiAuthMgrList
	 * @날짜		: 2018. 4. 13.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *			
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> indiAuthMgrList(CommandMap commandMap) throws Exception
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = indiAuthMgrDAO.indiAuthMgrList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			//listRowCnt = getGridListSize(commandMap.getMap());
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
	 * @메소드명	: indiAuthMgrSave
	 * @날짜		: 2018. 4. 13.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *			
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> indiAuthMgrSave(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;
		
		String[] userAuthSeqList = commandMap.get("p_userAuthSeq").toString().split(",");
		
		for(String userAuthSeq : userAuthSeqList) {
			Map<String, Object> pkgParam = new HashMap<String, Object>();
			
			pkgParam.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
			pkgParam.put("authValue", commandMap.get("p_authValue"));
			pkgParam.put("userAuthSeq", userAuthSeq);
			
			int updateResult = indiAuthMgrDAO.indiAuthMgrSave(pkgParam);
			
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
	

}
