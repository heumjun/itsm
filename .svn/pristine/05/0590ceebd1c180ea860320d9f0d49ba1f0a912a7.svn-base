package com.web.common.util;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;

/**
 * 
 * @파일명 : PageUtil.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		그리드에서 표현되어질 페이지정보를 취득하기위한 클레스
 *     </pre>
 */
public class PageUtil
{

	/**
	 * 
	 * @메소드명 : getPageCount
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		그리드의 페이지 사이즈와 리스트 총 사이즈를 이용해 라스트 페이지를 구한다.
	 *     </pre>
	 * 
	 * @param p_pageSize
	 * @param p_listRowCnt
	 * @return
	 */
	public static int getPageCount(Object p_pageSize, Object p_listRowCnt)
	{
		int pageSize = 99999;
		int listRowCnt = 1;

		if (p_pageSize != null)
		{
			pageSize = Integer.parseInt(p_pageSize.toString());
		}
		if (p_listRowCnt != null)
		{
			listRowCnt = Integer.parseInt(p_listRowCnt.toString());
		}
		int pageCount = 0;
		int remain;

		// 총 페이지 수를 구하기 위한 나머지 계산
		remain = listRowCnt % pageSize;
		if (remain == 0)
			pageCount = listRowCnt / pageSize;
		else
			pageCount = listRowCnt / pageSize + 1;

		return pageCount;
	}

	/**
	 * 
	 * @메소드명 : actionPageBefore
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		페이징 작업 전처리
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	public static CommandMap actionPageBefore(CommandMap commandMap)
	{
		// 페이징 처리 START
		int pageSize = Integer.parseInt(commandMap.get(Constants.FROM_GRID_PAGE_SIZE).toString());
		int curPageNo = Integer.parseInt(commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO).toString());

		// 페이지 전처리
		int p_page_start_no = (curPageNo - 1) * pageSize;
		int p_page_end_no = p_page_start_no + pageSize;

		commandMap.put("p_page_start_no", p_page_start_no);
		commandMap.put("p_page_end_no", p_page_end_no);

		return commandMap;
	}

	/**
	 * 
	 * @메소드명 : actionPageAfter
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		페이징 작업 후처리
	 *     </pre>
	 * 
	 * @param commandMap
	 * @param result
	 * @param list
	 * @return
	 */
	public static Map<String, Object> actionPageAfter(CommandMap commandMap, Map<String, Object> result,
			List<Map<String, Object>> list)
	{

		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);

		if (list.size() > 0)
		{
			// 리스트 최대 값을 구함.
			// 첫번째 행의 cnt를 받아옴.
			Map<String, Object> map = list.get(0);
			int listRowCnt = Integer.parseInt(map.get("cnt").toString());

			// 라스트 페이지를 구한다.
			Object lastPageCnt = "page>total";
			if (!"N".equals(commandMap.get("isPaging")))
			{
				lastPageCnt = getPageCount(pageSize, listRowCnt);
			}

			// 페이징에 필요한 값들 넣음
			result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
			result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
			result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
			result.put(Constants.GRID_RESULT_DATA, list);
		}
		return result;
	}

}
